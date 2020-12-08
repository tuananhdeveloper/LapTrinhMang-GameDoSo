/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handler;

import client.model.PlayingData;
import client.view.PlayScreen;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.DecodeFactory;
import message.DetectedNumberMessage;
import message.GameResultMessage;
import message.GetTestMessage;
import message.Message;
import message.PlayingMessage;
import server.Client;
import server.ServerThread;

/**
 *
 * @author Tuananh
 */
public class PlayingHandler extends Handler {

    private static final int UNIQUE_NUMBERS = 10;
    private int number[] = new int[PlayScreen.NUM_ROWS * PlayScreen.NUM_COLUMNS];

    public PlayingHandler(Socket socket, ObjectOutputStream oos, Connection con, PlayingMessage playingMessage) {
        super(socket, oos, con, playingMessage);
    }

    @Override
    public void writeData() {
        String user = ((PlayingMessage) playingMessage).getUser().getUserName();
        String competitor = ((PlayingMessage) playingMessage).getCompetitor().getUserName();
        
        ObjectOutputStream oosCompetitor = null;
        for (Map.Entry<String, Client> entry : ServerThread.clients.entrySet()) {
            if (getUsername(entry.getKey()).equals(competitor)) {
                oosCompetitor = entry.getValue().getOos();
                break;
            }
        }
        if (playingMessage instanceof GetTestMessage) {
            initTest();

            try {
                oos.writeObject(new PlayingData(number, UNIQUE_NUMBERS));
                if(oosCompetitor != null ) oosCompetitor.writeObject(new PlayingData(number, UNIQUE_NUMBERS));
                
            } catch (IOException ex) {
                Logger.getLogger(PlayingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (playingMessage instanceof DetectedNumberMessage) {
            try {
                System.out.println(((DetectedNumberMessage)playingMessage).getDetectedNumber());
                if(oosCompetitor != null) oosCompetitor.writeObject(playingMessage);
            } catch (IOException ex) {
                Logger.getLogger(PlayingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(playingMessage instanceof GameResultMessage) {
            GameResultMessage resultMessage = (GameResultMessage) playingMessage;
            System.out.println("idUser1: " + resultMessage.getUser().getId());
            System.out.println("idUser2: " + resultMessage.getCompetitor().getId());
            writeResult(resultMessage);
        }
    }

    private void writeResult(GameResultMessage resultMessage) {
        String query = "INSERT INTO result(iduser1, iduser2, idwin, timewin) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, resultMessage.getUser().getId());
            ps.setInt(2, resultMessage.getCompetitor().getId());
            ps.setInt(3, resultMessage.getIdWin());
            ps.setInt(4, resultMessage.getTimeWin());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlayingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initTest() {
        Random rd = new Random();
        
        int size = PlayScreen.NUM_ROWS * PlayScreen.NUM_COLUMNS;
        for(int i = 1; i <= UNIQUE_NUMBERS; i++) {
            number[rd.nextInt(size)] = i;
        }
        
        for(int i = 0; i < size; i++) {
            if(number[i] == 0) {
                number[i] = rd.nextInt(UNIQUE_NUMBERS) + 1;
            }
        }
    }
}
