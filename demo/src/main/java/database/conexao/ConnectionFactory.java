package database.conexao;

import backend.usuario.Usuario;
import frontend.util.Alerts;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.*;

public class ConnectionFactory {

    public Connection recuperaConexao() {

        try {
            return DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/controle-de-ponto", "postgres", "123456");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrarUsuario(String login, String senha, String matricula, String nome, String tipo) {
        if (login     == null || login     == "" ||
            senha     == null || senha     == "" ||
            matricula == null || matricula == "" ||
            nome      == null || nome      == "" ||
            tipo      == null || tipo      == "") {
            System.out.println("Preencher todos os valores");
        } else {
            String sql = "INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES (?, ?, ?, ?, ?);";

            Connection conn = recuperaConexao();

            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, login);
                preparedStatement.setString(2, senha);
                preparedStatement.setString(3, matricula);
                preparedStatement.setString(4, nome);
                preparedStatement.setString(5, tipo);

                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Set<Usuario> usuarioCadastrado(){
        Set<Usuario> usuarios = new HashSet<>();

        String sql = "SELECT * FROM usuario;";

        Connection conn = recuperaConexao();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                String login = resultSet.getString(2);
                String senha = resultSet.getString(3);
                String matricula = resultSet.getString(4);
                String nome = resultSet.getString(5);
                String tipo = resultSet.getString(6);
                usuarios.add(Usuario.criarUsuario(login, senha, matricula, nome, tipo));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    // Ideias de como pode ficar alguns metodos desta classe

//    public Map<String, Arrays> consultar(String sql){
//
//        Map<String, Arrays> resultadoConsulta = new HashMap<>();
//
//        try {
//            PreparedStatement ps = this.conn.prepareStatement(sql);
//            ResultSet resultSet = ps.executeQuery();
//
//            String[] listaNomeColunas = {"id", "nome usuario"};
//
//            for (String nomeColuna : listaNomeColunas) {
//                resultadoConsulta.put(nomeColuna, (Arrays) resultSet.getArray(nomeColuna));
//            }
//
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//        return resultadoConsulta;
//    }
//
//    public void inserirDados(String sql){
//        /*
//        Esse metodo vai rodar o sql que você passar como parametro e inserir registros nessa tabela
//
//        Exemplo de como o parametro sql tem que vir:
//            INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES ('abc12', 12345, 12345678, Nome Teste,Colaborador);
//        */
//        try {
//            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
//            preparedStatement.execute();
//        }catch (SQLException e){
//            Alerts.showAlert("ERRO", "Erro ao salvar no banco", "Erro ao salvar \nErro:\n"+e, Alert.AlertType.ERROR);
//            throw new RuntimeException(e);
//        }
//    }
}
