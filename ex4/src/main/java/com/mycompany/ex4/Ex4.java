/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nicco
 */
public class Ex4 {

    /**
     * @param args the command line arguments
     *
     */
    public static void printReadWriteProperties(Class bean) {
        List<Method> methods = Arrays.asList(bean.getMethods());
        methods.stream().filter(m -> m.getName().startsWith("get"))
                .forEach(method -> {
                    String methodName = method.getName().substring(3);
                    String setMethodName = "set" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
                    Method setMethod = methods.stream().filter(m -> m.getName().equals(setMethodName)).findAny().orElse(null);
                    if (setMethod != null) {

                        System.out.println("- " + methodName);
                    }
                });

    }

    public static void printReadOnlyProperties(Class bean) {
        List<Method> methods = Arrays.asList(bean.getMethods());
        methods.stream().filter(m -> m.getName().startsWith("get"))
                .forEach(method -> {
                    String methodName = method.getName().substring(3);
                    String setMethodName = "set" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
                    Method setMethod = methods.stream().filter(m -> m.getName().equals(setMethodName)).findAny().orElse(null);
                    if (setMethod == null) {

                        System.out.println("- " + methodName);
                    }
                });

    }
    

    public static void printWriteOnlyProperties(Class bean) {
        List<Method> methods = Arrays.asList(bean.getMethods());
        methods.stream().filter(m -> m.getName().startsWith("set"))
                .forEach(method -> {
                    String methodName = method.getName().substring(3);
                    String getMethodName = "get" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
                    Method getMethod = methods.stream().filter(m -> m.getName().equals(getMethodName)).findAny().orElse(null);
                    if (getMethod == null) {

                        System.out.println("- " + methodName);
                    }
                });

    }
    

    public static void printSubscriptableEvents(Class bean) {
        List<Method> methods = Arrays.asList(bean.getMethods());
        methods.stream().
                filter(m -> m.getName().endsWith("Listener") && m.getName().startsWith("add"))
                .forEach(m -> {
                String eventName = m.getName().substring(3, m.getName().indexOf("Listener"));
                System.out.println("- " + eventName);
                });
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write the bean to inspect: ");
        String b = r.readLine();
        Class bean = Class.forName(b);
        System.out.println("Read and Write properties:");
        printReadWriteProperties(bean);
        System.out.println("\nRead only properties:");
        printReadOnlyProperties(bean);
        System.out.println("\nWrite only properties:");
        printWriteOnlyProperties(bean);
        System.out.println("\nSubscriptable events:");
        printSubscriptableEvents(bean);
    }

}
