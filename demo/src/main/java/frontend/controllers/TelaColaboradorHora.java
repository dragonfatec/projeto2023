package frontend.controllers;


import frontend.util.Contraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class TelaColaboradorHora implements Initializable {
    public Button btnRegistrarHora;
    public Button btnConsultar;
    public DatePicker campoData;
    public TextArea campoJustificativa;
    public ChoiceBox campoTipo;
    public Button btnConfirmar;
    public Button btnCancelar;
    public DatePicker campoData1;
    public ComboBox horasInicio;
    public ComboBox minutosInicio;
    public ComboBox horasFim;
    public ComboBox minutosFim;


    public void confirmarRegistroHora(ActionEvent actionEvent) {
    }

    public void cancelarRegistroHora(ActionEvent actionEvent) {
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

    }

}