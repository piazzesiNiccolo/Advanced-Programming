/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.framework.mapreduce.piazzesi;

import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 *
 * @author nicco
 */
public abstract class MapReduce {
    
    
    public final <T,S,K,V> void compute(Path p, File f){
        Stream<Pair<K,V>> i = read(p);
        Stream<Pair<T,S>> out = map(i);
        write(f,reduce(out.sorted(compare())));
    }
    protected abstract <K,V> Stream<Pair<K,V>> read(Path p);
    protected abstract <T,S,K,V> Stream<Pair<T, S>> map(Stream<Pair<K,V>> in);
    protected abstract <T> Comparator<T> compare();
    protected abstract <T,S,K,V> Stream<Pair<T, S>> reduce(Stream<Pair<K,V>> in);
    protected abstract <K,V> void write(File f, Stream<Pair<K,V>> r );
}
