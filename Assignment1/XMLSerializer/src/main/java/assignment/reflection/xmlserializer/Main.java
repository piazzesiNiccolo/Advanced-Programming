/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nicco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Student s1 = new Student("Niccolò", "Piazzesi",22);
            Student s2 = new Student("Jane", "Doe", 42);
            Student s3 = new Student();
            Student[] students = {s1,s2,s3};
            XMLSerializer.serialize(students, "boh.xml");
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
