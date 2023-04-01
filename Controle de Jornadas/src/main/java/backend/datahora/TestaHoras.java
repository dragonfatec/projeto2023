package backend.datahora;


public class TestaHoras {
    public static void main(String[] args){

        Data data = new Data();

        // System.out.println(data.validarData("29/02/2016"));
        // System.out.println(data.validarData("29/02/2017"));
        // System.out.println(data.validarData("30/01/2017"));
        // System.out.println(data.validarData("31/04/2017"));

        System.out.println(data.pegarDiferencaMinutos("30/03/2023 20:00", "30/03/2023 21:25"));

        
    }
}
