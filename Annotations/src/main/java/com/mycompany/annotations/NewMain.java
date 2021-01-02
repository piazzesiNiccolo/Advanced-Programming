/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.annotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Samuele
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("WRITE THE CLASS OF WHICH YOU WANT TO RUN THE TESTS: ");
            Class test = Class.forName(read.readLine());
            List<Method> methods = Arrays.asList(test.getDeclaredMethods())
                                        .stream()
                                        .filter(m -> m.getAnnotation(Test.class) != null)
                                        .collect(Collectors.toList());
            for (Method method : methods) {
                int returnedValue = (int)method.invoke(method.getParameters());
                int expectedValue = method.getAnnotation(Test.class).value();
                String s = returnedValue == expectedValue ? "ok" : "ko";
                System.out.println("Method " +  method.getName() + ": " + s );
            }
        
        } catch (IOException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
