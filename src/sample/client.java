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

public class client {
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    Stage stg;


    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void press(ActionEvent e) {
        try {


            if (userName.getText().equals("") || password.getText().equals("")) {
                toast.makeText(stg, "Enter valid UserName and Password", 2000, 500, 500);
            } else {
                Socket s = new Socket("localhost", 6000);
                PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String toSend = "manufacturer;" + userName.getText() + "," + password.getText();
                writer.println(toSend);
                String reply = input.readLine();
                System.out.println(reply);
                if (reply.equals("Welcome")) {

                    stg.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("manufactMainPage.fxml"));
                    Parent root = loader.load();
                    Scene mainScene = new Scene(root);
                    Stage window = new Stage();
                    window.setScene(mainScene);
                    manufactMainController controller = loader.getController();
                    controller.socketInfo(s);
                    controller.getStage(window);

                    window.show();

                } else {
                    toast.makeText(stg, "Wrong UserName or Password", 2000, 500, 500);
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }


}
