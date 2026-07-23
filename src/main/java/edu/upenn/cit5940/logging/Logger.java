package edu.upenn.cit5940.logging;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Logger {
    private FileWriter out;
    public enum LogStatus{
        INFO, ERROR
    }
    private static final Logger logger = new Logger();

    //preventing external init
    private Logger(){
        try {
            out = new FileWriter("tech_news_search.log",true);
        }catch(Exception e){

        }
    }
    public static Logger getInstance(){
        return logger;
    }
    public void LogInformation(String action, LogStatus logStatus){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        var time = sdf.format(System.currentTimeMillis());
        try{
            out.write("["+time+"] "+logStatus+" "+action +"\n");
        }catch(Exception e){}finally {
            try{
                out.flush();
                out.close();}catch(Exception e){}

        }
        System.out.println();
    }



}