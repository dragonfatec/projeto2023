package frontend.util;

import javafx.scene.control.TableView;

public class Tabela {

    public String dataInicio;
    public String dataFim;
    public String cliente;
    public String status;

    public Tabela(String dataInicio, String dataFim, String cliente, String status){
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cliente = cliente;
        this.status = status;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getCliente() {
        return cliente;
    }

    public String getStatus() {
        return status;
    }
}
