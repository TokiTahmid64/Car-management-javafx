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

public class editCarController {
    @FXML
    private TextField reg;

    Socket s;
    Stage stg;

    public void socketInfo(Socket s) {
        this.s = s;
    }

    public void stageInfo(Stage s) {
        this.stg = s;
    }

    public void search(ActionEvent e) throws IOException {
        String temp = reg.getText();
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        writer.println("searchCar" + ";" + temp);
        String reply = input.readLine();
        System.out.println(reply);
        if (reply.equals("No")) {
            toast.makeText(stg, "Car not found", 2000, 500, 500);
            // stg.close();
        } else {


            stg.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editCarInterface.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);
            Stage window = new Stage();
            window.setScene(mainScene);
            editCarInterfaceController controller = loader.getController();
            controller.socketInfo(s);
            controller.getString(reply);
            controller.stageInfo(window);

            window.show();
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
        controller.getStage(window);

        window.show();
    }
}
