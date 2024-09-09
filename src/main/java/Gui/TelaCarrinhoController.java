package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class TelaCarrinhoController {

    @FXML
    private ListView<String> carrinhoListView; // Exibe os produtos no carrinho
    @FXML
    private Button botaoCaixa; // Botão para ir à tela de caixa
    @FXML
    private Button botaoVoltar; // Botão para voltar
    @FXML
    private Label totalLabel; // Exibe o total do carrinho

    private static List<String> produtosNoCarrinho; // Lista de produtos no carrinho (deve ser inicializada de outra forma no seu sistema)

    @FXML
    public void initialize() {
        if (produtosNoCarrinho != null) {
            carrinhoListView.getItems().addAll(produtosNoCarrinho);
            atualizarTotal();
        } else {
            carrinhoListView.getItems().clear();
            totalLabel.setText("R$ 0,00");
        }
    }

        private void atualizarTotal() {
        // Exemplo simples de cálculo do total, você deve adaptar conforme sua lógica de preços
        double total = produtosNoCarrinho.stream()
                .mapToDouble(produto -> obterPrecoProduto(produto))
                .sum();

        totalLabel.setText(String.format("R$ %.2f", total));
    }

    private double obterPrecoProduto(String produto) {
        // Lógica para obter o preço de um produto. Deve ser adaptada conforme sua implementação.
        // Exemplo fictício, substitua com a lógica real.
        return 10.0; // Valor fixo para todos os produtos. Substitua com a lógica real.
    }

    @FXML
    public void irParaCaixa(ActionEvent evento) {
        try {
            Parent telaCaixa = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-caixa.fxml"));
            Scene cenaCaixa = new Scene(telaCaixa);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaCaixa);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao Ir para Caixa");
            alerta.setHeaderText(null);
            alerta.setContentText("Não foi possível carregar a tela de caixa.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void voltar(ActionEvent evento) {
        try {
            Parent telaProdutos = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-produtos.fxml"));
            Scene cenaProdutos = new Scene(telaProdutos);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaProdutos);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao Voltar");
            alerta.setHeaderText(null);
            alerta.setContentText("Não foi possível carregar a tela de produtos.");
            alerta.showAndWait();
        }
    }

    public static void setProdutosNoCarrinho(List<String> produtos) {
        produtosNoCarrinho = produtos;
    }
}
