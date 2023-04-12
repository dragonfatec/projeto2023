package backend.datahora;

import backend.usuario.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

//DAO - Data Acess Object
public class ConexaoDAO {
    private ConnectionFactory connection;

    public ConexaoDAO(){
        this.connection = new ConnectionFactory();
    }

    public void cadastrarUsuario(String login, String senha, String matricula, String nome, String tipo){

        String sql = "INSERT INTO usuario(login, senha, matricula, nome, tipo) VALUES (?, ?, ?, ?, ?);";

        Connection conn = connection.recuperaConexao();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, senha);
            preparedStatement.setString(3, matricula);
            preparedStatement.setString(4, nome);
            preparedStatement.setString(5, tipo);

            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Usuario> usuarioCadastrado(){

        Set<Usuario> usuarios = new HashSet<>();

        String sql = "SELECT * FROM usuario;";

        Connection conn = connection.recuperaConexao();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                String login = resultSet.getString(2);
                String senha = resultSet.getString(3);
                String matricula = resultSet.getString(4);
                String nome = resultSet.getString(5);
                String tipo = resultSet.getString(6);

                usuarios.add(new Usuario(login, senha, matricula, nome, tipo));

            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    return usuarios;
    }
}
