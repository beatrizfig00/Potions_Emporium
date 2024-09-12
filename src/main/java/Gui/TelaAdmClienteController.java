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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaAdmClienteController {

    @FXML
    private Button voltarButton;
    @FXML
    private Button removerButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private ListView<String> listaClientes;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCoruja;
    @FXML
    private TextField campoFlooPowder;

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
        arquivoCliente.carregarClientes();
        clientes = arquivoCliente.getAllClientes();

        listaClientes.getItems().clear();

        for (Cliente cliente : clientes) {
            String clienteInfo = String.format("ID: %d | Nome: %s | Coruja: %s | Floo Powder: %s",
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCoruja(),
                    cliente.getFlooPowder());
            listaClientes.getItems().add(clienteInfo);
        }
    }

    @FXML
    private void removerCliente(ActionEvent evento) {
        String clienteSelecionado = listaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Cliente clienteParaRemover = null;
            for (Cliente cliente : clientes) {
                if (verificarInformacoesCliente(cliente, clienteSelecionado)) {
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
        String novoNome = campoNome.getText();
        String novaCoruja = campoCoruja.getText();
        String novoFlooPowder = campoFlooPowder.getText();
        String clienteSelecionado = listaClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null && !novoNome.trim().isEmpty() && !novaCoruja.trim().isEmpty() && !novoFlooPowder.trim().isEmpty()) {
            Cliente clienteParaAtualizar = null;
            for (Cliente cliente : clientes) {
                if (verificarInformacoesCliente(cliente, clienteSelecionado)) {
                    clienteParaAtualizar = cliente;
                    break;
                }
            }
            if (clienteParaAtualizar != null) {
                clienteParaAtualizar.setNome(novoNome);
                clienteParaAtualizar.setCoruja(novaCoruja);
                clienteParaAtualizar.setFlooPowder(novoFlooPowder);
                arquivoCliente.salvarClientes();
                showAlert(AlertType.INFORMATION, "Cliente Atualizado", "O cliente foi atualizado com sucesso!");
                limparCampos();
                carregarClientesNaLista();
            }
        } else {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, selecione um cliente e insira todas as informações.");
        }
    }

    private boolean verificarInformacoesCliente(Cliente cliente, String clienteInfo) {
        String clienteInfoFormatted = String.format("ID: %d | Nome: %s | Coruja: %s | Floo Powder: %s",
                cliente.getId(), cliente.getNome(), cliente.getCoruja(), cliente.getFlooPowder());
        return clienteInfo.equals(clienteInfoFormatted);
    }

    private void limparCampos() {
        campoNome.clear();
        campoCoruja.clear();
        campoFlooPowder.clear();
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
