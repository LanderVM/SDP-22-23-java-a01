package domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	
	private SimpleIntegerProperty idForTable;
	private SimpleStringProperty companyForTable;
	private SimpleStringProperty dateForTable;
	
	private String company; 
	private String customerName; 
	private int id; 
	private Date date; 
	private String address; 
	private double total;
    private List<Product> products; 
    private Status status; 
    private TransportService transportService; 
    private Packaging packaging;
    
	public Order( String company, String customerName, int id, Date date,String address, double total, List<Product> products, Status status, 
			TransportService transportService,Packaging packaging) {
		super();
		this.company = company;
		this.customerName = customerName;
		this.id = id;
		this.date = date;
		this.address = address;
		this.total = total;
		this.products = products;
		this.status = status;
		this.transportService = transportService;
		this.packaging = packaging;
		
		idForTable = new SimpleIntegerProperty();
		idForTable.set(id);
		companyForTable = new  SimpleStringProperty();
		companyForTable.set(company);
		dateForTable = new SimpleStringProperty();
		dateForTable.set(date.toString());
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TransportService getTransportService() {
		return transportService;
	}

	public void setTransportService(TransportService transportService) {
		this.transportService = transportService;
	}

	public Packaging getPackaging() {
		return packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return id == other.id;
	}
	
	public IntegerProperty idForTableProperty() {
		return this.idForTable;
	}
	
	public StringProperty companyForTableProperty() {
		return this.companyForTable;
	}
	public StringProperty dateForTableProperty() {
		return this.dateForTable;
	}
	
}