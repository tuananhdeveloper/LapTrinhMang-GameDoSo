/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;
import client.Status;
import java.io.Serializable;

/**
 *
 * @author Long
 */
public class User implements Serializable{
    private static final long serialversionUID = 129348938L;
    private int id;
    private String userName;
    private String password;
    private String name;
//    private int totalGame;
//    private float score;
//    private float totalTime;
    private Status status;

    public User() {
    }

    public User(int id, String userName, String name) {
        this.id = id;
        this.userName = userName;
        this.name = name;
    }
    
    public User(int id, String userName, String password, String name, Status status) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
//        this.totalGame = totalGame;
//        this.score = score;
//        this.totalTime = totalTime;
        this.status = status;
    }

    
//    public int getTotalGame() {
//        return totalGame;
//    }
//
//    public void setTotalGame(int totalGame) {
//        this.totalGame = totalGame;
//    }
//
//    public float getScore() {
//        return score;
//    }
//
//    public void setScore(float score) {
//        this.score = score;
//    }
//
//    public float getTotalTime() {
//        return totalTime;
//    }
//
//    public void setTotalTime(float totalTime) {
//        this.totalTime = totalTime;
//    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", name=" + name + '}';
    }
    
    
}
