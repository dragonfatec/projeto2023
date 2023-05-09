package backend.cliente;

public class Cliente {
    private String empresa;
    private String responsavel;
    private String email;
    private String telefone;
    private String projeto;

    public Cliente(String nomeCliente, String responsavel, String email, String telefone, String projeto) {
        this.empresa = nomeCliente;
        this.responsavel = responsavel;
        this.email = email;
        this.telefone = telefone;
        this.projeto = projeto;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }
}
