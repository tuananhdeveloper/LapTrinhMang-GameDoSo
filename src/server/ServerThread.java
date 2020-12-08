package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import message.Message;
import server.handler.ChangeStatusHandler;
import server.handler.GetAllUserHandler;
import server.handler.Handler;
import server.handler.LoginHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.PlayingMessage;
import server.handler.InvitationHandler;
import server.handler.PlayingHandler;
import server.handler.RankingHandler;

/**
 *
 * @author Tuananh
 */
public class ServerThread extends Thread {

    protected Socket socket;
    protected Connection con;
    public static Map<String, Client> clients = new HashMap<>();

    public ServerThread(Socket socket, Connection con) {
        this.socket = socket;
        this.con = con;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            Handler handler = null;
            while (true) {
                Object object = ois.readObject();
                if (object instanceof String) {
                    String msg = (String) object;
                    System.out.println(msg);
                    if (msg.startsWith(Message.PREFIX_MSG_LOGIN)) {
                        handler = new LoginHandler(socket, oos, con, msg);
                    } else if (msg.startsWith(Message.PREFIX_MSG_GET_USER)) {
                        handler = new GetAllUserHandler(socket, oos, con, msg);
                    } else if (msg.startsWith(Message.PREFIX_MSG_CHANGE_STATUS)) {
                        handler = new ChangeStatusHandler(socket, oos, con, msg);
                    } else if (msg.startsWith(Message.PREFIX_MSG_INVITATION)) {
                        handler = new InvitationHandler(socket, oos, con, msg);
                    } else if(msg.startsWith(Message.MSG_GET_RANKINGS_TABLE)) {
                        handler = new RankingHandler(socket, oos, con, msg);
                    }
                    
                } else if (object instanceof PlayingMessage) {
                    handler = new PlayingHandler(socket, oos, con, (PlayingMessage)object);
                }

                if (handler != null) {
                    handler.writeData();
                }
            }

        } catch (IOException ex) {
            //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
