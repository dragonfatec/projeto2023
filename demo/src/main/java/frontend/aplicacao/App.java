package frontend.aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {


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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tela.fxml"));
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);

        stage.setTitle("2rpnet");
        stage.setScene(tela);
        stage.show();

    }

}
