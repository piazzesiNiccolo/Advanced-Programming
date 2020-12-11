/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.util.Arrays;
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
            System.out.println("students.xml file created\n");
            
            //XMLable class with at least one field not marked as XMLField
            Employee e1 = new Employee("Marco Rossi", "Junior Developer", 1500.0);
            Employee e2 = new Employee(2, "Giovanni Bianchi", "Senior Developer", 3250.70);
            Employee e3 = new Employee(3, "Samuele Innocenti", "Project Manager", 4000.0);
            Object[] employees = {e1, e2, e3};
            XMLSerializer.serialize(employees, "employees.xml");
            System.out.println("employees.xml file created\n");
            
            //Class not annotated as XMLable,should not produce any file
            String str1 = "string one";
            String str2 = "string two";
            String str3 = "string three";
            String[] strings = {str1, str2, str3};
            XMLSerializer.serialize(strings, "strings.xml");

            //EXCERCISE 3: try to deserialize the xml files produced before and inspect 
            //the objects it should have created
            Object[] deserializedStudents = XMLDeserializer.deserializeXML("students.xml");
            System.out.println("\nDeserializing objects from students.xml:");
            Arrays.stream(deserializedStudents).forEach(e -> System.out.println(e.toString()));
            Object[] deserializedEmployees = XMLDeserializer.deserializeXML("employees.xml");
            System.out.println("\n\nDeserializing objects from employees.xml:");
            Arrays.stream(deserializedEmployees).forEach(e -> System.out.println(e.toString()));

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
