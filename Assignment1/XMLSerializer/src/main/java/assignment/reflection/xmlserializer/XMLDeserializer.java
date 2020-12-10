/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author nicco
 */
public class XMLDeserializer {

    public static Object[] deserializeXML(String filePath) {
        Object[] result = {};
        try {

            File f = new File(filePath);
            /*
            i was having problems with whitespaces in the xml so i simply convert it to a one line string
             */
            BufferedReader read = new BufferedReader(new FileReader(f));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = read.readLine()) != null) {
                sb.append(line.trim());
            }
            String xml = sb.toString();
            System.out.println(xml);

            //boiler code to setup xml DOM parsing
            InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder build = factory.newDocumentBuilder();

            Document doc = build.parse(in);

            Element root = doc.getDocumentElement();
            sb.setLength(0);
            sb.append(root.getNodeName());
            sb.append('.');
            sb.append(root.getFirstChild().getNodeName());

            //setup for class construction
            Class c = Class.forName(sb.toString());

            Map<String, Class> fieldTypes = new HashMap<>();
            fieldTypes.put("int", int.class);
            fieldTypes.put("double", double.class);
            fieldTypes.put("short", short.class);
            fieldTypes.put("float", float.class);
            fieldTypes.put("long", long.class);
            fieldTypes.put("boolean", boolean.class);
            fieldTypes.put("byte", byte.class);
            fieldTypes.put("char", char.class);
            fieldTypes.put("String", String.class);

            for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {
                for (Node field = child.getFirstChild(); field != null; field = field.getNextSibling()) {
                    String fieldType = field.getAttributes().getNamedItem("type").getNodeValue();
                    Class fieldClass = fieldTypes.get(fieldType);
                    Object fieldValue = field.getTextContent();
                    switch(){
                        
                    }
                    System.out.println(fieldClass.getName() + ":" + fieldValue);
                }

            }
        } catch (ClassNotFoundException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
}
