package iplApp;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class outputPlayer {
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

            FileOutputStream file = new FileOutputStream("D:\\iplapp\\ouput.txt");
            DataOutputStream data = new DataOutputStream(file);

            for(int i=1;i<4;i++) {
                rsSet = Stmnt.executeQuery("select * from ipltable where teamId = '"+i+"'order by playerScore ASC");
                while (rsSet.next()) {
                    String teamId = rsSet.getString("teamId");
                    String teamName = rsSet.getString("teamName");
                    String playerName = rsSet.getString("playerName");
                    String playerScore = rsSet.getString("playerScore");
                    String s =teamId+" "+teamName+" "+playerName+" " +playerScore+"\n";
                    data.writeBytes(s);
                }
                System.out.println("Team "+i+" has been written to output.txt" );
            }
            System.out.println("The players data have been written to output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
