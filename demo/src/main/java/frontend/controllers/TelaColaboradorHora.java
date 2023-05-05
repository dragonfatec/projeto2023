package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
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

public class TelaColaboradorHora implements Initializable {
    // Texto
    public Label textoTipoCargo;

    // Input - TextField


    // input - TextArea
    public TextArea campoJustificativa;

    // Input - Data
    public DatePicker dataInicio;
    public DatePicker dataFim;

    // ChoiceBox
    public ChoiceBox campoTipo;
    public ComboBox horasInicio;
    public ComboBox minutosInicio;
    public ComboBox horasFim;
    public ComboBox minutosFim;

    // Botão
    public Button btnRegistrarHora;
    public Button btnConsultar;
    public Button btnConfirmar;
    public Button btnCancelar;

    // Metodos

    @FXML
    private void cancelarRegistroHora() {
//        btnCancelar.setOnAction(actionEvent -> Platform.exit());
        try {
            App.mudarTela("colaborador_consulta.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void confirmarRegistroHora(){

//            try{
                String horaInicio = horasInicio.getValue().toString();
                String horaFim = horasFim.getValue().toString();
                String dtInicio = dataInicio.getValue().toString();
                String dtFim = dataFim.getValue().toString();
                String tipoHora = campoTipo.getValue().toString();
                String justificativa = campoJustificativa.getText();

//                String sql = "INSERT INTO hora(dt_init, hora_inicio, hora_fim, justificativa, tipo_hora) VALUES ('" +
//                         sqlDate +"','"+
//                         horaInicial +"','"+
//                         horaFinal +"','"+
//                         justificativa +"','"+
//                        tipoHora +"'"+
//                        ")";

//                StringBuilder sql = new StringBuilder();
//                sql.append("INSERT INTO hora(dt_init, hora_inicio, hora_fim, justificativa, tipo_hora)")
//                        .append("VALUES (")
//                        .append("'").append(dtInicio).append("'")
//                        .append("'").append(horaInicio).append("'")
//                        .append("'").append(dtFim).append("'")
//                        .append("'").append(horaFim).append("'")
//                        .append("'").append(tipoHora).append("'")
//                        .append("'").append(justificativa).append("'")
//                        .append(")");

//                ConnectionFactory con = new ConnectionFactory();
//                con.inserirDados(sql);
//                Alerts.showAlert("ERRO", null, "Apontado com sucesso!\n ERRO:", Alert.AlertType.ERROR);
//
//            }
//            catch (Exception e){
//                Alerts.showAlert("ERRO", null, "Por favor, preencha corretamente todos os campos\n ERRO:"+e, Alert.AlertType.ERROR);
//            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> tiposDeHora = FXCollections.observableArrayList("Extra","Sobreaviso");
        campoTipo.setItems(tiposDeHora);

        ArrayList<String> minutosLista = new ArrayList<>();
        ArrayList<String> horasLista = new ArrayList<>();
        for (int i = 0; i <= 60; i++) {
            minutosLista.add(Integer.toString(i));

            if (i <= 24){
                horasLista.add(Integer.toString(i));
            }
        }

        ObservableList<String> minutos = FXCollections.observableArrayList(minutosLista);
        minutosInicio.setItems(minutos);
        minutosFim.setItems(minutos);

        ObservableList<String> horas = FXCollections.observableArrayList(horasLista);
        horasInicio.setItems(horas);
        horasFim.setItems(horas);



        // TESTE
        Usuario.criarInstancia("Lukas", "12345","23oj2","Lukas", "gestor");

        Usuario usuario = Usuario.getInstancia();

        if (usuario.getTipo().equalsIgnoreCase("colaborador")){
            textoTipoCargo.setText("Olá Colaborador!");
        }
        else{
            textoTipoCargo.setText("Olá Gestor!");
        }

    }

}