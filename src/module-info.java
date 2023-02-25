module SDP2_Java_a01 {

    exports domain;
    exports repository;
    exports gui;
    exports application;
    exports exceptions;
    exports util;

    requires java.persistence;
    requires java.sql;
    requires java.instrument;
    requires org.junit.jupiter.api;
    requires javafx.controls;
    requires javafx.fxml;

    opens domain;
    opens application to javafx.fxml, javafx.graphics;
    opens gui to javafx.fxml, javafx.graphics;
}
