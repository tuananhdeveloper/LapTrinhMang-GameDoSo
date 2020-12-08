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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import message.DecodeFactory;
import server.Client;
import server.ServerThread;

/**
 *
 * @author Tuananh
 */
public class ChangeStatusHandler extends Handler {

    public ChangeStatusHandler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        super(socket, oos, con, msg);
    }

    @Override
    public void writeData() {
        Pair<String, Status> p = DecodeFactory.msgChangeStatus(msg);
        String username = p.getKey();
        Status status = p.getValue();

        Map<String, Client> map = new HashMap<>();
        map.putAll(ServerThread.clients);

        for (Map.Entry<String, Client> entry : map.entrySet()) {
            Client client = entry.getValue();

            //change status from clients
            if (getUsername(entry.getKey()).equals(username)) {
                if (null != status) switch (status) {
                    case OFFLINE:
                        ServerThread.clients.remove(entry.getKey());
                        continue;
                    case PLAYING:{
                        Client c = new Client(client.getUserName(),
                                client.getIpAddress(),
                                client.getSocket(), client.getOos());
                        c.setPlaying(true);
                        ServerThread.clients.replace(entry.getKey(), c);
                            break;
                        }
                    case ONLINE:{
                        Client c = new Client(client.getUserName(),
                                client.getIpAddress(),
                                client.getSocket(), client.getOos());
                        c.setPlaying(false);
                        ServerThread.clients.replace(entry.getKey(), c);
                            break;
                        }
                    default:
                        break;
                }
            }

            //write message;
            try {
                client.getOos().writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(ChangeStatusHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
