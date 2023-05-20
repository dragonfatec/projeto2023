package frontend.controllers;

import backend.usuario.TiposDeUsuario;
import backend.usuario.Usuario;
import backend.util.Criptografia;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
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


    /////     Metodos Publicos     /////
    public void fazerLogin(ActionEvent actionEvent) throws IOException {
        if (conn.validarAcessoUsuario(campoUsuario.getText(),Criptografia.criptografar(campoSenha.getText()))){
            if(Usuario.getInstancia().getCargo() == TiposDeUsuario.RH){
                App.mudarTela(NomesArquivosFXML.aprovaHora+".fxml");
            }else {
                App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
            }
        }
        else{
            Alerts.showAlert("Erro", "","O usuario ou a senha está incorreto", Alert.AlertType.ERROR);
        }
    }


    /////     Metodo Override     /////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionFactory.setInstancia();
    }
}
