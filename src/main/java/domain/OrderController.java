package domain;

import exceptions.EntityDoesntExistException;
import exceptions.OrderStatusException;
import gui.view.OrderDTO;
import gui.view.OrderTrackingMailDTO;
import gui.view.ProductDTO;
import jakarta.persistence.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.CarrierDao;
import persistence.OrderDao;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Emailv31;
import java.nio.file.Path;


public class OrderController {


    private final OrderDao orderDao;
    private final CarrierDao carrierDao;
    private ObservableList<OrderDTO> orderList = FXCollections.emptyObservableList();

    public OrderController(OrderDao orderDao, CarrierDao carrierDao) {
        this.orderDao = orderDao;
        this.carrierDao = carrierDao;
    }

    public ObservableList<OrderDTO> getOrderList(int userId, boolean postedOnly) {
        if (postedOnly)
            this.orderList = FXCollections.observableList(orderDao.getAllForUserPosted(userId).stream().map(OrderDTO::new).collect(Collectors.toList()));
        else
            this.orderList = FXCollections.observableList(orderDao.getAllForUser(userId).stream().map(OrderDTO::new).collect(Collectors.toList()));
        return this.orderList;
    }

    public ObservableList<OrderDTO> getOrderByIdView(int id) {
        return FXCollections.observableArrayList(new OrderDTO(orderDao.get(id)));
    }

    public Order getOrderById(int orderId) {
        return orderDao.get(orderId);
    }

    public Supplier getCustomerForOrder(int orderId) {
        return orderDao.getCustomerForOrder(orderId);
    }
    
    public void getPrice(int orderId) {
        List<OrderLine> list = orderDao.getOrderLinesForOrder(orderId);
        list.stream().forEach(el -> System.out.println(el.getOriginalAcquisitionPrice()));
    }
    
    public ObservableList<ProductDTO> getProductsList(int orderId) {
        List<OrderLine> list = orderDao.getOrderLinesForOrder(orderId);
        return FXCollections.observableArrayList(list.stream().map(el -> new ProductDTO(el.getProduct(), el.getCount())).toList());
    }

    public void processOrder(int orderId, String carrierName, int supplierId) throws EntityNotFoundException, OrderStatusException, EntityDoesntExistException {
        Order order = orderDao.get(orderId);
        if (order == null) throw new EntityDoesntExistException("There is no order for given orderId!");
        Carrier carrier = carrierDao.getForSupplier(carrierName, supplierId);
        if (carrier == null)
            throw new EntityDoesntExistException("There is no Carrier for given carrierName and supplierId!");
        if (!order.getStatus().equals(Status.POSTED))
            throw new OrderStatusException("Order must have status POSTED in order to get processed!");
        
        order.setCarrier(carrier);
        order.setStatus(Status.PROCESSED);
        order.addNotification(new Notification(order));
        order.generateTrackingCode();
        
        
        orderDao.update(order);
        orderList.set(getIndex(order.getOrderId()), new OrderDTO(order));
        
        OrderTrackingMailDTO orderTrackingMailDTO = orderDao.getUserDataForProcessedMail(orderId);
        sendMail(orderTrackingMailDTO);
    }

    private int getIndex(int orderId) {
        List<OrderDTO> correspondingDTOs = orderList.stream().filter(dto -> dto.getOrderId() == orderId).toList();
        if (correspondingDTOs.isEmpty())
            throw new IllegalArgumentException("There is no OrderDTO matching this orderId!");
        if (correspondingDTOs.size() > 1)
            throw new RuntimeException("There were multiple OrderDTO found matching this orderId!");
        return orderList.indexOf(correspondingDTOs.get(0));
    }
    
    private boolean sendMail(OrderTrackingMailDTO orderTrackingMail) {
    	String email = orderTrackingMail.email();
    	String trackingCode = orderTrackingMail.trackingCode();
    	String verificationCode = orderTrackingMail.verificationCode();
    	
        MailjetClient client = new MailjetClient("1a4d488a3c09949ad2389515fa70481b", "5fa37c3c46b4b63dc0637536c4c81c0a");
        String htmlContent = getEmailHtmlFromFile("src/main/resources/email/emailLayout.html");
		if(htmlContent.isBlank() || htmlContent == null) {
			return false;
		}
		
		htmlContent = htmlContent.replace("{{TRACKING_CODE}}", trackingCode).replace("{{VERIFICATION_CODE}}", verificationCode);
		
        MailjetRequest emailRequest = new MailjetRequest(Emailv31.resource)
                .property(Email.FROMEMAIL, "delawareTrackAndTrace@gmail.com")
                .property(Email.FROMNAME, "Delaware")
                .property(Email.SUBJECT, "Track&Trace details")
                .property(Email.HTMLPART, htmlContent)
                .property(Email.RECIPIENTS, new JSONArray().put(new JSONObject().put("Email", email)));

        try {
            client.post(emailRequest);
            return true;
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            return false;
        }
    }

    private String getEmailHtmlFromFile(String filePath) {
		Path path = Paths.get(filePath);
		try {
			return Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

}