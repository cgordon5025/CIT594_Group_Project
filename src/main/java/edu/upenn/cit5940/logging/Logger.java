package edu.upenn.cit5940.logging;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Logger {
    public static LogStatus LogStatus;
    private FileWriter out;
    public enum LogStatus{
        INFO, ERROR;

    }
    private static final Logger logger = new Logger();

    //preventing external init and empty
    private Logger(){}
    public void initLogger(String filePath){
        if(out!=null) return; //prevent re-initialization
        try{
            out = new FileWriter(filePath,true);
        }catch(Exception e){
            System.out.println("Error int logger");
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
            out.flush();
        }catch(Exception e){
            System.out.println("error");
        }
    }
    public void closeLogger(){
        try{
        out.close();
        }catch(Exception e){
            System.out.println();
        }
    }



}