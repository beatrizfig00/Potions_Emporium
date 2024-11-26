package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
public class TelaAtualizarProdutoController {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoDescricao;
    @FXML
    private TextField campoPreco;
    @FXML
    private TextField campoCodigoBarra;
    @FXML
    private TextField campoQuantidade;

    @FXML
    private void salvarProduto() {
        String nome = campoNome.getText();
        String descricao = campoDescricao.getText();
        String preco = campoPreco.getText();
        String codigoBarra = campoCodigoBarra.getText();
        String quantidade = campoQuantidade.getText();

        if (nome.isEmpty() || descricao.isEmpty() || preco.isEmpty() || codigoBarra.isEmpty() || quantidade.isEmpty()) {
            exibirAlerta(AlertType.WARNING, "Aviso", "Todos os campos devem ser preenchidos.");
        } else {
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Produto atualizado com sucesso.");
        }
    }

    @FXML
    private void cancelarEdicao() {
        campoNome.clear();
        campoDescricao.clear();
        campoPreco.clear();
        campoCodigoBarra.clear();
        campoQuantidade.clear();

        exibirAlerta(AlertType.INFORMATION, "Cancelado", "Edição cancelada.");
    }

    private void exibirAlerta(AlertType tipoAlerta, String titulo, String mensagem) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void voltarParaEstoque(ActionEvent evento) {
        Stage stage = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();
        stage.close();
    }
}