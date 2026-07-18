package edu.upenn.cit5940.datamanagement;

import java.io.File;
import java.nio.file.Files;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFileReader {
    public static Set<String> readTXTFile(String filename) {
        try (Stream<String> lines = Files.lines(new File(filename).toPath())) {
            return lines.collect(Collectors.toSet());
        } catch (Exception e) {
            return Set.<String>of(); //if any error occurs we want it to return the empty list
        }
    }
}