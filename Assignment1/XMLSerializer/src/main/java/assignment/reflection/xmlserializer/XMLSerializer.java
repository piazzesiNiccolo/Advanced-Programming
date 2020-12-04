/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nicco
 */
public class XMLSerializer {

    public static void serialize(Object[] arr, String fileName) throws IllegalArgumentException, IllegalAccessException {
        try {
            //we assume that every object is of the same class so we need to check only the first element
            Class c = arr[0].getClass(); 
            if (c.isAnnotationPresent(XMLable.class)) {
                StringBuilder s = new StringBuilder();
                BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
                
                //clear the file and set it up for xml
                w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                w.append("<").append("Objects").append(">\n"); //we need a unique root element
                
                for (Object a : arr) {
                    s.append("  <").append(c.getSimpleName()).append(">\n");
                    serializeFields(c, s, a);
                    s.append("  </").append(c.getSimpleName()).append(">\n");
                    w.append(s.toString());
                    s.setLength(0);
                }
                w.append("</").append("Objects").append(">\n");
               
                w.close(); // close the file actually writing the serialization on disk
            }

        } catch (IOException ex) {
            Logger.getLogger(XMLSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void serializeFields(Class c, StringBuilder s, Object a) throws IllegalAccessException, SecurityException, IllegalArgumentException {

        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true); //need it to access private fields
            XMLfield ann = f.getAnnotation(XMLfield.class);
            if (ann != null) {
                s.append("    <");
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
