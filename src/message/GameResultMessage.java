/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import client.model.User;

/**
 *
 * @author Tuananh
 */
public class GameResultMessage extends PlayingMessage{

    private int timeWin;
    private int idWin;
    
    public GameResultMessage(User user, User competitor, int timeWin, int idWin) {
        super(user, competitor);
        this.timeWin = timeWin;
        this.idWin = idWin;
    }

    public int getTimeWin() {
        return timeWin;
    }

    public void setTimeWin(int timeWin) {
        this.timeWin = timeWin;
    }

    public int getIdWin() {
        return idWin;
    }

    public void setIdWin(int idWin) {
        this.idWin = idWin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCompetitor() {
        return competitor;
    }

    public void setCompetitor(User competitor) {
        this.competitor = competitor;
    }
}
