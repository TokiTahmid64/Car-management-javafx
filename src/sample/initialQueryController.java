package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class initialQueryController {

    Stage stg;
    Socket s;


    public void getStage(Stage s) {
        stg = s;
    }

    public void getSocket(Socket s) {
        this.s = s;
    }


    public void user(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userLogin.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        userLoginController controller = loader.getController();
        controller.getStage(window);

        window.show();
    }

    public void manufacturer(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manufactLogin.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        client controller = loader.getController();
        controller.getStage(window);


        window.show();
    }

    public void quit(ActionEvent e) {
        stg.close();
    }
}
