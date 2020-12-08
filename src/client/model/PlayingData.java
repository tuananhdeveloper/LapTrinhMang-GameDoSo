/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.Serializable;

/**
 *
 * @author Tuananh
 */
public class PlayingData implements Serializable{
    private int number[];
    private int uniqueNumber;

    public PlayingData(int[] number, int uniqueNumber) {
        this.number = number;
        this.uniqueNumber = uniqueNumber;
    }

    public int[] getNumber() {
        return number;
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }
    
    
}
