package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import styles.GameFont;
import styles.GameColor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author tuananhdev
 */
public class UserRender extends JPanel implements ListCellRenderer<ItemUser>{

    private JLabel icon = new JLabel();
    private JLabel name = new JLabel();
    private JLabel status = new JLabel();
    private JButton btnInvite;
    
    public UserRender() {
        this.setFocusable(false);
        this.setBackground(new Color(0, 0, 0, 0));
        BorderLayout layout = new BorderLayout();
        layout.setHgap(16);
        setLayout(layout);

        name.setForeground(Color.decode(GameColor.colorPrimary));
        name.setPreferredSize(new Dimension(40, 40));
        name.setFont(GameFont.nameUserFont);
        status.setPreferredSize(new Dimension(80, 40));
        btnInvite = new JButton("Mời");
        btnInvite.setBackground(Color.decode(GameColor.colorPrimary));
        btnInvite.setForeground(Color.white);
        btnInvite.setPreferredSize(new Dimension(80, 30));
     
        FlowLayout layout2 = new FlowLayout();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(layout2);
        jPanel.setBackground(new Color(0, 0, 0, 0));
        jPanel.add(status);
        jPanel.add(btnInvite);
        
        add(icon, BorderLayout.WEST);
        add(name, BorderLayout.CENTER);
        add(jPanel, BorderLayout.EAST);
           
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemUser> list, ItemUser value, int index, boolean isSelected, boolean cellHasFocus) {
        try {
            
            Image img = ImageIO.read(new File(value.getImage())).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            icon.setIcon(new ImageIcon(img));
            name.setText(value.getName());
            status.setText(this.getStatus(value.getStatus()));
            status.setForeground(Color.decode(this.getColor(value.getStatus())));
        } catch (IOException ex) {
            Logger.getLogger(UserRender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    private String getColor(Status status) {
        switch (status) {
            case ONLINE:
                return GameColor.colorOnlineStatus;
            case OFFLINE:
                return GameColor.colorOfflineStatus;
            case PLAYING:
                return GameColor.colorPlayingStatus;
        }
        return null;
    }
    private String getStatus(Status status) {
        switch(status) {
            case ONLINE: return "Trực tuyến";
            case OFFLINE: return "Ngoại tuyến";
            case PLAYING: return "Đang chơi";    
        }
        return null;
    }
}
