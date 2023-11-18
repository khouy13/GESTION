
module com.example.ecommerceproject {

    requires javafx.controls;

    requires javafx.fxml;

    requires javafx.base;

    requires org.apache.commons.io;

    requires java.sql.rowset;

    requires itextpdf;

    requires java.datatransfer;

    requires java.desktop;

    requires jpedal.gpl;

    requires javafx.swing;

    requires json;

    opens com.example.ecommerceproject to javafx.fxml;

    exports com.example.ecommerceproject;

}