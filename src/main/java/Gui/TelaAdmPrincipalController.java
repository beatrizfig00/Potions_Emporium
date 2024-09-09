package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class TelaAdmPrincipalController {

    @FXML
    private void abrirEstoque(ActionEvent evento) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-estoque.fxml"));
        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirRelatorio(ActionEvent evento) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-relatorio.fxml"));
        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirClientes(ActionEvent evento) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-adm-cliente.fxml"));
        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void voltarPaginaInicial(ActionEvent evento) throws IOException {
        try {
            Parent telaPrincipal = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-inicial.fxml"));
            Scene cenaPrincipal = new Scene(telaPrincipal);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaPrincipal);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}