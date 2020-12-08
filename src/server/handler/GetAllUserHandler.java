/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;

import client.Status;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.model.User;
import java.util.Map;
import server.Client;
import server.ServerThread;

/**
 *
 * @author Tuananh
 */
public class GetAllUserHandler extends Handler{

    public GetAllUserHandler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        super(socket, oos, con, msg);
    }

    @Override
    public void writeData() {
        try {
            ArrayList<User> list = getAllUsers();
            oos.writeObject(list);
        } catch (IOException ex) {
            Logger.getLogger(GetAllUserHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetAllUserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<User> getAllUsers() throws Exception {
        String query = "Select * FROM user";
        ArrayList<User> userlist = new ArrayList<>();
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Status status = Status.OFFLINE;
                
                for(Map.Entry<String, Client> entry: ServerThread.clients.entrySet()) {
                    if(entry.getKey().startsWith(rs.getString("username"))) {
                        if(entry.getValue().isPlaying()){
                            status = Status.PLAYING;
                        }
                        else status = Status.ONLINE;
                    }
                }
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("name"),
                        status);
              
                userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userlist;
    }    
}
