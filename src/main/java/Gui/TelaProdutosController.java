package Gui;

import Arquivos.ArquivoEstoque;
import Negocio.Produto;
import Negocio.produtos.*;
import Exceptions.DadosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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

    private static final String ARQUIVO_PRODUTOS = "produtos.csv";
    private ArquivoEstoque arquivoEstoque;

    @FXML
    public void initialize() {
        produtosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        arquivoEstoque = new ArquivoEstoque(ARQUIVO_PRODUTOS);
        carregarProdutosDoArquivo();
    }

    private void carregarProdutosDoArquivo() {
        try {
            Map<Produto, Integer> produtos = arquivoEstoque.carregarProdutos();
            List<String> listaProdutos = new ArrayList<>();
            for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
                Produto produto = entry.getKey();
                Integer quantidade = entry.getValue();
                listaProdutos.add(String.format("%s (Quantidade: %d)", produto.getNome(), quantidade));
            }
            produtosListView.getItems().addAll(listaProdutos);
        } catch (IOException | DadosInvalidosException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao Carregar Produtos");
            alerta.setHeaderText(null);
            alerta.setContentText("Não foi possível carregar os produtos. Verifique o arquivo.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void irParaCarrinho(ActionEvent evento) {
        List<String> produtosSelecionados = produtosListView.getSelectionModel().getSelectedItems();

        if (produtosSelecionados.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Nenhum Produto Selecionado");
            alerta.setHeaderText("Você não selecionou nenhum produto.");
            alerta.setContentText("Por favor, selecione pelo menos um produto para adicionar ao carrinho.");
            alerta.showAndWait();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Produtos Adicionados");
            alerta.setHeaderText(null);
            alerta.setContentText("Os produtos foram adicionados ao carrinho.");
            alerta.showAndWait();

            try {
                Parent telaCarrinho = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-carrinho.fxml"));
                Scene cenaCarrinho = new Scene(telaCarrinho);
                Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                janela.setScene(cenaCarrinho);
                janela.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void voltarParaTelaPrincipal(ActionEvent evento) {
        try {
            Parent telaPrincipal = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-inicial.fxml"));
            Stage janelaAtual = (Stage) ((Node) evento.getSource()).getScene().getWindow();

            Stage novaJanela = new Stage();
            novaJanela.setScene(new Scene(telaPrincipal));
            novaJanela.setResizable(false);

            novaJanela.show();
            janelaAtual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void encerrarPrograma(ActionEvent evento) {
        Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        janela.close();
    }
}
