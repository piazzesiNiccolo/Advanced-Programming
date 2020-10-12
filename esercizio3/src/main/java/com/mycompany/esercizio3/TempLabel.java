/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esercizio3;


import java.beans.*;
import java.io.Serializable;
import javax.swing.JLabel;

import java.io.Serializable;
import javax.swing.JLabel;


public class TempLabel extends JLabel implements Serializable {

    @Override
    public void setText(String text) {
        if (text.equals("")) {
            super.setText("");
            
        } else {
        double celsius = Double.parseDouble(text);
        double farenheit = celsius *9/5 + 32;
        super.setText(String.valueOf(farenheit)); //To change body of generated methods, choose Tools | Templates.
        }
     }
    
  
    public TempLabel() {
        
    }
    
   
}
