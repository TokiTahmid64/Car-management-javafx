package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class editCarInterfaceController {
    Socket s;
    String reply;
    Stage stg;


    @FXML
    private TextField registration;
    @FXML
    private TextField model;
    @FXML
    private TextField price;
    @FXML
    private TextField available;
    @FXML
    private TextField color1;
    @FXML
    private TextField color2;
    @FXML
    private TextField color3;
    @FXML
    private TextField year;
    @FXML
    private TextField manufacturer;
    @FXML
    private ImageView image;
    String imagePath;

    public void socketInfo(Socket s) {
        this.s = s;
    }

    public void stageInfo(Stage s) {
        this.stg = s;
    }

    public void getString(String temp) throws IOException {
        this.reply = temp;

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

            imagePath = token[9];
            System.out.println(token[9]);
            image.setImage(new Image(token[9]));
        }
    }


    public void update(ActionEvent e) throws IOException {
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        String send = "updateCar" + ";" + registration.getText() + "," + model.getText() + "," + price.getText() + "," + available.getText() + "," + color1.getText() + "," + color2.getText() + "," + color3.getText() + "," + year.getText() + "," + manufacturer.getText() + "," + imagePath;
        writer.println(send);
        toast.makeText(stg, "Updated Successfully", 2000, 500, 500);


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

    public void choose(ActionEvent e) {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        System.out.println(selected.toURI().toString());
        if (selected != null) {
            image.setImage(new Image(selected.toURI().toString()));
            imagePath = selected.toURI().toString();

        }
    }
}
