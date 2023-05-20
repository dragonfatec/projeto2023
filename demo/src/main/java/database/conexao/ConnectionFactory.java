package database.conexao;

import backend.datahora.RegistroDataHora;
import backend.usuario.Usuario;
import frontend.util.Tabela;
import frontend.util.TabelaAprova;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

public class ConnectionFactory {

    private static Connection conn;
    private final RegistroDataHora hora = new RegistroDataHora();

    public static void setInstancia() {
        if(conn == null) {
            try {
               conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controle-de-ponto", "postgres", "123456");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void cadastrarUsuario(String matricula, String senha, String nome, String cargo, Integer id_equipe) {

            String sql = String.format("INSERT INTO usuario(matricula,senha, nome, cargo, id_equipe) VALUES ('%s','%s','%s','%s',%s);",matricula, senha, nome,cargo,id_equipe);

            run(sql);
    }
    public void apontarHorasExtra(String matricula, String data_inicial, String data_final, String equipe, String tipo_hora, String justificativa, String cliente){

        try {
            PreparedStatement pr = conn.prepareStatement("INSERT INTO hora (matricula, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente, status)\n" +
                                                             "VALUES (?,?,?,?,?,?,?,'Em andamento');");
            pr.setString(1,matricula);
            Timestamp dt_inicial = Timestamp.valueOf(data_inicial);
            pr.setTimestamp(2, dt_inicial);
            Timestamp dt_final = Timestamp.valueOf(data_final);
            pr.setTimestamp(3, dt_final);
            pr.setString(4,justificativa);
            pr.setInt(5, Integer.valueOf(getColuna("equipe","id_equipe", "nome_equipe",equipe)));
            pr.setString(6,tipo_hora);
            pr.setInt(7, Integer.valueOf(getColuna("cliente", "id_cliente","empresa",cliente)));

            pr.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public String getColuna(String tabela, String coluna, String campoFiltro, String valorFiltro){
        String id = "";
        String sql = "SELECT " + coluna + " FROM " + tabela + " WHERE "+ campoFiltro + " = '" + valorFiltro +"';" ;
        try{
            ResultSet rs = run(sql);
            while(rs.next()){
                id = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public void apontarHorasSobreaviso(Usuario usuario, String data_inicial, String data_final, String equipe, String tipo_hora) {
//
//            try {
//                Connection conn = getInstancia();
//                PreparedStatement pr = conn.prepareStatement("INSERT INTO hora (id_usuario, data_hora_inicial, data_hora_final, id_equipe, tipo_hora) VALUES (?,?,?,?,?);");
//
//                String dtInicial = data_inicial;
//                String dtFinal = data_final;
//                pr.setTimestamp(2, Timestamp.valueOf(dtInicial));
//                pr.setTimestamp(3, Timestamp.valueOf(dtFinal));
//
//                Integer id_equipe = getIdEquipe(equipe);
//                pr.setInt(4, id_equipe);
//
//                pr.setString(5, tipo_hora);
//
//                pr.execute();
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
    }
    public ArrayList<String> getEquipe(String login){
        ArrayList<String> equipes = new ArrayList<>();

        String sql = "SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.id_usuario = eu.id_usuario WHERE us.login = '" + login + "';";

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
    public Boolean validarAcessoUsuario(String matricula, String passw){
        String sql = "SELECT * FROM usuario WHERE matricula = '"+matricula+"' and senha = '"+passw+"';";
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String mat = rs.getString(1);
                String senha = rs.getString(2);
                String nome = rs.getString(3);
                String cargo = rs.getString(4);

                Usuario.criarInstancia(mat,senha,nome,cargo);
                return true;
            }
        return false;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Tabela> getHorasUsuario(String matricula){
        ArrayList<Tabela> tabela = new ArrayList<>();
        String sql =  "SELECT data_hora_inicial    AS Data_Inicial," +
                        "data_hora_final      AS Data_Final," +
                        "CASE WHEN cliente.empresa IS NULL THEN '-' ELSE cliente.empresa END," +
                        "CASE WHEN status IS NULL THEN 'Em andamento' ELSE status END AS status " +
                        "FROM hora "+
                        "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente " +
                        "JOIN usuario ON usuario.matricula = hora.matricula " +
                        "WHERE usuario.matricula = '"+ matricula+"'";

        try{
            ResultSet rs = run(sql);
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
                          "CASE WHEN cliente.empresa IS NULL THEN '-' ELSE cliente.empresa END, " +
                          "hora.tipo_hora, hora.id_hora " +
                           "FROM hora " +
                          "LEFT JOIN usuario ON usuario.matricula = hora.matricula " +
                          "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente "+
                          "WHERE hora.id_equipe = " + id_equipe + " AND status = 'Em andamento';";
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
                  Integer id_hora = rs.getInt(6);
                  int minutosTotal = (hora.pegarDiferencaMinutos(dataHoraInicial, dataHoraFinal));
                  int horas = minutosTotal / 60;
                  int minutos = minutosTotal - (horas * 60);
                  String totalDeHoras = (horas < 10? "0" + horas: Integer.toString(horas)) + ":" + (minutos < 10? "0" + minutos: Integer.toString(minutos));

                  TabelaAprova tabela = new TabelaAprova(id_hora,colaborador,dataHoraInicial, dataHoraFinal, cliente, tipo, totalDeHoras);
                  tb.add(tabela);
              }
              return tb;

          }catch (SQLException e){
              throw new RuntimeException(e);
          }
    }
    public void atualizarStatus(String nomeTabela, String nomeCampo, String novoValor, String condicao, boolean novoValorEhNumero){
        /*
         * A condicao é o filtro para atualizar apenas 1 linha.
         * Exemplo de String que deve vir na variavel condicao:
         *   id = 1
         *   nome = 'Teste'
         */
        novoValor = novoValorEhNumero ? novoValor : "'" + novoValor + "'";
        String sql = String.format("UPDATE %s SET %s = %s WHERE %s", nomeTabela, nomeCampo, novoValor, condicao);
        System.out.println(sql);
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private ResultSet run(String sql){
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            return pr.executeQuery();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
//        return conn.prepareStatement("SELECT * FROM usuario LIMIT 1;").executeQuery();
    }
    public ArrayList<String> getListaColuna(String matriculaUserOuEquipe, String equipeOuCliente) {
        /*
         * Esse metodo vai procurar na tabela e vai retornar todos as equipes, usuarios, ... o que o usuario escolher
        */
        String sql = "";
        switch (equipeOuCliente.toLowerCase()){
            case "equipe":
                sql = "SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.matricula = eu.matricula WHERE us.matricula = '" + matriculaUserOuEquipe + "';";
                break;
            case "cliente":
                sql = "SELECT cl.empresa FROM equipe_cliente ec INNER JOIN equipe eq ON eq.id_equipe = eq.id_equipe INNER JOIN cliente cl ON cl.id_cliente = cl.id_cliente WHERE eq.nome_equipe = '"+ matriculaUserOuEquipe +"';";
                break;
        }

        ArrayList<String> lista = new ArrayList<>();

            try {
                ResultSet rs = run(sql);
                while (rs.next()){
                    lista.add(rs.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return lista;
    }
}