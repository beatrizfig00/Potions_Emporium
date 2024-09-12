package Gui;

import Exceptions.DadosInvalidosException;
import Negocio.Produto;
import Negocio.produtos.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaEstoqueController {

    @FXML
    private Button botaoAdicionar;
    @FXML
    private Button botaoAtualizar;
    @FXML
    private Button botaoRemover;
    @FXML
    private Button botaoVoltar;
    @FXML
    private ListView<String> listaProdutos;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoDescricao;
    @FXML
    private TextField campoPreco;
    @FXML
    private TextField campoCategoria;
    @FXML
    private TextField campoCodigoBarra;
    @FXML
    private TextField campoQuantidade;
    @FXML
    private TextField campoHabitat;
    @FXML
    private TextField campoOrigem;
    @FXML
    private TextField campoPoder;
    @FXML
    private TextField campoAutor;
    @FXML
    private TextField campoNumeroPaginas;
    @FXML
    private TextField campoEfeito;
    @FXML
    private TextField campoTempoEfeito;
    @FXML
    private ComboBox<String> comboTipoProduto;

    @FXML
    private void initialize() {
        comboTipoProduto.getItems().addAll(
                "Produto Animal",
                "Produto Ingrediente",
                "Produto Item",
                "Produto Livro",
                "Produto Poção"
        );
    }

    @FXML
    private void adicionarProduto(ActionEvent evento) {
        String tipoProduto = comboTipoProduto.getValue();
        String nome = campoNome.getText();
        String descricao = campoDescricao.getText();
        String precoTexto = campoPreco.getText();
        String categoria = campoCategoria.getText();
        String codigoBarra = campoCodigoBarra.getText();
        String quantidadeTexto = campoQuantidade.getText();

        if (nome == null || nome.trim().isEmpty() ||
                descricao == null || descricao.trim().isEmpty() ||
                precoTexto == null || precoTexto.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty() ||
                codigoBarra == null || codigoBarra.trim().isEmpty() ||
                quantidadeTexto == null || quantidadeTexto.trim().isEmpty() ||
                tipoProduto == null || tipoProduto.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        double preco;
        int quantidade;
        try {
            preco = Double.parseDouble(precoTexto);
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Preço ou quantidade inválidos.");
            return;
        }

        Produto produto = null;
        try {
            switch (tipoProduto) {
                case "Produto Animal":
                    String habitat = campoHabitat.getText();
                    if (habitat == null || habitat.trim().isEmpty()) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o habitat.");
                        return;
                    }
                    produto = new ProdutoAnimal(0, nome, descricao, preco, categoria, codigoBarra, quantidade, habitat);
                    break;
                case "Produto Ingrediente":
                    String origem = campoOrigem.getText();
                    if (origem == null || origem.trim().isEmpty()) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira a origem.");
                        return;
                    }
                    produto = new ProdutoIngrediente(0, nome, descricao, preco, categoria, codigoBarra, quantidade, origem);
                    break;
                case "Produto Item":
                    String poder = campoPoder.getText();
                    if (poder == null || poder.trim().isEmpty()) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o poder.");
                        return;
                    }
                    produto = new ProdutoItem(0, nome, descricao, preco, categoria, codigoBarra, quantidade, poder);
                    break;
                case "Produto Livro":
                    String autor = campoAutor.getText();
                    String numeroPaginasTexto = campoNumeroPaginas.getText();
                    if (autor == null || autor.trim().isEmpty() || numeroPaginasTexto == null || numeroPaginasTexto.trim().isEmpty()) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o autor e o número de páginas.");
                        return;
                    }
                    int numeroPaginas;
                    try {
                        numeroPaginas = Integer.parseInt(numeroPaginasTexto);
                    } catch (NumberFormatException e) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Número de páginas inválido.");
                        return;
                    }
                    produto = new ProdutoLivro(0, nome, descricao, preco, categoria, codigoBarra, quantidade, autor, numeroPaginas);
                    break;
                case "Produto Poção":
                    String efeito = campoEfeito.getText();
                    String tempoEfeitoTexto = campoTempoEfeito.getText();
                    if (efeito == null || efeito.trim().isEmpty() || tempoEfeitoTexto == null || tempoEfeitoTexto.trim().isEmpty()) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o efeito e o tempo de efeito.");
                        return;
                    }
                    int tempoEfeito;
                    try {
                        tempoEfeito = Integer.parseInt(tempoEfeitoTexto);
                    } catch (NumberFormatException e) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Tempo de efeito inválido.");
                        return;
                    }
                    produto = new ProdutoPocao(0, nome, descricao, preco, categoria, codigoBarra, quantidade, efeito, tempoEfeito);
                    break;
                default:
                    showAlert(AlertType.WARNING, "Tipo de Produto Inválido", "Tipo de produto selecionado é inválido.");
                    return;
            }

            listaProdutos.getItems().add(produto.toString());
            limparCampos();
            showAlert(AlertType.INFORMATION, "Produto Adicionado", "O produto foi adicionado com sucesso!");

        } catch (DadosInvalidosException e) {
            showAlert(AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }
    }

    private void limparCampos() {
        campoNome.clear();
        campoDescricao.clear();
        campoPreco.clear();
        campoCategoria.clear();
        campoCodigoBarra.clear();
        campoQuantidade.clear();
        campoHabitat.clear();
        campoOrigem.clear();
        campoPoder.clear();
        campoAutor.clear();
        campoNumeroPaginas.clear();
        campoEfeito.clear();
        campoTempoEfeito.clear();
        comboTipoProduto.setValue(null);
    }


    @FXML
    private void atualizarProduto(ActionEvent evento) {
        String tipoProduto = comboTipoProduto.getValue();
        String nome = campoNome.getText();
        String descricao = campoDescricao.getText();
        String precoTexto = campoPreco.getText();
        String categoria = campoCategoria.getText();
        String codigoBarra = campoCodigoBarra.getText();
        String quantidadeTexto = campoQuantidade.getText();

        String campoEspecifico = null;
        if (tipoProduto != null) {
            switch (tipoProduto) {
                case "Produto Animal":
                    campoEspecifico = campoHabitat.getText();
                    break;
                case "Produto Ingrediente":
                    campoEspecifico = campoOrigem.getText();
                    break;
                case "Produto Item":
                    campoEspecifico = campoPoder.getText();
                    break;
                case "Produto Livro":
                    campoEspecifico = campoAutor.getText() + "," + campoNumeroPaginas.getText();
                    break;
                case "Produto Poção":
                    campoEspecifico = campoEfeito.getText() + "," + campoTempoEfeito.getText();
                    break;
            }
        }

        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um produto para atualizar.");
            return;
        }

        if (nome == null || nome.trim().isEmpty() ||
                descricao == null || descricao.trim().isEmpty() ||
                precoTexto == null || precoTexto.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty() ||
                codigoBarra == null || codigoBarra.trim().isEmpty() ||
                quantidadeTexto == null || quantidadeTexto.trim().isEmpty() ||
                tipoProduto == null || tipoProduto.trim().isEmpty() ||
                campoEspecifico == null || campoEspecifico.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        double preco;
        int quantidade;
        try {
            preco = Double.parseDouble(precoTexto);
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Preço ou quantidade inválidos.");
            return;
        }

        Produto produtoAtualizado = null;
        try {
            switch (tipoProduto) {
                case "Produto Animal":
                    produtoAtualizado = new ProdutoAnimal(0, nome, descricao, preco, categoria, codigoBarra, quantidade, campoEspecifico);
                    break;
                case "Produto Ingrediente":
                    produtoAtualizado = new ProdutoIngrediente(0, nome, descricao, preco, categoria, codigoBarra, quantidade, campoEspecifico);
                    break;
                case "Produto Item":
                    produtoAtualizado = new ProdutoItem(0, nome, descricao, preco, categoria, codigoBarra, quantidade, campoEspecifico);
                    break;
                case "Produto Livro":
                    String[] autorPaginas = campoEspecifico.split(",");
                    if (autorPaginas.length != 2) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o autor e o número de páginas.");
                        return;
                    }
                    produtoAtualizado = new ProdutoLivro(0, nome, descricao, preco, categoria, codigoBarra, quantidade, autorPaginas[0], Integer.parseInt(autorPaginas[1]));
                    break;
                case "Produto Poção":
                    String[] efeitoTempo = campoEspecifico.split(",");
                    if (efeitoTempo.length != 2) {
                        showAlert(AlertType.WARNING, "Entrada Inválida", "Por favor, insira o efeito e o tempo de efeito.");
                        return;
                    }
                    produtoAtualizado = new ProdutoPocao(0, nome, descricao, preco, categoria, codigoBarra, quantidade, efeitoTempo[0], Integer.parseInt(efeitoTempo[1]));
                    break;
            }

            int index = listaProdutos.getSelectionModel().getSelectedIndex();
            listaProdutos.getItems().set(index, produtoAtualizado.toString());
            limparCampos();
            showAlert(AlertType.INFORMATION, "Produto Atualizado", "O produto foi atualizado com sucesso!");

        } catch (DadosInvalidosException e) {
            showAlert(AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }
    }

    @FXML
    private void removerProduto(ActionEvent evento) {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            listaProdutos.getItems().remove(produtoSelecionado);
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
