package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Map;


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
        ConnectionFactory conn = new ConnectionFactory();
        String user = campoUsuario.getText();
        String senha = campoSenha.getText();

        Map<Integer, Usuario> resultadoQuery = conn.getUsuario(user,senha);

//        String senhaCriptografada = Criptografia.criptografar(senha);

        if (resultadoQuery.size() == 1){
            Usuario usuario = resultadoQuery.get(0);
            Usuario.criarInstancia(usuario.getLogin(), usuario.getSenha(), usuario.getMatricula(), usuario.getNome(), usuario.getCargo(), usuario.getId_equipe());
            App.mudarTela("registraHora.fxml");
        }
        else{
            Alerts.showAlert("Erro", "","O usuario ou a senha está incorreto", Alert.AlertType.ERROR);
        }
    }
}
