package frontend.controllers;

import database.conexao.ConnectionFactory;
import frontend.util.Alerts;
import frontend.util.Contraints;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TelaController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private void cancelarRegistroHora() {
        btnCancelar.setOnAction(actionEvent -> Platform.exit());
    }


    @FXML
    private Button btnConfirmar;


    @FXML
    void confirmarRegistroHora(){
            String horaInicial = campoHoraInicial.getText();
            String horaFinal = campoHoraFinal.getText();
            LocalDate data = campoData.getValue();
            Date sqlDate = Date.valueOf(data);
            String tipoHora = campoTipo.getValue();
            String justificativa = campoJustificativa.getText();

            try {
                ConnectionFactory conn = new ConnectionFactory();

                PreparedStatement statement = conn.recuperaConexao().prepareStatement("INSERT INTO hora(data_registro, hora_inicio, hora_fim, justificativa, tipo) VALUES (?, ?, ?, ?, ?)");
                statement.setDate(1, sqlDate);
                statement.setString(2, horaInicial);
                statement.setString(3, horaFinal);
                statement.setString(4, justificativa);
                statement.setString(5, tipoHora);

                boolean confirmado = Alerts.showAlert("Confirma", null, "Deseja confirmar?", Alert.AlertType.CONFIRMATION);
                if (confirmado) {
                    statement.executeUpdate();
                    statement.close();

                    campoData.setValue(null);
                    campoTipo.setValue(null);
                    campoHoraFinal.clear();
                    campoHoraInicial.clear();
                    campoJustificativa.clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnRegistrarHora;

    @FXML
    private DatePicker campoData;

    @FXML
    private TextField campoHoraFinal;

    @FXML
    private TextField campoHoraInicial;

    @FXML
    private TextField campoJustificativa;

    @FXML
    private ChoiceBox<String> campoTipo;


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
