package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import client.common.Game;
import java.awt.HeadlessException;
import java.io.IOException;

/**
 *
 * @author tuananhdev
 */
public class ClientRunner extends Game{
    
    public static void main(String[] args) throws HeadlessException, IOException {
        MyGame game = new MyGame();
        game.start();
    }
}
