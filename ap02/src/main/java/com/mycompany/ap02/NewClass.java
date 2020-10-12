/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ap02;

/**
 *
 * @author Niccolò Piazzesi
 */
import java.util.ArrayList;
public class NewClass {
    
    ArrayList<Integer> a;

    public NewClass(ArrayList<Integer> a) {
        this.a = a;
    }
    
    public NewClass(){
        this.a = new ArrayList<>();
    }

    public ArrayList<Integer> getA() {
        return a;
    }

    public void setA(ArrayList<Integer> a) {
        this.a = a;
    }
    
    
    
}
