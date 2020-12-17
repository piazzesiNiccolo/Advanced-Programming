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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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

    
    private static boolean isDeserializable(Class c){
        boolean check = false;
        if(c.isAnnotationPresent(XMLable.class)){
            try {
                c.getConstructor();
                for(Field f: c.getDeclaredFields()){
                    f.setAccessible(true);
                    check  = !(Modifier.isStatic(f.getModifiers())) && 
                            (f.getType().isPrimitive() || f.getType().equals(String.class)) &&
                            f.isAnnotationPresent(XMLfield.class);
                    
                    if(!check){
                        //as soon as one field is not deserializable we stop
                        break;
                    }
                                
                }
            } catch (NoSuchMethodException ex) {
                return false; //if no default constructor exists getConstructor raises this exception
            } catch (SecurityException ex) {
                Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    public static Object[] deserializeXML(String filePath) {
        List<Object> result = new ArrayList<>();
        //i prefer to use a list for intermediate operations and convert it to an array at the end
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

            Element root = parseXMLFromString(xml);
            //get the full class name by concatenating the package name used as the root node with
            //the class name provided by the first object of the list
            sb.setLength(0);
            sb.append(root.getNodeName());
            sb.append('.');
            sb.append(root.getFirstChild().getNodeName());

            /*
            Setup for deserialization: try to find the class with the name that was parsed
            and check if it can be deserialized
            */
            Class c = Class.forName(sb.toString());
            if(isDeserializable(c)){
                
                //possible types of each field
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


                //iterate on the xml elements and populate the list with the objects
                for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {
                    result.add(createObject(c, child, types));
                }
            }
        } catch (ClassNotFoundException | SAXException | IOException | ParserConfigurationException | DOMException | NoSuchMethodException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result.isEmpty()){result.add("COULD NOT DESERIALIZE");}
        return result.stream().toArray();
    }

    private static Object createObject(Class c, Node child, Map<String, Class> types) throws DOMException, NoSuchMethodException {
        Object res = "NOT CREATED"; //prefer it to initializing it as null
        try {
            /*
            parse each xml node to find type and value of each field, then use the appropriate constructor to create
            the instance
            */
            int length = child.getChildNodes().getLength();
            Class[] fieldTypes = new Class[length];
            Object[] fieldValues = new Object[length];
            int i = 0;
            
            for (Node field = child.getFirstChild(); field != null; field = field.getNextSibling()) {
                
                String fieldType = field.getAttributes().getNamedItem("type").getNodeValue();
                String fieldValue = field.getTextContent();
                
                fieldTypes[i] = types.get(fieldType);
                if (fieldType.equals("String")) {
                    fieldValues[i] = fieldValue;
                } else {
                    fieldValues[i] = parsePrimitive(fieldType, fieldValue);
                    }
                i++;
                
            }
            /*
            actually create the object. It's assumed that the proper constructor exists.
            For the objects created by the default constructor, their fields were instantianted with a default value
            so this should work properly for them as well
            */
            Constructor con = c.getConstructor(fieldTypes);
            res = con.newInstance(fieldValues);
            
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    private static Object parsePrimitive(String fieldType, String value){
        Object res = "NOT PARSED";
        /*
        a simple case switch to parse the string read as the appropriate value
        */  
        switch(fieldType){
            case "int":
                res = Integer.parseInt(value);
                break;
            case "double":
                res = Double.parseDouble(value);
                break;
            case "float":
                res = Float.parseFloat(value);
                break;
            case "short":
                res = Short.parseShort(value);
                break;
            case "long":
                res = Long.parseLong(value);
                break;
            case "byte":
                res = Byte.parseByte(value);
                break;
            case "char":
                // in the case of a char value we assume that the string parsed is one character long
                res = value.charAt(0); 
                break;
            case "boolean":
                res = Boolean.parseBoolean(value);
                break;
            default:
                break;
            
        }
        return res;
    }
    
    private static Element parseXMLFromString(String xml) throws IOException, UnsupportedEncodingException, SAXException, ParserConfigurationException {
        //boiler code to parse the XML
        InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = factory.newDocumentBuilder();
        Document doc = build.parse(in);
        Element root = doc.getDocumentElement();
        return root;
    }

}
