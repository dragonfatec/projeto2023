package frontend.controllers;

import backend.usuario.Usuario;
import backend.util.Criptografia;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class Login implements Initializable {
    // Objetos
    ConnectionFactory conn = new ConnectionFactory();

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

        String user = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (conn.validarAcessoUsuario(user,Criptografia.criptografar(senha))){
            App.mudarTela("registraHora.fxml");

        }
        else{
            Alerts.showAlert("Erro", "","O usuario ou a senha está incorreto", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionFactory.setInstancia();
    }
}
