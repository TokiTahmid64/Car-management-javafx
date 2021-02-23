package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class viewRegUserController {
    @FXML
    private JFXTextField Reg;
    Socket s;
    Stage stg;

    public void getSocket(Socket s) {
        this.s = s;
    }

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void enter(ActionEvent e) throws IOException {
        //  stg.close();

        String temp = Reg.getText();
        if (temp.equals("")) {
            toast.makeText(stg, "Please Enter Valid Registration Numebr", 2000, 500, 500);
        } else {
            PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

            writer.println("searchCar" + ";" + temp);
            String reply = input.readLine();
            if (reply.equals("No")) {
                toast.makeText(stg, "Car not found", 2000, 500, 500);

            } else {


                stg.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRegUserInterface.fxml"));
                Parent root = loader.load();
                Scene mainScene = new Scene(root);
                Stage window = new Stage();
                window.setScene(mainScene);
                viewRegUserInterfaceController controller = loader.getController();
                controller.socketInfo(s);
                controller.getString(reply);
                controller.stageInfo(window);

                window.show();
            }
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
