package edu.upenn.cit5940.datamanagement;

public class NormalizeText {
    public static String[] normalizeText(String text) {
        return text.trim().toLowerCase().replaceAll("[^a-z0-9\\s-]", " ")
                .replaceAll("^-+", "") //remove leading -
                .replaceAll("-+$", "") //remove trailing -
                .replaceAll("\\s-+", " ") //remove trailing - after space
                .replaceAll("-+\\s", " ") //remove leading - b4 space
                .split(" ");// split at the spaces
    }
}