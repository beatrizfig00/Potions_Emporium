package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaEstoqueController {

    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button removerButton;
    @FXML
    private Button voltarButton;
    @FXML
    private ListView<String> listaProdutos;
    @FXML
    private TextField campoAdicionar;
    @FXML
    private TextField campoAtualizar;
    @FXML
    private TextField campoRemover;

    @FXML
    private void adicionarProduto(ActionEvent evento) {
        String produto = campoAdicionar.getText();
        if (produto != null && !produto.trim().isEmpty()) {
            listaProdutos.getItems().add(produto);
            campoAdicionar.clear();
            showAlert(AlertType.INFORMATION, "Produto Adicionado", "O produto foi adicionado com sucesso!");
        } else {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira um nome de produto.");
        }
    }

    @FXML
    private void atualizarProduto(ActionEvent evento) {
        String novoProduto = campoAtualizar.getText();
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null && novoProduto != null && !novoProduto.trim().isEmpty()) {
            int index = listaProdutos.getItems().indexOf(produtoSelecionado);
            listaProdutos.getItems().set(index, novoProduto);
            campoAtualizar.clear();
            showAlert(AlertType.INFORMATION, "Produto Atualizado", "O produto foi atualizado com sucesso!");
        } else {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, selecione um produto e insira o novo nome.");
        }
    }

    @FXML
    private void removerProduto(ActionEvent evento) {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            listaProdutos.getItems().remove(produtoSelecionado);
            campoRemover.clear();
            showAlert(AlertType.INFORMATION, "Produto Removido", "O produto foi removido com sucesso!");
        } else {
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um produto para remover.");
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
