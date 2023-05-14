package backend.datahora;

public class DataHora {
    private String dataHoraInicio;
    private String dataHoraFim;
    private RegistroDataHora data;

    public DataHora(String dataHoraInicio, String dataHoraFim, RegistroDataHora data) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.data = new RegistroDataHora();
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(String dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
