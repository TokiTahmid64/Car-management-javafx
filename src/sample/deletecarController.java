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

public class deletecarController {
    @FXML
    private TextField registration;
    Socket s;
    Stage stg;

    public void socketInfo(Socket s) {
        this.s = s;
    }

    public void stageInfo(Stage s) {
        this.stg = s;
    }

    public void delete(ActionEvent e) throws IOException {
        String reg = registration.getText();
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String s = "deleteCar" + ";" + reg;
        writer.println(s);
        String reply = input.readLine();
        if (reply.equals("No")) {
            toast.makeText(stg, "Car not found", 2000, 500, 500);
        } else {
            toast.makeText(stg, "Deleted Successfully", 2000, 500, 500);
        }
    }

    public void back(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manufactMainPage.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        manufactMainController controller = loader.getController();
        controller.socketInfo(s);
        controller.getStage(stg);

        window.show();
    }
}
