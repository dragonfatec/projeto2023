package frontend.util;

import javafx.scene.control.CheckBox;

public class TabelaUsuario {
    private String matricula;
    private String nome;
    private CheckBox selecione;

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public boolean getSelecione() {
        return selecione.isSelected();
    }

    public void selecionarUsuario() {
        this.selecione.setSelected(true);
    }
}
