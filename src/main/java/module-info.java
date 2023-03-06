module SDP2_Java_a01 {

    exports domain;
    exports application;
    exports exceptions;
    exports util;
    exports persistence;
    exports gui.view;

    requires java.sql;
    requires java.instrument;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires jakarta.persistence;
    

    opens domain;
    opens application to javafx.fxml, javafx.graphics;
    opens gui to javafx.fxml, javafx.graphics;
    opens gui.controller to javafx.fxml, javafx.graphics;
}
