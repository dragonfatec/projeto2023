package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AprovaHora implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();

    // Label
    @FXML public Label textoNomeUsuario;

    // TextArea
    @FXML public TextArea campoJustificativa;

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


    /////     Metodos Publicos     /////
    public void aprovarHoras(ActionEvent actionEvent) {
        salvarHorasAvaliadas("Aprovada", campoJustificativa.getText().trim().equals("") ? "-" : campoJustificativa.getText());
    }

    public void reprovarHoras(ActionEvent actionEvent) {
        if(!campoJustificativa.getText().trim().equals("")){
            salvarHorasAvaliadas("Reprovada", campoJustificativa.getText());
        }
        else{
            Alerts.showAlert("Erro", null, "O campo Justificativa é obrigatório quando as horas é reprovada", Alert.AlertType.WARNING);
        }
    }

    public void selecionarTudo(ActionEvent actionEvent) {
        for (TabelaAprova tb : tabela.getItems()){
            tb.getSelecione().setSelected(true);
        }
    }

    public void atualizarTabela(ActionEvent event) {
//        Integer id_equipe = conn.getListaColuna(campoEscolhaEquipe.getValue().toString(),"equipe");
        Integer id_equipe = Integer.parseInt(conn.getColuna("equipe", "id_equipe", "nome_equipe", campoEscolhaEquipe.getValue().toString()));
//        ObservableList<TabelaAprova> listaHorasPendentes = FXCollections.observableArrayList(conn.getHoraEquipe(id_equipe));
//        listaHorasPendentes.addAll(conn.getHoraEquipe(id_equipe));
        tabela.setItems(FXCollections.observableArrayList(conn.getHoraEquipe(id_equipe,Usuario.getInstancia().getCargo())));
    }

    public void irParaConsultaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.admin + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }


    /////     Metodos Privados     /////
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

    private void salvarHorasAvaliadas(String status, String justificativa){
        for (TabelaAprova tb : tabela.getItems()){
            if (tb.getSelecione().isSelected()){
                conn.atualizarStatus("hora", "status", status, "id_hora = " + tb.getId(), false);
                conn.atualizarStatus("hora", "justificativa_status", justificativa, "id_hora = " + tb.getId(), false);
            }
        }
        Alerts.showAlert("Avaliação alterada!", null, "Avaliação foi "+ status + " com sucesso!", Alert.AlertType.INFORMATION);
        atualizarTabela(new ActionEvent());
    }


    /////     Metodo Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Verificando acesso para todas as telas
        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.admin);
        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);

        // Para preencher o campo de equipe
        campoEscolhaEquipe.getItems().addAll(conn.getListaColuna(usuario.getMatricula(),"equipe"));

        // Dados para Teste
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

    public void irParaCadastra(MouseEvent mouseEvent) {
        
    }
}
