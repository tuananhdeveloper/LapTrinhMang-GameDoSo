package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Tuananh
 */
public class Client {

    private String userName;
    private String ipAddress;
    private Socket socket = null;
    private ObjectOutputStream oos;
    private boolean playing;
    
    public Client(String userName, String ipAddress, Socket socket, ObjectOutputStream oos) {
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.socket = socket;
        this.oos = oos;
        
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public String getUserName() {
        return userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    
    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public Socket getSocket() {
        return socket;
    }
    
}
