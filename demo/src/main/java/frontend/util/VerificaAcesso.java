package frontend.util;

import backend.usuario.TiposDeUsuario;
import javafx.scene.control.Button;

public class VerificaAcesso {
    private static boolean verificarRegraDeAcesso(TiposDeUsuario tipoUsuario, NomesArquivosFXML telaNome) {
        switch (tipoUsuario.toString().toLowerCase()){
            case "colaborador":
                return telaNome == NomesArquivosFXML.admin || telaNome == NomesArquivosFXML.registraHora;
            case "gerente":
                return telaNome == NomesArquivosFXML.admin || telaNome == NomesArquivosFXML.registraHora || telaNome == NomesArquivosFXML.aprovaHora;
            case "rh":
                return telaNome == NomesArquivosFXML.aprovaHora || telaNome == NomesArquivosFXML.cadastra;
            default:
                return false;
        }
    }

    public static void verificarAcesso(Button button, TiposDeUsuario tipoUsuario, NomesArquivosFXML telaNome){
        if (!verificarRegraDeAcesso(tipoUsuario, telaNome)){
            button.setVisible(false);
        }
    }
}
