package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PotionsEmporiumApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage janelaPrincipal) throws IOException {
        FXMLLoader carregarFXML = new FXMLLoader(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-inicial.fxml"));
        Scene tela = new Scene(carregarFXML.load(), 1280, 700);
        janelaPrincipal.setTitle("Potions Emporium");
        janelaPrincipal.setScene(tela);
        janelaPrincipal.setResizable(false);
        janelaPrincipal.show();
    }

}
