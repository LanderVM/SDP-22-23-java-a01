module SDP2_Java_a01 {

    exports domain;
    exports application;
    exports exceptions;
    exports util;
    exports gui.view;
    exports persistence;
    exports persistence.impl;

    requires java.sql;
    requires java.instrument;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires jakarta.persistence;
	requires java.desktop;


    opens domain;
    opens application to javafx.fxml, javafx.graphics;
    opens gui.view to javafx.fxml, javafx.graphics;
    opens gui.controller to javafx.fxml, javafx.graphics;
    opens util to javafx.fxml, javafx.graphics;
}
