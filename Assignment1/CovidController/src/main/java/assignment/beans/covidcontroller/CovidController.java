/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.beans.covidcontroller;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author nicco
 */
public class CovidController implements Serializable, VetoableChangeListener {

    public static final String PROP_REDUCED_CAPACITY = "reducedCapacity";

    private int reducedCapacity;

    public CovidController() {
        reducedCapacity = 25;

    }

    public int getReducedCapacity() {
        return reducedCapacity;
    }

    public void setReducedCapacity(int reducedCapacity) {
        this.reducedCapacity = reducedCapacity;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent ev) throws PropertyVetoException {
         //we check if the event is on an integer property and if the value is bigger than the reduced capacity
        if ( ev.getNewValue().getClass().equals(Integer.class) && (Integer) ev.getNewValue() > reducedCapacity) {
            throw new PropertyVetoException(PROP_REDUCED_CAPACITY, ev);
        }

    }

}