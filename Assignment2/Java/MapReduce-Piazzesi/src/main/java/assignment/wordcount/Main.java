/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.wordcount;

import assignment.mapreduce.MapReduce;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MapReduce m = new WordCount();
            Path p = Path.of("Books/", args);
            m.compute(p, new File("f.csv"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
