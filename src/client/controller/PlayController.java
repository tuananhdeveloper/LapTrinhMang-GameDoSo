/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.Status;
import client.StatusManager;
import client.common.Controller;
import client.common.Screen;
import client.common.View;
import client.model.PlayingData;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import client.model.User;
import client.view.GameScreen;
import client.view.PlayScreen;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.Timer;
import message.DecodeFactory;
import message.DetectedNumberMessage;
import message.DetectedNumberMessage.DetectedState;
import message.GameResultMessage;
import message.GetTestMessage;
import message.Message;

/**
 *
 * @author Tuananh
 */
public class PlayController extends Controller implements View.OnClickListener, View.OnWindowClosingListener {

    private PlayScreen playScreen;
    private int currentNumber = 0;
    private boolean invitation;

    private int UNIQUE_NUMBERS = 0;
    private User user, competitor;

    private boolean complete = false;
    private boolean fail = false;

    private boolean competitorComplete = false;
    private boolean competitorFail = false;

    private static final String MSG_LOSE = "Đối thủ đã dò xong, bạn đã thua \n Bạn có muốn chơi tiếp?";
    private static final String MSG_CONTINUE = "Đối thủ không hoàn thành, nhấn OK để tiếp tục chơi!!";
    private static final String MSG_WIN = "Bạn đã thắng \n Bạn có muốn chơi tiếp?";
    private static final String MSG_NOT_COMPLETE = "Không hoàn thành";
    private static final String MSG_COMPLETE = "Hoàn thành";

    private static final String MSG_TIE = "Hòa! \n Bạn có muốn chơi tiếp";
    private static final String MSG_EXIT = "Đối thủ đã thoát!";

    private boolean listening = true;

    private PlayControllerThread controllerThread;
    private long start;

    public PlayController(View view, User user, User competitor, boolean invitation) {
        super(view, user);
        this.user = user;
        this.competitor = competitor;
        this.invitation = invitation;

        System.out.println("iduser: " + competitor.getId());
        System.out.println("user: " + user.getUserName());
        System.out.println("competitor: " + competitor.getUserName());

        this.playScreen = (PlayScreen) view;
        this.playScreen.setTextToLabelUser(user.getName());
        this.playScreen.setTextToLabelCompetitor(competitor.getName());

        view.setOnClickListener(this);
        view.setOnWindowClosingListener(this);
        this.playScreen.disableAllButton();

        //get test
        try {
            getTest();
            controllerThread = new PlayControllerThread();
            controllerThread.start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playAgain() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getTest();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private void getTest() throws IOException, ClassNotFoundException {
        complete = false;
        fail = false;
        competitorComplete = false;
        competitorFail = false;
        currentNumber = 0;

        if (invitation == true) {
            oos.writeObject(new GetTestMessage(user, competitor));
        }

    }

    @Override
    public void onClick(ActionEvent e) {
        if(e.getActionCommand().equals("exit")) {
            showGameScreen();
            return;
        }
        
        int idButton = Integer.valueOf(e.getActionCommand());
        int number = playScreen.getData(idButton);
        if (number == currentNumber + 1) {
            currentNumber = number;
            playScreen.updateLabel1(currentNumber + "/" + UNIQUE_NUMBERS);
            this.sendDetectedNumber(user,
                    competitor,
                    currentNumber,
                    DetectedNumberMessage.DetectedState.DETECTING);

            if (currentNumber == UNIQUE_NUMBERS) {
                currentNumber = 0;

                complete = true;
                this.sendDetectedNumber(user,
                        competitor,
                        currentNumber,
                        DetectedNumberMessage.DetectedState.COMPLETE);
                playScreen.disableAllButton();

                try {
                    checkComplete();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            fail = true;
            this.sendDetectedNumber(user,
                    competitor,
                    currentNumber,
                    DetectedNumberMessage.DetectedState.FAIL);
            playScreen.disableAllButton();
            try {
                checkFail();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void checkComplete() throws IOException, ClassNotFoundException {
        if (competitorComplete == true && complete == false) {
            writeResult(competitor.getId(), System.currentTimeMillis() - start);
            playAgain();
            int options = showComfirmDialog(MSG_LOSE);
            if (options == 0) { //YES
                playScreen.enableAllButton();
                start = System.currentTimeMillis();
            } else {
                showGameScreen();
            }
        } else if (competitorComplete == true && complete == true) {
            writeResult(0, System.currentTimeMillis() - start);
            playAgain();
            int options = showComfirmDialog(MSG_TIE);
            if (options == 0) {
                playScreen.enableAllButton();
                start = System.currentTimeMillis();
            } else {
                showGameScreen();
            }
        } else if (complete == true && competitorComplete == false) {
            writeResult(user.getId(), System.currentTimeMillis() - start);
            playAgain();
            int options = showComfirmDialog(MSG_WIN);
            if (options == 0) { //YES
                playScreen.enableAllButton();
                start = System.currentTimeMillis();
            } else {
                showGameScreen();
            }
        }
    }

    private void writeResult(int idWin, long time) {
        if (invitation) {
            try {
                oos.writeObject(new GameResultMessage(user, competitor, (int) time / 1000, idWin));
            } catch (IOException ex) {
                Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void checkFail() throws IOException, ClassNotFoundException {
        if (fail == true && competitorFail == true) {
            writeResult(0, System.currentTimeMillis() - start);
            playAgain();
            int options = showComfirmDialog(MSG_TIE);
            if (options == 0) {
                playScreen.enableAllButton();
                start = System.currentTimeMillis();
            } else {
                showGameScreen();
            }
        } else if (fail == false && competitorFail == true) {
            showMessageDialog(MSG_CONTINUE);
        } else if (fail == true && competitorFail == false) {
            showMessageDialog(MSG_NOT_COMPLETE);
        }
    }

    private void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(playScreen, msg);
    }

    private int showComfirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(playScreen, msg, "Thông báo", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void onWindowClosing(WindowEvent e) {
        new StatusManager(user.getUserName(), Status.OFFLINE, oos).changeStatus();
    }

    private void showGameScreen() {
        try {
            new StatusManager(user.getUserName(), Status.ONLINE, oos).changeStatus();

            Screen gameScreen = new GameScreen(view.game);
            view.game.setScreen(gameScreen);

            GameController gc = new GameController((View) gameScreen, user);

        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendDetectedNumber(User user, User competitor, int currentNumber, DetectedNumberMessage.DetectedState state) {
        try {
            oos.writeObject(new DetectedNumberMessage(user,
                    competitor,
                    currentNumber,
                    state
            ));
        } catch (IOException ex) {
            Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class PlayControllerThread extends Thread {

        @Override
        public void run() {
            try {
                listen();
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void listen() throws IOException, ClassNotFoundException, InterruptedException {
        while (true) {
            synchronized (ois) {
                Object object = ois.readObject();
                if (object instanceof String) {
                    String msg = (String) object;
                    if (msg.startsWith(Message.PREFIX_MSG_CHANGE_STATUS)) {
                        Pair<String, Status> p = DecodeFactory.msgChangeStatus(msg);
                        //return game screen
                        if (p.getKey().equals(user.getUserName()) && p.getValue() == Status.ONLINE) {
                            ois.wait();
                        } else if (p.getKey().equals(competitor.getUserName()) && p.getValue() != Status.PLAYING) {
                            showMessageDialog(MSG_EXIT);
                            ois.wait();
                        }
                    }
                } else if (object != null && object instanceof PlayingData) {

                    PlayingData data = (PlayingData) object;
                    UNIQUE_NUMBERS = data.getUniqueNumber();
                    this.playScreen.setData(data.getNumber());

                    this.playScreen.updateLabel1("0/" + UNIQUE_NUMBERS);
                    this.playScreen.updateLabel2("0/" + UNIQUE_NUMBERS);
                    this.playScreen.enableAllButton();

                    start = System.currentTimeMillis();
                } else if (object instanceof DetectedNumberMessage) {
                    try {
                        DetectedNumberMessage dnm = (DetectedNumberMessage) object;
                        DetectedState state = dnm.getDetectedState();
                        int detectedNumber = dnm.getDetectedNumber();
                        switch (state) {
                            case DETECTING:
                                playScreen.updateLabel2(detectedNumber + "/" + UNIQUE_NUMBERS);
                                break;
                            case COMPLETE:
                                playScreen.disableAllButton();
                                competitorComplete = true;
                                checkComplete();
                                break;
                            case FAIL:
                                competitorFail = true;
                                checkFail();
                                break;
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(PlayController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }
}
