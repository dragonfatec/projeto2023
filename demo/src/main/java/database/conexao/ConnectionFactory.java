package database.conexao;

import backend.usuario.Usuario;
import frontend.util.Alerts;
import javafx.scene.control.Alert;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void cadastrarUsuario(String login, String senha, String matricula, String nome, String cargo, Integer id_equipe) {
        if (login     == null || login     == "" ||
            senha     == null || senha     == "" ||
            matricula == null || matricula == "" ||
            nome      == null || nome      == "" ||
            cargo     == null || cargo     == "" ||
            id_equipe == null) {
            System.out.println("Preencher todos os valores");
        } else {
            String sql = "INSERT INTO usuario(login, senha, matricula, nome, cargo, id_equipe) VALUES (?,?,?,?,?,?);";
            Connection conn = recuperaConexao();
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, login);
                preparedStatement.setString(2, senha);
                preparedStatement.setString(3, matricula);
                preparedStatement.setString(4, nome);
                preparedStatement.setString(5, cargo);
                preparedStatement.setInt(6, id_equipe);

                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void apontarHorasExtra(Usuario usuario, String data_inicial, String data_final, String equipe, String tipo_hora, String justificativa, String cliente, String tipoHora){

        if (     usuario      == null ||
                 data_inicial == null ||
                 data_final   == null ||
                 equipe       == null ||
                 equipe       == ""   ||
                 tipo_hora    == null ||
                 tipo_hora    == ""   ||
                 justificativa == null||
                 cliente       == null||
                 justificativa.equals(""))
                     Alerts.showAlert("Aviso!",null,"Preencher todos os campos!", Alert.AlertType.WARNING);
        else {
                try {
                    Connection conn = recuperaConexao();
                    PreparedStatement pr = conn.prepareStatement("INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente) VALUES (?,?,?,?,?,?,?);");

                    Integer id_user = getListaUsuario().get(usuario);
                    pr.setInt(1, id_user);

                    String dtInicial = data_inicial;
                    String dtFinal = data_final;
                    pr.setTimestamp(2, Timestamp.valueOf(dtInicial));
                    pr.setTimestamp(3, Timestamp.valueOf(dtFinal));

                    pr.setString(4,justificativa);

                    Integer id_equipe = getListaEquipe().get(equipe);
                    pr.setInt(5, id_equipe);

                    pr.setString(6, tipo_hora);

                    Integer id_cliente = getListaCliente().get(cliente);
                    pr.setInt(7,id_cliente);

                    pr.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
    }

    public void apontarHorasSobreaviso(Usuario usuario, String data_inicial, String data_final, String equipe, String tipo_hora) {
        if (usuario      == null ||
            data_inicial == null ||
            data_final   == null ||
            equipe       == null ||
            equipe       == ""   ||
            tipo_hora    == null ||
            tipo_hora    == "") {
            Alerts.showAlert("Aviso!", null, "Preencher todos os campos!", Alert.AlertType.WARNING);
        }else {
            try {
                Connection conn = recuperaConexao();
                PreparedStatement pr = conn.prepareStatement("INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (?,?,?,?,?);");

                Integer id_user = getListaUsuario().get(usuario);
                pr.setInt(1, id_user);

                String dtInicial = data_inicial;
                String dtFinal = data_final;
                pr.setTimestamp(2, Timestamp.valueOf(dtInicial));
                pr.setTimestamp(3, Timestamp.valueOf(dtFinal));

                Integer id_equipe = getListaEquipe().get(equipe);
                pr.setInt(4, id_equipe);

                pr.setString(5, tipo_hora);

                pr.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public HashMap<String, Integer> getListaEquipe(){
        HashMap<String, Integer> equipe = new HashMap<>();

        String sql = "SELECT * FROM equipe;";
        Connection conn = recuperaConexao();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Integer id = rs.getInt(1);
                String equipeNome = rs.getString(2);
                equipe.put(equipeNome,id);
            }
            return equipe;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getEquipe(){
        ArrayList<String> equipes = new ArrayList<>();

        String sql = "SELECT * FROM equipe;";
        Connection conn = recuperaConexao();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String equipeNome = rs.getString(2);
                equipes.add(equipeNome);
            }
            return equipes;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public HashMap<String, Integer> getListaCliente(){
        HashMap<String, Integer> cliente = new HashMap<>();

        String sql = "SELECT * FROM cliente";
        Connection conn = recuperaConexao();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String cl = rs.getString(2);
                Integer id_cliente = rs.getInt(1);

                cliente.put(cl,id_cliente);
            }

            return cliente;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getCliente(){
        ArrayList<String> cliente = new ArrayList<>();

        String sql = "SELECT * FROM cliente;";

        Connection conn = recuperaConexao();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                String empresa = rs.getString(2);
                cliente.add(empresa);
            }
            return cliente;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public HashMap<Usuario,Integer> getListaUsuario(){
        Connection conn = recuperaConexao();
        HashMap<Usuario,Integer> user = new HashMap<>();

        String sql = "SELECT * FROM usuario;";
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Integer id = rs.getInt(1);
                String login = rs.getString(2);
                String senha = rs.getString(3);
                String matricula = rs.getString(4);
                String nome = rs.getString(5);
                String cargo = rs.getString(6);
                Integer id_equipe  =rs.getInt(7);

                Usuario.criarInstancia(login,senha,matricula,nome,cargo,id_equipe);
                user.put(Usuario.getInstancia(),id);
            }
            return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}