package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

public class TelaPrincipalClienteController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button botaoFechar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Button botaoCadastro;

    @FXML
    private Button botaoAlohomora;

    @FXML
    private ImageView imagemChave;

    @FXML
    private Pane painelPrincipal;

    // Lógica de arquivo comentada
    // private Map<String, String> usuarios = new HashMap<>();

    @FXML
    public void encerrarPrograma(ActionEvent evento) {
        Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        janela.close();
    }

    @FXML
    public void irParaCadastro(ActionEvent evento) {
        try {
            Parent telaCadastro = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-cadastro.fxml"));
            Scene cenaCadastro = new Scene(telaCadastro);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaCadastro);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void voltarParaTelaPrincipal(ActionEvent evento) {
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

    // Método de login com valores fixos para teste
    @FXML
    public void realizarLogin(ActionEvent evento) {
        // Usuário e senha fixos para o teste
        String usuarioTeste = "cliente";
        String senhaTeste = "12345";

        // Captura as entradas do usuário
        String login = loginField.getText();
        String senha = senhaField.getText();

        // Verifica se o login e senha estão corretos
        if (login.equals(usuarioTeste) && senha.equals(senhaTeste)) {
            try {
                // Carrega a tela de produtos ao realizar o login com sucesso
                Parent telaProdutos = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-produtos.fxml"));
                Scene cenaProdutos = new Scene(telaProdutos);
                Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                janela.setScene(cenaProdutos);
                janela.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Exibe alerta de erro caso o login ou senha estejam incorretos
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Login");
            alerta.setHeaderText("Login ou senha incorretos.");
            alerta.setContentText("Por favor, tente novamente.");
            alerta.showAndWait();
        }
    }

    // Lógica de login com arquivo comentada
    /*
    @FXML
    public void initialize() {
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        String caminhoArquivo = "caminho/para/usuarios.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length == 2) {
                    String usuario = partes[0].trim();
                    String senha = partes[1].trim();
                    usuarios.put(usuario, senha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void realizarLogin(ActionEvent evento) {
        String login = loginField.getText();
        String senha = senhaField.getText();

        if (usuarios.containsKey(login) && usuarios.get(login).equals(senha)) {
            try {
                Parent telaProdutos = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-produtos.fxml"));
                Scene cenaProdutos = new Scene(telaProdutos);
                Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                janela.setScene(cenaProdutos);
                janela.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Login");
            alerta.setHeaderText("Login ou senha incorretos.");
            alerta.setContentText("Por favor, tente novamente.");
            alerta.showAndWait();
        }
    }
    */
}
