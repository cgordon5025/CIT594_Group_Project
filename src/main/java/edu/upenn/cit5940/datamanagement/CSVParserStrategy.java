package edu.upenn.cit5940.datamanagement;

import java.io.*;
import java.util.*;
import edu.upenn.cit5940.common.dto.Article;


public class CSVParserStrategy implements ArticleParserStrategy {

    // states for parsing a CSV file
    private enum STATES {
        START_OF_FIELD,
        NORMAL_FIELD,
        QUOTED_FIELD,
        QUOTE_IN_QUOTED_FIELD,
        CR_AT_RECORD_END
    }

    @Override
    public void parse(File file) throws Exception {

        try (CharacterReader characterReader = new CharacterReader(file.getPath())) {
            readAllArticles(characterReader);
        }
    }

    /**
     * Reads the entire CSV stream and parses it into a map of Articles.
     *
     * @return A map where the key is the article's URI (String) and the value
     * is the fully populated Article object.
     * @throws IOException when the underlying reader encounters an error.
     * @throws CSVFormatException when the CSV file is formatted incorrectly.
     */
    public void readAllArticles(CharacterReader reader) throws IOException, CSVFormatException {

        List<String> currentRecordFields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();

        // kick off the reading with the start_of_field state
        STATES state = STATES.START_OF_FIELD;

        int input;

        while ((input = reader.read()) != -1) {
            char c = (char) input; // cast int input as char

            switch (state) {
                case START_OF_FIELD -> {
                    switch (c) {
                        case '"' -> state = STATES.QUOTED_FIELD;
                        case ',' -> currentRecordFields.add(""); // in case missing header
                        case '\r' -> {
                            currentRecordFields.add("");
                            state = STATES.CR_AT_RECORD_END;
                        }
                        case '\n' -> {
                            currentRecordFields.add("");
                            ProcessArticleRecord.processRecord(currentRecordFields);
                            currentRecordFields.clear();
                        }
                        default -> {
                            currentField.append(c);
                            state = STATES.NORMAL_FIELD;
                        }
                    }
                }

                case NORMAL_FIELD -> {
                    switch (c) {
                        case ',' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0); // reset field length
                            state = STATES.START_OF_FIELD;
                        }
                        case '\r' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0);
                            state = STATES.CR_AT_RECORD_END;
                        }
                        case '\n' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0);
                            ProcessArticleRecord.processRecord(currentRecordFields);
                            currentRecordFields.clear();
                            state = STATES.START_OF_FIELD;
                        }
                        case '"' -> throw new CSVFormatException();
                        default  -> currentField.append(c);
                    }
                }

                case QUOTED_FIELD -> {
                    switch (c) {
                        case '"' -> state = STATES.QUOTE_IN_QUOTED_FIELD;
                        default  -> currentField.append(c); // handle commas, LFs, and CRs
                    }
                }

                case QUOTE_IN_QUOTED_FIELD -> {
                    switch (c) {
                        case '"' -> {
                            currentField.append('"');
                            state = STATES.QUOTED_FIELD;
                        }
                        case ',' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0);
                            state = STATES.START_OF_FIELD;
                        }
                        case '\r' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0);
                            state = STATES.CR_AT_RECORD_END;
                        }
                        case '\n' -> {
                            currentRecordFields.add(currentField.toString());
                            currentField.setLength(0);
                            ProcessArticleRecord.processRecord(currentRecordFields);
                            currentRecordFields.clear();
                            state = STATES.START_OF_FIELD;
                        }
                        default -> throw new CSVFormatException();
                    }
                }

                case CR_AT_RECORD_END -> {
                    switch (c) {
                        case '\n' -> {
                            ProcessArticleRecord.processRecord(currentRecordFields);
                            currentRecordFields.clear();
                            state = STATES.START_OF_FIELD;
                        }
                        default -> throw new CSVFormatException();
                    }
                }
            }
        }

        // if EOF encounters CR
        if (state == STATES.CR_AT_RECORD_END) {
            throw new CSVFormatException();
            // if we encounter an unclosed quote at end of stream
        } else if (state == STATES.QUOTED_FIELD) {
            throw new CSVFormatException();
            // handle end of CSV in case there are no LF
        } else if (state == STATES.NORMAL_FIELD || state == STATES.QUOTE_IN_QUOTED_FIELD) {
            currentRecordFields.add(currentField.toString());
            ProcessArticleRecord.processRecord(currentRecordFields);
            // handles case where comma is the ending character
        } else if (state == STATES.START_OF_FIELD && !currentRecordFields.isEmpty()) {
            currentRecordFields.add("");
            ProcessArticleRecord.processRecord(currentRecordFields);
        }
    }
}
