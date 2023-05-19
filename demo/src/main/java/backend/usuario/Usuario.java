package backend.usuario;

import java.sql.SQLException;

public class Usuario {
    private String matricula;
    private String senha;
    private String nome;
    private TiposDeUsuario cargo;
    private static Usuario instancia;
    private Usuario(){
    }
    private Usuario(String matricula, String senha, String nome, String cargo){
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
        switch (cargo.toLowerCase()){
            case "colaborador":
                this.cargo = TiposDeUsuario.Colaborador;
                break;
            case "gerente":
                this.cargo = TiposDeUsuario.Gerente;
                break;
            case "rh":
                this.cargo = TiposDeUsuario.RH;
                break;
        }
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
    public String getMatricula() {
        return matricula;
    }
    public String getNome() {
        return nome;
    }
    public TiposDeUsuario getCargo() {
        return cargo;
    }
    @Override
    public String toString() {
        return "Nome: " + getNome();
    }
}
