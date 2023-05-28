package frontend.controllers;

import backend.usuario.Situacao;
import backend.usuario.TiposDeUsuario;
import backend.usuario.Usuario;
import backend.util.Criptografia;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.Alerts;
import frontend.util.NomesArquivosFXML;
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

public class Admin implements Initializable {
    // Objetos
    private final ConnectionFactory conn = new ConnectionFactory();
    private final Usuario usuario = Usuario.getInstancia();

    // Label
    public Label labelMatricula;
    public Label labelCadastroUsuarioRealizado;
    public Label textoNomeUsuario;
    public Label labelCadastroEquipeRealizado;
    public Label labelCadastroClienteRealizado;
    public Label textoNomeUsuario2;
    public Label labelUsuarioEditar;
    public Label labelClienteEditor;
    public Label labelEquipeEditar;

    // Input - TextField
    public TextField campoNomeUsuario;
    public TextField campoSenhaUsuario;
    public TextField campoEmpresaCliente;
    public TextField campoResponsavelCliente;
    public TextField campoEmailCliente;
    public TextField campoTelefoneCliente;
    public TextField campoProjetoCliente;
    public TextField campoNomeEquipe;
    public TextField campoEditaEmpresaCliente;
    public TextField campoEditaResponsavelCliente;
    public TextField campoEditaEmailCliente;
    public TextField campoEditaTelefoneCliente;
    public TextField campoEditaProjetoCliente;
    public TextField campoEditaNomeEquipe;
    public TextField campoEditaNomeUsuario;
    public TextField campoEditaSenhaUsuario;

    // Button
    public Button btnRegistrarHora;
    public Button btnConsultar;
    public Button btnCadastrarUsuario;
    public Button btnCadastrarCliente;
    public Button btnCadastrarEquipe;
    public Button btnAreaCadastro;
    public Button btnAprovaHora;
    public Button btnAreaEdicao;
    public Button btnAreaCadastro2;
    public Button btnAreaEdicao2;
    public Button btnAprovaHora2;
    public Button btnEditarNomeUsuario;
    public Button btnEditarSenhaUsuario;
    public Button btnSalvarUsuario;
    public Button btnEditarTelefoneCliente;
    public Button btnEditarProjetoCliente;
    public Button btnEditarEmailCliente;
    public Button btnEditarResponsavelCliente;
    public Button btnSalvarCliente;
    public Button btnEditarNomeEquipe;
    public Button btnSalvarEquipe;
    public Button btnEditarClienteEquipe;

    // Button - RadioButton
    @FXML
    ToggleGroup toggleGroup;
    public RadioButton radioColaborador;
    public RadioButton radioGestor;
    public RadioButton radioAdmin;
    public RadioButton radioColaboradorEdita;
    public RadioButton radioGestorEdita;
    public RadioButton radioAdminEdita;

    // ChoiceBox / CheckBox
    public ChoiceBox campoEscolhaCadastro;
    public ChoiceBox campoClienteEquipe;
    public ChoiceBox campoEscolhaEdicao;
    public ChoiceBox campoEscolhaParaEditar;
    public CheckBox checkboxUsuarioAtivo;

    // AnchorPane
    public AnchorPane anchorpaneCadastroCliente;
    public AnchorPane anchorpaneAreaEdicao;
    public AnchorPane anchorpaneCadastroUsuario;
    public AnchorPane anchorpaneCadastroEquipe;
    public AnchorPane anchorpaneAdmin;
    public AnchorPane anchorpaneAreaCadastro;
    public AnchorPane anchorpaneEscolhaCadastro;
    public AnchorPane anchorpaneEditarUsuario;
    public AnchorPane anchorpaneEscolhaCadastro11;
    public AnchorPane anchorpaneEscolhaCadastro1;
    public AnchorPane anchorpaneEditarCliente;
    public AnchorPane anchorpaneEditorEquipe;

    // Table
    public TableView tabelaColaboradoresEquipe;
    public TableColumn colunaMatriculaEquipe;
    public TableColumn colunaNomeEquipe;
    public TableColumn colunaSelectEquipe;
    public TableView tabelaColaboradoresEditarEquipe;
    public TableColumn colunaMatriculaEditarEquipe;
    public TableColumn colunaNomeEditarEquipe;
    public TableColumn colunaSelectEditarEquipe;

    // Variaveis para uso
    private AnchorPane ultimaTelaUsada = new AnchorPane();


    /////     Metodos Publicos     /////
    public String gerarMatricula(){
        // Esse metodo vai gerar (aleatoriamente) e preencher o campo de matricula
        ArrayList<String> listaMatriculaExistente = conn.getListaColuna(null, "usuario");
        while (true){
            // Criar uma matricula aleatoria com 8 digitos
            String matricula = String.format("%0" + 8 + "d", new Random().nextInt(1, 100000000));
            if(!listaMatriculaExistente.contains(matricula)){
                // Aqui vai o preenchimento do TextField com a matricula
                return matricula;
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

    public void cadastrarUsuario(MouseEvent mouseEvent) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        toggleGroup.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            if (!radioButton.equals(selectedRadioButton)) {
                radioButton.setSelected(false);
            }
        });
        String matricula = gerarMatricula();
        conn.cadastrarUsuario(matricula, Criptografia.criptografar(campoSenhaUsuario.getText()), campoNomeUsuario.getText(), TiposDeUsuario.valueOf(selectedRadioButton.getText()), Situacao.Ativo);
        labelCadastroUsuarioRealizado.setVisible(true);
        labelMatricula.setText(matricula);
    }

    public void cadastrarEquipe(MouseEvent mouseEvent) {
    }

    public void cadastrarCliente(MouseEvent mouseEvent) {
    }

    public void mudarParaCadastra(){
        ultimaTelaUsada.setVisible(false);
        anchorpaneAreaEdicao.setVisible(false);
        anchorpaneAreaCadastro.setVisible(true);
    }

    public void mudarParaEdita(){
        ultimaTelaUsada.setVisible(false);
        anchorpaneAreaCadastro.setVisible(false);
        anchorpaneAreaEdicao.setVisible(true);
    }

    public void mudarDeTelaCadastro(){
        ultimaTelaUsada.setVisible(false);
        switch (campoEscolhaCadastro.getValue().toString().toLowerCase()){
            case "usuario":
                anchorpaneCadastroUsuario.setVisible(true);
                ultimaTelaUsada = anchorpaneCadastroUsuario;
                break;
            case "equipe":
                anchorpaneCadastroEquipe.setVisible(true);
                ultimaTelaUsada = anchorpaneCadastroEquipe;
                break;
            case "cliente":
                anchorpaneCadastroCliente.setVisible(true);
                ultimaTelaUsada = anchorpaneCadastroCliente;
                break;
        }
    }

    public void mudarDeTela(){
        ultimaTelaUsada.setVisible(false);
        switch (campoEscolhaEdicao.getValue().toString().toLowerCase()) {
            case "usuario" -> {
                anchorpaneEditarUsuario.setVisible(true);
                ultimaTelaUsada = anchorpaneEditarUsuario;
            }
            case "equipe" -> {
                anchorpaneEditorEquipe.setVisible(true);
                ultimaTelaUsada = anchorpaneEditorEquipe;
            }
            case "cliente" -> {
                anchorpaneEditarCliente.setVisible(true);
                ultimaTelaUsada = anchorpaneEditarCliente;
            }
        }
    }

    public void preencherDados(){
        switch (campoEscolhaEdicao.getValue().toString().toLowerCase()) {
            case "usuario" -> {
                preencherEditaUsuario();
            }
            case "equipe" -> {
                preencherEditaEquipe();
            }
            case "cliente" -> {
                preencherEditaCliente();
            }
        }
    }

    public void preencherEditaUsuario(){
//        Usuario userSelecionado = conn.
//        campoEditaNomeUsuario.setText();
//        campoEditaSenhaUsuario.setText();
        // selecionar o cargo do usuario
//        switch (userSelecionado.getCargo()){
//            case Colaborador -> {
//                radioColaboradorEdita.setSelected(true);
//            }
//            case Gerente -> {
//                radioGestorEdita.setSelected(true);
//            }
//            case RH -> {
//                radioAdminEdita.setSelected(true);
//            }
//        }
    }

    public void salvarEditaUsuario(){

        String filtro = "'" + campoEscolhaParaEditar.getValue() + "'";
        conn.atualizarStatus("usuario", "nome", campoEditaNomeUsuario.getText(), "matricula = "+filtro, false);
        conn.atualizarStatus("usuario", "senha", campoEditaSenhaUsuario.getText(), "matricula = "+filtro, false);
//        conn.atualizarStatus("usuario", "cargo", carg, "matricula = "+filtro, false);
        conn.atualizarStatus("usuario", "status", checkboxUsuarioAtivo.isSelected() ? "Ativado" : "Desativado", "matricula = "+filtro, false);

        Alerts.showAlert("Atualizado!", null, "A empresa foi atualizado com sucesso!", Alert.AlertType.INFORMATION);
    }

    public void preencherEditaEquipe(){
        campoEditaNomeEquipe.setText(conn.getColuna("equipe", "nome_equipe", "nome_equipe", campoEscolhaParaEditar.getValue().toString()));
        /*
            * Se o primeiro não der certo então o segundo deve dar
        select us.matricula, us.nome, case when eq.nome = 'Equipe teste' then 1 else 2 as prioridade from equipe_usuario eu inner join usario us on eu.matricula = us.matricula inner join equipe eq on eu.id_equipe = eq.id_equipe order by prioridade
        select * from (select us.matricula, us.nome, case when eq.nome = 'Equipe teste' then 1 else 2 as prioridade from equipe_usuario eu inner join usario us on eu.matricula = us.matricula inner join equipe eq on eu.id_equipe = eq.id_equipe) order by prioridade
        */
        // Para colocar tudo na tabela
//        ObservableList<TabelaUsuario> tabelasObjetoLista = FXCollections.observableArrayList();
//        tabelasObjetoLista.addAll(conn......);
//        tabelaColaboradoresEditarEquipe.setItems(tabelasObjetoLista);
    }

    public void salvarEditarEquipe(){
//        String id_equipe = conn.getColuna("equipe", "id_equipe", "nome_equipe", campoEscolhaParaEditar.getValue().toString());
//        for (TabelaUsuario tb : tabelaColaboradoresEditarEquipe.getItems()){
//            // salvar usando a matricula e o id_equipe
//        }

        // apos salvar tudo agora vai atualizar o nome da equipe
        String filtro = "'" + campoEscolhaParaEditar.getValue() + "'";
         conn.atualizarStatus("equipe", "nome_equipe", campoEditaNomeEquipe.getText(), "equipe = "+filtro, false);
        Alerts.showAlert("Atualizado!", null, "A empresa foi atualizado com sucesso!", Alert.AlertType.INFORMATION);
    }

    public void preencherEditaCliente(){
//        Cliente cliente = conn.getCliente(campoEscolhaParaEditar.getValue().toString());
//        campoEditaEmpresaCliente.setText(cliente.getEmpresa());
//        campoEditaResponsavelCliente.setText(cliente.getResponsavel());
//        campoEditaEmailCliente.setText(cliente.getEmail());
//        campoEditaTelefoneCliente.setText(cliente.getTelefone());
//        campoEditaProjetoCliente
    }

    public void salvarEditaCliente(){
        String filtro = "'" + campoEscolhaParaEditar.getValue() + "'";
        conn.atualizarStatus("cliente", "empresa", campoEditaEmpresaCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "responsavel", campoEditaResponsavelCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "email", campoEditaEmailCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "telefone", campoEditaTelefoneCliente.getText(), "empresa = "+filtro, false);
//        conn.atualizarStatus("cliente", "projeto", campoEditaProjetoCliente.getText(), "empresa = "+filtro, false);
        Alerts.showAlert("Atualizado!", null, "O cliente foi atualizado com sucesso!", Alert.AlertType.INFORMATION);
    }

    public void mudarSenha(ActionEvent actionEvent) {
        switch (btnEditarSenhaUsuario.getText()){
            case "Mudar senha" -> {
                campoEditaSenhaUsuario.setText("2rp");
                btnEditarSenhaUsuario.setText("Cancelar");
            }
            case "Cancelar" -> {
                campoEditaSenhaUsuario.setText("**********");
                btnEditarSenhaUsuario.setText("Mudar senha");
            }
        }
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
        textoNomeUsuario.setText(usuario.getNome());
//        ArrayList<String> listaOpcoes = {"usuario", "cliente", "equipe"};
        String[] listaOpcoes = {"usuario", "cliente", "equipe"};
        campoEscolhaEdicao.getItems().addAll(listaOpcoes);
        campoEscolhaCadastro.getItems().addAll(listaOpcoes);


    }
}
