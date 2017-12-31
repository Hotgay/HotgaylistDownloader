package org.ho.yaml.tests;

import java.awt.event.KeyEvent;

/**
 *
 * @author THo
 */
public class TextEvent {
    
    public long timestamp;
    //public KeyEvent event;
    char chr;
    
    public TextEvent(){}
    
    /** Creates a new instance of TextEvent */
    //public TextEvent(long timestamp, KeyEvent event) {
    //    this.timestamp = timestamp;
    //    this.event = event;
    //}
    
    public TextEvent(long timestamp, char chr){
        this.timestamp = timestamp;
        this.chr = chr;
    }
    
    public String getChar(){
        if (chr == '\b')
            return "BACKSPACE";
        else
            return "" + chr;
    }
    
    public void setChar(String ch){
        if (ch.equals("BACKSPACE"))
            chr = '\b';
        else
            chr = ch.charAt(0);
    }
    
    public String toString(){
        return "(" + timestamp + ", " + chr + ")";
    }
}

