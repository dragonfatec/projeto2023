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

        // CÓDIGO PARA TESTE JAVAFX
        // stage.setTitle("2rpnet");
        // Button botao = new Button("clique aqui");
        // botao.setOnAction(new EventHandler<ActionEvent>() {

        //     @Override
        //     public void handle(ActionEvent event) {
        //         System.out.println("Clicou no botao");
        //     }
        // });

        // StackPane root = new StackPane();
        // root.getChildren().addAll(botao);
        // stage.setScene(new Scene(root, 250, 400, false, null));
        // stage.show();

        stagePrincipal = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("colaborador_registrohora.fxml"));
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
}
