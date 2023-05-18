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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistraHora implements Initializable {
    // Objetos
    ConnectionFactory conn = new ConnectionFactory();
    RegistroDataHora hora = new RegistroDataHora();

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

    // Metodos
    @FXML
    private void cancelarRegistroHora() {
        App.sair();
    }

    @FXML
    void confirmarRegistroHora(){
        ConnectionFactory conn = new ConnectionFactory();

        if (verificarPreenchimentosDosCampos()){

            String dataIn = dataInicio.getValue() + " " + horasInicio.getValue() + ":" + minutosInicio.getValue();
            String dataFm = dataFim.getValue() + " " + horasFim.getValue() + ":" + minutosFim.getValue();

            if (hora.vaidarDataESequencia(dataIn, dataFm)){

                conn.apontarHorasExtra(Usuario.getInstancia().getLogin(), dataIn+":00", dataFm+":00", campoEquipe.getValue(), campoTipo.getValue(), campoJustificativa.getText(), campoCliente.getValue());

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
//            boolean just = campoJustificativa.getText().equals("") && campoTipo.getValue().equals("Extra") ? false : true;
            return !campoCliente.getValue().equals("") && !campoEquipe.getValue().equals("") && !campoTipo.getValue().equals("") && campoJustificativa.getText().equals("");
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
//        if (usuario.getCargo().equalsIgnoreCase("usuario")){
//            btnAprovaHora.setVisible(false);
//        }


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
