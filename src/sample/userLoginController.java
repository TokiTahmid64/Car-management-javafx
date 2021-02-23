package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class userLoginController {
    @FXML
    private TextField userField;
    Stage stg;

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void enter(ActionEvent e) throws IOException {
        try {
            Socket s = new Socket("localhost", 6000);
            PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));


            String toSend = "user;" + userField.getText();
            writer.println(toSend);


            stg.close();
            System.out.println("yoooo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userMainPage.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);
            Stage window = new Stage();
            window.setScene(mainScene);
            userMainPageController controller = loader.getController();
            controller.socketInfo(s);
            controller.getStage(window);

            window.show();
            toast.makeText(window, "Welcome " + userField.getText() + "!", 2000, 500, 500);


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
