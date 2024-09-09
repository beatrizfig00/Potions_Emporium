package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class TelaAdmController {

    @FXML
    private Button entrarButton;
    @FXML
    private Button voltarButton;
    @FXML
    private PasswordField senhaField;
    @FXML
    private Button botaoVoltar;

    private static final String SENHA_CORRETA = "admin123";

    @FXML
    public void entrar(ActionEvent evento) {
        String senha = senhaField.getText();
        if (SENHA_CORRETA.equals(senha)) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Login Bem-Sucedido");
            alerta.setHeaderText(null);
            alerta.setContentText("Login realizado com sucesso!");
            alerta.showAndWait();

            try {
                FXMLLoader carregarFXML = new FXMLLoader(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-adm-principal.fxml"));
                Parent root = carregarFXML.load();
                Stage janelaAtual  = (Stage) entrarButton.getScene().getWindow();

                Stage novaJanela = new Stage();
                novaJanela.setScene(new Scene(root));
                novaJanela.setResizable(false);

                novaJanela.show();
                janelaAtual.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText(null);
                alertaErro.setContentText("Não foi possível carregar a tela principal.");
                alertaErro.showAndWait();
            }

        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Login");
            alerta.setHeaderText(null);
            alerta.setContentText("Senha incorreta. Tente novamente.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void voltarParaTelaPrincipal(ActionEvent evento) {
        try {
            Parent telaPrincipal = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-inicial.fxml"));
            Stage janelaAtual = (Stage) ((Node) evento.getSource()).getScene().getWindow();

            Stage novaJanela = new Stage();
            novaJanela.setScene(new Scene(telaPrincipal));
            novaJanela.setResizable(false);

            novaJanela.show();
            janelaAtual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
