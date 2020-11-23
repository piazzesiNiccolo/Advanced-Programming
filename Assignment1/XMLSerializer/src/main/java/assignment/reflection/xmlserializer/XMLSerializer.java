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
    
    public static void serialize(Object[] arr, String fileName){
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
            List<String> l = Arrays.stream(arr[0].getClass().getAnnotations())
                                .map(e -> e.annotationType().getSimpleName())
                                .collect(Collectors.toList());
            if (l.contains("XMLable")) {
                w.append("ciao ciao ciao");
                w.flush();
                w.close();
            }
            
            /*c.get
                w.append("ciao ciao ciao");
                w.flush();
                w.close();
            }*/
        } catch (IOException ex) {
            Logger.getLogger(XMLSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
