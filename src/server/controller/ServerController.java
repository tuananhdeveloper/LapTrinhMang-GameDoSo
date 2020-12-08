/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerThread;
import server.views.ServerView;


/**
 *
 * @author Long
 */
public class ServerController {

    private ServerView view;
    private Connection con;
    private ServerSocket myServer;
    private Socket clientSocket;

    private static final int PORT = 6666;

    public ServerController(ServerView view) {
        this.view = view;
        getDBConnection("gamedoso", "root", "");
        openServer(PORT);
        view.showMessage("TCP server is running...");
        while (true) {
            listen();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        String dbUrl = "jdbc:mysql://localhost/" + dbName;
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (ClassNotFoundException | SQLException e) {
            view.showMessage(e.getStackTrace().toString());
        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            view.showMessage(e.toString());
        }
    }

    private void listen() {
        try {
            clientSocket = myServer.accept();
            
            System.out.println("Login: " + clientSocket.getRemoteSocketAddress());
            ServerThread serverThread = new ServerThread(clientSocket, con);
            serverThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
