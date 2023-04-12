package backend.usuario;

import java.sql.SQLException;

public class Usuario {

    private String login;
    private String senha;
    private String matricula;
    private String nome;
    private  String tipo;

    public Usuario(String login, String senha, String matricula, String nome, String tipo){
        this.login = login;
        this.senha = senha;
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Nome: " + getNome();
    }
}
