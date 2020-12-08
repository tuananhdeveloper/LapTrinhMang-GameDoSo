package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import server.controller.ServerController;
import server.views.ServerView;

/**
 *
 * @author tuananhdev
 */
public class ServerRunner {
    public static void main(String[] args) {
        ServerView view = new ServerView();
        ServerController control = new ServerController(view);
    }
}
