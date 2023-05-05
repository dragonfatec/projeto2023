package backend.login;

public class Login {
    public boolean verificarLogin(String user, String senha) {


        return false;
    }

    private String[] consultarLoginNoBanco(String user, String senha){
        String[] loginInfo = {};

        // fazer a consulta e adicionar no loginInfo

        // Jeito de usar o Criptografia...
         String senhaTeste = "senhadeteste";
         System.out.println(Criptografia.criptografar(senha));

        return loginInfo;
    }
}
