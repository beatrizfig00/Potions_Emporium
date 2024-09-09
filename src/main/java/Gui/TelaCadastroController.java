package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cidadeField;
    @FXML
    private TextField ruaField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField corujaField;
    @FXML
    private TextField flooPowderField;
    @FXML
    private TextField senhaField;
    @FXML
    private Button botaoFechar;

    @FXML
    public void encerrarPrograma(ActionEvent evento) {
        Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        janela.close();
    }

    @FXML
    public void salvarCadastro(ActionEvent evento) {
        String nome = nomeField.getText();
        String cidade = cidadeField.getText();
        String rua = ruaField.getText();
        String numero = numeroField.getText();
        String coruja = corujaField.getText();
        String flooPowder = flooPowderField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || cidade.isEmpty() || rua.isEmpty() || numero.isEmpty() || coruja.isEmpty() || flooPowder.isEmpty() || senha.isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro de Cadastro");
            alerta.setHeaderText("Campos obrigatórios não preenchidos.");
            alerta.setContentText("Por favor, preencha todos os campos.");
            alerta.showAndWait();
            return;
        }
        // Substitua pelo caminho real do seu arquivo
        String caminhoArquivo = "caminho/para/usuarios.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write(String.format("%s:%s%n", coruja, senha));
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Cadastro realizado");
            alerta.setHeaderText(null);
            alerta.setContentText("Cadastro realizado com sucesso.");
            alerta.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro de Cadastro");
            alerta.setHeaderText("Não foi possível salvar o cadastro.");
            alerta.setContentText("Por favor, tente novamente.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void voltar(ActionEvent evento) {
        try {
            Parent telaCliente = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-principal-cliente.fxml"));
            Scene cenaCliente = new Scene(telaCliente);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaCliente);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
