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

    public int ID;

    @XMLfield(type = "String", name = "name")
    private final String fullName;

    @XMLfield(type = "String")
    private String role;
    
    @XMLfield(type = "double", name = "salary")
    private double taxedSalary;

    public double getTaxedSalary() {
        return taxedSalary;
    }

    public void setTaxedSalary(double taxedSalary) {
        this.taxedSalary = taxedSalary;
    }

    public Employee(int ID, String fullName, String role,double taxedSalary) {
        this(fullName, role,taxedSalary);
        this.ID = ID;
        this.taxedSalary = taxedSalary;

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

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public Employee(String fullName, String role,double taxedSalary) {
        this.ID = 0;
        this.fullName = fullName;
        this.role = role;
        this.taxedSalary = taxedSalary;
    }

}
