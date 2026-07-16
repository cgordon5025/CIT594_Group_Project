package edu.upenn.cit5940.datamanagement;

import edu.upenn.cit5940.common.dto.CSVFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {
    private final CharacterReader reader;

    public JsonFileReader(CharacterReader reader){this.reader = reader;}
    private enum STATES {
        // Add states here
        NORMAL,
        INSIDE_QUOTES,
        AFTER_QUOTES,
        INSIDE_CR
    }

    private STATES csvLineState = STATES.NORMAL;

    /**
     * Reads the entire CSV stream and parses it into a map of Articles.
     *
     * @return A map where the key is the article's URI (String) and the value
     * is the fully populated Article object.
     * @throws IOException        when the underlying reader encounters an error.
     * @throws CSVFormatException when the CSV file is formatted incorrectly.
     */
    public void readAllArticles() throws IOException, CSVFormatException {
        // TODO: Add code here
        try {
            //reader has been init in the constructor
            //note its a char reader (extension of buffered reader)
            //Generally speaking we want to split at the comma

            var temp = reader.read(); //reader reads in DESC vals of ASCII
//            Map<String, Article> runningArticles = new HashMap<>();
            List<String> singleArticleRaw = new ArrayList<>(); //should maintain insertion order
            StringBuilder currWord = new StringBuilder();
//            while (temp != -1) {
//                switch (csvLineState) {
//                    case NORMAL:
//                        switch (temp) {
//                            case 44:
//                                if (currWord.isEmpty()) { //clean up to tie up the final word into the raw values
//                                    singleArticleRaw.add("");
//                                } else {
//                                    singleArticleRaw.add(currWord.toString());
//                                }
//                                currWord.setLength(0);
//                                break;
//                            case 34:
//                                csvLineState = STATES.INSIDE_QUOTES; //set state to inside quote and leave
//                                break;
//                            case 13:
//                                csvLineState = STATES.INSIDE_CR;
//                                //lf or CR are ot be line breaks so we tie everything up and add the article
//                                break;
//                            case 10:
//                                try {
//                                    singleArticleRaw.add(String.valueOf(currWord)); //store currentword in case
//                                    currWord = new StringBuilder();
//                                    if (singleArticleRaw.size() > 1) {
//                                        ProcessArticleRecord.processRecord(singleArticleRaw);
//                                    }
////                                    Article newArticle = new Article(singleArticleRaw);
////                                    runningArticles.put(newArticle.getTitle(), newArticle);
//                                    singleArticleRaw.clear();
//                                } catch (Error e) {
//                                    throw new CSVFormatException(); //is missing columns
//                                }
//                                break;
//                            case 32:
//                                if (currWord.isEmpty()) {
//                                    throw new CSVFormatException();
//                                }
//                            default:
//                                currWord.append((char) temp);
//                        }
//                        break;
//                    case INSIDE_CR:
//                        if (temp == 10) {
//                            singleArticleRaw.add(String.valueOf(currWord)); //store currentword in case
//                            currWord = new StringBuilder();
//                            if (singleArticleRaw.size() > 1) {
//                                ProcessArticleRecord.processRecord(singleArticleRaw);
//                            }
//                            singleArticleRaw.clear();
//                            csvLineState = STATES.NORMAL;
//                        } else {
//                            throw new CSVFormatException();
//                        }
//                        break;
//                    case INSIDE_QUOTES: //to be done while inside quotes
//                        if (temp == 34) {
//                            csvLineState = STATES.AFTER_QUOTES; //if while inside quotes we fine more " then we're outside
//                        } else {
//                            currWord.append((char) temp);
//                        }
//                        break;
//                    case AFTER_QUOTES:
//                        if (temp == 34) {
//                            currWord.append('"'); //this means we have layered thinking the aaa,"b ""something""b", ccc example
//                            csvLineState = STATES.INSIDE_QUOTES;
//                        } else if (temp == 44) {
//                            if (currWord.isEmpty()) {
//                                singleArticleRaw.add("");
//                            } else {
//                                singleArticleRaw.add(currWord.toString());
//                            }
//                            currWord.setLength(0);
//                            csvLineState = STATES.NORMAL;
//                        } else {
//                            throw new CSVFormatException();
//                        }
//                        break;
//                }
//                temp = reader.read();
//            }
//            if (csvLineState != STATES.NORMAL) { //make sure we're all closed up
//                throw new CSVFormatException();
//            }
//            //clean up so that we put the final word in the string builder
//            if (currWord.isEmpty()) {
//                singleArticleRaw.add("");
//            } else {
//                singleArticleRaw.add(currWord.toString());
//            }
//            if (singleArticleRaw.size() > 1) {
//                ProcessArticleRecord.processRecord(singleArticleRaw);
//            }
            reader.close();
        } catch (Error e) {
            throw new IOException(e);
        }
        return;
    }
}