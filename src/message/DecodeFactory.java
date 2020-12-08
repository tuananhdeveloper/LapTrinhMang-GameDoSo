package message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.Status;
import javafx.util.Pair;

/**
 *
 * @author Tuananh
 */
public class DecodeFactory {
    
    public static Pair<String, String> msgLogin(String msg) {
        String tmp[] = msg.split("-");
        String username = tmp[1].split(":")[1];
        String password = tmp[2].split(":")[1];
        return new Pair(username, password);
    }

    public static Pair<String, Status> msgChangeStatus(String msg) {
        String tmp[] = msg.split("-");
        String username = tmp[1].split(":")[1];
        Status status = Status.stringToStatus(tmp[2].split(":")[1]);
        return new Pair(username, status);
    }
    
    public static Pair<String, String> msgInvite(String msg) {
        String tmp[] = msg.split("-");
        String sender = tmp[1].split(":")[1];
        String receiver = tmp[2].split(":")[1];
        return new Pair(sender, receiver);
    }
    
    public static Pair<String, String> msgAcceptInvitation(String msg) {
        String tmp[] = msg.split("-");
        String user1 = tmp[1].split(":")[1];
        String user2 = tmp[2].split(":")[1];
        return new Pair(user1, user2);
    }
    
    public static Pair<String, String> msgGetTest(String msg) {
        String tmp[] = msg.split("-");
        String username = tmp[1].split(":")[1];
        String competitor = tmp[2].split(":")[1];
        return new Pair(username, competitor);
    }
}
