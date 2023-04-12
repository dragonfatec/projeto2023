package backend.datahora;

public class TestaHoras {
    private static ConexaoDAO service = new ConexaoDAO();

    public static void main(String[] args){

        Data data = new Data();

        // System.out.println(data.validarData("29/02/2016"));
        // System.out.println(data.validarData("29/02/2017"));
        // System.out.println(data.validarData("30/01/2017"));
        // System.out.println(data.validarData("31/04/2017"));

        System.out.println(data.calcularHorasExtras("04/04/2023 20:00", "04/04/2023 22:00"));

//        service.cadastrarUsuario("Teste100000","123","1111","Lucas Oliveira","Dev");

        System.out.println(new ConexaoDAO().usuarioCadastrado());

    }
}
