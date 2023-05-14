package frontend.controllers;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.NomesArquivosFXML;
import frontend.util.Tabela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultaHora implements Initializable {
    public Button btnAprovaHora;
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

    // Metodos
    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // instancia do usuario
        Usuario usuario = Usuario.getInstancia();
        if (usuario.getCargo().equals("Usuario")){
            btnAprovaHora.setVisible(false);
        }


        ObservableList<Tabela> tabelasObjetoLista = FXCollections.observableArrayList();

        ConnectionFactory conn = new ConnectionFactory();

        tabelasObjetoLista.addAll(conn.getHorasUsuario(usuario.getLogin()));

        // TESTE
//        tabelasObjetoLista.add(new Tabela("02/05/2023 12:00","02/05/2023 15:00","Cliente","Aprovado"));
//        tabelasObjetoLista.add(new Tabela("02/02/2023 12:00","02/02/2023 15:00","Cliente","Em andamento"));
//        tabelasObjetoLista.add(new Tabela("02/04/2023 12:00","02/04/2023 15:00","Cliente","Reprovado"));
//        tabelasObjetoLista.add(new Tabela("02/05/2023 12:00","02/05/2023 15:00","Cliente","Aprovado"));


        colunaDataHoraInicial.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataInicio"));
        colunaDataHoraFinal.setCellValueFactory(new PropertyValueFactory<Tabela, String>("dataFim"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<Tabela, String>("cliente"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<Tabela, String>("status"));

        tabela.setItems(tabelasObjetoLista);

        textoNomeUsuario.setText("Olá "+ usuario.getNome() + "!");
    }
}
