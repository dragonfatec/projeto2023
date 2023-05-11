package frontend.controllers;

import backend.usuario.Usuario;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class Login {
    // Label
    @FXML public Label textoNomeUsuario;

    // Texto - TextField
    @FXML public TextField campoUsuario;
    @FXML public TextField campoSenha;

    // Button
    @FXML public Button btnEntrar;

    // Metodos
    @FXML
    public void fazerLogin(ActionEvent actionEvent) throws IOException {
        boolean loginAceito = backend.util.Login.verificarLogin(campoUsuario.getText(), campoSenha.getText(), true);
        if (loginAceito){
            App.mudarTela("consultaHora.fxml");
        }
        else{
            Alerts.showAlert("Erro", "","Usuario ou a senha está incorreto", Alert.AlertType.ERROR);
        }
    }
}
