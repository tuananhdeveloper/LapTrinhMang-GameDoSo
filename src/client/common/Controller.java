/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.common;

import client.model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuananhdev
 */
public abstract class Controller{
    protected View view;
    protected User user;
    protected static int PORT = 6666;
    protected static String HOST = "192.168.43.208";
    
    protected static Socket mySocket;
    protected static ObjectOutputStream oos;
    protected static ObjectInputStream ois;
    
    public Controller(View view) {
        this.view = view;
    }

    public Controller(View view, User user) {
        this.view = view;
        this.user = user;
    }
    
}
