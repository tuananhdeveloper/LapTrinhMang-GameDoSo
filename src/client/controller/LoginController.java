/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.model.User;
import client.view.GameScreen;
import client.view.LoginScreen;
import client.common.Controller;
import client.common.Screen;
import client.common.View;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import message.EncodeFactory;
import message.Message;

/**
 *
 * @author tuananhdev
 */
public class LoginController extends Controller implements View.OnClickListener {

    private LoginScreen loginView;

    public LoginController(View view) {
        super(view);

        this.view.setOnClickListener(this);
        this.loginView = (LoginScreen) this.view;
    }

    @Override
    public void onClick(ActionEvent e) {

        if (!loginView.getUserName().equals("") && !loginView.getPassword().equals("")) {

            try {
                mySocket = new Socket(HOST, PORT);
                oos = new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(EncodeFactory.msgLogin(loginView.getUserName(), loginView.getPassword()));

                ois = new ObjectInputStream(mySocket.getInputStream());
                
                Object object = ois.readObject();
                if (object instanceof User) {
                    Screen screen = new GameScreen(this.view.game);
                    this.view.game.setScreen(screen);
                    GameController gameController = new GameController((View) screen, (User) object);

                } else if (object instanceof String) {
                    String msg = (String) object;
                    if (msg.startsWith(Message.MSG_USER_LOGGEDIN)) {
                        JOptionPane.showMessageDialog(loginView, "Tài khoản đã được đăng nhập");
                    } else if (msg.startsWith(Message.MSG_LOGIN_FAIL)) {
                        JOptionPane.showMessageDialog(loginView, "Sai tên đăng nhập hoặc mật khẩu");
                    }
                    mySocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


}
