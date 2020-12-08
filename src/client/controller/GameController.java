/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.model.User;
import client.view.GameScreen;
import client.common.Controller;
import client.common.View;
import client.ItemUser;
import client.Status;
import client.StatusManager;
import client.view.PlayScreen;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import message.DecodeFactory;
import message.EncodeFactory;
import message.Message;

/**
 *
 * @author tuananhdev
 */
public class GameController extends Controller implements View.OnMouseClickListener, View.OnWindowClosingListener {

    private JList list;
    private DefaultListModel<ItemUser> model;
    private ArrayList<User> userList;

    public GameController(View view, User user) {
        super(view, user);
        this.view.setOnMouseClickListener(this);
        this.view.setOnWindowClosingListener(this);

        this.list = ((GameScreen) this.view).getList();
        this.model = ((GameScreen) this.view).getModel();

        //set name 
        ((GameScreen) this.view).setName(user.getName());

        try {
            //get list
            oos.writeObject(Message.PREFIX_MSG_GET_USER);
            listener.start();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //change status to online
        new StatusManager(user.getUserName(), Status.ONLINE, oos).changeStatus();
    }

    private User getUser(String username) {
        for (User u : userList) {
            if (u.getUserName().equals(username)) {
                return u;
            }
        }
        return null;
    }

    private void updateList() {
        model = new DefaultListModel<>();

        for (User u : userList) {
            ItemUser item = new ItemUser(u.getName(), "src/assets/person.png", u.getStatus());
            model.addElement(item);
        }
        list.setModel(model);
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        int index = this.list.locationToIndex(e.getPoint());
        if (e.getX() >= 350 && e.getX() <= 430 && e.getY() >= 10 + index * 50 && e.getY() <= 40 + index * 50) {
            this.onButtonClick(index);
        }

    }

    private void onButtonClick(int position) {
        User u = userList.get(position);
        if (u.getStatus() == Status.ONLINE) {
            try {
                oos.writeObject(EncodeFactory.msgInvite(user.getUserName(), u.getUserName()));
            } catch (IOException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(u.getStatus() == Status.OFFLINE) {
            JOptionPane.showMessageDialog(view, "Người chơi hiện đang ngoại tuyến!");
        }
        else if(u.getStatus() == Status.PLAYING) {
            JOptionPane.showMessageDialog(view, "Người chơi hiện đang chơi game!");
        }
    }

    @Override
    public void onWindowClosing(WindowEvent e) {
        new StatusManager(user.getUserName(), Status.OFFLINE, oos).changeStatus();
    }

    Thread listener = new Thread(() -> {
        try {
            listen();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    });

    public void listen() throws IOException, ClassNotFoundException {
        while (true) {
            synchronized (ois) { 
                Object object = ois.readObject();
                if (object instanceof ArrayList<?>) {
                    userList = (ArrayList<User>) object;
                    for (User u : userList) {
                        if (u.getUserName().equals(user.getUserName())) {
                            userList.remove(u);
                            break;
                        }
                    }

                    //update
                    updateList();
                    continue;
                }

                if (!(object instanceof String)) {
                    continue;
                }
                String msg = (String) object;
                if (msg.startsWith(Message.PREFIX_MSG_CHANGE_STATUS)) {
                    Pair<String, Status> p = DecodeFactory.msgChangeStatus(msg);
                    String username = p.getKey();
                    System.out.println(username);
                    Status status = p.getValue();
                    changeStatus(username, status);
                } else if (msg.startsWith(Message.PREFIX_MSG_INVITE_INVITATION)) {
                    Pair<String, String> p = DecodeFactory.msgInvite(msg);
                    String sender = p.getKey();
                    int result = JOptionPane.showConfirmDialog(view, "Lời mời từ: " + sender, "Lời mời", JOptionPane.YES_NO_OPTION);

                    if (result == 0) {
                        try {
                            //YES
                            new StatusManager(user.getUserName(), Status.PLAYING, oos).changeStatus();
                            //changeStatus(sender, Status.PLAYING);

                            oos.writeObject(EncodeFactory.msgAcceptInvitation(user.getUserName(), sender));

                            PlayScreen playScreen = new PlayScreen(view.game);
                            view.game.setScreen(playScreen);
                            PlayController pController = new PlayController(playScreen, user, getUser(sender), false);
                            ois.wait();
                        } catch (IOException | InterruptedException ex) {
                            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                    }
                } else if (msg.startsWith(Message.PREFIX_MSG_ACCEPT_INVITATION)) {
                    Pair<String, String> p = DecodeFactory.msgAcceptInvitation(msg);
                    String sender = p.getKey();
                    User competitor = getUser(sender);
                    if (competitor != null) {
                        try {
                            new StatusManager(user.getUserName(), Status.PLAYING, oos).changeStatus();
                            //show screen

                            System.out.println(userList);
                            PlayScreen playScreen = new PlayScreen(view.game);
                            view.game.setScreen(playScreen);
                            PlayController pController = new PlayController(playScreen, user, competitor, true);
                            ois.wait();
                        } catch (HeadlessException | IOException | InterruptedException ex) {
                            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        }

    }

    private void changeStatus(String username, Status status) {
        if (userList != null) {
            for (User u : userList) {
                if (u.getUserName().equals(username)) {
                    User user = new User();
                    user.setId(u.getId());
                    user.setUserName(u.getUserName());
                    user.setName(u.getName());
                    user.setStatus(status);

                    userList.set(userList.indexOf(u), user);
                    updateList();
                }
            }
        }

    }
}
