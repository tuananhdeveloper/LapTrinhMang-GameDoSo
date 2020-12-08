/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import client.model.User;
import java.io.Serializable;

/**
 *
 * @author Tuananh
 */
public class PlayingMessage implements Serializable{
    protected User user;
    protected User competitor;

    public PlayingMessage(User user, User competitor) {
        this.user = user;
        this.competitor = competitor;
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
