package iplApp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;

public class insertPlayer {
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

            FileInputStream file = new FileInputStream("D:\\iplapp\\input.txt");
            DataInputStream in = new DataInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str;
            LinkedList list = new LinkedList();
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            Iterator itr;
            for (itr = list.iterator(); itr.hasNext(); ) {
                String st = itr.next().toString();
                String[] split = st.split("\t");
                String teamName = "", playerName = "",teamId="",playerScore="";
                for (int i = 0; i < split.length; i++) {
                    teamName = split[0];
                    playerName = split[1];
                }

                rsSet = Stmnt.executeQuery("select * from iplteam");
                while(rsSet.next()){
                    if(teamName.equals(rsSet.getString("teamName"))){
                        teamId=new String(rsSet.getString("teamId"));
                    }}
                Scanner keyBoardInput = new Scanner(System.in);
                System.out.println("Enter the score of "+playerName);
                playerScore = keyBoardInput.nextLine();


                int k = Stmnt.executeUpdate("insert into ipltable(teamId,teamName,playerName,playerScore) values('" + teamId + "','" + teamName + "','" + playerName + "','"+playerScore+"')");
                System.out.println("inserted "+playerName+" into db");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
