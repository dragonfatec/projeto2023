package frontend.util;

import javafx.scene.control.CheckBox;

public class TabelaUsuario {
    private String matricula;
    private String nome;
    private CheckBox selecione;

    public TabelaUsuario(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.selecione = new CheckBox();
    }
    public String getMatricula() {
        return matricula;
    }
    public String getNome() {
        return nome;
    }
    public CheckBox getSelecione(){
        return selecione;
    }
    public boolean estaSelecionado() {
        return selecione.isSelected();
    }
    public void selecionarUsuario() {
        this.selecione.setSelected(true);
    }
}
