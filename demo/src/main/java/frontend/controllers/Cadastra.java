package frontend.controllers;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
import frontend.util.VerificaAcesso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Cadastra implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();

    // Label
    public Label labelMatricula;
    public Label labelCadastroUsuarioRealizado;
    public Label textoNomeUsuario121;
    public Label labelCadastroEquipeRealizado;
    public Label textoNomeUsuario1;
    public Label labelCadastroClienteRealizado;

    // Input - TextField
    public TextField campoNomeUsuario;
    public TextField campoSenhaUsuario;
    public TextField campoEmpresaCliente;
    public TextField campoResponsavelCliente;
    public TextField campoEmailCliente;
    public TextField campoTelefoneCliente;
    public TextField campoProjetoCliente;
    public TextField campoNomeEquipe;

    // Button
    public Button btnRegistrarHora121;
    public Button btnConsultar131;
    public Button btnCadastrarUsuario;
    public Button btnCadastrarCliente;
    public Button btnCadastrarEquipe;
    public Button btnAreaCadastro;
    public Button btnAprovaHora1;
    public Button btnAprovaHora121;

    // Button - RadioButton
    public RadioButton radioColaborador;
    public RadioButton radioGestor;
    public RadioButton radioAdmin;

    // ChoiceBox
    public ChoiceBox campoEscolhaCadastro;
    public ChoiceBox campoClienteEquipe;

    // AnchorPane
    public AnchorPane anchorpaneCadastroCliente;
    public AnchorPane anchorpaneAreaEdicao;
    public AnchorPane anchorpaneCadastroUsuario;
    public AnchorPane anchorpaneCadastroEquipe;
    public AnchorPane anchorpaneAdmin;
    public AnchorPane anchorpaneAreaCadastro;
    public AnchorPane anchorpaneEscolhaCadastro;

    // Table
    public TableView tabelaColaboradoresEquipe;
    public TableColumn colunaMatriculaEquipe;
    public TableColumn colunaNomeEquipe;
    public TableColumn colunaSelectEquipe;


    /////     Metodos Publicos     /////
    public void gerarMatricula(){
        // Esse metodo vai gerar (aleatoriamente) e preencher o campo de matricula
        ArrayList<String> listaMatriculaExistente = conn.getListaColuna(null, "usuario");
        while (true){
            // Criar uma matricula aleatoria com 8 digitos
            String matricula = String.format("%0" + 8 + "d", new Random().nextInt(1, 100000000));
            if(!listaMatriculaExistente.contains(matricula)){
                // Aqui vai o preenchimento do TextField com a matricula
                break;
            }
        }
    }

    public void salvarUsuario(){
        // Para salvar o usario
//        conn.cadastrarUsuario();

        // Colocar no banco
//        if(){
//            Alerts.showAlert("Usuario salvo", null, "Usuario foi salvo com sucesso. \n\nLembre-se, a senha é padrão. \nSenha: 2rp", Alert.AlertType.INFORMATION);
//        }
//        else{
//            Alerts.showAlert("Erro", null, "Erro ao salvar o usuario. \nErro:\n"+erro, Alert.AlertType.ERROR);
//        }
    }

    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaConsultaHora(ActionEvent actionEvent) {
    }

    public void cadastrarUsuario(MouseEvent mouseEvent) {
    }

    public void cadastrarEquipe(MouseEvent mouseEvent) {
    }

    public void cadastrarCliente(MouseEvent mouseEvent) {
    }


    /////     Metodo Privados     /////
    private boolean verificarPreenchimentosDosCampos(){
        try{
//            dataInicio.getValue().toString();
//            horasInicio.getValue().toString();
//            minutosInicio.getValue().toString();
//            dataFim.getValue().toString();
//            horasFim.getValue().toString();
//            minutosFim.getValue().toString();
//            // Se for Hora Extra então é obrigatório o campo Justificativa
//            return !campoCliente.getValue().equals("") && !campoEquipe.getValue().equals("") && !campoTipo.getValue().equals("") && !campoJustificativa.getText().equals("");

            return true; // so para nao dar erro enquanto não é implementado a tela
        }
        catch (Exception e){
            return false;
        }
    }

    private void limparCampos(){
//        dataInicio.setValue(null);
//        horasInicio.setValue(null);
//        minutosInicio.setValue(null);
//        dataFim.setValue(null);
//        horasFim.setValue(null);
//        minutosFim.setValue(null);
//        campoJustificativa.clear();
//        campoEquipe.setValue(null);
//        campoTipo.setValue(null);
//        campoCliente.setValue(null);
    }


    /////     Metodos Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificando acesso para todas as telas
//        VerificaAcesso.verificarAcesso(btnAprovaHora, usuario.getCargo(), NomesArquivosFXML.aprovaHora);
//        VerificaAcesso.verificarAcesso(, usuario.getCargo(), NomesArquivosFXML.cadastrarUsuario);
//        VerificaAcesso.verificarAcesso(btnConsultar, usuario.getCargo(), NomesArquivosFXML.consultaHora);
//        VerificaAcesso.verificarAcesso(btnRegistrarHora, usuario.getCargo(), NomesArquivosFXML.registraHora);
        anchorpaneCadastroUsuario.setVisible(true);
        anchorpaneCadastroUsuario.setDisable(false);
    }
}
