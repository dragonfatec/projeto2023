package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
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
import java.util.ResourceBundle;

public class RegistraHora implements Initializable {
    // Texto
    @FXML Label textoNomeUsuario;

    // Input - TextField


    // input - TextArea
    @FXML TextArea campoJustificativa;

    // Input - Data
    @FXML DatePicker dataInicio;
    @FXML DatePicker dataFim;

    // ChoiceBox
    @FXML ChoiceBox campoTipo;
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

        String dataIn = dataInicio.getValue().toString();
        String horaIn = horasInicio.getValue().toString();
        String minutoIn = minutosInicio.getValue().toString();

        String dataF = dataFim.getValue().toString();
        String horaF = horasFim.getValue().toString();
        String minutoF = minutosFim.getValue().toString();

        String dtInicio = dataIn + " " + horaIn +":"+ minutoIn +":00";
        String dtFim = dataF + " " + horaF +":"+ minutoF +":00";

        System.out.println(dtInicio);

        conn.apontarHoras(Usuario.getInstancia(),dtInicio,dtFim,campoEquipe.getValue(),campoTipo.getTypeSelector());

//        conn.apontarHoras(Usuario.getInstancia(), horaInicio, horaFim, );
//            try{
//                String horaInicio = horasInicio.getValue().toString();
//                String horaFim = horasFim.getValue().toString();
//                String dtInicio = dataInicio.getValue().toString();
//                String dtFim = dataFim.getValue().toString();
//                String tipoHora = campoTipo.getValue().toString();
//                String justificativa = campoJustificativa.getText();

//                ConnectionFactory.
    }

    public void consultarHoras(ActionEvent actionEvent) {
        try {
            App.mudarTela(NomesArquivosFXML.consultaHora + ".fxml");
        } catch (IOException e) {
            System.out.println("deu erro");
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionFactory conn = new ConnectionFactory();

        ObservableList<String> tiposDeHora = FXCollections.observableArrayList("Extra","Sobreaviso");
        campoTipo.setItems(tiposDeHora);

        ArrayList<String> minutosLista = new ArrayList<>();
        ArrayList<String> horasLista = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10)
                minutosLista.add("0" + Integer.toString(i));
            else
                minutosLista.add(Integer.toString(i));

            if (i <= 24){
                if (i < 10)
                    horasLista.add("0" + Integer.toString(i));
                else
                    horasLista.add(Integer.toString(i));
            }
        }

        ObservableList<String> minutos = FXCollections.observableArrayList(minutosLista);
        minutosInicio.setItems(minutos);
        minutosFim.setItems(minutos);

        ObservableList<String> horas = FXCollections.observableArrayList(horasLista);
        horasInicio.setItems(horas);
        horasFim.setItems(horas);

        campoEquipe.getItems().addAll(conn.getEquipe());
        campoCliente.getItems().addAll(conn.getCliente());

        // TESTE
        Usuario.criarInstancia("Lukas", "12345","23oj2","Lukas", "gestor",1);

        Usuario usuario = Usuario.getInstancia();

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");

    }


}