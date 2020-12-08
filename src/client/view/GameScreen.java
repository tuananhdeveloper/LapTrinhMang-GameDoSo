/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;
import client.common.Game;
import client.common.View;
import client.ItemUser;
import client.MyGame;
import client.UserRender;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import styles.*;


/**
 *
 * @author tuananhdev
 */
public class GameScreen extends View{

    private JList<ItemUser> list;
    private DefaultListModel model;
    private JButton btnInviteRandomly, btnEditProfile;
    private UserRender userRender;
    
    private JLabel labelName;
    private JLabel icon;
    
    public GameScreen(Game game) throws HeadlessException, IOException {
        super(game);
        
    }

    public JList<ItemUser> getList() {
        return list;
    }
    
    @Override
    public void init() {
        setLayout(new BorderLayout());
        model = new DefaultListModel<>();
        list = new JList();
        list.setModel(model);
        
        list.setFixedCellHeight(50);
        list.setEnabled(true);
        list.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (e.getX() >= 350 && e.getX() <= 430 && e.getY() >= 10 + index * 50 && e.getY() <= 40 + index * 50) {
                    list.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        
        userRender = new UserRender();
        list.setCellRenderer(userRender);
        
        btnInviteRandomly = new JButton("Mời ngẫu nhiên");
        btnEditProfile = new JButton("Sửa thông tin");
        this.customButton(btnInviteRandomly);
        this.customButton(btnEditProfile);
        
        JLabel labelWelcome = new JLabel("Xin chào");
        labelWelcome.setForeground(Color.decode(GameColor.colorAccent));
        labelWelcome.setFont(GameFont.titleFont);
        labelWelcome.setAlignmentX(CENTER_ALIGNMENT);
        JPanel panelWelcome = new JPanel();
        panelWelcome.setLayout(new BoxLayout(panelWelcome, BoxLayout.Y_AXIS));
        panelWelcome.add(labelWelcome);
        
        JPanel userPanel = new JPanel(new BorderLayout(0, 20));
        userPanel.setBackground(new Color(0, 0, 0, 0));
        userPanel.setMaximumSize(new Dimension(150, 100));
        
        labelName = new JLabel();
        labelName.setForeground(Color.decode(GameColor.colorPrimary));
        labelName.setPreferredSize(new Dimension(100, 20));
        labelName.setFont(GameFont.labelFont);
        
        icon = new JLabel();
        
        try {
            Image img = ImageIO.read(new File("src/assets/person.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            icon.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        userPanel.add(panelWelcome, BorderLayout.NORTH);
        userPanel.add(icon, BorderLayout.CENTER);
        userPanel.add(labelName, BorderLayout.EAST);
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JScrollPane jScrollPane = new JScrollPane(list);
        jScrollPane.setBackground(Color.white);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBackground(new Color(0, 0, 0, 0));
        jPanel.setPreferredSize(new Dimension(300, MyGame.HEIGHT));


        jPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        jPanel.add(userPanel);
        jPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        jPanel.add(btnInviteRandomly);
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(btnEditProfile);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(jPanel, BorderLayout.EAST);
    }
    
    private void customButton(JButton button) {
        button.setMaximumSize(new Dimension(140, 46));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode(GameColor.colorAccent3));
        button.setFocusable(false);
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
    }
    

    @Override
    public void setName(String name) {
        labelName.setText(name);
        labelName.setToolTipText(name);
    }
    
    public DefaultListModel<ItemUser> getModel() {
       return model;
    }

    @Override
    public void addEvent() {
        list.addMouseListener(this);
        
    }
}
