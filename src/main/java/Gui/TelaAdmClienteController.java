package Gui;

import Arquivos.ArquivoCliente;
import Exceptions.DadosInvalidosException;
import Negocio.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class TelaAdmClienteController {

    @FXML
    private Button voltarButton;
    @FXML
    private Button removerButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button okButton;
    @FXML
    private ListView<String> listaClientes;
    @FXML
    private TextField campoId;
    @FXML
    private TextField campoAtualizar;

    private ArquivoCliente arquivoCliente;
    private List<Cliente> clientes;

    @FXML
    private void initialize() {
        arquivoCliente = new ArquivoCliente("clientes.csv");
        try {
            carregarClientesNaLista();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Erro", "Não foi possível carregar a lista de clientes.");
        }
    }

    private void carregarClientesNaLista() throws IOException, DadosInvalidosException {
        clientes = arquivoCliente.carregarClientes();
        listaClientes.getItems().clear();
        for (Cliente cliente : clientes) {
            listaClientes.getItems().add(cliente.getNome());
        }
    }

    @FXML
    private void removerCliente(ActionEvent evento) {
        String clienteSelecionado = listaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Cliente clienteParaRemover = null;
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(clienteSelecionado)) {
                    clienteParaRemover = cliente;
                    break;
                }
            }
            if (clienteParaRemover != null) {
                clientes.remove(clienteParaRemover);
                listaClientes.getItems().remove(clienteSelecionado);
                arquivoCliente.removerCliente(clienteParaRemover.getId());
                try {
                    arquivoCliente.salvarClientes();
                } catch (IOException e) {
                    showAlert(AlertType.ERROR, "Erro", "Não foi possível salvar as alterações.");
                }
                showAlert(AlertType.INFORMATION, "Cliente Removido", "O cliente foi removido com sucesso!");
            }
        } else {
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um cliente para remover.");
        }
    }

    @FXML
    private void atualizarCliente(ActionEvent evento) throws IOException, DadosInvalidosException {
        String novoNome = campoAtualizar.getText();
        String clienteSelecionado = listaClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null && novoNome != null && !novoNome.trim().isEmpty()) {
            Cliente clienteParaAtualizar = null;
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(clienteSelecionado)) {
                    clienteParaAtualizar = cliente;
                    break;
                }
            }
            if (clienteParaAtualizar != null) {
                clienteParaAtualizar.setNome(novoNome);
                try {
                    arquivoCliente.salvarClientes();
                } catch (IOException e) {
                    showAlert(AlertType.ERROR, "Erro", "Não foi possível salvar as alterações.");
                }
                showAlert(AlertType.INFORMATION, "Cliente Atualizado", "O cliente foi atualizado com sucesso!");
            }
            campoAtualizar.clear();
            carregarClientesNaLista();
        } else {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, selecione um cliente e insira o novo nome.");
        }
    }

    @FXML
    private void confirmarId(ActionEvent evento) {
        String id = campoId.getText();
        if (id != null && !id.trim().isEmpty()) {
            showAlert(AlertType.INFORMATION, "ID Confirmado", "O ID foi confirmado com sucesso!");
        } else {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira um ID válido.");
        }
    }

    @FXML
    private void voltarPaginaInicial(ActionEvent evento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-adm-principal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível retornar à página inicial.");
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
