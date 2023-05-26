package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.NomesArquivosFXML;
import frontend.util.Tabela;
import frontend.util.VerificaAcesso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultaHora implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();

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
    @FXML Button btnAprovaHora;


    /////     Metodos Publicos     /////
    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }


    /////     Metodo Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificando acesso para todas as telas
        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.admin);
        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);

        // Preenchendo a tabela com as horas lançadas do usuario
        ObservableList<Tabela> tabelasObjetoLista = FXCollections.observableArrayList();
        tabelasObjetoLista.addAll(conn.getHorasUsuario(usuario.getMatricula()));

        // TESTE
//        tabelasObjetoLista.add(new Tabela("02/05/2023 12:00","02/05/2023 15:00","Cliente","Aprovado"));
//        tabelasObjetoLista.add(new Tabela("02/02/2023 12:00","02/02/2023 15:00","Cliente","Em andamento"));
//        tabelasObjetoLista.add(new Tabela("02/04/2023 12:00","02/04/2023 15:00","Cliente","Reprovado"));
//        tabelasObjetoLista.add(new Tabela("02/05/2023 12:00","02/05/2023 15:00","Cliente","Aprovado"));

        // Para cada campo da tabela vamos "conectar" com um parametro da classe Tabela
        colunaDataHoraInicial.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataInicio"));
        colunaDataHoraFinal.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataFim"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<Tabela, String>("cliente"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<Tabela, String>("status"));
        tabela.setItems(tabelasObjetoLista);

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
