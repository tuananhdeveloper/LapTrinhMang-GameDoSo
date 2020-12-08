/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.io.Serializable;

/**
 *
 * @author Long
 */
public class Rankings implements Serializable{
    private User user;
    private float totalScore;
    private float averageTime;

    public Rankings() {
    }

    public Rankings(User user, float totalScore, float averageTime) {
        this.user = user;
        this.totalScore = totalScore;
        this.averageTime = averageTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public float getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(float averageTime) {
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
