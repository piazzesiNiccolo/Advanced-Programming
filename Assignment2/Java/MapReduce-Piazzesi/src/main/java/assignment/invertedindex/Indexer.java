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
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 *
 * @author nicco
 */
public class Indexer extends MapReduce<Pair<String, List<String>>, Pair<String, List<Pair<String, Integer>>>, Pair<String, Pair<String, Integer>>> {

    @Override
    protected Stream<Pair<String, List<String>>> read(Path p) throws IOException {
        Reader r = new Reader(p);
        return r.read();
    }

    @Override
    protected Stream<Pair<String, List<Pair<String, Integer>>>> map(Stream<Pair<String, List<String>>> in) {
        return in.map(p -> numLines(p));
                
        
        }

    private Pair<String, List<Pair<String, Integer>>> numLines(Pair<String, List<String>> p) {
        
        String doc = p.getKey();
        List<Pair<String, Integer>> lines = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        p.getValue().forEach((String l) -> {
            int curr = i.getAndIncrement();
            Arrays.stream(l.split(" "))
                    .map(s->s.toLowerCase().replaceAll("[^a-z-9]", ""))
                    .filter(s->s.length() > 3)
                    .forEach(s -> lines.add(new Pair<>(s,curr)));
                });
        return new Pair<>(doc, lines);

    }

    @Override
    protected Stream<Pair<String, List<Pair<String, Integer>>>> compare(Stream<Pair<String, List<Pair<String, Integer>>>> s) {

        return s.sorted(Comparator.comparing(Pair::getKey));
    }

    @Override
    protected Stream<Pair<String, Pair<String, Integer>>> reduce(Stream<Pair<String, List<Pair<String, Integer>>>> in) {
        return in.flatMap(w -> w
                .getValue()
                .stream()
                .map(x -> new Pair<>(x.getKey(), new Pair<>(w.getKey(), x.getValue())))
                .sorted(Comparator.comparing(Pair::getKey))
                .sorted((p1, p2) -> p1.getValue().getKey().compareTo(p2.getValue().getKey()))
                .sorted((p1, p2) -> p1.getValue().getValue().compareTo(p2.getValue().getValue()))

                
        );
    }

    @Override
    protected void write(File f, Stream<Pair<String, Pair<String, Integer>>> r) throws IOException {
        PrintStream ps = new PrintStream(f);
        r.forEach(p -> ps.println(p.getKey() + ", " + p.getValue().getKey() + ", "
                + p.getValue().getValue()));
        ps.close();
    }

}
