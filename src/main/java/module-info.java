module com.potionsemporium.potions_emporium2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.potionsemporium.potions_emporium2 to javafx.fxml;
    exports com.potionsemporium.potions_emporium2;
}