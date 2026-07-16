package edu.upenn.cit5940;

import edu.upenn.cit5940.common.dto.CSVFormatException;
import edu.upenn.cit5940.datamanagement.ArticlesParsed;
import edu.upenn.cit5940.datamanagement.CharacterReader;
import edu.upenn.cit5940.datamanagement.JsonFileReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //TAKES IN ARGS FROM LAUNCH OF APP (E.G. MYPROJECT.JAVA FILENAME.CSV)
try{
    //THIS IS WRITTEN BY CHARLEE TO TEST THE JSON PATH

    JsonFileReader parsed = new JsonFileReader(new CharacterReader(args[0]));
    parsed.readAllArticles(args[0]);
    ArticlesParsed.buildGraphFromArticles();
}catch(IOException| CSVFormatException e){
    System.out.println("error");
}
    }
}