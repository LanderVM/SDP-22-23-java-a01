package main;

import domain.DomainController;

public class startUp {

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