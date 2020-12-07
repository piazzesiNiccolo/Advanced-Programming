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

            // XMLable class with all fields marked as XMLField
            Student s1 = new Student("Niccolò", "Piazzesi", 22);
            Student s2 = new Student("Jane", "Doe", 42);
            Student s3 = new Student("Luca", "Mezzi", 21);
            Object[] students = {s1, s2, s3};
            XMLSerializer.serialize(students, "students.xml");

            //XMLable class with at least one field not marked as XMLField
            
            Employee e1 = new Employee(1, "Marco Rossi", "Junior Developer");
            Employee e2 = new Employee(2, "Giovanni Bianchi", "Senior Developer");
            Employee e3 = new Employee(3, "Samuele Innocenti", "Project Manager");
            Object[] employees = {e1,e2,e3};
            XMLSerializer.serialize(employees, "employees.xml");
            
            //Class not annotated as XMLable,should not produce any file
            String str1 = "string one";
            String str2 = "string two";
            String str3 = "string three";
            String[] strings ={str1,str2,str3};
            XMLSerializer.serialize(strings,"strings.xml");
            
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
