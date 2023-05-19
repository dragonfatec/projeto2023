package backend.usuario;

import java.sql.SQLException;

public class Usuario {
    private String matricula;
    private String senha;
    private String nome;
    private String cargo;
    private static Usuario instancia;
    private Usuario(){

    }
    private Usuario(String matricula, String senha, String nome, String cargo){
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
        this.cargo = cargo;
    }
    public static Usuario getInstancia(){
        if (instancia == null){
            instancia = new Usuario();
        }
        return instancia;
    }
    public static void criarInstancia(String matricula, String senha,  String nome, String cargo){
        if (instancia == null){
            instancia = new Usuario(matricula, senha,  nome, cargo);
        }
    }
    public static Usuario criarUsuario(String matricula, String senha,  String nome, String cargo){
        return new Usuario(matricula, senha,  nome, cargo);
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
