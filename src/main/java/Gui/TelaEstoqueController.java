package Gui;

import Arquivos.ArquivoEstoque;
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

    private ArquivoEstoque arquivoEstoque;

    @FXML
    private void initialize() {
        comboTipoProduto.getItems().addAll(
                "Produto Animal",
                "Produto Ingrediente",
                "Produto Item",
                "Produto Livro",
                "Produto Poção"
        );

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
                case "Produto Animal":
                    String habitat = campoHabitat.getText();
                    produto = new ProdutoAnimal(0, nome, descricao, preco, codigoBarra, quantidade, habitat);
                    break;
                case "Produto Ingrediente":
                    String origem = campoOrigem.getText();
                    produto = new ProdutoIngrediente(0, nome, descricao, preco, codigoBarra, quantidade, origem);
                    break;
                case "Produto Item":
                    String poder = campoPoder.getText();
                    produto = new ProdutoItem(0, nome, descricao, preco, codigoBarra, quantidade, poder);
                    break;
                case "Produto Livro":
                    String autor = campoAutor.getText();
                    int numeroPaginas = Integer.parseInt(campoNumeroPaginas.getText());
                    produto = new ProdutoLivro(0, nome, descricao, preco, codigoBarra, quantidade, autor, numeroPaginas);
                    break;
                case "Produto Poção":
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
    private void removerProduto(ActionEvent evento) {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            String nomeProduto = extrairNomeDoProduto(produtoSelecionado);

            Produto produto = arquivoEstoque.getProdutoPorNome(nomeProduto);
            if (produto != null) {
                arquivoEstoque.removerProduto(produto);
                salvarProdutos();
                carregarProdutosNoListView();
                showAlert(AlertType.INFORMATION, "Produto Removido", "O produto foi removido com sucesso!");
            } else {
                showAlert(AlertType.WARNING, "Produto Não Encontrado", "O produto selecionado não foi encontrado no estoque.");
            }
        } else {
            showAlert(AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um produto para remover.");
        }
    }

    private String extrairNomeDoProduto(String detalhesProduto) {
        String[] partes = detalhesProduto.split(", ");
        if (partes.length > 0) {
            return partes[0].replace("Nome: ", "");
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

            String tipoProduto = arquivoEstoque.determinarTipoProduto(produto);

            String detalhesEspecificos = "";
            try {
                detalhesEspecificos = arquivoEstoque.obterDetalhesEspecificos(produto);
            } catch (Exception e) {
                detalhesEspecificos = "Detalhes não disponíveis";
            }

            String detalhesProduto = String.format(
                    "Nome: %s, Descrição: %s, Preço: %.2f, Código de Barra: %s, Quantidade: %d, Tipo: %s, Detalhes: %s",
                    produto.getNome(), produto.getDescricao(), produto.getPreco(),
                    produto.getCodigoBarra(), quantidade, tipoProduto, detalhesEspecificos);

            listaProdutos.getItems().add(detalhesProduto);
        }
    }


    private void salvarProdutos() {
        try {
            arquivoEstoque.salvarProdutos();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro", "Não foi possível salvar os produtos no arquivo.");
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
        comboTipoProduto.setValue(null);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
