package sample;

import connectivity.dbconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class clientThreadFromServerEnd extends Thread {
    Socket soc;
    ServerSocket server_;
    String identificationMsg;

    clientThreadFromServerEnd(ServerSocket server_, Socket s, String id) {
        this.server_ = server_;
        this.soc = s;
        this.identificationMsg = id;
    }

    public void run() {


        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            PrintWriter output = new PrintWriter(soc.getOutputStream(), true);
            dbconnection conn = new dbconnection();
            Connection c = conn.getConnection();

            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql;
            String s;


            while (true) {


                // System.out.println("painai");
                s = input.readLine();
                // System.out.println("paisi");
                String token[] = s.split(";");
                conn = new dbconnection();
                c = conn.getConnection();

                ps = null;
                rs = null;

                if (token[0].equals("viewAllCars")) {

                    sql = "SELECT * FROM CarData";


                    String allCarsInfo = "";

                    try {
                        ps = c.prepareStatement(sql);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String reg = rs.getString("Registration");
                            String mod = rs.getString("Model");
                            String pr = rs.getString("Price");
                            String av = rs.getString("Available");
                            String col1 = rs.getString("Color1");
                            String col2 = rs.getString("Color2");
                            String col3 = rs.getString("Color3");
                            String yr = rs.getString("Year_");
                            String mn = rs.getString("Manufacturer");
                            String img = rs.getString("image");
                            String temp = reg + "," + mod + "," + pr + "," + av + "," + col1 + "," + col2 + "," + col3 + "," + yr + "," + mn + "," + img + ";";

                            allCarsInfo = allCarsInfo + temp;


                        }
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }
                    output.println(allCarsInfo);
                } else if (token[0].equals("addNewCar")) {
                    String temp = token[1];
                    String tok[] = temp.split(",");
                    // System.out.println(tok.length);
                    // String temp=token[1];

                    sql = "Select * from CarData where Registration=?";
                    ps = c.prepareStatement(sql);
                    ps.setString(1, tok[0]);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        output.println("No");
                    } else {
                        System.out.println("entered");


                        sql = "INSERT INTO CarData(Registration,Model,Price,Available,Color1,Color2,Color3,Year_,Manufacturer,image) VALUES(?,?,?,?,?,?,?,?,?,?)";
                        try {
                            ps = c.prepareStatement(sql);
                            ps.setString(1, tok[0]);
                            ps.setString(2, tok[1]);
                            ps.setString(3, tok[2]);
                            ps.setString(4, tok[3]);
                            ps.setString(5, tok[4]);
                            ps.setString(6, tok[5]);
                            ps.setString(7, tok[6]);
                            ps.setString(8, tok[7]);
                            ps.setString(9, tok[8]);
                            ps.setString(10, tok[9]);
                            ps.execute();
                            //  System.out.println("Data inserted");

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        output.println("ok");
                    }
                    c.close();
                } else if (token[0].equals("deleteCar")) {

                    String temp = token[1];

                    sql = "Select * from CarData where Registration=?";
                    ps = c.prepareStatement(sql);
                    ps.setString(1, temp);
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        sql = "DELETE FROM CarData WHERE Registration=?";

                        ps = c.prepareStatement(sql);
                        ps.setString(1, temp);
                        ps.execute();
                        output.println("done");


                    } else {
                        output.println("No");
                    }
                    c.close();


                } else if (token[0].equals("searchCar")) {
                    String temp = token[1];

                    sql = "Select * from CarData where Registration=?";
                    ps = c.prepareStatement(sql);
                    ps.setString(1, temp);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        String send = rs.getString("Registration") + "," + rs.getString("Model") + "," + rs.getString("Price") +
                                "," + rs.getString("Available") + "," + rs.getString("Color1") + "," + rs.getString("Color2") + "," + rs.getString("Color3") + "," + rs.getString("Year_") + "," + rs.getString("Manufacturer") + "," + rs.getString("image");
                        output.println(send);
                    } else {
                        output.println("No");
                    }
                    c.close();


                } else if (token[0].equals("updateCar")) {
                    String temp = token[1];
                    String tok[] = temp.split(",");


                    sql = "UPDATE CarData set  Registration=?,Model= ?,Price=?,Available=?,Color1=?,Color2=?,Color3=?,Year_=?,Manufacturer=?,image=? WHERE Registration = ?";
                    ps = c.prepareStatement(sql);
                    ps.setString(1, tok[0]);
                    ps.setString(2, tok[1]);
                    ps.setString(3, tok[2]);
                    ps.setString(4, tok[3]);
                    ps.setString(5, tok[4]);
                    ps.setString(6, tok[5]);
                    ps.setString(7, tok[6]);
                    ps.setString(8, tok[7]);
                    ps.setString(9, tok[8]);
                    ps.setString(10, tok[9]);
                    ps.setString(11, tok[0]);
                    ps.execute();


                } else if (token[0].equals("exit")) {

                    output.println("bye");
                    soc.close();

                }


            }


        } catch (IOException | SQLException e) {
            System.out.println(e);

        }


    }

}
