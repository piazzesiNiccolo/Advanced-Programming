/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author nicco
 */
public class XMLSerializer {

    public static void serialize(Object[] arr, String fileName) throws IllegalArgumentException, IllegalAccessException {
        try {
            StringBuilder s = new StringBuilder();
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
            w.write("");
            Class c = arr[0].getClass(); //we assume that every object is of the same class
            if (c.isAnnotationPresent(XMLable.class)) {
                for (Object a : arr) {
                    s.append("<").append(c.getSimpleName()).append(">\n");
                    serializeFields(c, s, a);
                    s.append("</").append(c.getSimpleName()).append(">\n");
                    w.append(s.toString());
                    s.setLength(0);
                }
            }
            w.flush();
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(XMLSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void serializeFields(Class c, StringBuilder s, Object a) throws IllegalAccessException, SecurityException, IllegalArgumentException {

        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            XMLfield ann = f.getAnnotation(XMLfield.class);
            if (ann != null) {
                s.append(" <");
                String s1 = ann.name().equals("") ? f.getName() : ann.name();
                s.append(s1);
                s.append(" type=");
                s.append("\"").append(ann.type()).append("\">");
                s.append(f.get(a));
                s.append("</").append(s1).append(">\n");

            }
        }
    }

}
