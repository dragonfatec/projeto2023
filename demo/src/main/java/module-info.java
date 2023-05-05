module tela.registro.horas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens frontend.aplicacao to javafx.fxml;
    exports frontend.aplicacao;
    exports frontend.controllers;
    opens frontend.controllers to javafx.fxml;
    exports frontend.util;
    opens frontend.util to javafx.fxml;
}