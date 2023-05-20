package frontend.controllers;

import frontend.aplicacao.App;
import frontend.util.NomesArquivosFXML;

import java.io.IOException;

public class Util {
    /*
    A ideia é que esse metodo poderia ser um "extends" dos outros controllers, e então usar esses metodos e talvez
    até outros metodos que todas as telas tem em comum
    */

    public void irParaAprovaHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.aprovaHora + ".fxml");
    }

    public void irParaCadastraUsuario() throws IOException {
        App.mudarTela(NomesArquivosFXML.cadastraUsuario + ".fxml");
    }

    public void irParaConsultaHoraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.consultaHora + ".fxml");
    }

    public void irParaRegistraHora() throws IOException {
        App.mudarTela(NomesArquivosFXML.registraHora + ".fxml");
    }
}
