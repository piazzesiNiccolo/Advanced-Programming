/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.invertedindex;

import assignment.mapreduce.MapReduce;
import assignment.utils.Pair;
import assignment.utils.Reader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author nicco
 */
public class Indexer extends MapReduce<Pair<String,List<String>>, Pair<String,Pair<String,Integer>>, Pair<String,Pair<String,Integer>>>{

    @Override
    protected Stream<Pair<String,List<String>>> read(Path p) throws IOException {
        Reader r = new Reader(p);
        return r.read();
    }

    @Override
    protected Stream<Pair<String,Pair<String,Integer>>> map(Stream<Pair<String,List<String>>> in) {
               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Stream<Pair<String,Pair<String,Integer>>> compare(Stream<Pair<String,Pair<String,Integer>>> s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Stream<Pair<String,Pair<String,Integer>>> reduce(Stream<Pair<String,Pair<String,Integer>>> in) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void write(File f, Stream<Pair<String,Pair<String,Integer>>> r) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
