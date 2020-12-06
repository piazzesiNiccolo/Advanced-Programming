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
            //it's assumed that every object is of the same class so i simply get it from the first element
            Class c = arr[0].getClass();
            if (c.isAnnotationPresent(XMLable.class)) {

                // the string that will be written is modified multiple times, so i decided to use a stringbuilder
                StringBuilder s = new StringBuilder();
                BufferedWriter w = new BufferedWriter(new FileWriter(fileName));

                w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");//clear the file and set it up for xml
                w.append("<Objects>\n"); //<Objects> will be the unique root element, this was needed to have a correct xml file

                for (Object a : arr) {
                    //for some reason /t would write two tabs so i had to write s.append("  <") 
                    // with the whitespaces added manually for the class name and the fields
                    s.append("  <").append(c.getSimpleName()).append(">\n");
                    serializeFields(c, s, a);
                    s.append("  </").append(c.getSimpleName()).append(">\n");
                    w.append(s.toString());
                    s.setLength(0);
                }
                w.append("</Objects>\n");

                w.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(XMLSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void serializeFields(Class c, StringBuilder s, Object a) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        /*
        i decided to separate the fields serialization to have tidier code. For the same reason i 
        decided to use the enhanced for loop instead of streams
        to iterate on the fields
         */
        for (Field f : c.getDeclaredFields()) {

            f.setAccessible(true); //need it to access private fields
            XMLfield ann = f.getAnnotation(XMLfield.class);
            if (ann != null) {
                s.append("    <");
                String s1 = ann.name().equals("") ? f.getName() : ann.name(); //check to see if annotation name is set or has default value
                s.append(s1);
                s.append(" type=");
                s.append("\"").append(ann.type()).append("\">");
                s.append(f.get(a));
                s.append("</").append(s1).append(">\n");

            }
        }
    }

}
