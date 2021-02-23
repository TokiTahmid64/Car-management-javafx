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

import java.io.*;
import java.net.Socket;

public class addNewCarController {
    Socket s;
    Stage stg;
    @FXML
    private TextField reg;
    @FXML
    private TextField mod;
    @FXML
    private TextField pr;
    @FXML
    private TextField av;
    @FXML
    private TextField col1;
    @FXML
    private TextField col2;
    @FXML
    private TextField col3;
    @FXML
    private TextField yr;
    @FXML
    private TextField man;
    @FXML
    private ImageView image;
    String imagePath = "";

    public void socketInfo(Socket s) {
        this.s = s;
    }

    public void stageInfo(Stage s) {
        this.stg = s;
    }

    public void add(ActionEvent e) throws IOException {
        String registration;
        String model;
        String price;
        String available;
        String color1;
        String color2;
        String color3;
        String Year;
        String Manufacturer;
        registration = reg.getText();
        model = mod.getText();
        price = pr.getText();
        available = av.getText();
        color1 = col1.getText();
        color2 = col2.getText();
        color3 = col3.getText();
        Year = yr.getText();
        Manufacturer = man.getText();
        try {
            Integer.parseInt(available);
            if (registration.equals("") || model.equals("") || price.equals("") || available.equals("") || color1.equals("") || color2.equals("") || color3.equals("") || Year.equals("") || Manufacturer.equals("") || imagePath.equals("")) {
                toast.makeText(stg, "Please enter valid Fields", 2000, 500, 500);
            } else {


                PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String temp = "addNewCar" + ";" + registration + "," + model + "," + price + "," + available + "," + color1 + "," + color2 + "," + color3 + "," + Year + "," + Manufacturer + "," + imagePath;
                writer.println(temp);
                String reply = input.readLine();
                if (reply.equals("No")) {
                    toast.makeText(stg, "Car already registered with this ID", 2000, 500, 500);
                } else {
                    toast.makeText(stg, "Added Successfully", 2000, 500, 500);
                }
            }
        } catch (Exception ex) {
            toast.makeText(stg, "Please Enter valid Number of cars", 2000, 500, 500);
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

    public void choose(ActionEvent e) {
        FileChooser fc = new FileChooser();
        File selected = fc.showOpenDialog(null);
        if (selected != null) {
            image.setImage(new Image(selected.toURI().toString()));
            imagePath = selected.toURI().toString();
        } else {
            toast.makeText(stg, "File not selected", 2000, 500, 500);
        }
    }
}
