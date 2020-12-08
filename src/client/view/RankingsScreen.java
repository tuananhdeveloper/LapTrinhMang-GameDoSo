/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.MyGame;
import client.common.Game;
import client.common.Screen;
import client.common.View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import styles.GameColor;

/**
 *
 * @author Long
 */
public class RankingsScreen extends View implements Screen{
    private JTable list;
    private DefaultTableModel model;
    private JButton btnSortByScore, btnSortByTime, btnBack;

    public RankingsScreen(Game game) throws HeadlessException, IOException {
        super(game);
    }
    
    @Override
    public void init() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel();         
        list = new JTable(model);
        
        btnBack = new JButton("Trở lại");
        btnSortByScore = new JButton("Xếp theo điểm");
        btnSortByTime = new JButton("Xếp theo thời gian");
        this.customButton(btnBack);
        this.customButton(btnSortByScore);
        this.customButton(btnSortByTime);
                
        JScrollPane jScrollPane = new JScrollPane(list);
        jScrollPane.setBackground(Color.white);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBackground(new Color(0, 0, 0, 0));
        jPanel.setPreferredSize(new Dimension(300, MyGame.HEIGHT));
        
        jPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        jPanel.add(btnBack);
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(btnSortByScore);
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(btnSortByTime);
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

    public JTable getList() {
        return this.list;
    }

    public DefaultTableModel getModel() {
        return model;
    }
           
    @Override
    public void addEvent() {
        btnBack.addActionListener(this);
        btnBack.setActionCommand("back");
        btnSortByScore.addActionListener(this);
        btnSortByScore.setActionCommand("score");
        btnSortByTime.addActionListener(this);
        btnSortByTime.setActionCommand("time");       
    }
    
}
