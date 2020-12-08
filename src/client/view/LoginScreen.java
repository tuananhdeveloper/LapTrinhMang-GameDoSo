/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.common.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import styles.*;

/**
 *
 * @author tuananhdev
 */
public class LoginScreen extends View implements Screen{

    public JPanel jPanel1, jPanel2;
    public JLabel title, labelUsername, labelPassword;
    public JTextField txtUsername, txtPassword;
    public JButton btnLogin;
    
    public LoginScreen(Game game) throws HeadlessException, IOException {
        super(game);

    }

    public String getUserName() {
        return txtUsername.getText();
    }
    
    public String getPassword() {
        return txtPassword.getText();
    }

    @Override
    public void init() {
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        //title
        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel1.setMaximumSize(new Dimension(200, 100));
        title = new JLabel("ĐĂNG NHẬP");
        title.setFont(GameFont.titleFont);
        title.setForeground(Color.decode(GameColor.colorPrimary));
        jPanel1.add(title);
        //form login
        
        
        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        GridLayout grid = new GridLayout(3, 2, 10, 10);
        jPanel2.setLayout(grid);
        jPanel2.setMaximumSize(new Dimension(420, 180));
        
        labelUsername = new JLabel("Tên đăng nhập");
        labelUsername.setFont(GameFont.labelFont);
        labelUsername.setForeground(Color.decode(GameColor.colorAccent2));
        labelPassword = new JLabel("Mật khẩu");
        labelPassword.setFont(GameFont.labelFont);
        labelPassword.setForeground(Color.decode(GameColor.colorAccent2));
        txtUsername = new JTextField();
        txtUsername.setSize(100, 40);
        txtPassword = new JPasswordField();
        txtPassword.setSize(100, 40);
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(Color.decode(GameColor.colorAccent));
        btnLogin.setFocusable(false);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setMaximumSize(new Dimension(80, 48));
        jPanel2.add(labelUsername);
        jPanel2.add(txtUsername);
        jPanel2.add(labelPassword);
        jPanel2.add(txtPassword);
        JPanel jp = new JPanel();
        jp.setBackground(new Color(0, 0, 0, 0));
        jPanel2.add(jp);
        jPanel2.add(btnLogin);
        jPanel2.setBorder(new EmptyBorder(16, 16, 16, 16));
        
        this.add(jPanel1);
        this.add(jPanel2);
       

    }

    @Override
    public void addEvent() {
        btnLogin.addActionListener(this);
    }
}
