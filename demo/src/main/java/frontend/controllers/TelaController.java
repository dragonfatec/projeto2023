package frontend.controllers;

import database.ConexaoDAO;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.Contraints;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

// packages
import backend.usuario.Usuario;

public class TelaController implements Initializable {
    // Objetos
    Usuario usuario = Usuario.getInstancia();

    // Label
    
    // Input - Texto
    @FXML
    private TextField campoHoraFinal;
    @FXML
    private TextField campoHoraInicial;
    @FXML
    private TextField campoJustificativa;

    // Input - Data
    @FXML
    private DatePicker campoData;

    // Input - Select
    @FXML
    private ChoiceBox<String> campoTipo;

    // Button
    @FXML
    private Button btnConsultar;
    @FXML
    private Button btnRegistrarHora;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConfirmar;

    // Metodos

    @FXML
    private void cancelarRegistroHora() {
//        btnCancelar.setOnAction(actionEvent -> Platform.exit());
        try {
            App.mudarTela("colaborador_consulta.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void confirmarRegistroHora(){
            try{
                String horaInicial = campoHoraInicial.getText();
                String horaFinal = campoHoraFinal.getText();
                LocalDate data = campoData.getValue();
                Date sqlDate = Date.valueOf(data);
                String tipoHora = campoTipo.getValue();
                String justificativa = campoJustificativa.getText();

                String sql = "INSERT INTO hora(data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES (" +
                        sqlDate +
                        horaInicial +
                        horaFinal +
                        justificativa +
                        tipoHora +
                        ")";
                ConexaoDAO con = new ConexaoDAO();
                con.inserirDados(sql);

            }
            catch (Exception e){
                Alerts.showAlert("ERRO", null, "Por favor, preencha corretamente todos os campos\n ERRO:"+e, Alert.AlertType.ERROR);
            }

//            try {
//                ConnectionFactory conn = new ConnectionFactory();
//
//                PreparedStatement statement = conn.recuperaConexao().prepareStatement("INSERT INTO hora(data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES (?, ?, ?, ?, ?)");
//                statement.setDate(1, sqlDate);
//                statement.setString(2, horaInicial);
//                statement.setString(3, horaFinal);
//                statement.setString(4, justificativa);
//                statement.setString(5, tipoHora);
//
//                boolean confirmado = Alerts.showAlert("Confirma", null, "Deseja confirmar?", Alert.AlertType.CONFIRMATION);
//                if (confirmado) {
//                    statement.executeUpdate();
//                    statement.close();
//
//                    campoData.setValue(null);
//                    campoTipo.setValue(null);
//                    campoHoraFinal.clear();
//                    campoHoraInicial.clear();
//                    campoJustificativa.clear();
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> tiposDeHora = FXCollections.observableArrayList("Extra","Sobreaviso");
        campoTipo.setItems(tiposDeHora);

        Contraints.setTextFieldDouble(campoHoraInicial);
        Contraints.setTextFieldDouble(campoHoraFinal);
        Contraints.setTextFieldMaxLength(campoHoraInicial, 5);
        Contraints.setTextFieldMaxLength(campoHoraFinal, 5);
    }
}
