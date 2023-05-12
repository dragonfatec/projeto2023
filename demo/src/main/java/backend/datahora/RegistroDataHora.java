package backend.datahora;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegistroDataHora {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public double calcularHorasExtras(String dataHoraInicio, String dataHoraFim){
        if (vaidarDataESequencia(dataHoraInicio, dataHoraFim)){
            int minutos = pegarDiferencaMinutos(dataHoraInicio, dataHoraFim);
            // minutos dividido por 60 (quantidade de minutos me 1 hora) vezes o valor (em reais) da hora
            return minutos;

        }
        return 0;
    }

    public boolean vaidarDataESequencia(String dataHoraInicio, String dataHoraFim){
        if (validarData(dataHoraInicio) && validarData(dataHoraFim)){
            return verificarSequenciaDatas(dataHoraInicio, dataHoraFim);
        }
        return false;
    }

    public boolean validarData(String data){
        try {
            String dateFormat = "dd/MM/uuuu HH:mm";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern(dateFormat)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(data, dateTimeFormatter);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public int pegarDiferencaMinutos(String dtInicio, String dtFim){
        try {
            // obtém a diferença entre duas datas em minutos
            long elapsedms = simpleDateFormat.parse(dtFim).getTime() - simpleDateFormat.parse(dtInicio).getTime();
            long diferencaMinutos = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);
            return (int) diferencaMinutos;
        }
        catch (ParseException e) {
            return -1;
        }
    }

    public boolean verificarSequenciaDatas(String dataInicio, String dataFim){
        try {
            Date dataInicioFormatada = simpleDateFormat.parse(dataInicio);
            Date dataFimFormatadata = simpleDateFormat.parse(dataFim);
            return dataFimFormatadata.compareTo(dataInicioFormatada) > 0 && new Date().compareTo(dataFimFormatadata) > 0;
        }
        catch(ParseException e){
            return false;
        }
    }
}
