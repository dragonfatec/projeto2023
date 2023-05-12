package frontend.controllers;

import backend.usuario.Usuario;
import frontend.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AprovaHora implements Initializable {
    // Label
    @FXML public Label textoNomeUsuario;


    // Table
    @FXML public TableView tabela;
    @FXML public TableColumn colunaColaborador;
    @FXML public TableColumn colunaDataHoraInicial;
    @FXML public TableColumn colunaDataHoraFinal;
    @FXML public TableColumn colunaCliente;
    @FXML public TableColumn colunaHoraExtra;
    @FXML public TableColumn ColunaSobreaviso;

    // Button
    @FXML public Button btnRegistrarHora;
    @FXML public Button btnConsultar;
    @FXML public Button btnAprovar;
    @FXML public Button btnSelecionarTudo;
    @FXML public Button btnAprovaHora;
    @FXML public Button btnReprovar;

    // ChoiceBox
    @FXML public ChoiceBox campoEscolhaEquipe;
    @FXML public CheckBox checkboxLinha1;
    @FXML public CheckBox checkboxLinha2;
    @FXML public CheckBox checkboxLinha3;
    @FXML public CheckBox checkboxLinha4;
    @FXML public CheckBox checkboxLinha5;
    @FXML public CheckBox checkboxLinha6;
    @FXML public CheckBox checkboxLinha7;
    @FXML public CheckBox checkboxLinha8;
    @FXML public CheckBox checkboxLinha9;
    @FXML public CheckBox checkboxLinha10;
    @FXML public CheckBox checkboxLinha11;
    @FXML public CheckBox checkboxLinha12;
    @FXML public CheckBox checkboxLinha13;
    @FXML public CheckBox checkboxLinha14;
    @FXML public CheckBox checkboxLinha15;
    @FXML public CheckBox checkboxLinha16;

    // Listas
    ArrayList<CheckBox> listaDeCheckBox = new ArrayList<>();

    // Metodos
    public void pegarJustificativa(){

    }

    public void aprovarHoras(ActionEvent actionEvent) {
    }

    public void reprovarHoras(ActionEvent actionEvent) {
    }

    public void selecionarTudo(ActionEvent actionEvent) {
        for (CheckBox cb : listaDeCheckBox){
            cb.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Adicionando todos os CheckBox em uma lista
        listaDeCheckBox.add(checkboxLinha1);
        listaDeCheckBox.add(checkboxLinha2);
        listaDeCheckBox.add(checkboxLinha3);
        listaDeCheckBox.add(checkboxLinha4);
        listaDeCheckBox.add(checkboxLinha5);
        listaDeCheckBox.add(checkboxLinha6);
        listaDeCheckBox.add(checkboxLinha7);
        listaDeCheckBox.add(checkboxLinha8);
        listaDeCheckBox.add(checkboxLinha9);
        listaDeCheckBox.add(checkboxLinha10);
        listaDeCheckBox.add(checkboxLinha11);
        listaDeCheckBox.add(checkboxLinha12);
        listaDeCheckBox.add(checkboxLinha13);
        listaDeCheckBox.add(checkboxLinha14);
        listaDeCheckBox.add(checkboxLinha15);
        listaDeCheckBox.add(checkboxLinha16);

        Usuario usuario = Usuario.getInstancia();

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
