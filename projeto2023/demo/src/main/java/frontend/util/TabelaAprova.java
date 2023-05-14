package frontend.util;

import javafx.scene.control.CheckBox;

public class TabelaAprova {

    private String colaborador;
    private String dataHoraInicial;
    private String dataHoraFinal;
    private String cliente;
    private String tipo;
    private String totalDeHoras;
    private CheckBox selecione;

    public TabelaAprova(String colaborador, String dataHoraInicial, String dataHoraFinal, String cliente, String tipo,
            String totalDeHoras) {
        this.colaborador = colaborador;
        this.dataHoraInicial = dataHoraInicial;
        this.dataHoraFinal = dataHoraFinal;
        this.cliente = cliente;
        this.tipo = tipo;
        this.totalDeHoras = totalDeHoras;
        this.selecione = new CheckBox();
    }

    public String getColaborador() {
        return colaborador;
    }

    public String getDataHoraInicial() {
        return dataHoraInicial;
    }

    public String getDataHoraFinal() {
        return dataHoraFinal;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTotalDeHoras() {
        return totalDeHoras;
    }

    public CheckBox getSelecione() {
        return selecione;
    }

}
