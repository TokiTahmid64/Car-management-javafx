package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class manufactMainController {
    Socket s;
    Stage stg;

    public void socketInfo(Socket s) {
        this.s = s;
    }

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void viewAllCars(ActionEvent e) throws IOException {


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        writer.println("viewAllCars; ");
        String reply = null;
        try {
            reply = input.readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

    public void addNewCar(ActionEvent e) throws IOException {
        stg.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewCar.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        addNewCarController controller = loader.getController();
        controller.socketInfo(s);
        controller.stageInfo(window);


        window.show();
    }

    public void deleteCar(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteCar.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        deletecarController controller = loader.getController();
        controller.socketInfo(s);
        controller.stageInfo(window);

        window.show();

    }

    public void editCar(ActionEvent e) throws IOException {
        stg.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editCar.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        editCarController controller = loader.getController();
        controller.socketInfo(s);
        controller.stageInfo(window);

        window.show();

    }

    public void exit(ActionEvent e) throws IOException, InterruptedException {


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        writer.println("exit; ");
        String reply = null;
        try {
            reply = input.readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        stg.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("initialQuery.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        initialQueryController controller = loader.getController();
        controller.getSocket(s);
        controller.getStage(window);
        window.show();
        if (reply.equals("bye")) {
            Stage p = new Stage();
            p.setAlwaysOnTop(true);


            toast.makeText(p, "Thanks for visiting", 2000, 500, 500);

        }
    }


}
