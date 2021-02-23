package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class viewModelUserController {
    @FXML
    private TextField model;
    @FXML
    private TextField carmake;
    Socket c;
    String mod;
    String cm;
    Stage stg;

    public void getSocket(Socket s) {
        this.c = s;
    }

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void enter(ActionEvent e) throws IOException {
        mod = model.getText();
        cm = carmake.getText();
        if (mod.equals("") || cm.equals("")) {
            toast.makeText(stg, "Please enter valid fields", 2000, 500, 500);
        } else {
            PrintWriter writer = new PrintWriter(c.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(c.getInputStream()));

            writer.println("searchCar2" + ";" + mod + "," + cm);
            String reply = input.readLine();
            //System.out.println(reply);
            if (reply.equals("No")) {
                toast.makeText(stg, "Car not found", 2000, 500, 500);

            } else {


                String token[] = reply.split(";");

                TableView tableView = new TableView();


                TableColumn<carClass, String> column1 = new TableColumn<>("Registration");
                column1.setCellValueFactory(new PropertyValueFactory<>("registration"));


                TableColumn<carClass, String> column2 = new TableColumn<>("Model");
                column2.setCellValueFactory(new PropertyValueFactory<>("model"));

                TableColumn<carClass, String> column3 = new TableColumn<>("Price");
                column3.setCellValueFactory(new PropertyValueFactory<>("price"));

                TableColumn<carClass, String> column4 = new TableColumn<>("Available");
                column4.setCellValueFactory(new PropertyValueFactory<>("available"));

                TableColumn<carClass, String> column5 = new TableColumn<>("Color1");
                column5.setCellValueFactory(new PropertyValueFactory<>("color1"));

                TableColumn<carClass, String> column6 = new TableColumn<>("Color2");
                column6.setCellValueFactory(new PropertyValueFactory<>("color2"));

                TableColumn<carClass, String> column7 = new TableColumn<>("Color3");
                column7.setCellValueFactory(new PropertyValueFactory<>("color3"));

                TableColumn<carClass, String> column8 = new TableColumn<>("Year");
                column8.setCellValueFactory(new PropertyValueFactory<>("year"));

                TableColumn<carClass, String> column9 = new TableColumn<>("Manufacturer");
                column9.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

                TableColumn<carClass, ImageView> column10 = new TableColumn<>("Image");
                column10.setCellValueFactory(new PropertyValueFactory<>("image"));

                tableView.getColumns().add(column1);
                tableView.getColumns().add(column2);
                tableView.getColumns().add(column3);
                tableView.getColumns().add(column4);
                tableView.getColumns().add(column5);
                tableView.getColumns().add(column6);
                tableView.getColumns().add(column7);
                tableView.getColumns().add(column8);
                tableView.getColumns().add(column9);
                tableView.getColumns().add(column10);


                ImageView img;

                for (int i = 0; i < token.length; i++) {

                    String tok[] = token[i].split(",");
                    img = new ImageView(new Image(tok[9]));
                    img.setFitHeight(200);
                    img.setFitWidth(200);


                    carClass car = new carClass(tok[0], tok[1], tok[2], tok[3], tok[4], tok[5], tok[6], tok[7], tok[8], img);
                    tableView.getItems().add(car);
                }


                Stage stage = new Stage();
                VBox vbox = new VBox(tableView);
                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(manufactMainController.class.getResource("table.css").toExternalForm());
                stage.setScene(scene);
                stage.setAlwaysOnTop(true);
                stage.setTitle("All car list");
                stage.showAndWait();

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
        controller.socketInfo(c);
        controller.getStage(window);

        window.show();
    }
}
