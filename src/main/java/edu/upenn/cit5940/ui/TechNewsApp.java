package edu.upenn.cit5940.ui;

import edu.upenn.cit5940.common.dto.Article;
import edu.upenn.cit5940.datamanagement.*;
import edu.upenn.cit5940.processor.ArticleProcessor;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class TechNewsApp {
    private boolean running = true;
    private AppState currentState;
    private final ArticleProcessor processor = new ArticleProcessor();

    public TechNewsApp() {
        // set up the UI layer and begins in main menu
        this.currentState = new MainMenuState();
    }

    /**
     * Changes from one application state to another
     * @param newState: new appstate
     */
    public void changeState(AppState newState) {
        this.currentState = newState;
    }

    /**
     * Handles exiting of the program
     */
    public void exit() {
        this.running = false;
        System.out.println("Thank you for using Tech News Search Engine. Goodbye!");
    }

    /**
     * Runs the main loop of the TechNewsApp program
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            currentState.handleInput(this, scanner);
        }
        scanner.close();
    }

    /**
     * Getter for the processor that's associated with the TechNewsApp
     * @return the ArticleProcessor
     */
    public ArticleProcessor getProcessor() {
        return processor;
    }
}