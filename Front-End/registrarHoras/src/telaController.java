package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class HelloController {
    // Label


    // Input
    @FXML
    private DatePicker campoData;
    @FXML
    private TextField campoHoraFinal;
    @FXML
    private TextField campoHoraInicial;
    @FXML
    private TextField campoJustificativa;
    @FXML
    private ChoiceBox<?> campoTipo;

    // Button
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnConsultar;
    @FXML
    private Button btnRegistrarHora;


    @FXML
    void cancelarRegistroHora(ActionEvent event) {

    }

    @FXML
    void confirmarRegistroHora(ActionEvent event) {
        Data dataClass = new Data();

        String dataI = dataClass.transformarData(campoData.getValue(), "dd/MM/yyyy") +
                " " +
                campoHoraInicial.getText();

        String dataF = dataClass.transformarData(campoData.getValue(), "dd/MM/yyyy") +
                " " +
                campoHoraFinal.getText();

        System.out.println("validando a data...");
        System.out.println("resultado da validação Inicial - validar: " + dataClass.validarData(dataI));
        System.out.println("resultado da validação Fianl - validar: " + dataClass.validarData(dataF));
        System.out.println("resultado da validação - sequencial: " + dataClass.verificarSequenciaDatas(dataI, dataF));
    }
}
