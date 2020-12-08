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
public class DetectedNumberMessage extends PlayingMessage{
    
    private int detectedNumber;
    public static enum DetectedState {
        DETECTING, COMPLETE, FAIL
    }
    
    private DetectedState detectedState;
    public DetectedNumberMessage(User user, User competitor, int detectedNumber, DetectedState detectedState) {
        super(user, competitor);
        
        this.detectedNumber = detectedNumber;
        this.detectedState = detectedState;
    }

    public int getDetectedNumber() {
        return detectedNumber;
    }

    public DetectedState getDetectedState() {
        return detectedState;
    }
    
    
}
