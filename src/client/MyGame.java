package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import client.common.Controller;
import client.controller.HomeController;
import client.view.HomeScreen;
import client.common.Game;
import client.common.Screen;
import client.common.View;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuananhdev
 */
public class MyGame extends Game{

    public static int WIDTH = 760;
    public static int HEIGHT = 450;
    private Screen screen;
    private Controller controller;

    public MyGame() throws HeadlessException, IOException {
        screen = new HomeScreen(this);
    }

    public void start() {
        setScreen(screen);
        controller = new HomeController((View) screen);
    }

}
