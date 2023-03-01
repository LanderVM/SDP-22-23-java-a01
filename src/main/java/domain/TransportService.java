package domain;

public enum TransportService {
    BPOST,
    POSTNL;
    
    public static  TransportService giveTransportService (String ts) {
    	if (ts.equals("BPOST")) {
    		return BPOST;
    	} else if (ts.equals("POSTNL")) {
    		return POSTNL;
    	} else {
    		throw new IllegalArgumentException("Given transport service doesn't exist!");
    	}
    }
}
