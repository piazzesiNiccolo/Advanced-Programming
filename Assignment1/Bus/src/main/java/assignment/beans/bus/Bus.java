/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.beans.bus;

import java.beans.*;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicco
 */
public class Bus implements Serializable {

    public static final String PROP_CAPACITY = "capacity";
    public static final String PROP_DOOROPEN = "doorOpen";
    public static final String PROP_NUMPASSENGERS = "numPassengers";
    public static final int SLEEP = 6000;

    private int capacity;
    private boolean doorOpen;
    private int numPassengers;

    private PropertyChangeSupport propertySupport;

    public Bus() {
        capacity = 50;
        doorOpen = false;
        numPassengers = 20;
        propertySupport = new PropertyChangeSupport(this);
    }

    public void activate() { 
        Random r = new Random();
        while (true) {
            try {
                Thread.sleep(SLEEP);
                int decr = r.nextInt(numPassengers+1);
                numPassengers -= decr;
            } catch (InterruptedException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void setCapacity(int capacity) {
        int oldCapacity = this.capacity;
        this.capacity = capacity;
        propertySupport.firePropertyChange(PROP_CAPACITY, oldCapacity, this.capacity);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
