/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.common.Controller;
import client.common.Screen;
import client.common.View;
import client.model.Rankings;
import client.view.HomeScreen;
import client.view.RankingsScreen;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import message.Message;

/**
 *
 * @author Long
 */
public class RankingsController extends Controller implements View.OnClickListener {

    private JTable list;
    private DefaultTableModel model;
    private ArrayList<Rankings> rankingsTable;

    public RankingsController(View view) {
        super(view);
        this.view.setOnClickListener(this);

        this.list = ((RankingsScreen) this.view).getList();
        this.model = ((RankingsScreen) this.view).getModel();

        rankingsTable = getRankingsTable();
        
        sortByScore();
    }

    @Override
    public void onClick(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                backToHome();
                break;
            case "score":
                sortByScore();
                break;
            case "time":
                sortByTime();
                break;
        }
    }

    public ArrayList<Rankings> getRankingsTable() {
        ArrayList<Rankings> list = new ArrayList<Rankings>();
        try {
            mySocket = new Socket(HOST, PORT);
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(Message.MSG_GET_RANKINGS_TABLE);
            ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            list = (ArrayList<Rankings>) o;
            
        } catch (IOException | ClassNotFoundException e) {
        }
        return list;
    }

    private void backToHome() {
        try {

            //init login view, login controller
            Screen homeScreen = (Screen) new HomeScreen(this.view.game);
            HomeController homeController = new HomeController((View) homeScreen);
            this.view.game.setScreen(homeScreen);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sortByScore() {        
        Collections.sort(rankingsTable, (o1, o2) -> {            
            return o1.getTotalScore() > o2.getTotalScore()? -1 : 1;
        });
        getTable();
    }

    private void sortByTime() {
        Collections.sort(rankingsTable, (o1, o2) -> {
            return o1.getAverageTime() > o2.getAverageTime()? -1 : 1;
        });
        getTable();
    }

    private void getTable() {
        Vector vctHeader = new Vector();
        vctHeader.add("STT");
        vctHeader.add("Tên người chơi");
        vctHeader.add("Tổng điểm");
        vctHeader.add("Thời gian trung bình");
        Vector vctData = new Vector();
        
        int i = 1;
        for (Rankings r : rankingsTable) {
            Vector v = new Vector();
            v.add(i);
            v.add(r.getUser().getName());
            v.add(r.getTotalScore());
            v.add(r.getAverageTime());
            vctData.add(v);
            i++;
        }
        model.setDataVector(vctData, vctHeader);
    }

}
