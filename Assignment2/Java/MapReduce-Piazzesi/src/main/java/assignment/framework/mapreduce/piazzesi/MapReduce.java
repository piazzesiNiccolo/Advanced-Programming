/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.framework.mapreduce.piazzesi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 *
 * @author nicco
 * @param <K>
 * @param <V>
 * @param <T>
 * @param <S>
 */
public abstract class MapReduce<K,V,T,S> {
    
    
    public final void compute(Path src, File dest) throws IOException{
        Stream i = read(src);
        Stream out = map(i);
        Stream sorted = out.sorted();
        Stream r = reduce(sorted);
        write(dest, r);
        
        
    }
    protected abstract Stream<Pair<K,V>> read(Path p) throws IOException;
    protected abstract Stream<Pair<T, S>> map(Stream<Pair<K,V>> in);
    
    protected abstract Stream<Pair<T, S>> reduce(Stream<Pair<T,S>> in);
    protected abstract  void write(File f, Stream<Pair<T,S>> r ) throws IOException;
}
