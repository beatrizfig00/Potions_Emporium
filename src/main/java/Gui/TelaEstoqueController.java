package Gui;

import Arquivos.ArquivoEstoque;
import Exceptions.DadosInvalidosException;
import Negocio.Produto;
import Negocio.produtos.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

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
    private ArquivoEstoque arquivoEstoque;
    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {
        comboTipoProduto.getItems().addAll("Animal", "Ingrediente", "Item Mágico", "Livro", "Poção"
        );

        comboTipoProduto.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Animal":
                    tabPane.getSelectionModel().select(0);
                    break;

                case "Ingrediente":
                    tabPane.getSelectionModel().select(1);
                    break;

                case "Item Mágico":
                    tabPane.getSelectionModel().select(2);
                    break;

                case "Livro":
                    tabPane.getSelectionModel().select(3);
                    break;

                case "Poção":
                    tabPane.getSelectionModel().select(4);
                    break;
            }
        });
            arquivoEstoque = new ArquivoEstoque("estoque.csv");
            carregarProdutosNoListView();
    }

    @FXML
    private void adicionarProduto(ActionEvent evento) {
        try {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            String codigoBarra = campoCodigoBarra.getText();
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            String tipoProduto = comboTipoProduto.getValue();

            Produto produto = null;

            switch (tipoProduto) {
                case "Animal":
                    String habitat = campoHabitat.getText();
                    produto = new ProdutoAnimal(0, nome, descricao, preco, codigoBarra, quantidade, habitat);
                    break;
                case "Ingrediente":
                    String origem = campoOrigem.getText();
                    produto = new ProdutoIngrediente(0, nome, descricao, preco, codigoBarra, quantidade, origem);
                    break;
                case "Item Mágico":
                    String poder = campoPoder.getText();
                    produto = new ProdutoItem(0, nome, descricao, preco, codigoBarra, quantidade, poder);
                    break;
                case "Livro":
                    String autor = campoAutor.getText();
                    int numeroPaginas = Integer.parseInt(campoNumeroPaginas.getText());
                    produto = new ProdutoLivro(0, nome, descricao, preco, codigoBarra, quantidade, autor, numeroPaginas);
                    break;
                case "Poção":
                    String efeito = campoEfeito.getText();
                    int tempoEfeito = Integer.parseInt(campoTempoEfeito.getText());
                    produto = new ProdutoPocao(0, nome, descricao, preco, codigoBarra, quantidade, efeito, tempoEfeito);
                    break;
            }

            arquivoEstoque.adicionarProduto(produto, quantidade);
            salvarProdutos();
            carregarProdutosNoListView();

            limparCampos();
            showAlert(AlertType.INFORMATION, "Produto Adicionado", "O produto foi adicionado com sucesso!");

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Preço ou quantidade inválidos.");
        } catch (DadosInvalidosException e) {
            showAlert(AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }
    }

    @FXML
    private void atualizarProduto(ActionEvent evento) {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um produto para atualizar.");
            return;
        }

        try {
            Produto produtoExistente = arquivoEstoque.getProdutoPorNome(produtoSelecionado);
            if (produtoExistente == null) {
                showAlert(AlertType.WARNING, "Produto Não Encontrado", "O produto selecionado não foi encontrado no estoque.");
                return;
            }

            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            String codigoBarra = campoCodigoBarra.getText();
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            String tipoProduto = comboTipoProduto.getValue();

            produtoExistente.setNome(nome);
            produtoExistente.setDescricao(descricao);
            produtoExistente.setPreco(preco);
            produtoExistente.setCodigoBarra(codigoBarra);
            produtoExistente.setQuantidade(quantidade);

            switch (tipoProduto) {
                case "Produto Animal":
                    String habitat = campoHabitat.getText();
                    ((ProdutoAnimal) produtoExistente).setHabitat(habitat);
                    break;
                case "Produto Ingrediente":
                    String origem = campoOrigem.getText();
                    ((ProdutoIngrediente) produtoExistente).setOrigem(origem);
                    break;
                case "Produto Item":
                    String poder = campoPoder.getText();
                    ((ProdutoItem) produtoExistente).setPoder(poder);
                    break;
                case "Produto Livro":
                    String autor = campoAutor.getText();
                    int numeroPaginas = Integer.parseInt(campoNumeroPaginas.getText());
                    ((ProdutoLivro) produtoExistente).setAutor(autor);
                    ((ProdutoLivro) produtoExistente).setNumeroPaginas(numeroPaginas);
                    break;
                case "Produto Poção":
                    String efeito = campoEfeito.getText();
                    int tempoEfeito = Integer.parseInt(campoTempoEfeito.getText());
                    ((ProdutoPocao) produtoExistente).setEfeito(efeito);
                    ((ProdutoPocao) produtoExistente).setTempoEfeito(tempoEfeito);
                    break;
            }

            salvarProdutos();
            carregarProdutosNoListView();

            limparCampos();
            showAlert(AlertType.INFORMATION, "Produto Atualizado", "O produto foi atualizado com sucesso!");

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Entrada Inválida", "Preço ou quantidade inválidos.");
        } catch (DadosInvalidosException e) {
            showAlert(AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }
    }

    @FXML
    private void removerProduto() {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            String nomeProduto = extrairNomeDoProduto(produtoSelecionado);
            Produto produto = arquivoEstoque.getProdutoPorNome(nomeProduto);

            if (produto != null) {
                arquivoEstoque.removerProduto(produto);
                listaProdutos.getItems().remove(produtoSelecionado);
                System.out.println("Produto removido: " + nomeProduto);
                showAlert(AlertType.INFORMATION, "Produto Removido", "O produto foi removido com sucesso!");
            } else {
                System.out.println("Produto não encontrado no estoque.");
            }
        } else {
            System.out.println("Nenhum produto foi selecionado para remoção.");
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um produto para remover.");
        }
    }

    private String extrairNomeDoProduto(String detalhesProduto) {
        String[] partes = detalhesProduto.split(", ");
        if (partes.length > 1) {
            return partes[1].replace("Nome: ", "");
        }
        return "";
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

    private void carregarProdutosNoListView() {
        listaProdutos.getItems().clear();
        Map<Produto, Integer> produtos = arquivoEstoque.getAllProdutos();

        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();

            String detalhesEspecificos = arquivoEstoque.obterDetalhesEspecificos(produto);
            String item = String.format("ID: %d, Nome: %s, Descrição: %s, Preço: %.2f, Código de Barras: %s, Quantidade: %d, Detalhes: %s",
                    produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getCodigoBarra(), produto.getQuantidade(), detalhesEspecificos);

            listaProdutos.getItems().add(item);
        }
    }

    private void salvarProdutos() {
        try {
            arquivoEstoque.salvarProdutos();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível salvar os produtos.");
        }
    }

    private void limparCampos() {
        campoNome.clear();
        campoDescricao.clear();
        campoPreco.clear();
        campoCodigoBarra.clear();
        campoQuantidade.clear();
        campoHabitat.clear();
        campoOrigem.clear();
        campoPoder.clear();
        campoAutor.clear();
        campoNumeroPaginas.clear();
        campoEfeito.clear();
        campoTempoEfeito.clear();
     // comboTipoProduto.setValue(null);
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
