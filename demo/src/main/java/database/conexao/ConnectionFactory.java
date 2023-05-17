package database.conexao;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import frontend.util.Alerts;
import frontend.util.Tabela;
import frontend.util.TabelaAprova;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConnectionFactory {
    RegistroDataHora reg = new RegistroDataHora();

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
    public void apontarHorasExtra(String login, String data_inicial, String data_final, String equipe, String tipo_hora, String justificativa, String cliente){
        if (login         == null ||
            data_inicial  == null ||
            data_final    == null ||
            equipe        == null ||
            equipe        == ""   ||
            justificativa == null ||
            cliente       == null ||
            justificativa.equals("")) {
            Alerts.showAlert("Aviso!", null, "Preencher todos os campos!", Alert.AlertType.WARNING);
        }else {
                try {
                    Connection conn = recuperaConexao();
                    PreparedStatement pr = conn.prepareStatement("INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente) VALUES (?,?,?,?,?,?,?);");

                    Integer id_user = getIdUsuario(login);
                    pr.setInt(1, id_user);

                    String dtInicial = data_inicial;
                    String dtFinal = data_final;
                    pr.setTimestamp(2, Timestamp.valueOf(dtInicial));
                    pr.setTimestamp(3, Timestamp.valueOf(dtFinal));

                    pr.setString(4,justificativa);

                    Integer id_equipe = getIdEquipe(equipe);
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

                Integer id_equipe = getIdEquipe(equipe);
                pr.setInt(4, id_equipe);

                pr.setString(5, tipo_hora);

                pr.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Integer getIdEquipe(String equipe){
        Integer id_equipe = 0;
        String sql = "SELECT id_equipe FROM equipe WHERE nome_equipe = '" + equipe + "'";
        Connection conn = recuperaConexao();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                id_equipe = rs.getInt(1);
            }
            return id_equipe;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getEquipe(String login){
        ArrayList<String> equipes = new ArrayList<>();

        String sql = "SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.id_usuario = eu.id_usuario WHERE us.login = '" + login + "';";
        Connection conn = recuperaConexao();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String equipeNome = rs.getString(1);
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
    public ArrayList<String> getCliente(String nomeEquipe){
        ArrayList<String> cliente = new ArrayList<>();

        String sql = "SELECT cl.nome_cliente FROM equipe_cliente AS ec INNER JOIN equipe AS eq ON ec.id_equipe = eq.id_equipe INNER JOIN cliente AS cl ON cl.id_cliente = ec.id_cliente \n" +
                "WHERE eq.nome_equipe = '"+ nomeEquipe + "'";

        Connection conn = recuperaConexao();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                String empresa = rs.getString(1);
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
                Integer id_equipe = rs.getInt(7);

                user.put(Usuario.criarUsuario(login,senha,matricula,nome,cargo,id_equipe),id);
            }
            return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Integer getIdUsuario(String loginUsuario){
        Connection conn = recuperaConexao();
        String sql = "SELECT id_usuario FROM usuario WHERE login = '"+ loginUsuario +"';";
        Integer id = 0;
        try{
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                id =  rs.getInt(1);
            }
            System.out.println(id);
            return id;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Map<Integer, Usuario> getUsuario(String user, String passw){
        Connection conn = recuperaConexao();
        Map<Integer, Usuario> listaUsuarios = new HashMap<>();

        String sql = "SELECT * FROM usuario where login = '"+user+"' and senha = '"+passw+"'";
        int cont = 0;
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

                listaUsuarios.put(cont, Usuario.criarUsuario(login,senha,matricula,nome,cargo,id_equipe));
                cont += 1;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return listaUsuarios;
    }
    public ArrayList<Tabela> getHorasUsuario(String login){
        ArrayList<Tabela> tabela = new ArrayList<>();
        String sql =  "SELECT data_hora_inicial    AS Data_Inicial," +
                        "data_hora_final      AS Data_Final," +
                        "CASE WHEN cliente.nome_cliente IS NULL THEN '-' ELSE cliente.nome_cliente END," +
                        "CASE WHEN aprovacao IS NULL THEN 'Em andamento' ELSE aprovacao END AS status " +
                        "FROM hora "+
                        "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente " +
                        "JOIN usuario ON usuario.id_usuario = hora.id_usuario " +
                        "WHERE usuario.login = '"+ login+"'";
        Connection conn = recuperaConexao();
        try{
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String dataInicio = rs.getString(1);
                String dataFim = rs.getString(2);
                String cliente = rs.getString(3);
                String status = rs.getString(4);

                Tabela tb = new Tabela(dataInicio, dataFim, cliente, status);
                tabela.add(tb);
            }
            return tabela;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public ArrayList<TabelaAprova> getHoraEquipe(Integer id_equipe){
          String sql = "SELECT usuario.nome, " +
            	   "hora.data_hora_inicial, " +
            	   "hora.data_hora_final, " +
            	   "CASE WHEN cliente.nome_cliente IS NULL THEN '-' ELSE cliente.nome_cliente END, " +
            	   "hora.tipo_hora " +
                   "FROM hora " +
            	   "LEFT JOIN usuario ON usuario.id_usuario = hora.id_usuario " +
            	   "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente "+
//            	   "LEFT JOIN equipe ON equipe.id_equipe = hora.id_equipe "+
            	   "WHERE hora.id_equipe = '" + id_equipe + "'";

          Connection conn = recuperaConexao();
          ArrayList<TabelaAprova> tb = new ArrayList<>();
          try{
              PreparedStatement pr = conn.prepareStatement(sql);
              ResultSet rs = pr.executeQuery();
              while (rs.next()){
                  String colaborador = rs.getString(1);
                  String dataHoraInicial = rs.getString(2);
                  String dataHoraFinal = rs.getString(3);
                  String cliente = rs.getString(4);
                  String tipo = rs.getString(5);
//                  String totalDeHoras = Integer.toString(reg.pegarDiferencaMinutos(dataHoraInicial, dataHoraFinal));
                  int minutosTotal = (reg.pegarDiferencaMinutos(dataHoraInicial, dataHoraFinal));
                  int horas = minutosTotal / 60;
                  int minutos = minutosTotal - (horas * 60);
//                  String totalDeHoras = horas + ":" + minutos;
                  String totalDeHoras = (horas < 10? "0" + horas: Integer.toString(horas)) + ":" + (minutos < 10? "0" + minutos: Integer.toString(minutos));

                  TabelaAprova tabela = new TabelaAprova(colaborador,dataHoraInicial, dataHoraFinal, cliente, tipo, totalDeHoras);
                  tb.add(tabela);
              }
              return tb;

          }catch (SQLException e){
              throw new RuntimeException(e);
          }
    }

    public void atualizarInformacao(String nomeTabela, String nomeCampo, String novoValor, String condicao, boolean novoValorEhNumero){
        /*
         * A condicao é o filtro para atualizar apenas 1 linha.
         * Exemplo de String que deve vir na variavel condicao:
         *   id = 1
         *   nome = 'Teste'
         */
        novoValor = novoValorEhNumero ? novoValor : "'" + novoValor + "'";
        String sql = String.format("UPDATE %s SET %s = %s WHERE %s", nomeTabela, nomeCampo, novoValor, condicao);
        run(sql);
    }

    private ResultSet run(String sql){
        Connection conn = recuperaConexao();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            return pr.executeQuery();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> dontUseThis(String valorId, String equipeOuCliente) throws SQLException {
        /*
         * Esse metodo vai procurar na tabela e vai retornar todos as equipes, usuarios, ... o que o usuario escolher
        */

        String sql = "";
        switch (equipeOuCliente.toLowerCase()){
            case "equipe":
                sql = String.format("Select equipe_nome from equipe where id_equipe = '%s'", valorId);
            case "cliente":
                sql = "";
        }

        ArrayList<String> lista = new ArrayList<>();

        while (run(sql).next()){

        }

        return lista;
    }


}