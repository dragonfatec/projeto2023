package database.conexao;

import backend.cliente.Cliente;
import backend.datahora.RegistroDataHora;
import backend.equipe.Equipe;
import backend.usuario.Situacao;
import backend.usuario.TiposDeUsuario;
import backend.usuario.Usuario;
import frontend.util.*;

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
    public void cadastrarUsuario(String matricula, String senha, String nome, TiposDeUsuario cargo, Situacao situacao) {
            String sql = String.format("INSERT INTO usuario(matricula,senha, nome, cargo, situacao) VALUES ('%s','%s','%s','%s','%s');",matricula, senha, nome,cargo,situacao);
            runAtualizar(sql);
    }
    public void apontarHoras(String matricula, String data_inicial, String data_final, String equipe, String tipo_hora, String justificativa, String cliente){

        runAtualizar(String.format("INSERT INTO hora (matricula, data_hora_inicial, data_hora_final, justificativa, id_equipe, tipo_hora, id_cliente, status) VALUES ('%s','%s','%s','%s',%s,'%s',%s,'Em andamento');",matricula,data_inicial,data_final,justificativa,Integer.valueOf(getColuna("equipe","id_equipe", "nome_equipe",equipe)),tipo_hora, Integer.valueOf(getColuna("cliente", "id_cliente","empresa",cliente))));
    }
    public void cadastrarEquipe(String nomeEquipe){
        String sql = String.format("INSERT INTO equipe (nome_equipe) VALUES ('%s');", nomeEquipe);
        runSet(sql);
    }
    public void cadastrarCliente(String empresa, String responsavel, String email, String telefone, String projeto){
        String sql = String.format("INSERT INTO cliente (empresa, responsavel, email, telefone, projeto) VALUES ('%s','%s','%s','%s','%s');", empresa,responsavel, email, telefone, projeto);
        runSet(sql);
    }
    public void cadastrarEquipeUsuario(String matricula, Integer id_equipe, String insertOuDrop){
        String sql = "";
        switch (insertOuDrop){
            case "insert" -> {
                sql = String.format("INSERT INTO equipe_usuario (id_equipe, matricula) VALUES (%s,'%s');", id_equipe, matricula);
            }
            default -> {
                sql = String.format("DELETE FROM equipe_usuario WHERE id_equipe = %s AND matricula = '%s';", id_equipe,matricula);
            }
        }
        run(sql);
    }
    public void cadastrarEquipeCliente(Integer id_equipe, Integer id_cliente, String insertOrDrop){
        String sql = "";
        switch (insertOrDrop){
            case "insert" -> {
                sql = String.format("INSERT INTO equipe_cliente (id_equipe, id_cliente) VALUES (%s,%s)",id_equipe, id_cliente);
            }
            default -> {
                sql = String.format("DELETE FROM equipe_cliente WHERE id_equipe = %s AND id_cliente = %s;",id_equipe, id_cliente);
            }
        }
        run(sql);
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
        runAtualizar(sql);
    }
    private void runSet(String sql){
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void runAtualizar(String sql){
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getInfoCSV(String matricula){
        String linha = "";
        String sql = String.format(" SELECT " +
                                        "usuario.matricula, " +
                                        "usuario.nome, " +
                                    "CASE " +
                                        "WHEN hora.tipo_hora = 'Sobreaviso' THEN '3016' " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 2 THEN '1601' " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 2 THEN CONCAT('2h 1601 E ',EXTRACT(EPOCH FROM (AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-2,'h 1602') " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 1 THEN CONCAT(EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600,'h 3000') " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 1 THEN CONCAT('1h 3000 e ',EXTRACT(EPOCH FROM (AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-1,'h 1809') " +
                                        "ELSE 'NULL' END AS verba, " +
                                    "CASE " +
                                        "WHEN hora.tipo_hora = 'Sobreaviso' THEN EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 2 THEN EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600* 1.75 " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) >= 6 AND EXTRACT(HOUR FROM hora.data_hora_inicial) <= 22 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 2 THEN  3.5 + ((EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-2)*2 " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 <= 1 THEN (EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600* 1.1429)*1.75 " +
                                        "WHEN hora.tipo_hora = 'Extra' AND EXTRACT(HOUR FROM hora.data_hora_inicial) > 22 OR EXTRACT(HOUR FROM hora.data_hora_inicial) < 6 AND EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600 > 1 THEN 1.75 + (((EXTRACT(EPOCH FROM AGE(hora.data_hora_final,hora.data_hora_inicial))/3600)-1)*1.429)*2 " +
                                        "ELSE 0.0 END AS horas_proporcionais, " +
                                        "hora.data_hora_inicial, " +
                                        "hora.data_hora_final, " +
                                        "hora.tipo_hora, " +
                                        "cliente.empresa, " +
                                        "cliente.responsavel, " +
                                        "cliente.projeto, " +
                                        "equipe.nome_equipe, " +
                                        "hora.justificativa, " +
                                        "hora.justificativa_status " +
                                    "FROM " +
                                        "hora " +
                                    "LEFT JOIN usuario ON usuario.matricula = hora.matricula " +
                                    "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente " +
                                    "LEFT JOIN equipe ON equipe.id_equipe = hora.id_equipe " +
                                    "WHERE usuario.matricula = '12345' " +
                                    "ORDER BY hora.data_hora_inicial", matricula);
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String nome = rs.getString(2);
                String verba = rs.getString(3);
                String horas = rs.getString(4);
                String dataIni = rs.getString(5);
                String dataFin = rs.getString(6);
                String tipoHora = rs.getString(7);
                String cliente = rs.getString(8);
                String responsavel = rs.getString(9);
                String projeto = rs.getString(10);
                String nomeEquipe = rs.getString(11);
                String justificativa = rs.getString(12);
                String justificativaStatus = rs.getString(13);

//                "matricula,nome,verba,horas,cliente,CR,projeto"
                linha = matricula+","+nome+","+verba+","+horas+","+cliente+","+nomeEquipe+","+projeto;
                list.add(linha);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Cliente getCliente(String nomeEmpresa){
        String sql = String.format("SELECT empresa, responsavel, email, telefone, projeto FROM cliente WHERE empresa = '%s';", nomeEmpresa);
        Cliente cl = null;
        try{
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String empresa = rs.getString(1);
                String resp = rs.getString(2);
                String email = rs.getString(3);
                String telefone = rs.getString(4);
                String projeto = rs.getString(5);

                cl = new Cliente(empresa,resp,email,telefone,projeto);
            }
            return cl;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Usuario getUsuario(String matricula){
        String sql = String.format("SELECT * FROM usuario WHERE matricula = '%s';", matricula);
        Usuario user = null;
        try{
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String mat = rs.getString(1);
                String senha = rs.getString(2);
                String nome = rs.getString(3);
                String cargo = rs.getString(4);
                String situacao = rs.getString(5);

                if (situacao.equals(Situacao.Ativo.toString()))
                    user = Usuario.criarUsuario(mat, senha, nome, cargo, Situacao.Ativo);
                else
                    user = Usuario.criarUsuario(mat, senha, nome, cargo, Situacao.Inativo);

            }
            return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Integer getIdEquipe(String nomeEquipe){
        String sql = String.format("SELECT id_equipe FROM equipe WHERE nome_equipe = '%s'", nomeEquipe);
        Integer id = 0;
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
            return id;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
    public Integer getIdCliente(String empresa){
        String sql = String.format("SELECT id_cliente FROM cliente WHERE empresa = '%s'", empresa);
        Integer id = 0;
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
            return id;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<TabelaCliente> getTabelaCliente(String nomeEquipe){
        ArrayList<TabelaCliente> list = new ArrayList<>();
        String sql = String.format("""
                SELECT
                   	CASE WHEN equipe.nome_equipe = '%s' THEN 1 ELSE 2 END AS prioridade,
                    cliente.empresa,
                    cliente.responsavel
                FROM
                    cliente
                LEFT JOIN (SELECT * FROM equipe_cliente WHERE id_equipe = %s) AS cl ON cl.id_cliente = cliente.id_cliente
                LEFT JOIN equipe ON equipe.id_equipe = cl.id_equipe
                ORDER BY
                    prioridade
                              """,nomeEquipe, getIdEquipe(nomeEquipe));
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Integer pri = rs.getInt(1);
                String emp = rs.getString(2);
                String resp = rs.getString(3);

                TabelaCliente tb = new TabelaCliente(emp,resp);
                if (pri.equals(1)){
                    tb.selecionarUsuario();
                }
                list.add(tb);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<TabelaUsuario> getTabelaUsuario(String nomeEquipe){
        ArrayList<TabelaUsuario> list = new ArrayList<>();
        String sql =String.format("SELECT " +
                "CASE WHEN equipe.nome_equipe = '%s' THEN '1' ELSE '2' END AS prioridade, " +
                "usuario.nome, " +
                "usuario.matricula, " +
                "equipe.nome_equipe " +
                "FROM usuario " +
                "LEFT JOIN (SELECT * FROM equipe_usuario WHERE id_equipe = %s) AS eq ON eq.matricula = usuario.matricula " +
                "LEFT JOIN equipe ON equipe.id_equipe = eq.id_equipe ORDER BY prioridade ",nomeEquipe.toUpperCase(), getIdEquipe(nomeEquipe));
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String matr = rs.getString(3);
                String nome = rs.getString(2);

                TabelaUsuario tb = new TabelaUsuario(matr,nome);
                if (rs.getString(1).equals("1")){
                    tb.selecionarUsuario();
                }
                list.add(tb);
            }
            return list;
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
    public Boolean validarAcessoUsuario(String matricula, String passw){
        setInstancia();
        String sql = "SELECT * FROM usuario WHERE matricula = '"+matricula+"' and senha = '"+passw+"' AND situacao ='Ativo'";
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String mat = rs.getString(1);
                String senha = rs.getString(2);
                String nome = rs.getString(3);
                String cargo = rs.getString(4);

                Usuario.criarInstancia(mat, senha, nome, cargo, Situacao.Ativo);
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
    public ArrayList<TabelaAprova> getHoraEquipe(Integer id_equipe, TiposDeUsuario cargo){
          String sql = "";
          switch (cargo){
              case Gerente:
                  sql = "SELECT usuario.nome, " +
                          "hora.data_hora_inicial, " +
                          "hora.data_hora_final, " +
                          "CASE WHEN cliente.empresa IS NULL THEN '-' ELSE cliente.empresa END, " +
                          "hora.tipo_hora, hora.id_hora " +
                          "FROM hora " +
                          "LEFT JOIN usuario ON usuario.matricula = hora.matricula " +
                          "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente "+
                          "WHERE hora.id_equipe = " + id_equipe + " AND status = 'Em andamento' AND cargo = 'Colaborador';";
                  break;
              case RH:
                  sql = "SELECT usuario.nome, " +
                          "hora.data_hora_inicial, " +
                          "hora.data_hora_final, " +
                          "CASE WHEN cliente.empresa IS NULL THEN '-' ELSE cliente.empresa END, " +
                          "hora.tipo_hora, hora.id_hora " +
                          "FROM hora " +
                          "LEFT JOIN usuario ON usuario.matricula = hora.matricula " +
                          "LEFT JOIN cliente ON cliente.id_cliente = hora.id_cliente "+
                          "WHERE hora.id_equipe = " + id_equipe + " AND status = 'Em andamento'AND cargo = 'Gerente';";
                  break;
          }
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

                  TabelaAprova tabela = new TabelaAprova(id_hora,colaborador,dataHoraInicial,dataHoraFinal,cliente,tipo,totalDeHoras);
                  tb.add(tabela);
              }
              return tb;

          }catch (SQLException e){
              throw new RuntimeException(e);
          }
    }
    public ArrayList<String> getListaColuna(String id, String equipeOuCliente) {
        /*
         * Esse metodo vai procurar na tabela e vai retornar todos as equipes, usuarios, ... o que o usuario escolher
        */
        String sql = "";
        switch (equipeOuCliente.toLowerCase()){
            case "equipe":
//                sql = "SELECT eq.nome_equipe FROM equipe_usuario AS eu INNER JOIN equipe AS eq ON eu.id_equipe = eq.id_equipe INNER JOIN usuario AS us ON us.matricula = eu.matricula WHERE us.matricula = '" + id + "';";
                sql = "SELECT equipe.nome_equipe FROM equipe;";
                break;
            case "cliente":
                sql = "SELECT cl.empresa FROM equipe_cliente ec INNER JOIN equipe eq ON eq.id_equipe = ec.id_equipe INNER JOIN cliente cl ON cl.id_cliente = ec.id_cliente WHERE eq.nome_equipe = '"+ id +"';";
                break;
            case "usuario":
                sql = "SELECT matricula FROM usuario;";
                break;
            case "usuario-matriculas":
                sql = "SELECT CONCAT (matricula, ' - ', nome) FROM usuario;";
                break;
            case "cliente-matriculas":
                sql = "SELECT empresa FROM cliente;";
                break;
            case "equipe-matriculas":
                sql = "SELECT nome_equipe FROM equipe;";
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
    private ResultSet run(String sql){
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            return pr.executeQuery();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}