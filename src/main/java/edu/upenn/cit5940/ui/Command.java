package edu.upenn.cit5940.ui;

/**
 * The Command interface defines any methods that Commands must implement, including:
 * -execute(): carries out the main function of the command
 */
public interface Command {
    void execute(String[] args);
}
