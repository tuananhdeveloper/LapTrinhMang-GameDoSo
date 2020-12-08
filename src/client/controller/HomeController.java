/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.view.LoginScreen;
import client.common.Controller;
import client.common.Screen;
import client.common.View;
import client.view.RankingsScreen;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuananhdev
 */
public class HomeController extends Controller implements View.OnClickListener {

    public HomeController(View view) {
        super(view);

        this.view.setOnClickListener(this);
    }

    @Override
    public void onClick(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                login();
                break;
            case "rank":
                showRank();
                break;
            //case "settings": showSettings(); break;    
        }
    }

    private void showRank() {
        try {

            Screen rankScreen = (Screen) new RankingsScreen(this.view.game);
            this.view.game.setScreen(rankScreen);
            RankingsController rankingsController = new RankingsController((View) rankScreen);
            

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void login() throws HeadlessException {
        try {

            //init login view, login controller
            Screen loginScreen = (Screen) new LoginScreen(this.view.game);
            LoginController loginController = new LoginController((View) loginScreen);
            this.view.game.setScreen(loginScreen);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
