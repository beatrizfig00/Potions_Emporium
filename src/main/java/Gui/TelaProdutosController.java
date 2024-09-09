package Gui;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaProdutosController {

    @FXML
    private ListView<String> produtosListView;
    @FXML
    private Button botaoIrParaCarrinho;
    @FXML
    private Button botaoVoltar;
    @FXML
    private Button botaoFechar;

    private static final String ARQUIVO_PRODUTOS = "produtos.txt"; // Caminho para o arquivo de produtos

    @FXML
    public void initialize() {

        produtosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
     //   carregarProdutosDoArquivo();
        produtosListView.getItems().add("Poção de Cura");
        produtosListView.getItems().add("vassoura");
        produtosListView.getItems().add("bola");
    }

    private void carregarProdutosDoArquivo() {
        List<String> produtos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PRODUTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                produtos.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao Carregar Produtos");
            alerta.setHeaderText(null);
            alerta.setContentText("Não foi possível carregar os produtos. Verifique o arquivo.");
            alerta.showAndWait();
        }

        produtosListView.getItems().addAll(produtos);
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
            // Lógica para adicionar os produtos ao carrinho
            // Aqui, você pode adicionar os produtos à classe Carrinho e depois navegar para a tela do carrinho.

            // Exemplo de adição de produtos ao carrinho (essa lógica pode variar conforme seu sistema)
            // Aqui você pode integrar com o seu sistema de carrinho.

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