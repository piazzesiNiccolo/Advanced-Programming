/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

/**
 *
 * @author nicco
 */
@XMLable
public class Student {

    @XMLfield(type = "String")
    public String firstName;

    @XMLfield(type = "String",name= "surname")
    public String lastName;

    @XMLfield(type = "int")
    private int age;

    public Student() {
    }

    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

}
