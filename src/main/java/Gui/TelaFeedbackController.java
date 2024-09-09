package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;

public class TelaFeedbackController {

    @FXML
    private ToggleButton toggleButton1;

    @FXML
    private ToggleButton toggleButton2;

    @FXML
    private ToggleButton toggleButton3;

    @FXML
    private ToggleButton toggleButton4;

    @FXML
    private ToggleButton toggleButton5;

    @FXML
    public void salvarFeedback(ActionEvent evento) {
        String feedback = "";
        if (toggleButton1.isSelected()) {
            feedback = "1";
        } else if (toggleButton2.isSelected()) {
            feedback = "2";
        } else if (toggleButton3.isSelected()) {
            feedback = "3";
        } else if (toggleButton4.isSelected()) {
            feedback = "4";
        } else if (toggleButton5.isSelected()) {
            feedback = "5";
        } else {
            feedback = "Nenhuma nota selecionada";
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Feedback Recebido");
        alerta.setHeaderText(null);
        alerta.setContentText("Sua nota: " + feedback);
        alerta.showAndWait();
    }
}
