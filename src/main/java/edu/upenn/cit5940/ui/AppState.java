package edu.upenn.cit5940.ui;

import java.util.Scanner;

/**
 * The AppState interface enforces certain states of the TechNewsApp, such as:
 * MainMenuState,InteractiveModeState, CommandModeState, and HelpModeState
 */
public interface AppState {
    void handleInput(TechNewsApp app, Scanner scanner);
}
