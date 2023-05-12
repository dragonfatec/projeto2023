package frontend.controllers;

import backend.usuario.Usuario;
import frontend.util.Alerts;
import frontend.util.TabelaAprova;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AprovaHora implements Initializable {
    // Label
    @FXML public Label textoNomeUsuario;


    // Table
    @FXML public TableView<TabelaAprova> tabela;
    @FXML public TableColumn<TabelaAprova, String> colunaColaborador;
    @FXML public TableColumn<TabelaAprova, String> colunaDataHoraInicial;
    @FXML public TableColumn<TabelaAprova, String> colunaDataHoraFinal;
    @FXML public TableColumn<TabelaAprova, String> colunaCliente;
    @FXML public TableColumn<TabelaAprova, String> colunaTipo;
    @FXML public TableColumn<TabelaAprova, String> colunaTotalDeHoras;
    @FXML public TableColumn<TabelaAprova, CheckBox> colunaSelecione;

    // Button
    @FXML public Button btnRegistrarHora;
    @FXML public Button btnConsultar;
    @FXML public Button btnAprovar;
    @FXML public Button btnSelecionarTudo;
    @FXML public Button btnAprovaHora;
    @FXML public Button btnReprovar;

    // ChoiceBox
    @FXML public ChoiceBox campoEscolhaEquipe;

    // Metodos
    public void pegarJustificativa(){

    }

    public void aprovarHoras(ActionEvent actionEvent) {
    }

    public void reprovarHoras(ActionEvent actionEvent) {
    }

    public void selecionarTudo(ActionEvent actionEvent) {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        Usuario usuario = Usuario.getInstancia();

        ObservableList<TabelaAprova> listaHorasPendentes = FXCollections.observableArrayList();

        //Dados para Teste
        listaHorasPendentes.add(new TabelaAprova("Alec", "12/05/2023 18:00", "12/05/2023 19:00", "Americanas", "Sobreaviso", "01:00"));
        listaHorasPendentes.add(new TabelaAprova("Pedro", "12/05/2023 18:00", "12/05/2023 20:00", "Apple", "Hora Extra", "02:00"));
        listaHorasPendentes.add(new TabelaAprova("Lucas", "12/05/2023 18:00", "12/05/2023 18:30", "Americanas", "Sobreaviso", "00:30"));

        //Atribuição
        colunaColaborador.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("colaborador"));
        colunaDataHoraInicial.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("dataHoraInicial"));
        colunaDataHoraFinal.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("dataHoraFinal"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("cliente"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("tipo"));
        colunaTotalDeHoras.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("totalDeHoras"));
        colunaSelecione.setCellValueFactory(new PropertyValueFactory<TabelaAprova, CheckBox>("selecione"));

        tabela.setItems(listaHorasPendentes);

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
