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

public class userThreadFromServerEnd extends Thread {
    ServerSocket ss;
    Socket soc;
    String s1;

    userThreadFromServerEnd(ServerSocket ss, Socket s) {
        this.ss = ss;
        this.soc = s;
    }

    @Override
    public void run() {


        try {


            BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            PrintWriter output = new PrintWriter(soc.getOutputStream(), true);


            dbconnection conn = new dbconnection();
            Connection c = conn.getConnection();

            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql;

            while (true) {


                s1 = input.readLine();


                String token[] = s1.split(";");
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
                } else if (token[0].equals("searchCar")) {
                    String temp = token[1];

                    sql = "Select * from CarData where Registration=?";
                    try {
                        ps = c.prepareStatement(sql);
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }
                    try {
                        ps.setString(1, temp);
                    } catch (SQLException throwables) {
                        // throwables.printStackTrace();
                        System.out.println("");
                    }
                    try {
                        rs = ps.executeQuery();
                    } catch (SQLException throwables) {
                        // throwables.printStackTrace();
                        System.out.println("");
                    }


                    if (rs.next()) {
                        String send = rs.getString("Registration") + "," + rs.getString("Model") + "," + rs.getString("Price") +
                                "," + rs.getString("Available") + "," + rs.getString("Color1") + "," + rs.getString("Color2") + "," + rs.getString("Color3") + "," + rs.getString("Year_") + "," + rs.getString("Manufacturer") + "," + rs.getString("image");
                        output.println(send);
                    } else {
                        output.println("No");
                    }
                    c.close();


                    try {
                        c.close();
                    } catch (SQLException throwables) {

                        System.out.println("");
                    }


                } else if (token[0].equals("buyCar")) {

                    String temp = token[1];
                    sql = "SELECT * FROM carData where Registration=?";
                    try {
                        ps = c.prepareStatement(sql);
                    } catch (SQLException throwables) {

                        System.out.println("");
                    }
                    try {
                        ps.setString(1, temp);
                    } catch (SQLException throwables) {
                        // throwables.printStackTrace();
                        System.out.println("");
                    }
                    try {
                        rs = ps.executeQuery();
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }
                    String reply = null;


                    try {
                        reply = rs.getString("Available");
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }


                    try {
                        c.close();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    conn = new dbconnection();
                    c = conn.getConnection();


                    int num = Integer.parseInt(reply);
                    if (num <= 0) {
                        output.println("No");
                    } else {
                        sql = "UPDATE CarData set  Available=? WHERE Registration = ?";
                        try {
                            ps = c.prepareStatement(sql);
                            ps.setString(1, String.valueOf(num - 1));
                            ps.setString(2, temp);
                            ps.execute();
                        } catch (SQLException throwables) {
                            // throwables.printStackTrace();
                            System.out.println("");
                        }
                        output.println("done");
                    }


                } else if (token[0].equals("searchCar2")) {
                    String temp = token[1];
                    // System.out.println("....");
                    // System.out.println(temp);
                    // System.out.println("....");
                    String tok[] = temp.split(",");

                    sql = "Select * from CarData where Model=? AND Manufacturer=?";
                    try {
                        ps = c.prepareStatement(sql);
                    } catch (SQLException throwables) {
                        // throwables.printStackTrace();
                        System.out.println("");
                    }
                    try {
                        ps.setString(1, tok[0]);
                        ps.setString(2, tok[1]);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        rs = ps.executeQuery();
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }
                    ResultSet rs2 = rs;

                    String send = "";

                    try {
                        // if (rs2.next()) {
                        while (rs.next()) {
                            System.out.println("ay ay chad mama");
                            try {
                                temp = rs.getString("Registration") + "," + rs.getString("Model") + "," + rs.getString("Price") +
                                        "," + rs.getString("Available") + "," + rs.getString("Color1") + "," + rs.getString("Color2") + "," + rs.getString("Color3") + "," + rs.getString("Year_") + "," + rs.getString("Manufacturer") + "," + rs.getString("image") + ";";
                                send = send + temp;
                            } catch (SQLException throwables) {
                                //throwables.printStackTrace();
                                System.out.println("");
                            }

                        }
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                    }


                    if (!send.equals("")) {
                        // System.out.println(send);
                        output.println(send);
                    } else {
                        output.println("No");
                    }
                    try {
                        c.close();
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        System.out.println("");
                    }

                }
                else if (token[0].equals("exit")) {

                    output.println("bye");
                    soc.close();

                }

            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }

    }
}
