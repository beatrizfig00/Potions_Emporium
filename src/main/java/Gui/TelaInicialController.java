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

public class TelaInicialController {

    @FXML
    private Button botaoFechar;

    @FXML
    private Button botaoAdm;

    @FXML
    private Button botaoCliente;

    @FXML
    public void encerrarPrograma(ActionEvent evento) {
        Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        janela.close();
    }

    @FXML
    public void irParaAdm(ActionEvent evento) {
        try {
            Parent telaAdm = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-adm.fxml"));
            Scene cenaAdm = new Scene(telaAdm);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaAdm);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaCliente(ActionEvent event) {
        try {
            Parent telaCliente = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-principal-cliente.fxml"));
            Scene cenaCliente = new Scene(telaCliente);
            Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janela.setScene(cenaCliente);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
