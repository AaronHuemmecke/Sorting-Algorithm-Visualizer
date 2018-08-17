package main.Java.app;

import main.Java.Util.LanguageManager;
import main.Java.Views.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * This is the main class to run the program
 */
public class SortingAlgorithmVisualizerApp
{
    /**
     * main method for the program to run
     * @param args arguments for program execution, currently not used in any way
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        final ExplanationWindow explanationWindow = new ExplanationWindow();
        final VisualizerWindow visualizerWindow = new VisualizerWindow();
        final QuizWindow quizWindow = new QuizWindow(visualizerWindow);

        final MainWindow mainwindow = new MainWindow(explanationWindow, visualizerWindow, quizWindow);

        mainwindow.setVisible(true);
        mainwindow.setPreferredSize(new Dimension(1400,1000));
        mainwindow.setSize(new Dimension(1400,1000));
        mainwindow.setMinimumSize(new Dimension(1200,1000));
        mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainwindow.setLocationRelativeTo(null);

        LanguageManager.getLanguageManagerInstance().setCurrentLocale(Locale.GERMAN);
    }
}
