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

public class server {

    public static void main(String[] args) {


        {
            try (ServerSocket ss = new ServerSocket(6000)) {
                while (true) {

                    Socket s = ss.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String received = input.readLine();
                    String token[] = received.split(";");
                    int state = 0;
                    if (token[0].equals("manufacturer")) {

                        dbconnection conn = new dbconnection();
                        Connection c = conn.getConnection();

                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        String sql;

                        BufferedReader inp = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream(), true);

                        String identification[] = token[1].split(",");


                        sql = "SELECT * FROM users WHERE Username= '" + identification[0] + "' AND Password= '" + identification[1] + "'";

                        ps = c.prepareStatement(sql);

                        rs = ps.executeQuery();

                        if (rs.next()) {

                            output.println("Welcome");
                            state = 1;
                            //Manufacturer thread
                            clientThreadFromServerEnd thread = new clientThreadFromServerEnd(ss, s, token[1]);
                            thread.start();
                        }
                        if (state == 0) {
                            output.println("Wrong");

                        }

                        c.close();
                    } else {
                        // Usual user thread
                        userThreadFromServerEnd thread = new userThreadFromServerEnd(ss, s);
                        thread.start();
                    }
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
