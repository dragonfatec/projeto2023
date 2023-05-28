package frontend.util;

import javafx.scene.control.CheckBox;

public class TabelaEquipe {
    private int id;
    private String nome;

    public TabelaEquipe(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
