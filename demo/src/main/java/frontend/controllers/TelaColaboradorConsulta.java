package frontend.controllers;

import frontend.util.Tabela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaColaboradorConsulta implements Initializable {
    // Texto

    // Tabelas e Colunas
    @FXML
    TableView<Tabela> tabela;
    @FXML
    TableColumn<Tabela, String> colunaData;
    @FXML
    TableColumn<Tabela, String> colunaHoraInicio;
    @FXML
    TableColumn<Tabela, String> colunaHoraFim;
    @FXML
    TableColumn<Tabela, String> colunaProjeto;
    @FXML
    TableColumn<Tabela, String> colunaStatus;

    // Botões
    public Button btnRegistrarHora;
    public Button btnConsultar;

    ObservableList<Tabela> tabelasObjetoLista = FXCollections.observableArrayList();
    // Metodos



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        tabelasObjetoLista.add(new Tabela("02/05/2023","12:00","15:00","Projeto Batata","Aprovado"));
        tabelasObjetoLista.add(new Tabela("02/02/2023","12:00","15:00","Projeto Macarrão","Em andamento"));
        tabelasObjetoLista.add(new Tabela("02/04/2023","12:00","15:00","Projeto Sorvete","Reprovado"));
        tabelasObjetoLista.add(new Tabela("02/05/2023","12:00","15:00","Projeto Pizza","Aprovado"));

        colunaData.setCellValueFactory(new PropertyValueFactory<Tabela, String>("data"));
        colunaHoraInicio.setCellValueFactory(new PropertyValueFactory<Tabela, String>("horaInicio"));
        colunaHoraFim.setCellValueFactory(new PropertyValueFactory<Tabela, String>("horaFim"));
        colunaProjeto.setCellValueFactory(new PropertyValueFactory<Tabela, String>("projeto"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<Tabela, String>("status"));

        tabela.setItems(tabelasObjetoLista);

    }
}
