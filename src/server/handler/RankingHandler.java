/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;

import client.model.Rankings;
import client.model.User;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Long
 */
public class RankingHandler extends Handler{
    public RankingHandler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        super(socket, oos, con, msg);
    }

    @Override
    public void writeData() {
        try {
            oos.writeObject(getRankingsTable());
        } catch (IOException ex) {
            Logger.getLogger(GetAllUserHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetAllUserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<Rankings> getRankingsTable() throws Exception {
        String query1 = "Select DISTINCT user.* FROM user";
        String query2 = "SELECT * FROM result WHERE (iduser1 = ? or iduser2 = ?) AND (idwin = ? or idwin = 0)";
        
        ArrayList<Rankings> rankings = new ArrayList<>();        
        try {            
            Statement stmt = con.createStatement();
            ResultSet userList = stmt.executeQuery(query1);
            while (userList.next()) {
                Rankings rankItem = new Rankings();                
                
                PreparedStatement stm = con.prepareStatement(query2);
                stm.setInt(1, userList.getInt("id"));
                stm.setInt(2, userList.getInt("id"));
                stm.setInt(3, userList.getInt("id"));
                ResultSet battleList = stm.executeQuery();                
                
                
                    int winNumber = 0;
                    float totalTime = 0;
                    int tieNumber = 0;

                    while(battleList.next()){
                        if(battleList.getInt("idwin") == 0){
                            tieNumber++;                            
                        }
                        else{
                            winNumber++;
                            totalTime+=battleList.getInt("timewin");
                        }                        
                    }
                    
                    float totalScore = (float) (tieNumber*0.5 + winNumber);
                    if(totalScore != 0){
                        rankItem.setUser(new User(userList.getInt("id"),userList.getString("username"),userList.getString("name")));
                        rankItem.setTotalScore(totalScore);
                        rankItem.setAverageTime(totalTime/winNumber);
                        rankings.add(rankItem);
                    }                  
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankings;
    }
}
