package backend.usuario;

import java.sql.SQLException;

public class Usuario {

    private String login;
    private String senha;
    private String matricula;
    private String nome;
    private String cargo;
    private Integer id_equipe;
    private static Usuario instancia;

    private Usuario(){

    }
    private Usuario(String login, String senha, String matricula, String nome, String cargo, Integer id_equipe){
        this.login = login;
        this.senha = senha;
        this.matricula = matricula;
        this.nome = nome;
        this.cargo = cargo;
        this.id_equipe = id_equipe;
    }
    public static Usuario getInstancia(){
        if (instancia == null){
            instancia = new Usuario();
        }
        return instancia;
    }
    public static void criarInstancia(String login, String senha, String matricula, String nome, String cargo, Integer id_equipe){
        if (instancia == null){
            instancia = new Usuario(login, senha, matricula, nome, cargo, id_equipe);
        }
    }
    public static Usuario criarUsuario(String login, String senha, String matricula, String nome, String cargo, Integer id_equipe){
        return new Usuario(login, senha, matricula, nome, cargo, id_equipe);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Nome: " + getNome();
    }
}
