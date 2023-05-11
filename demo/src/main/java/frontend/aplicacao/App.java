package frontend.aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Stage stagePrincipal;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stagePrincipal = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);
        stagePrincipal.setTitle("2rpnet");
        stagePrincipal.setScene(tela);
        stagePrincipal.show();
    }

    public static void mudarTela(String telaNome) throws IOException {
        // O nome da tela tem que vir com ".fxml"
        stagePrincipal.setScene(new Scene(new FXMLLoader(App.class.getResource(telaNome)).load()));
    }

    public static void sair(){
        stagePrincipal.close();
    }
}
