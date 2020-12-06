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
public class Employee {
    
    public final int ID;

   

    @XMLfield(type = "String", name = "name")
    private final String fullName;

    @XMLfield(type = "String")
    private String role;

    public Employee(int ID, String fullName, String role) {
        this.ID = ID;
        this.fullName = fullName;
        this.role = role;
        

    }

    public Employee() {
        ID = 1;
        fullName = " ";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public Employee(String fullName, String role) {
        this.ID = 0;
        this.fullName = fullName;
        this.role = role;
    }

    

}
