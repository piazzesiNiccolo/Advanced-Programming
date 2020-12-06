/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.reflection.xmlserializer;

import java.io.File;
import java.io.IOException;

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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder build = factory.newDocumentBuilder();
            Document doc = build.parse(f);
            Element root = doc.getDocumentElement();
            System.out.println(root.getNodeName());
            System.out.println(root.getFirstChild());

        } catch (/*ClassNotFoundException | */SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
