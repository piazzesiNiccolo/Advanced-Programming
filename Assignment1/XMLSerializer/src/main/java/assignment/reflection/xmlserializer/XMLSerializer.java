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

                w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");//clear the file and set it up
                
                /*
                i use the package name as root element for two reasons:
                - it makes the root element unique, producing valid xml
                - it makes it easier to deserialize because you can get the full class name of the objects by simply
                concatenating this name with the simple class name that is used as a tag of the nodes
                */
                w.append("<" + c.getPackageName() + ">\n"); 
                for (Object a : arr) {
                    //encapsulate each object in a XML node that has the class name as his tag
                    s.append("  <").append(c.getSimpleName()).append(">\n");
                    serializeFields(c, s, a);
                    s.append("  </").append(c.getSimpleName()).append(">\n");
                    w.append(s.toString());
                    s.setLength(0);
                }
                w.append("</" + c.getPackageName() + ">");

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
            
            //the field is serialized only if it's a string or of primitive type
            if (f.getType().isPrimitive() || f.getType().equals(String.class)) {
                f.setAccessible(true); //need it to access private fields
                XMLfield ann = f.getAnnotation(XMLfield.class);
                
                if (ann != null) {
                    //create the xml element for each field
                    s.append("    <");
                    //if the annotation name is set to default value simply use the field original name
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

}
