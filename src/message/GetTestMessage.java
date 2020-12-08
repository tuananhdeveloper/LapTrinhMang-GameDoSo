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
public class GetTestMessage extends PlayingMessage{

    public GetTestMessage(User user, User competitor) {
        super(user, competitor);
    }
    
}
