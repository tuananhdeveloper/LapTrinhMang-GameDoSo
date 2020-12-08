/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import message.EncodeFactory;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuananh
 */
public class StatusManager {
    private String username;
    private Status status;
    private ObjectOutputStream oos;

    public StatusManager(String username, Status status, ObjectOutputStream oos) {
        this.username = username;
        this.status = status;
        this.oos = oos;
    }

    
    
    public void changeStatus() {
        try {
            oos.writeObject(EncodeFactory.msgChangeStatus(username, status));
        } catch (IOException ex) {
            Logger.getLogger(StatusManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
