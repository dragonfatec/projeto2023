package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import frontend.util.Tabela;
import frontend.util.TabelaAprova;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<TabelaAprova> getHorasSelecionada(){
        ArrayList<TabelaAprova> horasSelecionada = new ArrayList<>();

        for (TabelaAprova tb : tabela.getItems()){
            if (tb.getSelecione().isSelected()){
                horasSelecionada.add(tb);
                System.out.println(tb.getColaborador()+" foi selecionado para aprovar/reprovar as horas");
            }
        }
        return horasSelecionada;
    }

    public void pegarJustificativa(){

    }

    public void aprovarHoras(ActionEvent actionEvent) {
        getHorasSelecionada();
    }

    public void reprovarHoras(ActionEvent actionEvent) {
    }

    public void selecionarTudo(ActionEvent actionEvent) {
        
    }

    public void atualizarTabela(ActionEvent event) {
        ConnectionFactory conn = new ConnectionFactory();

        Integer id_equipe = conn.getIdEquipe(campoEscolhaEquipe.getValue().toString());

        ObservableList<TabelaAprova> listaHorasPendentes = FXCollections.observableArrayList();
        listaHorasPendentes.addAll(conn.getHoraEquipe(id_equipe));
        tabela.setItems(listaHorasPendentes);
    }

    public void irParaConsultaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.consultaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

//        Usuario.criarInstancia("lukas", "lukas321", "54321", "Lukas Fernando","Usuario",1);

        Usuario usuario = Usuario.getInstancia();

        ConnectionFactory conn = new ConnectionFactory();

        // Para preencher o campo de equipe
        campoEscolhaEquipe.getItems().addAll(conn.getEquipe(usuario.getLogin()));

        //Dados para Teste
//        ObservableList<TabelaAprova> listaHorasPendentes = FXCollections.observableArrayList();
//        listaHorasPendentes.add(new TabelaAprova("Alec", "12/05/2023 18:00", "12/05/2023 19:00", "Americanas", "Sobreaviso", "01:00"));
//        listaHorasPendentes.add(new TabelaAprova("Pedro", "12/05/2023 18:00", "12/05/2023 20:00", "Apple", "Hora Extra", "02:00"));
//        listaHorasPendentes.add(new TabelaAprova("Lucas", "12/05/2023 18:00", "12/05/2023 18:30", "Americanas", "Sobreaviso", "00:30"));
//        tabela.setItems(listaHorasPendentes);

        // Atribuição
        colunaColaborador.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("colaborador"));
        colunaDataHoraInicial.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("dataHoraInicial"));
        colunaDataHoraFinal.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("dataHoraFinal"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("cliente"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("tipo"));
        colunaTotalDeHoras.setCellValueFactory(new PropertyValueFactory<TabelaAprova, String>("totalDeHoras"));
        colunaSelecione.setCellValueFactory(new PropertyValueFactory<TabelaAprova, CheckBox>("selecione"));

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
