package Gui;

import Arquivos.ArquivoEstoque;
import Negocio.Produto;
import Exceptions.DadosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaProdutosController {

    @FXML
    private ListView<String> produtosListView;
    @FXML
    private Button botaoIrParaCarrinho;
    @FXML
    private Button botaoVoltar;
    @FXML
    private Button botaoFechar;
    @FXML
    private TextField quantidadeTextField;

    private static final String ARQUIVO_ESTOQUE = "estoque.csv";
    private ArquivoEstoque arquivoEstoque;
    private List<Produto> produtosSelecionados;

    @FXML
    public void initialize() {
        produtosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        arquivoEstoque = new ArquivoEstoque(ARQUIVO_ESTOQUE);
        produtosSelecionados = new ArrayList<>();
        carregarProdutosDoArquivo();
    }

    private void carregarProdutosDoArquivo() {
        Map<Produto, Integer> produtos = arquivoEstoque.getAllProdutos();
        List<String> listaProdutos = new ArrayList<>();
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            Integer quantidade = entry.getValue();
            String descricao = produto.getDescricao();
            double preco = produto.getPreco();
            String tipo = arquivoEstoque.determinarTipoProduto(produto);
            String detalhesEspecificos = arquivoEstoque.obterDetalhesEspecificos(produto);

            listaProdutos.add(String.format("%s\nDescrição: %s\nPreço: %.2f\nTipo: %s\nDetalhes: %s\nQuantidade: %d",
                    produto.getNome(), descricao, preco, tipo, detalhesEspecificos, quantidade));
        }
        produtosListView.getItems().setAll(listaProdutos);
    }

    @FXML
    public void irParaCarrinho(ActionEvent evento) {
        List<String> produtosSelecionadosText = produtosListView.getSelectionModel().getSelectedItems();
        if (produtosSelecionadosText.isEmpty() || quantidadeTextField.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Seleção");
            alerta.setHeaderText("Nenhum Produto Selecionado ou Quantidade Não Informada");
            alerta.setContentText("Por favor, selecione pelo menos um produto e informe a quantidade.");
            alerta.showAndWait();
        } else {
            List<Produto> produtosParaAdicionar = new ArrayList<>();
            try {
                int quantidade = Integer.parseInt(quantidadeTextField.getText());
                for (String produtoText : produtosSelecionadosText) {
                    Produto produto = encontrarProdutoPorTexto(produtoText);
                    if (produto != null) {
                        int quantidadeDisponivel = arquivoEstoque.getQuantidadeProduto(produto);
                        if (quantidade <= quantidadeDisponivel) {
                            produto.setQuantidade(quantidade);
                            produtosParaAdicionar.add(produto);
                            arquivoEstoque.atualizarQuantidade(produto, quantidadeDisponivel - quantidade);
                        } else {
                            Alert alerta = new Alert(Alert.AlertType.WARNING);
                            alerta.setTitle("Quantidade Insuficiente");
                            alerta.setHeaderText(null);
                            alerta.setContentText(String.format("Não há estoque suficiente para o produto: %s. Disponível: %d", produto.getNome(), quantidadeDisponivel));
                            alerta.showAndWait();
                            return; 
                        }
                    }
                }

                if (!produtosParaAdicionar.isEmpty()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-caixa.fxml"));
                    Parent telaCaixa = loader.load();

                    TelaCaixaController controller = loader.getController();

                    Scene cenaCaixa = new Scene(telaCaixa);
                    Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                    janela.setScene(cenaCaixa);
                    janela.show();

                } else {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Nenhum Produto Adicionado");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Nenhum produto foi adicionado ao carrinho.");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Quantidade Inválida");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, insira uma quantidade válida.");
                alerta.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro ao Ir para Carrinho");
                alerta.setHeaderText(null);
                alerta.setContentText("Não foi possível carregar a tela do carrinho.");
                alerta.showAndWait();
            } catch (DadosInvalidosException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Produto encontrarProdutoPorTexto(String texto) {
        for (Produto produto : arquivoEstoque.getAllProdutos().keySet()) {
            if (texto.contains(produto.getNome())) {
                return produto;
            }
        }
        return null;
    }

    @FXML
    public void adicionarAoCarrinho(ActionEvent evento) {
        List<String> produtosSelecionadosText = produtosListView.getSelectionModel().getSelectedItems();
        if (produtosSelecionadosText.isEmpty() || quantidadeTextField.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Seleção");
            alerta.setHeaderText("Nenhum Produto Selecionado ou Quantidade Não Informada");
            alerta.setContentText("Por favor, selecione pelo menos um produto e informe a quantidade para cada produto.");
            alerta.showAndWait();
        } else {
            List<Produto> produtosParaAdicionar = new ArrayList<>();
            try {
                int quantidade = Integer.parseInt(quantidadeTextField.getText());
                for (String produtoText : produtosSelecionadosText) {
                    Produto produto = encontrarProdutoPorTexto(produtoText);
                    if (produto != null) {
                        int quantidadeDisponivel = getQuantidadeProduto(produto);
                        if (quantidade <= quantidadeDisponivel) {
                            produto.setQuantidade(quantidade);
                            produtosParaAdicionar.add(produto);
                            arquivoEstoque.atualizarQuantidade(produto, quantidadeDisponivel - quantidade);
                        } else {
                            Alert alerta = new Alert(Alert.AlertType.WARNING);
                            alerta.setTitle("Quantidade Insuficiente");
                            alerta.setHeaderText(null);
                            alerta.setContentText(String.format("Não há estoque suficiente para o produto: %s. Disponível: %d", produto.getNome(), quantidadeDisponivel));
                            alerta.showAndWait();
                        }
                    }
                }
                if (!produtosParaAdicionar.isEmpty()) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Adicionado ao Carrinho");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Produtos adicionados ao carrinho com sucesso.");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Quantidade Inválida");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, insira uma quantidade válida.");
                alerta.showAndWait();
            } catch (DadosInvalidosException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getQuantidadeProduto(Produto produto) {
        return arquivoEstoque.getQuantidadeProduto(produto);
    }

    @FXML
    public void encerrarPrograma(ActionEvent evento) {
        Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        janela.close();
    }
}
