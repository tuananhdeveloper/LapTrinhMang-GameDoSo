/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.common.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import styles.*;

/**
 *
 * @author tuananhdev
 */
public class HomeScreen extends View {

    private static Game game;

    public JPanel jPanel;
    public JLabel title;
    public JButton btnLogin, btnRank, btnSettings;

    public HomeScreen(Game game) throws HeadlessException, IOException {
        super(game);
    }

    @Override
    public void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        btnLogin = new JButton("Đăng nhập");
        this.customButton(btnLogin);

        btnRank = new JButton("Xếp hạng");
        this.customButton(btnRank);

        btnSettings = new JButton("Cài đặt");
        this.customButton(btnSettings);

        title = new JLabel("GAME DÒ SỐ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(GameFont.titleFont);
        title.setForeground(Color.decode(GameColor.colorPrimary));

        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 68)));
        this.add(btnLogin);

        this.add(Box.createRigidArea(new Dimension(0, 16)));
        this.add(btnRank);
        this.add(Box.createRigidArea(new Dimension(0, 16)));
        this.add(btnSettings);
    }

    private void customButton(JButton button) {
        button.setMaximumSize(new Dimension(170, 48));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode(GameColor.colorPrimary));
        button.setFocusable(false);
        button.setForeground(Color.WHITE);
    }

//    @Override
//    public void update() {
//        
//    }

    @Override
    public void addEvent() {
        btnLogin.addActionListener(this);
        btnLogin.setActionCommand("login");
        btnRank.addActionListener(this);
        btnRank.setActionCommand("rank");
        btnSettings.addActionListener(this);
        btnSettings.setActionCommand("settings");
    }

}
