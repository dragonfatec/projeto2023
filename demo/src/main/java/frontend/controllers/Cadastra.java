package frontend.controllers;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import frontend.util.VerificaAcesso;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Cadastra implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();

    // Texto


    // Input - TextField


    // input - TextArea


    // Input - Data


    // ChoiceBox


    // Botão


    /////     Metodos Publicos     /////
    public void gerarMatricula(){
        // Esse metodo vai gerar (aleatoriamente) e preencher o campo de matricula
        ArrayList<String> listaMatriculaExistente = conn.getListaColuna(null, "usuario");
        while (true){
            // Criar uma matricula aleatoria com 8 digitos
            String matricula = String.format("%0" + 8 + "d", new Random().nextInt(1, 100000000));
            if(!listaMatriculaExistente.contains(matricula)){
                // Aqui vai o preenchimento do TextField com a matricula
                break;
            }
        }
    }

    public void salvarUsuario(){
        // Para salvar o usario
//        conn.cadastrarUsuario();

        // Colocar no banco
//        if(){
//            Alerts.showAlert("Usuario salvo", null, "Usuario foi salvo com sucesso. \n\nLembre-se, a senha é padrão. \nSenha: 2rp", Alert.AlertType.INFORMATION);
//        }
//        else{
//            Alerts.showAlert("Erro", null, "Erro ao salvar o usuario. \nErro:\n"+erro, Alert.AlertType.ERROR);
//        }
    }

    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }


    /////     Metodo Privados     /////
    private boolean verificarPreenchimentosDosCampos(){
        try{
//            dataInicio.getValue().toString();
//            horasInicio.getValue().toString();
//            minutosInicio.getValue().toString();
//            dataFim.getValue().toString();
//            horasFim.getValue().toString();
//            minutosFim.getValue().toString();
//            // Se for Hora Extra então é obrigatório o campo Justificativa
//            return !campoCliente.getValue().equals("") && !campoEquipe.getValue().equals("") && !campoTipo.getValue().equals("") && !campoJustificativa.getText().equals("");

            return true; // so para nao dar erro enquanto não é implementado a tela
        }
        catch (Exception e){
            return false;
        }
    }

    private void limparCampos(){
//        dataInicio.setValue(null);
//        horasInicio.setValue(null);
//        minutosInicio.setValue(null);
//        dataFim.setValue(null);
//        horasFim.setValue(null);
//        minutosFim.setValue(null);
//        campoJustificativa.clear();
//        campoEquipe.setValue(null);
//        campoTipo.setValue(null);
//        campoCliente.setValue(null);
    }


    /////     Metodos Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificando acesso para todas as telas
//        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
//        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.consultaHora);
//        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);
    }
}
