/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import message.DecodeFactory;
import message.Message;
import server.Client;
import server.ServerThread;

/**
 *
 * @author Tuananh
 */
public class InvitationHandler extends Handler {

    public InvitationHandler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        super(socket, oos, con, msg);
    }

    @Override
    public void writeData() {
        Pair<String, String> p = null;
        if(msg.startsWith(Message.PREFIX_MSG_ACCEPT_INVITATION)){
            p = DecodeFactory.msgAcceptInvitation(msg);
        }
        else if (msg.startsWith(Message.PREFIX_MSG_INVITE_INVITATION)) {
            p = DecodeFactory.msgInvite(msg);
            //String sender = p.getKey();
        }
        
        if (p != null) {
            String receiver = p.getValue();
            for (Map.Entry<String, Client> entry : ServerThread.clients.entrySet()) {
                if (getUsername(entry.getKey()).equals(receiver)) {
                    try {
                        entry.getValue().getOos().writeObject(msg);
                    } catch (IOException ex) {
                        Logger.getLogger(InvitationHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }

    }

}
