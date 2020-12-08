/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.common;
import client.ImagePanel;
import client.MyGame;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author tuananhdev
 */
public abstract class View extends JFrame implements ActionListener, MouseListener, WindowListener, Screen{

    private int xMouse;
    private int yMouse;
    public Game game;
    
    public interface OnClickListener {
        void onClick(ActionEvent e);
    }
    
    public interface OnMouseClickListener {
        void onMouseClick(MouseEvent e);
    }
    
    public interface OnWindowClosingListener {
        void onWindowClosing(WindowEvent e);
    }
    
    protected OnClickListener onClickListener;
    protected OnMouseClickListener onMouseClickListener;
    protected OnWindowClosingListener onWindowClosingListener;
    
    public View(Game game) throws HeadlessException, IOException {
        super();
        this.game = game;
        
        setSize(MyGame.WIDTH, MyGame.HEIGHT);

        this.setLocationRelativeTo(null);
        
        BufferedImage myImage = ImageIO.read(new File("src/assets/background.jpg"));
        this.setContentPane(new ImagePanel(myImage));
        this.setLayout(new FlowLayout());
        this.addWindowListener(this);
        this.setResizable(false);
    }

    public void setOnWindowClosingListener(OnWindowClosingListener onWindowClosingListener) {
        this.onWindowClosingListener = onWindowClosingListener;
    }

    public void setOnMouseClickListener(OnMouseClickListener onMouseClickListener) {
        this.onMouseClickListener = onMouseClickListener;
    }
    
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(this.onWindowClosingListener != null) {
            this.onWindowClosingListener.onWindowClosing(e);
        }
        System.exit(0);
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        onMouseClickListener.onMouseClick(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        onClickListener.onClick(e);
    }

    @Override
    public void showScreen() {
        this.setVisible(true);
    }

    @Override
    public void hideScreen() {
        this.setVisible(false);
    }
    
    
    public abstract void init();
    public abstract void addEvent();
    
}
