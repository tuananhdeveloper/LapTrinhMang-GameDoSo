/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import message.PlayingMessage;
/**
 *
 * @author Tuananh
 */
public abstract class Handler {
    
    protected Socket socket;
    protected Connection con;
    protected String msg;
    protected ObjectOutputStream oos;
    
    protected PlayingMessage playingMessage;
    
    public Handler(Socket socket, ObjectOutputStream oos, Connection con, String msg) {
        this.socket = socket;
        this.con = con;
        this.msg = msg;
        this.oos = oos;
    }
    
    public Handler(Socket socket, ObjectOutputStream oos, Connection con, PlayingMessage playingMessage) {
        this.socket = socket;
        this.con = con;
        this.playingMessage = playingMessage;
        this.oos = oos;
    }
    
    public String getUsername(String key) {
        return key.split(":")[0];
    }
    
    public abstract void writeData();
}
