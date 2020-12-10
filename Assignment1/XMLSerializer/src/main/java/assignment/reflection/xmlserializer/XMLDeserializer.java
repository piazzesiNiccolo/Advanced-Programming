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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author nicco
 */
public class XMLDeserializer {

    public static Object[] deserializeXML(String filePath) {
        List<Object> result = new ArrayList<>();
        try {

            File f = new File(filePath);
            /*
            i was having problems with whitespaces in the xml so i simply convert it to a one line string
            and parse this string
             */
            BufferedReader read = new BufferedReader(new FileReader(f));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = read.readLine()) != null) {
                sb.append(line.trim());
            }
            String xml = sb.toString();
            
            Element root = parseXMLFromString(xml, sb);

            //setup for class construction
            Class c = Class.forName(sb.toString());

            Map<String, Class> types = new HashMap<>();
            types.put("int", int.class);
            types.put("double", double.class);
            types.put("short", short.class);
            types.put("float", float.class);
            types.put("long", long.class);
            types.put("boolean", boolean.class);
            types.put("byte", byte.class);
            types.put("char", char.class);
            types.put("String", String.class);

            for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {
                createObject(c, child, types);

            }
        } catch (ClassNotFoundException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DOMException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.stream().toArray();
    }

    private static Object createObject(Class c, Node child, Map<String, Class> types) throws DOMException, NoSuchMethodException {
        /*
        parse each xml object to find type and value of each field, then use the appropriate constructor to create
        the instance
        */
        int length = child.getChildNodes().getLength();
        Class[] fieldTypes = new Class[length];
        Object[] fieldValues = new Object[length];
        int i = 0;
        for (Node field = child.getFirstChild(); field != null; field = field.getNextSibling()) {
            String fieldType = field.getAttributes().getNamedItem("type").getNodeValue();
            if (fieldType.equals("String")) {
                fieldTypes[i] = String.class;
                
            } else {
                fieldTypes[i] = types.get(fieldType);
               

            }
            i++;

        }
        Constructor con = c.getConstructor(fieldTypes);
        return null;

    }

    private static Element parseXMLFromString(String xml, StringBuilder sb) throws IOException, UnsupportedEncodingException, SAXException, ParserConfigurationException {
        //boiler code to parse the XML
        InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = factory.newDocumentBuilder();
        Document doc = build.parse(in);
        Element root = doc.getDocumentElement();
        sb.setLength(0);
        sb.append(root.getNodeName());
        sb.append('.');
        sb.append(root.getFirstChild().getNodeName());
        return root;
    }

}
