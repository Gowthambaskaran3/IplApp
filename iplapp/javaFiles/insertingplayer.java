package iplApp;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class insertingplayer {
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

            String fileName = "D:\\iplapp\\input.txt";
            Scanner scanner = new Scanner(Paths.get(fileName));
            String teamName = "", playerName = "",teamId="",playerScore="";
            while(scanner.hasNext()) {
                String content = scanner.nextLine();
                String[] str = content.split("\t");
                teamName = str[0];
                playerName = str[1];

                rsSet = Stmnt.executeQuery("select * from iplteam");
                while(rsSet.next()){
                    if(teamName.equals(rsSet.getString("teamName"))){
                        teamId=new String(rsSet.getString("teamId"));
                    }}
                Scanner keyBoardInput = new Scanner(System.in);
                System.out.println("Enter the score of "+playerName);
                playerScore = keyBoardInput.nextLine();

                Stmnt.executeUpdate("insert into ipltable(teamId,teamName,playerName,playerScore) values('" + teamId + "','" + teamName + "','" + playerName + "','"+playerScore+"')");
                System.out.println("Inserted "+playerName+"into the DB");
            }
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
