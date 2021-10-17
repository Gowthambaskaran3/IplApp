package iplApp;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class updateScore {
    public static void main(String[] args) {
        Connection Conn = null;
        Statement Stmnt = null;
        ResultSet rsSet = null;
        try {

            Properties props = new Properties();
            props.load(new FileInputStream("D:\\iplapp\\ipl.properties"));

            String theUser = props.getProperty("user");
            String thePassword = props.getProperty("password");
            String theDburl = props.getProperty("dburl");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theDburl);
            System.out.println("User: " + theUser);


            Conn = DriverManager.getConnection(theDburl, theUser, thePassword);

            System.out.println("\nConnection successful!\n");

            Stmnt = Conn.createStatement();
            Scanner keyBoardInput = new Scanner(System.in);
            for (int i = 0; i<1; i++) {

                System.out.println("Enter player name :");
                String playerName = keyBoardInput.nextLine();
                System.out.println("Enter player score :");
                String playerScore = keyBoardInput.nextLine();
                rsSet = Stmnt.executeQuery("select * from ipltable");

                while (rsSet.next()) {

                    if (playerName.equals(rsSet.getString("playerName"))) {
                        PreparedStatement prepStat = Conn.prepareStatement("update ipltable set playerScore = ? where playerName = ? ");
                        prepStat.setString(1, playerScore);
                        prepStat.setString(2, playerName);
                        prepStat.executeUpdate();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }