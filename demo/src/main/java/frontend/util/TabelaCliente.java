package frontend.util;

import javafx.scene.control.CheckBox;

public class TabelaCliente {
    private int id;
    private final String empresa;
    private final String responsavel;
    private final CheckBox selecione;
    public TabelaCliente(String empresa, String responsavel) {
        this.empresa = empresa;
        this.responsavel = responsavel;
        this.selecione = new CheckBox();
    }

    public String getEmpresa() {
        return empresa;
    }
    public String getResponsavel() {
        return responsavel;
    }
    public CheckBox getSelecione() {
        return selecione;
    }
    public boolean getSeEstaSelecionado() {
        return selecione.isSelected();
    }
    public void selecionarUsuario() {
        this.selecione.setSelected(true);
    }
}
