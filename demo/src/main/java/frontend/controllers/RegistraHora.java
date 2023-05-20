package frontend.controllers;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import frontend.util.VerificaAcesso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistraHora implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final RegistroDataHora hora = new RegistroDataHora();
    private final Usuario usuario = Usuario.getInstancia();

    // Texto
    @FXML Label textoNomeUsuario;

    // Input - TextField


    // input - TextArea
    @FXML TextArea campoJustificativa;

    // Input - Data
    @FXML DatePicker dataInicio;
    @FXML DatePicker dataFim;

    // ChoiceBox
    @FXML ChoiceBox<String> campoTipo;
    @FXML ComboBox horasInicio;
    @FXML ComboBox minutosInicio;
    @FXML ComboBox horasFim;
    @FXML ComboBox minutosFim;
    @FXML ChoiceBox<String> campoEquipe;
    @FXML ChoiceBox<String> campoCliente;

    // Botão
    @FXML Button btnRegistrarHora;
    @FXML Button btnConsultar;
    @FXML Button btnConfirmar;
    @FXML Button btnCancelar;
    @FXML public Button btnAprovaHora;


    /////     Metodos Publicos     /////
    public void confirmarRegistroHora(){

        if (verificarPreenchimentosDosCampos()){

            String dataIn = dataInicio.getValue() + " " + horasInicio.getValue() + ":" + minutosInicio.getValue();
            String dataFm = dataFim.getValue() + " " + horasFim.getValue() + ":" + minutosFim.getValue();

            if (hora.vaidarDataESequencia(dataIn, dataFm)){

                conn.apontarHorasExtra(usuario.getMatricula(), dataIn+":00", dataFm+":00", campoEquipe.getValue(), campoTipo.getValue(), campoJustificativa.getText(), campoCliente.getValue());

                Alerts.showAlert("Sucesso!",null,"Apontamento realizado com Sucesso!", Alert.AlertType.INFORMATION);
                limparCampos();
            }
            else{
                Alerts.showAlert("Aviso!", null, "As datas tem que ser sequenciais! \nA data inicio tem que ser menor que a data fim,\n e a data fim tem que ser menor ou igual agora.", Alert.AlertType.WARNING);
            }
        }
        else{
            Alerts.showAlert("Aviso!", null, "Preencher todos os campos!", Alert.AlertType.WARNING);
        }
    }

    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaConsultaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.consultaHora + ".fxml");
    }

    public void atualizarCliente(ActionEvent actionEvent) {
        campoCliente.setItems(FXCollections.observableArrayList(conn.getListaColuna(campoEquipe.getValue(), "cliente")));
    }


    /////     Metodos Privados     /////
    @FXML
    private void cancelarRegistroHora() {
        App.sair();
    }

    private boolean verificarPreenchimentosDosCampos(){
        try{
            dataInicio.getValue().toString();
            horasInicio.getValue().toString();
            minutosInicio.getValue().toString();
            dataFim.getValue().toString();
            horasFim.getValue().toString();
            minutosFim.getValue().toString();
            // Se for Hora Extra então é obrigatório o campo Justificativa
            return !campoCliente.getValue().equals("") && !campoEquipe.getValue().equals("") && !campoTipo.getValue().equals("") && !campoJustificativa.getText().equals("");
        }
        catch (Exception e){
            return false;
        }
    }

    private void limparCampos(){
        dataInicio.setValue(null);
        horasInicio.setValue(null);
        minutosInicio.setValue(null);
        dataFim.setValue(null);
        horasFim.setValue(null);
        minutosFim.setValue(null);
        campoJustificativa.clear();
        campoEquipe.setValue(null);
        campoTipo.setValue(null);
        campoCliente.setValue(null);
    }


    /////     Metodo Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Verificando acesso para todas as telas
        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.consultaHora);
        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);

        ArrayList<String> minutosLista = new ArrayList<>();
        ArrayList<String> horasLista = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10)
                minutosLista.add("0" + i);
            else
                minutosLista.add(Integer.toString(i));

            if (i < 24){
                if (i < 10)
                    horasLista.add("0" + i);
                else
                    horasLista.add(Integer.toString(i));
            }
        }

        campoTipo.setItems(FXCollections.observableArrayList("Extra","Sobreaviso"));

        // Select dos campos data
        minutosInicio.setItems(FXCollections.observableArrayList(minutosLista));
        minutosFim.setItems(FXCollections.observableArrayList(minutosLista));

        horasInicio.setItems(FXCollections.observableArrayList(horasLista));
        horasFim.setItems(FXCollections.observableArrayList(horasLista));

        // Preenchendo o campo equipe
        campoEquipe.getItems().addAll(conn.getListaColuna(usuario.getMatricula(),"equipe"));

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
