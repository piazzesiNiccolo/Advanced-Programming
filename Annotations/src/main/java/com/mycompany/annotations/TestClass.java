/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.annotations;

/**
 *
 * @author Samuele
 */
public class TestClass {
    
    @Test(value=5)
    public static int successTest(){
        return 5;
    }
    
    @Test(value=3)
    public static int failingTest(){
        return 2;
    }
    
    public static String notTestMethod(){
        return "this is not a Test Method";
    }
    
    
}
