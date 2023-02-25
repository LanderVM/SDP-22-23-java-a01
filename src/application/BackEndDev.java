package application;

import domain.DomainController;

public class BackEndDev {

    public static void main(String[] args) {
        DomainController domainController = new DomainController();
        // seedDatabase();
        domainController.generateListOrders().forEach(System.out::println);
    }

    private static void seedDatabase() {
        // TODO, maar eerst ERD bijwerken
        // in persistence.xml dan ook "none" vervangen met "drop-and-create"
    }
}