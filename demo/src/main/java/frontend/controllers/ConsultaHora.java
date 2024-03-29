package frontend.controllers;

import backend.equipe.Equipe;
import backend.usuario.TiposDeUsuario;
import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import frontend.util.Tabela;
import frontend.util.VerificaAcesso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultaHora implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();
    public ChoiceBox campoSelecionaUsuario;

    // Texto
    @FXML Label textoNomeUsuario;

    // Tabelas e Colunas
    @FXML TableView<Tabela> tabela;
    @FXML TableColumn<Tabela, String> colunaDataHoraInicial;
    @FXML TableColumn<Tabela, String> colunaDataHoraFinal;
    @FXML TableColumn<Tabela, String> colunaCliente;
    @FXML TableColumn<Tabela, String> colunaStatus;

    // Botões
    @FXML Button btnRegistrarHora;
    @FXML Button btnConsultar;
    @FXML Button btnConsultar1;
    @FXML Button btnAprovaHora;
    @FXML Button exportExcel;
    @FXML Button btnCadastra;
    @FXML Button btnEdita;


    /////     Metodos Publicos     /////
    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }

    public void mudarParaEdita() throws IOException {
        Admin.qualTelaIniciar = "edita";
        App.mudarTela(NomesArquivosFXML.admin + ".fxml");
    }
    public void mudarParaCadastra() throws IOException {
        Admin.qualTelaIniciar = "cadastra";
        App.mudarTela(NomesArquivosFXML.admin + ".fxml");
    }
    public void generateCsv(){
        Usuario userSelecionado = conn.getUsuario(campoSelecionaUsuario.getValue().toString().split("-")[0].trim());
        String caminhoDeOrigem = "C:\\Users\\lucas\\Codigos\\Java\\projeto2023\\demo\\output\\"+ userSelecionado.getNome() + ".csv";
        ArrayList<String> lista = new ArrayList<>();
        for (String user : FXCollections.observableArrayList(conn.getInfoCSV(userSelecionado.getMatricula()))){
            lista.add(user);
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoDeOrigem))){
            bw.write("matricula,nome,verba,horas,cliente,CR,projeto");
            bw.newLine();
//            bw.write(userSelecionado.getMatricula().toString());
            for (String info: lista){
                bw.write(info);
                bw.newLine();
            }
            Alerts.showAlert("Sucesso!", null, "O arquivo foi exportado com sucesso!\nPasta:  "+caminhoDeOrigem, Alert.AlertType.INFORMATION);
        }
        catch (IOException e){
            System.out.println("Error writing: " + e.getMessage());
            Alerts.showAlert("Erro", null, "Não foi possivel exportar o arquivo", Alert.AlertType.ERROR);
        }
    }



    /////     Metodo Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificando acesso para todas as telas
        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.consultaHora);
        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);

        // Preenchendo a tabela com as horas lançadas do usuario
        ObservableList<Tabela> tabelasObjetoLista = FXCollections.observableArrayList();
        tabelasObjetoLista.addAll(conn.getHorasUsuario(usuario.getMatricula()));

        // Para cada campo da tabela vamos "conectar" com um parametro da classe Tabela
        colunaDataHoraInicial.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataInicio"));
        colunaDataHoraFinal.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataFim"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<Tabela, String>("cliente"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<Tabela, String>("status"));
        tabela.setItems(tabelasObjetoLista);

        if (usuario.getCargo().equals(TiposDeUsuario.RH)){
            exportExcel.setVisible(true);
            campoSelecionaUsuario.setVisible(true);
            campoSelecionaUsuario.getItems().addAll(conn.getListaColuna(null, "usuario-matriculas"));
            btnCadastra.setVisible(true);
            btnEdita.setVisible(true);
            btnConsultar1.setVisible(true);
            btnConsultar.setVisible(false);
        }
        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }

    public void mudarUsuario(ActionEvent event) {
        try{
            Usuario userSelecionado = conn.getUsuario(campoSelecionaUsuario.getValue().toString().split("-")[0].trim());
            tabela.setItems(FXCollections.observableArrayList(conn.getHorasUsuario(userSelecionado.getMatricula())));
        }catch (Exception ignored){

        }
    }

}
