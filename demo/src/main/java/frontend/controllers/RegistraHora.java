package frontend.controllers;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistraHora implements Initializable {
    public Button btnAprovaHora;
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

    // Metodos
    @FXML
    private void cancelarRegistroHora() {
        App.sair();
    }

    @FXML
    void confirmarRegistroHora(){
        ConnectionFactory conn = new ConnectionFactory();

//        try{
//            String dataIn = dataInicio.getValue().toString();
//            String horaIn = horasInicio.getValue().toString();
//            String minutoIn = minutosInicio.getValue().toString();
//
//            String dataF = dataFim.getValue().toString();
//            String horaF = horasFim.getValue().toString();
//            String minutoF = minutosFim.getValue().toString();
//            String dtInicio = dataIn + " " + horaIn +":"+ minutoIn +":00";
//            String dtFim = dataF + " " + horaF +":"+ minutoF +":00";
//
//            String cliente = campoCliente.getValue();
//            String equipe = campoEquipe.getValue();
//            String tipoHora = campoTipo.getValue();
//            String justificativa = campoJustificativa.getText();
//
//            System.out.println("a data"+dataIn);
//        }
//        catch (Exception e){
//           Alerts.showAlert("Aviso!", null, "Preencher todos os campos!", Alert.AlertType.WARNING);
//        }


        RegistroDataHora dt = new RegistroDataHora();
        if (verificarPreenchimentosDosCampos()){
//            String[] dataInicioSemFormatar = dataInicio.getValue().toString().split("-");
//            String dataIn = dataInicioSemFormatar[2] + "/" + dataInicioSemFormatar[1] + "/" + dataInicioSemFormatar[0] + " " + horasInicio.getValue().toString() + ":" + minutosInicio.getValue().toString();
//            String[] dataFimSemFormatar = dataFim.getValue().toString().split("-");
//            String dataFm = dataFimSemFormatar[2] + "/" + dataFimSemFormatar[1] + "/" + dataFimSemFormatar[0] + " " + horasFim.getValue().toString() + ":" + minutosFim.getValue().toString();

            String dataIn = dataInicio.getValue() + " " + horasInicio.getValue() + ":" + minutosInicio.getValue();
            String dataFm = dataFim.getValue() + " " + horasFim.getValue() + ":" + minutosFim.getValue();

            if (dt.vaidarDataESequencia(dataIn, dataFm)){

                conn.apontarHorasExtra(Usuario.getInstancia().getLogin(), dataIn+":00", dataFm+":00", campoEquipe.getValue(), campoTipo.getValue(), campoJustificativa.getText(), campoCliente.getValue());
//                conn.apontarHorasExtra(Usuario.getInstancia().getLogin(), "2023-05-11 01:00:00", "2023-05-11 05:00:00", campoEquipe.getValue(), campoTipo.getValue(), campoJustificativa.getText(), campoCliente.getValue());
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

    private boolean verificarPreenchimentosDosCampos(){
        try{
            dataInicio.getValue().toString();
            horasInicio.getValue().toString();
            minutosInicio.getValue().toString();
            dataFim.getValue().toString();
            horasFim.getValue().toString();
            minutosFim.getValue().toString();
            // Se for Hora Extra então é obrigatório o campo Justificativa
            boolean just = campoJustificativa.getText().equals("") && campoTipo.getValue().equals("Extra") ? false : true;
            return !campoCliente.getValue().equals("") && !campoEquipe.getValue().equals("") && !campoTipo.getValue().equals("") && just;
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

    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaConsultaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.consultaHora + ".fxml");
    }

    public void atualizarCliente(ActionEvent actionEvent) {
        ConnectionFactory conn = new ConnectionFactory();
        ObservableList<String> clientes = FXCollections.observableArrayList(conn.getCliente(campoEquipe.getValue()));
        campoCliente.setItems(clientes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Pegando a instancia do usuario
        Usuario usuario = Usuario.getInstancia();
        if (usuario.getCargo().equals("Usuario")){
            btnAprovaHora.setVisible(false);
        }

        ConnectionFactory conn = new ConnectionFactory();

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

        ObservableList<String> tiposDeHora = FXCollections.observableArrayList("Extra","Sobreaviso");
        campoTipo.setItems(tiposDeHora);

        ObservableList<String> minutos = FXCollections.observableArrayList(minutosLista);
        minutosInicio.setItems(minutos);
        minutosFim.setItems(minutos);

        ObservableList<String> horas = FXCollections.observableArrayList(horasLista);
        horasInicio.setItems(horas);
        horasFim.setItems(horas);

        campoEquipe.getItems().addAll(conn.getEquipe(usuario.getLogin()));

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}