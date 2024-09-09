/* package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Node;

import java.text.DecimalFormat;

public class TelaCaixaController {

    @FXML
    private Button botaoVoltar;
    @FXML
    private Button botaofinalizarCompra;
    @FXML
    private ToggleButton botaocoruja;
    @FXML
    private ToggleButton botaoteletransporte;
    @FXML
    private TextField galeaoTextField;
    @FXML
    private TextField sicleTextField;
    @FXML
    private TextField nuqueTextField;
    @FXML
    private Label totalLabel;
    @FXML
    private Label descontoLabel;
    @FXML
    private Label trocoLabel;

    private double total = 0.0; // Total da compra
    private double desconto = 0.0; // Desconto aplicado
    private double taxaEntrega = 0.0; // Taxa de entrega

    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    public void initialize() {
        // Inicialização dos valores
        totalLabel.setText("R$ " + df.format(total));
        descontoLabel.setText("R$ " + df.format(desconto));
        atualizarTaxaEntrega();
    }

    private void atualizarTaxaEntrega() {
        if (botaocoruja.isSelected()) {
            taxaEntrega = 5.0; // Taxa para coruja
        } else if (botaoteletransporte.isSelected()) {
            taxaEntrega = 10.0; // Taxa para teletransporte
        } else {
            taxaEntrega = 0.0; // Sem taxa se nenhuma opção selecionada
        }
        atualizarTotalComTaxa();
    }

    private void atualizarTotalComTaxa() {
        double totalComTaxa = total + taxaEntrega - desconto;
        totalLabel.setText("R$ " + df.format(totalComTaxa));
        calcularTroco();
    }

    private void calcularTroco() {
        double galeao = Double.parseDouble(galeaoTextField.getText().isEmpty() ? "0" : galeaoTextField.getText());
        double sicle = Double.parseDouble(sicleTextField.getText().isEmpty() ? "0" : sicleTextField.getText());
        double nuque = Double.parseDouble(nuqueTextField.getText().isEmpty() ? "0" : nuqueTextField.getText());

        double totalPago = galeao * 29.0 + sicle * 1.0 + nuque * 0.05;
        double totalComTaxa = total + taxaEntrega - desconto;
        double troco = totalPago - totalComTaxa;

        trocoLabel.setText("R$ " + df.format(troco));
    }

    @FXML
    public void selecionarCoruja(ActionEvent evento) {
        atualizarTaxaEntrega();
    }

    @FXML
    public void selecionarTeletransporte(ActionEvent evento) {
        atualizarTaxaEntrega();
    }

    @FXML
    public void voltar(ActionEvent evento) {
        Stage stage = (Stage) botaoVoltar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void finalizarCompra(ActionEvent evento) {
        // Lógica para finalizar a compra
        if (Double.parseDouble(galeaoTextField.getText().isEmpty() ? "0" : galeaoTextField.getText()) +
                Double.parseDouble(sicleTextField.getText().isEmpty() ? "0" : sicleTextField.getText()) +
                Double.parseDouble(nuqueTextField.getText().isEmpty() ? "0" : nuqueTextField.getText()) < total + taxaEntrega - desconto) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro na Compra");
            alerta.setHeaderText(null);
            alerta.setContentText("O valor pago é insuficiente para cobrir o total da compra.");
            alerta.showAndWait();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Compra Finalizada");
            alerta.setHeaderText(null);
            alerta.setContentText("Compra finalizada com sucesso!");
            alerta.showAndWait();

            try {
                Parent telaFeedback = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-feedback.fxml"));
                Scene cenaFeedback = new Scene(telaFeedback);
                Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
                janela.setScene(cenaFeedback);
                janela.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTotal(double total) {
        this.total = total;
        atualizarTotalComTaxa();
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
        descontoLabel.setText("R$ " + df.format(desconto));
        atualizarTotalComTaxa();
    }
}
*/
package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TelaCaixaController {

    @FXML
    private Label totalLabel; // Exibe o total da compra
    @FXML
    private TextField pagamentoField; // Campo para inserir o valor do pagamento
    @FXML
    private Button confirmarPagamentoButton; // Botão para confirmar o pagamento
    @FXML
    private Button cancelarButton; // Botão para cancelar a compra

    private double totalCompra;

    @FXML
    public void initialize() {
        // Pode deixar este método vazio se o totalCompra for definido externamente
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
        totalLabel.setText(String.format("R$ %.2f", totalCompra));
    }

    @FXML
    public void confirmarPagamento(ActionEvent evento) {
        try {
            double valorPago = Double.parseDouble(pagamentoField.getText());
            if (valorPago >= totalCompra) {
                double troco = valorPago - totalCompra;
                mostrarMensagem("Pagamento Confirmado", "Troco: R$ " + String.format("%.2f", troco));
                finalizarCompra();
                irParaTelaFinalizacao(evento);
            } else {
                mostrarMensagem("Erro no Pagamento", "Valor insuficiente. Por favor, insira um valor maior ou igual ao total da compra.");
            }
        } catch (NumberFormatException e) {
            mostrarMensagem("Erro no Pagamento", "Valor inválido. Por favor, insira um número válido.");
        }
    }

    @FXML
    public void cancelarCompra(ActionEvent evento) {
        // Voltar para a tela anterior (carrinho) sem salvar a compra
        irParaTelaCarrinho(evento);
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void finalizarCompra() {
        // Lógica para finalizar a compra
        // Pode incluir registro da compra, atualização do estoque, etc.
    }

    private void irParaTelaFinalizacao(ActionEvent evento) {
        try {
            Parent telaFinalizacao = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-finalizacao.fxml"));
            Scene cenaFinalizacao = new Scene(telaFinalizacao);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaFinalizacao);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro ao Ir para Finalização", "Não foi possível carregar a tela de finalização.");
        }
    }

    private void irParaTelaCarrinho(ActionEvent evento) {
        try {
            Parent telaCarrinho = FXMLLoader.load(getClass().getResource("/com/potionsemporium/potions_emporium2/tela-carrinho.fxml"));
            Scene cenaCarrinho = new Scene(telaCarrinho);
            Stage janela = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            janela.setScene(cenaCarrinho);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro ao Voltar para o Carrinho", "Não foi possível carregar a tela do carrinho.");
        }
    }
}
