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
    public ArrayList<String> getInfoCSV(){
        String linha = "";
        String sql = " SELECT " +
                "usuario.matricula, " +
                "usuario.nome, " +
                "hora.data_hora_inicial, " +
                "hora.data_hora_final, " +
                "hora.tipo_hora, "+
                "CASE " +
                "WHEN hora.tipo_hora = 'Extra' AND RANK() OVER (PARTITION BY  usuario.matricula, TO_CHAR(hora.data_hora_inicial, 'DD-MM-YYYY') ORDER BY hora.data_hora_inicial DESC) >= 2 THEN '100' " +
                "WHEN hora.tipo_hora = 'Extra' THEN '75' " +
                "ELSE '30' END AS verba, " +
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
                              "ORDER BY verba;";
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String matricula = rs.getString(1);
                String nome = rs.getString(2);
                String dataIni = rs.getString(3);
                String dataFin = rs.getString(4);
                String tipoHora = rs.getString(5);
                String verba = rs.getString(6);
                String empresa = rs.getString(7);
                String responsavel = rs.getString(8);
                String projeto = rs.getString(9);
                String nomeEquipe = rs.getString(10);
                String justificativa = rs.getString(11);
                String justificativaStatus = rs.getString(12);
                linha = matricula+","+nome+","+dataIni+","+dataFin+","+tipoHora+","+verba+"%,"+empresa+","+responsavel+","+projeto+","+nomeEquipe+","+justificativa+","+justificativaStatus;
                list.add(linha);
            }
            for(String row:list){
                System.out.println(row);
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
                LEFT JOIN equipe_cliente ON cliente.id_cliente = equipe_cliente.id_cliente 
                LEFT JOIN equipe ON equipe.id_equipe = equipe_cliente.id_equipe 
                ORDER BY 
                    prioridade""",nomeEquipe);
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
        String sql =String.format("SELECT CASE WHEN equipe.nome_equipe = '%s' THEN '1' ELSE '2' END AS prioridade, " +
                                    "usuario.nome, " +
                                    "usuario.matricula " +
                                    "FROM usuario " +
                                    "LEFT JOIN equipe_usuario ON usuario.matricula = equipe_usuario.matricula " +
                                    "LEFT JOIN equipe ON equipe.id_equipe = equipe_usuario.id_equipe " +
                                    "ORDER BY prioridade,nome", nomeEquipe.toUpperCase());
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