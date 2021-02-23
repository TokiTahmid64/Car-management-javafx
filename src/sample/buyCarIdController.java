package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class buyCarIdController {

    @FXML
    private Button enter;

    @FXML
    private TextField id;
    Socket s;
    Stage stg;

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void getSocket(Socket s) {
        this.s = s;
    }

    public void enter(ActionEvent e) throws IOException {
        String temp = id.getText();

        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        writer.println("searchCar" + ";" + temp);
        String reply = input.readLine();
        if (reply.equals("No")) {
            toast.makeText(stg, "Car not found", 2000, 500, 500);
        } else {
            stg.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("buyCarInterface.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);
            Stage window = new Stage();
            window.setScene(mainScene);
            buyCarInterfaceController controller = loader.getController();
            controller.socketInfo(s);
            controller.getString(reply);
            controller.stageInfo(window);

            window.show();
        }


    }

    public void back(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userMainPage.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        userMainPageController controller = loader.getController();
        controller.socketInfo(s);
        // controller.getString(reply);
        controller.getStage(window);

        window.show();
    }
}
