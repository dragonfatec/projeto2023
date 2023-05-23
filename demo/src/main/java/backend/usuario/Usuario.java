package backend.usuario;

public class Usuario {
    private String matricula;
    private String senha;
    private String nome;
    private TiposDeUsuario cargo;
    private Situacao situacao;
    private static Usuario instancia;
    private Usuario(){
    }
    private Usuario(String matricula, String senha, String nome, String cargo, Situacao situacao){
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
        this.situacao = situacao;
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
    public static void criarInstancia(String matricula, String senha,  String nome, String cargo, Situacao situacao){
        if (instancia == null){
            instancia = new Usuario(matricula, senha,  nome, cargo, situacao);
        }
    }
    public static Usuario criarUsuario(String matricula, String senha,  String nome, String cargo, Situacao situacao){
        return new Usuario(matricula, senha,  nome, cargo, situacao);
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
    public Situacao situacao(){return situacao;}
    @Override
    public String toString() {
        return "Nome: " + getNome();
    }
}
