package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tuananhdev
 */
public enum Status {
   ONLINE, OFFLINE, PLAYING;
   
   public static Status intToStatus(int i){
       switch (i) {
           case 1:
               return ONLINE;
           case 2:
               return OFFLINE;
           case 3:
               return PLAYING;
           default:
               return null;
       }
    }
   
   public static Status stringToStatus(String str){
       switch (str) {
           case "ONLINE":
               return ONLINE;
           case "OFFLINE":
               return OFFLINE;
           case "PLAYING":
               return PLAYING;
           default:
               return null;
       }
    }   
}
