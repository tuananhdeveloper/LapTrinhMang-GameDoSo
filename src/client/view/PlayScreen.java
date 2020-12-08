/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.common.Game;
import client.common.Screen;
import client.common.View;
import client.MyGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
public class PlayScreen extends View implements Screen {

    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;

    public static final String txtLabel1 = "Bạn đã dò được: ";
    public static final String txtLabel2 = "Đối thủ đã dò được: ";

    private JLabel label1, label2, labelUser, labelCompetitor;

    private JButton btnExit;
    private JButton cell[] = new JButton[NUM_ROWS * NUM_COLUMNS];

    public PlayScreen(Game game) throws HeadlessException, IOException {
        super(game);

    }

    @Override
    public void init() {
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLUMNS));
        for (int i = 0; i < NUM_ROWS * NUM_ROWS; i++) {
            JButton btn = new JButton();
            btn.setBackground(Color.WHITE);
            btn.setFocusable(false);
            btn.setActionCommand(i + "");
            cell[i] = btn;
            leftPanel.add(btn);
        }

        label1 = new JLabel(txtLabel1 + "0/0");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label1.setFont(GameFont.labelFontSmall);
        label1.setForeground(Color.decode(GameColor.colorPrimary));

        label2 = new JLabel(txtLabel2 + "0/0");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setFont(GameFont.labelFontSmall);
        label2.setForeground(Color.decode(GameColor.colorAccent2));

        btnExit = new JButton("Thoát");
        btnExit.setBackground(Color.decode(GameColor.colorAccent));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setForeground(Color.white);
        btnExit.setFocusable(false);
        btnExit.setMaximumSize(new Dimension(160, 40));

        labelUser = new JLabel();
        labelUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelUser.setFont(GameFont.labelFontSmall);
        labelUser.setForeground(Color.decode(GameColor.colorPrimary));

        labelCompetitor = new JLabel();
        labelCompetitor.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCompetitor.setFont(GameFont.labelFontSmall);
        labelCompetitor.setForeground(Color.decode(GameColor.colorAccent2));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(220, MyGame.HEIGHT));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        rightPanel.add(labelUser);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(labelCompetitor);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(label1);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(label2);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        rightPanel.add(btnExit);

        this.add(leftPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) throws HeadlessException, IOException {
        Game game = null;
        PlayScreen p = new PlayScreen(game);
        p.init();
        p.setVisible(true);
    }

    public void enableAllButton() {
        for (int i = 0; i < NUM_ROWS * NUM_COLUMNS; i++) {
            cell[i].setEnabled(true);
        }
    }

    public void disableAllButton() {
        for (int i = 0; i < NUM_ROWS * NUM_COLUMNS; i++) {
            cell[i].setEnabled(false);
        }
    }

    public void setTextToLabelUser(String text) {
        this.labelUser.setText(text);
    }

    public void setTextToLabelCompetitor(String text) {
        this.labelCompetitor.setText(text);
    }
    
    public void updateLabel1(String str) {
        label1.setText(txtLabel1 + str);
    }

    public void updateLabel2(String str) {
        label2.setText(txtLabel2 + str);
    }

    public int getData(int position) {
        return Integer.valueOf(cell[position].getText());
    }

    public void setData(int number[]) {
        for (int i = 0; i < NUM_ROWS * NUM_COLUMNS; i++) {
            cell[i].setText(number[i] + "");
        }
    }

    @Override
    public void addEvent() {
        for (int i = 0; i < NUM_ROWS * NUM_COLUMNS; i++) {
            cell[i].addActionListener(this);
        }
        
        btnExit.setActionCommand("exit");
        btnExit.addActionListener(this);
    }
}
