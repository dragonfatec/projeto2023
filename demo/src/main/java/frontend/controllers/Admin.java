package frontend.controllers;

import backend.cliente.Cliente;
import backend.usuario.Situacao;
import backend.usuario.TiposDeUsuario;
import backend.usuario.Usuario;
import backend.util.Criptografia;
import database.conexao.ConnectionFactory;
import frontend.aplicacao.App;
import frontend.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    ToggleGroup toggleGroup2;
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
    public TableView<TabelaUsuario> tabelaColaboradoresEditarEquipe;
    public TableColumn colunaMatriculaEditarEquipe;
    public TableColumn colunaNomeEditarEquipe;
    public TableColumn colunaSelectEditarEquipe;
    public TableView<TabelaCliente> tabelaClientesEquipe;
    public TableColumn colunaEmpresaEquipe;
    public TableColumn colunaSelectClientesDaEquipe;
    public TableView<TabelaCliente> tabelaClientesEditarEquipe;
    public TableColumn colunaEmpresaEditarEquipe;
    public TableColumn colunaSelectClientesEditarEquipe;
    public TableColumn colunaResponsavelClientesEditarEquipe;

    // Variaveis para uso
    private AnchorPane ultimaTelaUsada = new AnchorPane();
    public static String qualTelaIniciar = "cadastra";


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

        campoNomeUsuario.clear();
        campoSenhaUsuario.clear();
        selectedRadioButton.setSelected(false);
    }

    public void cadastrarEquipe(MouseEvent mouseEvent) {
        conn.cadastrarEquipe(campoNomeEquipe.getText());
        labelCadastroEquipeRealizado.setVisible(true);
        campoNomeEquipe.clear();
    }

    public void cadastrarCliente(MouseEvent mouseEvent) {
        conn.cadastrarCliente(campoEmpresaCliente.getText(), campoResponsavelCliente.getText(), campoEmailCliente.getText(), campoTelefoneCliente.getText(), campoProjetoCliente.getText());
        labelCadastroClienteRealizado.setVisible(true);
        campoEmpresaCliente.clear();
        campoResponsavelCliente.clear();
        campoEmailCliente.clear();
        campoTelefoneCliente.clear();
        campoProjetoCliente.clear();
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
        campoEscolhaParaEditar.setItems(FXCollections.observableArrayList());
        ultimaTelaUsada.setVisible(false);
        String selecionado = campoEscolhaEdicao.getValue().toString().toLowerCase();
        switch (selecionado) {
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

        campoEscolhaParaEditar.getItems().addAll(conn.getListaColuna(null, selecionado+"-matriculas"));
    }

    public void preencherDados(){
        switch (campoEscolhaEdicao.getValue().toString().toLowerCase()) {
            case "usuario" -> {
                System.out.println("usuario");
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
        try {
            Usuario userSelecionado = conn.getUsuario(campoEscolhaParaEditar.getValue().toString().split("-")[0].trim());
            campoEditaNomeUsuario.setText(userSelecionado.getNome());
            campoEditaSenhaUsuario.setText("");
            // selecionar o cargo do usuario
            switch (userSelecionado.getCargo()){
                case Colaborador -> {
                    radioColaboradorEdita.setSelected(true);
                }
                case Gerente -> {
                    radioGestorEdita.setSelected(true);
                }
                case RH -> {
                    radioAdminEdita.setSelected(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salvarEditaUsuario(){
        String filtro = "'" + campoEscolhaParaEditar.getValue().toString().split("-")[0].trim() + "'";
        conn.atualizarStatus("usuario", "nome", campoEditaNomeUsuario.getText(), "matricula = "+filtro, false);
        conn.atualizarStatus("usuario", "cargo", ((ToggleButton) toggleGroup2.getSelectedToggle()).getText(), "matricula = "+filtro, false);
        conn.atualizarStatus("usuario", "situacao", checkboxUsuarioAtivo.isSelected() ? Situacao.Ativo.toString() : Situacao.Inativo.toString(), "matricula = "+filtro, false);
        if (!campoEditaSenhaUsuario.getText().trim().equals("")){
            conn.atualizarStatus("usuario", "senha", Criptografia.criptografar(campoEditaSenhaUsuario.getText()), "matricula = "+filtro, false);
        }
        Alerts.showAlert("Atualizado!", null, "A empresa foi atualizado com sucesso!", Alert.AlertType.INFORMATION);

        // Limpando os campos
        campoEditaNomeUsuario.setText("");
        campoEditaSenhaUsuario.setText("");
        ((ToggleButton) toggleGroup2.getSelectedToggle()).setSelected(false);
        checkboxUsuarioAtivo.setSelected(false);

        // Recarregar os dados para atualizar o ChoiceBox
        campoEscolhaParaEditar.getItems().addAll(conn.getListaColuna(null, "usuario-matriculas"));
    }

    public void preencherEditaEquipe(){
        try {
            campoEditaNomeEquipe.setText(conn.getColuna("equipe", "nome_equipe", "nome_equipe", campoEscolhaParaEditar.getValue().toString()));
            String nomeEquipe = campoEscolhaParaEditar.getValue().toString();

            tabelaColaboradoresEditarEquipe.setItems(FXCollections.observableArrayList(conn.getTabelaUsuario(nomeEquipe)));
            tabelaClientesEditarEquipe.setItems(FXCollections.observableArrayList(conn.getTabelaCliente(nomeEquipe)));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void salvarEditarEquipe(){
        int idEquipe = Integer.parseInt(conn.getColuna("equipe", "id_equipe", "nome_equipe", campoEscolhaParaEditar.getValue().toString()));

        int count = Integer.max(tabelaColaboradoresEditarEquipe.getItems().size(), tabelaClientesEditarEquipe.getItems().size());
        for (int i = 0; i < count; i++) {
            try{
                String insertOuDrop="";
                // tabela de colaborador
                TabelaUsuario t = tabelaColaboradoresEditarEquipe.getItems().get(i);
                if (t.estaSelecionado()){
                    insertOuDrop = "insert";
                }
                else{
                    insertOuDrop = "drop";
                }
                conn.cadastrarEquipeUsuario(t.getMatricula(),conn.getIdEquipe(campoEscolhaParaEditar.getValue().toString()),insertOuDrop);
            }catch (Exception ignored){}

            try{
                String insertOuDrop="";
                // tabela de cliente
                TabelaCliente c = tabelaClientesEditarEquipe.getItems().get(i);
                if(c.getSeEstaSelecionado()){
                    insertOuDrop = "insert";
                }else {
                    insertOuDrop = "drop";
                }
                conn.cadastrarEquipeCliente(conn.getIdEquipe(campoEscolhaParaEditar.getValue().toString()),conn.getIdCliente(c.getEmpresa()),insertOuDrop);
            }catch (Exception ignored){

            }
        }

        // apos salvar tudo agora vai atualizar o nome da equipe
        conn.atualizarStatus("equipe", "nome_equipe", campoEditaNomeEquipe.getText().trim(), "id_equipe = " + idEquipe, false);
        Alerts.showAlert("Atualizado!", null, "A empresa foi atualizado com sucesso!", Alert.AlertType.INFORMATION);

        // Limpando os campos
        campoEditaNomeEquipe.setText("");
        tabelaColaboradoresEditarEquipe.getItems().clear();
        tabelaClientesEditarEquipe.getItems().clear();

        // Recarregar os dados para atualizar o ChoiceBox
        campoEscolhaParaEditar.getItems().addAll(conn.getListaColuna(null, "equipe-matriculas"));
    }

    public void preencherEditaCliente(){
        try {
            Cliente cliente = conn.getCliente(campoEscolhaParaEditar.getValue().toString().trim());
            campoEditaEmpresaCliente.setText(cliente.getEmpresa());
            campoEditaResponsavelCliente.setText(cliente.getResponsavel());
            campoEditaEmailCliente.setText(cliente.getEmail());
            campoEditaTelefoneCliente.setText(cliente.getTelefone());
            campoEditaProjetoCliente.setText(cliente.getProjeto());
        }catch (Exception e){e.printStackTrace();}
    }

    public void salvarEditaCliente(){
        String filtro = "'" + campoEscolhaParaEditar.getValue() + "'";
        conn.atualizarStatus("cliente", "empresa", campoEditaEmpresaCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "responsavel", campoEditaResponsavelCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "email", campoEditaEmailCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "telefone", campoEditaTelefoneCliente.getText(), "empresa = "+filtro, false);
        conn.atualizarStatus("cliente", "projeto", campoEditaProjetoCliente.getText(), "empresa = "+filtro, false);
        Alerts.showAlert("Atualizado!", null, "O cliente foi atualizado com sucesso!", Alert.AlertType.INFORMATION);

        // Limpando os campos
        campoEditaEmpresaCliente.setText("");
        campoEditaResponsavelCliente.setText("");
        campoEditaEmailCliente.setText("");
        campoEditaTelefoneCliente.setText("");
        campoEditaProjetoCliente.setText("");

        // Recarregar os dados para atualizar o ChoiceBox
        campoEscolhaParaEditar.getItems().addAll(conn.getListaColuna(null, "cliente-matriculas"));
    }


    /////     Metodos Override     /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textoNomeUsuario.setText(usuario.getNome());
        textoNomeUsuario2.setText(usuario.getNome());

        String[] listaOpcoes = {"usuario", "cliente", "equipe"};
        campoEscolhaEdicao.getItems().addAll(listaOpcoes);
        campoEscolhaCadastro.getItems().addAll(listaOpcoes);

        // Iniciando a tabela de Usuario quando for editar a Equipe
        colunaMatriculaEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaUsuario, String>("matricula"));
        colunaNomeEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaUsuario, String>("nome"));
        colunaSelectEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaUsuario, String>("selecione"));

//        tabelaClientesEquipe;


        // Iniciando a tabela de Cliente quando for editar a Equipe

        colunaEmpresaEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaCliente, String>("empresa"));
        colunaSelectClientesEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaCliente, String>("selecione"));
        colunaResponsavelClientesEditarEquipe.setCellValueFactory(new PropertyValueFactory<TabelaCliente, String>("responsavel"));
//        .setCellValueFactory(new PropertyValueFactory<TabelaUsuario, String>("selecione"));

        switch (qualTelaIniciar) {
            case "cadastra" -> mudarParaCadastra();
            case "edita" -> mudarParaEdita();
        }
    }
}
