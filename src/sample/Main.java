package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("initialQuery.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Welcome");

        primaryStage.initStyle(StageStyle.UNDECORATED);
        initialQueryController controller = loader.getController();
        //controller.socketInfo(s);
        controller.getStage(primaryStage);
        primaryStage.show();

        //window.show();*/


    }


    public static void main(String[] args) {
        launch(args);
    }
}
