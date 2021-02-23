package sample;

import com.jfoenix.controls.JFXButton;
import connectivity.dbconnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.System.exit;

public class admin extends Application {
    @FXML
    private JFXButton viewAll;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton delete;
    private Stage stg = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();
        stg.setScene(new Scene(root));


        stg.initStyle(StageStyle.UNDECORATED);
        stg.show();
        

    }

    public void viewAll(ActionEvent e) throws SQLException {
        dbconnection conn = new dbconnection();
        Connection c = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        String s;
        sql = "SELECT * FROM users";


        String allInfo = "";

        try {
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String user = rs.getString("Username");
                String pass = rs.getString("Password");
                String temp = user + "," + pass + ";";
                allInfo = allInfo + temp;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String token[] = allInfo.split(";");


        TableView<user> tableView = new TableView<user>();
        tableView.isResizable();
        tableView.setLayoutX(1000);
        tableView.setLayoutY(1000);


        TableColumn<user, String> column1 = new TableColumn<>("UserName");
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));


        TableColumn<user, String> column2 = new TableColumn<>("Password");
        column2.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);


        for (int i = 0; i < token.length; i++) {

            String tok[] = token[i].split(",");
            user use = new user(tok[0], tok[1]);
            tableView.getItems().add(use);
        }

        Stage stage = new Stage();
        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        scene.getStylesheets().add(manufactMainController.class.getResource("table.css").toExternalForm());

        stage.setScene(scene);

        stage.show();

    }

    public void add(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddManufacturer.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(mainScene);
        window.show();
    }

    public void exit_(ActionEvent e) {

        exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
