package com.example1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.beans.Statement;
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App {

    

    public static void main(String[] args) throws IOException {

        try {
            String url = new String("https://www.gismeteo.ru/diary/4248/");
            Document doc = Jsoup.connect(url).get();

            Elements number = doc.select("td[class=first]");
            Elements values = doc.select("td[class=first_in_group positive]");
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" +
                "user=root&password=89Monkey89")){

                    //java.sql.Statement state = conn.createStatement();
                    
                    String sqlUpdate = " INSERT INTO weather(Daty) " + " VALUES( ? ) ";
                    String sqlUpdate1 = " UPDATE weather " + " SET Temp_day = ? " + " WHERE Daty = ? ";

                    PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
                    PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdate1);
                    

                    List<Integer> newList = new ArrayList<Integer>();
                    List<Integer> newList1 = new ArrayList<Integer>();

                    String valu = values.select("td[class=first_in_group positive]").text();
                    String[] val = valu.split(" ");
                    
                    for(String g : val) {
                        System.out.println(g);
                        newList1.add(Integer.parseInt(g));
                    }
    
                    String numb = number.select("td[class=first]").text( );
                    System.out.println(numb);
                    String[] num = numb.split(" ");
                    System.out.println(num[0]);

                    for(String n : num) {
                        newList.add(Integer.parseInt(n));

                    }

                    for(int numberElementInList = 0; numberElementInList < num.length; numberElementInList++) {
                        pstmt.setInt(1, newList.get(numberElementInList));
                        int row = pstmt.executeUpdate();
                    }

                    for(int numberElementInList1 = 0, Dat = 0; numberElementInList1 < val.length; numberElementInList1+=2) {
                        Dat+=1;
                        //System.out.println(Dat);
                        //System.out.println(newList1.get(numberElementInList1));
                        pstmt1.setInt(1, newList1.get(numberElementInList1));
                        pstmt1.setInt(2, Dat);
                        int row1 = pstmt1.executeUpdate();
                    }
                    
                   // for(int l : newList){
                   //     for(int r : newList1) {
                    //        pstmt.setInt(1, newList.get(i));
                    //        
                     //       int row = pstmt.executeUpdate();
                    //    }
                    //}

                    //int row = pstmt.executeUpdate();
                    //state.execute("");
                    //String[] t = new String[10];
                    //for(Element numb : number) {
                           //int num1 = Integer.parseInt(numb);
                            //String num1 = numb.select("td[class=first]").text( );
                            //String miy = num + " ";
                            //
                            //System.out.print(miy);
                            //List<Integer> num = new List<Integer>();
                    //System.out.println(number);
                    //for(Element valu : values) {
                       // String val = valu.select("td[class=first_in_group positive]").text();
                       // System.out.println(val);
                    //}    
                             //String[] sub = number.;
                             //System.out.println(sub[3]);
                    //}
                    
                    System.out.println("Connection to Store DB succesfull!");
                 }
            } catch (Exception ex) {
                System.out.println("Плохо"); 
                System.out.println(ex);
            }
            
        } catch (Exception e) {

            
        }
    }

}