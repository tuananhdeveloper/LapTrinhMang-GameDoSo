package message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.Status;
import static message.Message.*;

/**
 *
 * @author Tuananh
 */
public class EncodeFactory {

    public static String msgLogin(String username, String password) {
        String msg = PREFIX_MSG_LOGIN + "-USERNAME:" + username + "-PASSWORD:" + password;
        return msg;
    }

    public static String msgGetAllUser() {
        String msg = PREFIX_MSG_GET_USER;
        return msg;
    }
    
    public static String msgChangeStatus(String username, Status status) {
        String msg = PREFIX_MSG_CHANGE_STATUS;
        msg += "-USERNAME:" + username + "-STATUS:" + status;
        return msg;
    }
    
    public static String msgInvite(String sender, String receiver) {
        String msg = PREFIX_MSG_INVITE_INVITATION;
        msg += "-SENDER:" + sender + "-RECEIVER:"+receiver;
        return msg;
    }
    
    public static String msgAcceptInvitation(String username1, String username2) {
        String msg = PREFIX_MSG_ACCEPT_INVITATION;
        msg += "-USER1:" + username1 + "-USER2:" + username2;
        return msg;
    }
    
    public static String msgGetTest(String username, String competition) {
        String  msg = PREFIX_MSG_GET_TEST;
        msg += "USER:" + username + "-COMPETITION:" + competition;
        return msg;
    }
}
