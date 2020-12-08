/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import message.DecodeFactory;
import message.Message;
import client.model.User;
import server.Client;
import server.ServerThread;

/**
 *
 * @author Tuananh
 */
public class LoginHandler extends Handler {

    public LoginHandler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        super(socket, oos, con, msg);
    }
    
    @Override
    public void writeData() {
        try {
            Pair p = DecodeFactory.msgLogin(msg);
            String username = (String) p.getKey();
            String password = (String) p.getValue();
            User user = login(username, password);
            
            if (isUserLoggedin(username)) {
                oos.writeObject(Message.MSG_USER_LOGGEDIN);
            } else if (user != null) {
                oos.writeObject(user);
                //add client
                Client c;
                c = new Client(username, socket.getRemoteSocketAddress().toString(), socket, oos);
                ServerThread.clients.put(user.getUserName() + ":" + socket.getRemoteSocketAddress().toString(), c);

            } else {
                oos.writeObject(Message.MSG_LOGIN_FAIL);
            }
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isUserLoggedin(String username) {
        for (Map.Entry<String, Client> entry : ServerThread.clients.entrySet()) {
            if (getUsername(entry.getKey()).equals(username)) {
                return true;
            }
        }
        return false;
    }

    private User login(String username, String password) throws SQLException {
        String query = "Select * FROM user WHERE username = ? AND password = ?";
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("name"));
            System.out.println(user.getId());
            return user;
        }
        return null;
    }
    
}
