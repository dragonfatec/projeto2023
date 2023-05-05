package frontend.util;

import javafx.scene.control.TableView;

public class Tabela {

    public String data;
    public String horaInicio;
    public String horaFim;
    public String projeto;
    public String status;

    public Tabela(String data, String horaInicio, String horaFim, String projeto, String status){
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.projeto = projeto;
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getProjeto() {
        return projeto;
    }

    public String getStatus() {
        return status;
    }
}
