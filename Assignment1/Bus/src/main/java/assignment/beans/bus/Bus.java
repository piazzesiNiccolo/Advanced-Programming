/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.beans.bus;

import java.beans.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author nicco
 */
public class Bus implements Serializable {

    public static final String PROP_CAPACITY = "capacity";
    public static final String PROP_DOOROPEN = "doorOpen";
    public static final String PROP_NUMPASSENGERS = "numPassengers";
    private final Timer timer = new Timer();

    private int capacity;
    private boolean doorOpen;
    private int numPassengers;

    private final PropertyChangeSupport propertySupport;
    private final VetoableChangeSupport vetos;

    public Bus() {
        capacity = 50;
        doorOpen = false;
        numPassengers = 20;
        propertySupport = new PropertyChangeSupport(this);
        vetos = new VetoableChangeSupport(this);
    }

    public void activate() {
        Random r = new Random();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (numPassengers > 0) {

                    int decr = r.nextInt(numPassengers + 1);
                    if (decr > 0) // no need to update numPassengers if no passenger is exiting
                    {
                        setNumPassengers(numPassengers - decr);
                    }
                }
            }
        }, 30000, 10000);

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isDoorOpen() {

        return doorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        boolean oldDoorOpen = this.doorOpen;
        this.doorOpen = doorOpen;
        propertySupport.firePropertyChange(PROP_DOOROPEN, oldDoorOpen, doorOpen);
    }

    public boolean getDoorOpen() {
        return this.doorOpen;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int newNumPassengers) {
        int oldNumPassengers = this.numPassengers;

        if (newNumPassengers <= capacity) {
            try {
                vetos.fireVetoableChange(PROP_NUMPASSENGERS, oldNumPassengers, newNumPassengers);
                numPassengers = newNumPassengers;
                setDoorOpen(true);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setDoorOpen(false);
                        propertySupport.firePropertyChange(PROP_NUMPASSENGERS, oldNumPassengers, newNumPassengers);
                    }
                }, 3000);
                //if the change is vetoed or the bus is at full capacity we do nothing
            } catch (PropertyVetoException ex) {

            }
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener l) {
        vetos.addVetoableChangeListener(l);
    }

    public void removeVetoableChangeListener(VetoableChangeListener l) {
        vetos.removeVetoableChangeListener(l);
    }

}

