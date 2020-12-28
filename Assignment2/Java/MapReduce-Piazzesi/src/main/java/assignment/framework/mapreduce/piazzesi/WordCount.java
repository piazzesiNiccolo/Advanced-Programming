/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.framework.mapreduce.piazzesi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author nicco
 */
public class WordCount extends MapReduce<String, List<String>, String, Integer> {

    @Override
    protected Stream<Pair<String, List<String>>> read(Path p) throws IOException {
        Reader r = new Reader(p);
        return r.read();
    }

    @Override
    protected Stream<Pair<String, Integer>> map(Stream<Pair<String, List<String>>> in) {
        return null;

    }

    @Override
    protected Stream<Pair<String, Integer>> reduce(Stream<Pair<String, Integer>> in) {

        Map<String, Integer> m = in
                .collect(Collectors.groupingBy(p -> p.getKey(), Collectors.summingInt(p -> p.getValue())));
        
        return m.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()));
    }

    @Override
    protected void write(File f, Stream<Pair<String, Integer>> r) throws FileNotFoundException {
        Writer.write(f, r);
    }

}
