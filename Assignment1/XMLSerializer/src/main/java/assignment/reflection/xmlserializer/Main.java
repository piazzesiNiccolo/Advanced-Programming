/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;
import java.util.Arrays;

/**
 *
 * @author nicco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Student s1 = new Student("Niccolò", "Piazzesi",22);
        Student s2 = new Student("boh", "boh", 20);
        Student s3 = new Student();
        Student[] students = {s1,s2,s3};
        XMLSerializer.serialize(students, "boh.xml");
        
    }
    
}
