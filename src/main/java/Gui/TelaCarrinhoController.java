package Gui;

import Arquivos.ArquivoCliente;
import Exceptions.DadosInvalidosException;
import Negocio.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    public static class TelaCadastroController {

        @FXML
        private TextField nomeField;
        @FXML
        private TextField cidadeField;
        @FXML
        private TextField ruaField;
        @FXML
        private TextField numeroField;
        @FXML
        private TextField corujaField;
        @FXML
        private TextField flooPowderField;
        @FXML
        private TextField senhaField;
        @FXML
        private Button botaoFechar;

        private ArquivoCliente arquivoCliente;

        public TelaCadastroController() {
            this.arquivoCliente = new ArquivoCliente("clientes.csv");
        }

        @FXML
        public void initialize() {
            try {
                arquivoCliente.carregarClientes();
            } catch (IOException | DadosInvalidosException e) {
                showAlert(Alert.AlertType.ERROR, "Erro ao carregar clientes", "Não foi possível carregar a lista de clientes.", e.getMessage());
            }
        }

        @FXML
        public void encerrarPrograma(ActionEvent evento) {
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.close();
        }

        @FXML
        public void salvarCadastro(ActionEvent evento) throws DadosInvalidosException {
            String nome = nomeField.getText();
            String cidade = cidadeField.getText();
            String rua = ruaField.getText();
            String numero = numeroField.getText();
            String coruja = corujaField.getText();
            String flooPowder = flooPowderField.getText();
            String senha = senhaField.getText();

            if (nome.isEmpty() || cidade.isEmpty() || rua.isEmpty() || numero.isEmpty() || coruja.isEmpty() || flooPowder.isEmpty() || senha.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro de Cadastro", "Campos obrigatórios não preenchidos.", "Por favor, preencha todos os campos.");
                return;
            }

            Cliente novoCliente = new Cliente(nome, coruja, flooPowder, senha);

            try {
                arquivoCliente.adicionarCliente(novoCliente);
                arquivoCliente.salvarClientes();
                showAlert(Alert.AlertType.INFORMATION, "Cadastro realizado", null, "Cadastro realizado com sucesso.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Cadastro", "Não foi possível salvar o cadastro.", "Por favor, tente novamente.");
            }
        }

        @FXML
        public void voltar(ActionEvent evento) {
            try {
                Parent telaCliente = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-principal-cliente.fxml"));
                Scene cenaCliente = new Scene(telaCliente);
                Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                janela.setScene(cenaCliente);
                janela.show();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela principal do cliente.", e.getMessage());
            }
        }

        private void showAlert(Alert.AlertType type, String title, String header, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
}
