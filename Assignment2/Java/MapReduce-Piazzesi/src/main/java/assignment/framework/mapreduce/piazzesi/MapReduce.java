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
 * @param <R>
 * @param <U>
 * @param <T>
 */
public abstract class MapReduce<R extends Pair,  U extends Pair, T extends Pair> {
    
    
    public final void compute(Path src, File dest) throws IOException{
        Stream i = read(src);
        Stream out = map(i);
                    
        Stream r = reduce(out);
        write(dest, r);
        
        
    }
    protected abstract  Stream<R> read(Path p) throws IOException;
    protected abstract Stream<U> map(Stream<R> in);
    
    protected abstract Stream<T> reduce(Stream<U> in);
    protected abstract  void write(File f, Stream<T> r ) throws IOException;
}
