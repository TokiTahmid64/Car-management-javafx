package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class viewModelInterfaceController {
    @FXML
    private TextField registration;

    @FXML
    private TextField model;

    @FXML
    private TextField price;

    @FXML
    private TextField color1;

    @FXML
    private TextField color2;

    @FXML
    private TextField color3;

    @FXML
    private TextField available;

    @FXML
    private TextField year;

    @FXML
    private TextField manufacturer;

    Socket s;
    Stage stg;
    String reply;
    @FXML
    private ImageView image;

    public void getSocket(Socket s) {
        this.s = s;
    }

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void getString(String t1) {


        reply = t1;
        String token[] = reply.split(",");
        registration.setText(token[0]);
        model.setText(token[1]);
        price.setText(token[2]);
        available.setText(token[3]);
        color1.setText(token[4]);
        color2.setText(token[5]);
        color3.setText(token[6]);
        year.setText(token[7]);
        manufacturer.setText(token[8]);
        if (token[9] != null) {


            image.setImage(new Image(token[9]));
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
        controller.getStage(window);

        window.show();
    }


}
