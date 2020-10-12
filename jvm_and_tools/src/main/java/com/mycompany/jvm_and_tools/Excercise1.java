/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jvm_and_tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuele
 */
public class Excercise1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n;
        System.out.println("Insert the number of strings to be sorted");
        try {
            n = Integer.parseInt(in.readLine());
            String[] input = new String[n];
            System.out.println("Insert the strings: ");
            for (int i = 0; i < n; i++) {
                input[i] = in.readLine();
            }
            Arrays.sort(input);
            Arrays.stream(input).forEach(s -> System.out.print(s + "  "));
        } catch (IOException ex) {
            Logger.getLogger(Excercise1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
