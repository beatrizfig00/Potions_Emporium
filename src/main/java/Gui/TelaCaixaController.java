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
import java.text.DecimalFormat;

public class TelaCaixaController {

    @FXML
    private Label totalLabel;
    @FXML
    private Label descontoLabel;
    @FXML
    private Label trocoLabel;
    @FXML
    private TextField galeaoTextField;
    @FXML
    private TextField sicleTextField;
    @FXML
    private TextField nuqueTextField;
    @FXML
    private Button botaofinalizarCompra;

    private double total = 0.0; 
    private double desconto = 0.0; 
    private double taxaEntrega = 0.0; 

    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    public void initialize() {
        totalLabel.setText("R$ " + df.format(total));
        descontoLabel.setText("R$ " + df.format(desconto));
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
    public void finalizarCompra(ActionEvent evento) {
        try {
            double valorPago = calcularValorPago();
            if (valorPago >= total + taxaEntrega - desconto) {
                calcularTroco();
                mostrarMensagem("Compra Finalizada", "Compra finalizada com sucesso!");
                irParaTelaFinalizacao(evento);
            } else {
                mostrarMensagem("Erro no Pagamento", "O valor pago é insuficiente para cobrir o total da compra.");
            }
        } catch (NumberFormatException e) {
            mostrarMensagem("Erro no Pagamento", "Valor inválido. Por favor, insira um número válido.");
        }
    }

    private double calcularValorPago() {
        double galeao = Double.parseDouble(galeaoTextField.getText().isEmpty() ? "0" : galeaoTextField.getText());
        double sicle = Double.parseDouble(sicleTextField.getText().isEmpty() ? "0" : sicleTextField.getText());
        double nuque = Double.parseDouble(nuqueTextField.getText().isEmpty() ? "0" : nuqueTextField.getText());

        return galeao * 29.0 + sicle * 1.0 + nuque * 0.05;
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
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

    public void setTotal(double total) {
        this.total = total;
        totalLabel.setText("R$ " + df.format(total));
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
        descontoLabel.setText("R$ " + df.format(desconto));
    }
}
