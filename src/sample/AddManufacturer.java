package sample;

import connectivity.dbconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddManufacturer {

    @FXML
    private TextField user;

    @FXML
    private TextField pass;

    Stage stg;

    public void getStage(Stage stg) {
        this.stg = stg;
    }

    public void add_(ActionEvent e) {
        String user_ = user.getText();
        String pass_ = pass.getText();

        dbconnection conn = new dbconnection();
        Connection c = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        String s;

        sql = "INSERT INTO users(Username,Password) VALUES(?,?)";
        try {
            ps = c.prepareStatement(sql);
            ps.setString(1, user_);
            ps.setString(2, pass_);
            ps.execute();
            toast.makeText(stg, "Added", 2000, 500, 500);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
