/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.common;

/**
 *
 * @author tuananhdev
 */
public abstract class Game{
    private Screen screen;
    
    public void setScreen(Screen screen) {
        if(this.screen != null) {
            this.screen.hideScreen();
        }
        this.screen = screen;
        
        if(this.screen != null) {
            ((View)this.screen).init();
            ((View)this.screen).addEvent();
            this.screen.showScreen();
        }
    }
    
}
