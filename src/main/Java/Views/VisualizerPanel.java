package main.Java.Views;

import javafx.util.Pair;
import main.Java.Util.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * VisualizerPanel in which the actual visualization of sorting algorithms take place
 */
public class VisualizerPanel extends JPanel
{
    /**
     * count of numbers to be sorted
      */
    private int numberCount;

    /**
     * ArrayList of numbers to be sorted
     */
    private ArrayList<Integer> randomNumbers;

    /**
     * ArrayList of rectangeles representing the numbers
     */
    private ArrayList<Rectangle2D> rectangles;

    /**
     * ArrayList containing the indices of all selected numbers
     */
    private ArrayList<Integer> selected;

    /**
     * ArrayList containing the indices of all sorted numbers
     */
    private ArrayList<Integer> sorted;

    /**
     * Boolean that indicates whether the visualization has been paused
     */
    private volatile boolean pause;

    /**
     * Boolean that indicates whether a reset is complete
     */
    private boolean resetComplete;

    /**
     * Graphics2d object used for visualization
     */
    private Graphics2D g2;

    /**
     * VisualizerWindow in which the VisualizerPanel is embedded
     */
    private VisualizerWindow visualizerWindow;

    /**
     * First annotationWindow
     */
    private AnnotationWindow annotationWindow1;

    /**
     * Second annotationWindow
     */
    private AnnotationWindow annotationWindow2;

    /**
     * animationDelay of the visualization
     */
    private int animationDelay;

    /**
     * number of variables also shown in the visualization
     */
    private int variables;

    /**
     * ArrayList of the indices for the indicators of variables
     */
    private ArrayList<Integer> variableIndicators;

    /**
     * Boolean that indicates whether the visualization has been reseted
     */
    private boolean reset;

    /**
     * Booleam that indicates whether the next step should be shown
     */
    private boolean nextStep;

    /**
     * Counter for the swaps of an algorithm
     */
    private int swapCount;

    /**
     * Counter for the accesses of an algorithm
     */
    private int accessCount;

    /**
     * counter for the comarisons of an algorithm
     */
    private int compareCount;

    /**
     * ArrayList that contains all the indices of numbers that are annotated using the first AnnotationWindow
     */
    private ArrayList<Integer> annotated1;

    /**
     * ArrayList that contains all indices and annotations of numbers that are annotated using the first AnnotationWindow
     */
    private ArrayList<Pair<Integer, String>> annotations1;

    /**
     * ArrayList that contains all the indices of numbers that are annotated using the second AnnotationWindow
     */
    private ArrayList<Integer> annotated2;

    /**
     * ArrayList that contains all indices and annotations of numbers that are annotated using the second AnnotationWindow
     */
    private ArrayList<Pair<Integer, String>> annotations2;

    /**
     * Thread of the current visualization
     */
    private Thread currentVisualization;

    /**
     * Name of the current visualization
     */
    private String currentVisualizationName;

    /**
     * Constructor of the visualizationPanel
     * @param numberCount count of numbers to be sorte
     * @param visualizerWindow VisualizerWindow in which the VisualizerPanel is embedded
     * @param annotationWindow1 first AnnotationWindow that contains annotations of particular numbers during the visualization of the algorithm
     * @param annotationWindow2 second AnnotationWindow that contains annotations of particular numbers during the visualization of the algorithm
     */
    public VisualizerPanel(int numberCount, VisualizerWindow visualizerWindow, AnnotationWindow annotationWindow1, AnnotationWindow annotationWindow2) {
        setBackground(Color.WHITE);
        setVisible(true);
        pause = false;
        reset = false;
        resetComplete = false;
        nextStep = false;

        currentVisualization = null;
        currentVisualizationName = "";

        variableIndicators = new ArrayList<Integer>();

        annotated1 = new ArrayList<Integer>();
        annotations1 = new ArrayList<Pair<Integer, String>>();

        annotated2 = new ArrayList<Integer>();
        annotations2 = new ArrayList<Pair<Integer, String>>();

        this.annotationWindow1 = annotationWindow1;
        this.annotationWindow2 = annotationWindow2;

        swapCount = 0;
        accessCount = 0;
        compareCount = 0;

        this.visualizerWindow = visualizerWindow;

        animationDelay = 250;

        this.numberCount = numberCount;

        randomNumbers = new ArrayList<Integer>();
        rectangles = new ArrayList<Rectangle2D>();

        for (int i = 0; i< numberCount; i++)
        {
            randomNumbers.add((int) (Math.floor(Math.random() * ( Math.floor(95) - Math.ceil(1))) + 5));
        }

        selected = new ArrayList<Integer>();
        sorted = new ArrayList<Integer>();
    }

    /**
     * Second constructor of the visualizationPanel used for the QuizWindow
     * @param numberCount count of numbers to be sorte
     * @param visualizerWindow VisualizerWindow in which the VisualizerPanel is embedded
     */
    public VisualizerPanel(int numberCount, VisualizerWindow visualizerWindow) {
        setBackground(Color.WHITE);
        setVisible(true);
        pause = false;
        reset = false;
        nextStep = false;

        this.visualizerWindow = visualizerWindow;

        currentVisualization = null;
        currentVisualizationName = "";

        variableIndicators = new ArrayList<Integer>();

        annotated1 = new ArrayList<Integer>();
        annotations1 = new ArrayList<Pair<Integer, String>>();

        annotated2 = new ArrayList<Integer>();
        annotations2 = new ArrayList<Pair<Integer, String>>();

        swapCount = 0;
        accessCount = 0;
        compareCount = 0;

        animationDelay = 250;



        this.numberCount = numberCount;

        randomNumbers = new ArrayList<Integer>();
        rectangles = new ArrayList<Rectangle2D>();

        for (int i = 0; i< numberCount; i++)
        {
            randomNumbers.add((int) (Math.floor(Math.random() * ( Math.floor(95) - Math.ceil(1))) + 5));
        }

        selected = new ArrayList<Integer>();
        sorted = new ArrayList<Integer>();
    }

    /**
     * Method responsible for drawing the visualization repeatedly
     * @param g Graphics object
     */
    protected void paintComponent(Graphics g)
    {

        ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("VisualizerWindow");

        g2 = (Graphics2D) g;
        super.paintComponent(g);

        if (annotationWindow1 != null)
            annotationWindow1.removeAllAnotations();

        if (annotationWindow2 != null)
            annotationWindow2.removeAllAnotations();

            Dimension dim = this.getSize();
            double width = (dim.getWidth() / (numberCount + (variables>0? variables + variableIndicators.size(): 0)) *1.0);

            for (int i = 0; i< numberCount + (variables>0? variables + variableIndicators.size(): 0); i++)
            {
                // display variable seperation
                if (variableIndicators.contains(i))
                {
                    Stroke orig1 = g2.getStroke();

                    Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                    g2.setStroke(dashed);

                    g.drawLine((int) (i * width + width / 8), 0, (int) (i*width + width / 4), 2000);

                    g2.setStroke(orig1);


                    Font orig2 = g2.getFont();
                    Font newFont = new Font(Font.DIALOG, Font.BOLD, 15);
                    g2.setFont(newFont);


                    AffineTransform orig3 = g2.getTransform();
                    AffineTransform at = new AffineTransform();
                    at.setToRotation(-Math.PI / 2);
                    g2.setTransform(at);


                    int margin=0;

                    switch(numberCount)
                    {
                        case 5: margin =  (int) (width *0.4); break;

                        case 10: margin = (int) (width *0.5); break;

                        case 15: margin = (int) (width *0.6); break;

                        case 20: margin = (int) (width *0.7); break;

                        case 25: margin = (int) (width *0.8); break;

                        case 30: margin = (int) (width * 0.9); break;
                    }

                    if (currentVisualization != null)
                    {
                       if(currentVisualizationName == "insertionSort" || currentVisualizationName == "quickSort")
                       {
                           g2.drawString(bundle.getString("variables"), (float) (- dim.getHeight()/2 - 65), (int) (i * width) + margin);
                       }
                       else if(variableIndicators.indexOf(i) == 0)
                       {
                           g2.drawString(bundle.getString("leftArray"), (float) (- dim.getHeight()/2 - 65), (int) (i * width) + margin);
                       }
                       else if(variableIndicators.indexOf(i) == 1)
                       {
                           g2.drawString(bundle.getString("rightArray"), (float) (- dim.getHeight()/2 - 65), (int) (i * width) + margin);
                       }
                    }
                    else
                    {
                        g2.drawString(bundle.getString("variables"), (float) (- dim.getHeight()/2 - 65), (int) (i * width) + margin);
                    }


                    g2.setFont(orig2);
                    g2.setTransform(orig3);
                }
                else
                {
                    g.setColor(Color.BLACK);


                    Rectangle2D rect = new Rectangle2D.Double(i * width, ((105 - randomNumbers.get(i)) / 105.0 * dim.getHeight()), width, ((randomNumbers.get(i) + 5) / 105.0) * dim.getHeight());
                    rectangles.add(rect);


                    if (sorted.contains(i)) {
                        g.setColor(visualizerWindow.getSortedColour());
                        g2.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
                        g.setColor(Color.BLACK);
                        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
                    }

                    if (selected.contains(i)) {
                        g.setColor(visualizerWindow.getSelectedColour());
                        g2.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
                        g.setColor(Color.BLACK);
                        g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());

                    }




                    if (annotated1.contains(i)) {
                        String annotationText = "";

                        for (Pair<Integer, String> annotation : annotations1) {
                            if (annotation.getKey().equals(i))
                                annotationText = annotation.getValue();
                        }


                        if (annotationWindow1 != null) {
                            annotationWindow1.addAnnotation((float) rect.getX(), (float) (rect.getX() + width), annotationText);
                            annotationWindow1.repaint();
                        }
                    }

                    if (annotated2.contains(i)) {
                        String annotationText = "";

                        for (Pair<Integer, String> annotation : annotations2) {
                            if (annotation.getKey().equals(i))
                                annotationText = annotation.getValue();
                        }


                        if (annotationWindow2 != null) {
                            annotationWindow2.addAnnotation((float) rect.getX(), (float) (rect.getX() + width), annotationText);
                            annotationWindow2.repaint();
                        }
                    }

                    drawCenteredString(g, "" + randomNumbers.get(i), new Rectangle((int) (i * width), 5, (int) width, 5), new Font(Font.DIALOG, 0, 12));

                    g2.draw(rect);
                }
            }
    }
    /**
     *   Draw a given String within a given rectangle with a given Font
     * @param g Graphics object
     * @param text text to be displayed
     * @param rect Rectangle in which the text is shown
     * @param font Font to be used fpr the text
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font)
    {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


    /**
     * Change the count of numbers to be sorted
     * @param newNumberCount new number count
     */
    public void changeNumberCount(int newNumberCount)
    {
        this.numberCount = newNumberCount;

        randomNumbers.clear();
        rectangles.clear();

        for (int i = 0; i< numberCount; i++)
        {
            randomNumbers.add((int) (Math.floor(Math.random() * ( Math.floor(95) - Math.ceil(1))) + 5));
        }

        repaint();
    }


    /**
     * Shuffle the numbers to be sorted
     */
    public void shuffle()
    {

        while(selected.size() > 0)
        {
            selected.remove(0);
        }

        while(sorted.size() > 0)
        {
            sorted.remove(0);
        }

        for (int i = 0; i< numberCount; i++)
        {
            randomNumbers.set(i, (int) (Math.floor(Math.random() * ( Math.floor(95) - Math.ceil(1))) + 5));
        }
        repaint();
    }


    /**
     * Visualize BubbleSort
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    public void bubbleSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        visualizerWindow.setCountAccesses(0);
        visualizerWindow.setCountComparisons(0);
        visualizerWindow.setCountSwaps(0);


        reset = false;

        Thread bubbleSortThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                for (int i = 1; i < randomNumbers.size(); i++)
                {

                        if (i == 1)
                        {
                            visualizerWindow.setCodeIndex1(0);

                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("Setze i auf 1");
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("Set i to 1");


                            checkPause();
                            sleep(animationDelay);
                            visualizerWindow.updateVariable("i", i);
                        }
                        else
                        {
                            visualizerWindow.setCodeIndex1(2);

                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("j hat die rechte Grenze erreicht.");
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("j has reached the right border");

                            checkPause();
                            sleep(animationDelay);

                            visualizerWindow.setCodeIndex1(0);
                            visualizerWindow.resetVariableValue(1);

                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("Erhöhe i um 1");
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("Increment i");



                            checkPause();
                            sleep(animationDelay);
                            visualizerWindow.updateVariable("i", i);
                        }



                        for (int j = 0; j < randomNumbers.size() - i; j += 1)
                        {
                                if (j == 0)
                                {
                                    visualizerWindow.setCodeIndex1(2);

                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                        visualizerWindow.setExplainField("Setze j auf 0");
                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                        visualizerWindow.setExplainField("Set j to 0");


                                    checkPause();
                                    sleep(animationDelay);

                                    visualizerWindow.updateVariable("j", j);
                                }
                                else
                                {
                                    visualizerWindow.setCodeIndex1(2);

                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                        visualizerWindow.setExplainField("Erhöhe j um 1");
                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                        visualizerWindow.setExplainField("Increment j");

                                    checkPause();
                                    sleep(animationDelay);

                                    visualizerWindow.updateVariable("j", j);
                                }

                                    sleep(animationDelay);


                                    annotated1.clear();
                                    annotations1.clear();

                                    annotated1.add(j);
                                    annotations1.add(new Pair(j,"j"));

                                annotated1.add(j+1);
                                annotations1.add(new Pair(j+1,"j+1"));


                                    selected.clear();
                                    selected.add(j);
                                    selected.add(j+1);
                                    repaint();

                                    visualizerWindow.setCodeIndex1(4);

                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                        visualizerWindow.setExplainField("Prüfe, ob " + randomNumbers.get(j) + " größer als " + randomNumbers.get(j+1) + " ist");
                                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                        visualizerWindow.setExplainField("Check if " + randomNumbers.get(j) + " is larger than " + randomNumbers.get(j+1));


                                    if (randomNumbers.get(j) > randomNumbers.get(j + 1))
                                    {
                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                            visualizerWindow.addTextToExplainField("Das ist hier der Fall");
                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                            visualizerWindow.addTextToExplainField("This is true");
                                    }
                                    else
                                    {
                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                            visualizerWindow.addTextToExplainField("Das ist hier nicht der Fall");
                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                            visualizerWindow.addTextToExplainField("This is false");
                                    }


                                    checkPause();
                                    sleep(animationDelay);


                                    compareCount++;
                                    accessCount+=2;
                                    visualizerWindow.setCountComparisons(compareCount);

                                    visualizerWindow.setCountAccesses(accessCount);

                                    if (randomNumbers.get(j) > randomNumbers.get(j + 1))
                                    {
                                        repaint();
                                        sleep(animationDelay);
                                        visualizerWindow.setCodeIndex1(5);

                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                            visualizerWindow.setExplainField("Also tausche " + randomNumbers.get(j) + " und " + randomNumbers.get(j + 1));
                                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                            visualizerWindow.setExplainField("Therefore swap " + randomNumbers.get(j) + " and " + randomNumbers.get(j + 1));

                                        checkPause();

                                        swap(j, j + 1);
                                        checkPause();

                                        repaint();
                                        sleep(animationDelay);
                                    }



                        }
                            sorted.add(randomNumbers.size() - i);
                            repaint();
                    }

                    annotated1.clear();
                    annotations1.clear();

                    selected.clear();

                    sorted.add(0);
                    sorted.add(1);

                    repaint();

                    visualizerWindow.getMainPanel().repaint();

                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Die Zahlen sind jetzt komplett sortiert");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("The numbers are entirely sorted now");


                    visualizerWindow.removeAllAnnotations();

                    sleep(animationDelay);

                    visualizerWindow.addRecord("BubbleSort", randomNumbers.size(), accessCount, compareCount, swapCount);
            }
        }
        );

        bubbleSortThread.start();

        currentVisualization = bubbleSortThread;

    }

    /**
     * Visualize SelectionSort
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    public void selectionSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        reset = false;
        resetComplete = false;


        Thread selectionSortThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!quiz)
                {
                    selected.clear();
                    annotated1.clear();
                    annotations1.clear();

                    visualizerWindow.setCountAccesses(0);
                    visualizerWindow.setCountComparisons(0);
                    visualizerWindow.setCountSwaps(0);
                }


                for (int i=0; i < randomNumbers.size() - 1; i++)
                {
                    selected.clear();

                        if (i == 0) {

                            if (!quiz)
                            {
                                visualizerWindow.setCodeIndex1(0);

                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.setExplainField("Setze i auf 0");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.setExplainField("Set i to 0");


                                annotated1.add(i);
                                annotations1.add(new Pair(i, "i"));
                            }

                            selected.add(i);

                            repaint();
                            checkPause();
                            sleep(animationDelay);


                            if (!quiz)
                                visualizerWindow.updateVariable("i", i);
                        }
                        else
                        {

                            if (!quiz)
                            {
                                visualizerWindow.setCodeIndex1(0);

                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.setExplainField("Erhöhe i um 1");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.setExplainField("Increment i");


                                annotated1.add(i);
                                annotations1.add(new Pair(i, "i"));
                            }

                            selected.add(i);

                            repaint();
                            checkPause();
                            sleep(animationDelay);


                            if (!quiz)
                                visualizerWindow.updateVariable("i", i);
                        }



                    if (!quiz)
                    {
                        visualizerWindow.setCodeIndex1(2);

                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Setze den Index des Minimums auf i = " + i);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Set the index of the minimum to i = " + i);
                    }


                    checkPause();
                    sleep(animationDelay);

                    int minIndex = i;

                    if (!quiz)
                        visualizerWindow.updateVariable("minIndex", i);



                    selected.add(minIndex);
                    selected.add(minIndex);
                    annotated1.add(minIndex);

                    if (!quiz)
                        annotations1.add(new Pair(minIndex, "i=min"));


                    sleep(animationDelay);
                    repaint();



                    if (!quiz)
                    {
                        visualizerWindow.setCodeIndex1(3);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Setze j auf i+1 = " + (i+1));
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Set j to i+1 = " + (i+1));
                    }


                    for (int j = i+1; j < randomNumbers.size(); j++)
                    {

                        if (i != minIndex && !quiz)
                        {
                            annotated1.add(i);
                            annotations1.add(new Pair(i, "i"));
                        }

                        if (!quiz)
                        {
                            visualizerWindow.setCodeIndex1(3);
                            if (j > i + 1)
                            {
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.setExplainField("Erhöhe j um 1");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.setExplainField("Increment j");
                            }

                            visualizerWindow.updateVariable("j", j);


                            annotated1.add(j);
                            annotations1.add(new Pair(j, "j"));
                        }



                        checkPause();
                        sleep(animationDelay);

                        selected.add(j);
                        sleep(animationDelay);
                        repaint();


                        if (!quiz)
                        {
                            visualizerWindow.setCodeIndex1(5);

                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("Prüfe, ob " + randomNumbers.get(j) + " kleiner als das aktuelle Minimum " + randomNumbers.get(minIndex) + " ist");
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("Check if " + randomNumbers.get(j) + " is smaller than the recent minimum " + randomNumbers.get(minIndex));


                            if (randomNumbers.get(j) < randomNumbers.get(minIndex))
                            {
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.addTextToExplainField("Das ist hier der Fall");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.addTextToExplainField("This is true");
                            }
                            else
                            {
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.addTextToExplainField("Das ist hier nicht der Fall");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.addTextToExplainField("This is false");
                        }

                        }

                        checkPause();
                        sleep(animationDelay);


                        compareCount++;
                        accessCount+=2;


                        if (!quiz)
                        {
                            visualizerWindow.setCountComparisons(compareCount);
                            visualizerWindow.setCountAccesses(accessCount);
                        }

                        if (randomNumbers.get(j) < randomNumbers.get(minIndex))
                        {
                            if(selected.size() >= 1)
                                selected.remove(selected.size()-1);

                            if(selected.size() >= 1)
                                selected.remove(selected.size()-1);


                            if (!quiz)
                            {
                                visualizerWindow.setCodeIndex1(6);

                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                    visualizerWindow.setExplainField("Also setze " + j + " als neuer Index des Minimums");
                                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                    visualizerWindow.setExplainField("Therefore et " + j + " as new index of the minimum");
                            }

                            minIndex = j;

                            checkPause();
                            sleep(animationDelay);

                            if (!quiz)
                            {
                                visualizerWindow.updateVariable("minIndex", j);

                                annotated1.clear();
                                annotations1.clear();

                                annotated1.add(minIndex);
                                annotations1.add(new Pair(minIndex, "min"));
                                annotated1.add(i);
                                annotations1.add(new Pair(i, "i"));
                            }

                            selected.add(minIndex);
                            sleep(animationDelay);

                            repaint();

                            if (!quiz)
                                annotationWindow1.repaint();
                        }
                        else
                        {
                            if(selected.size() >= 1)
                                selected.remove(selected.size()-1);
                        }

                        if (!quiz)
                        {
                            if (annotated1.size() > 0)
                                annotated1.remove(annotated1.size()-1);
                        }

                    }


                    if (!quiz) {
                        visualizerWindow.setCodeIndex1(3);

                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Alle Zahlen sind geprüft worden");
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("All numbers have been checked");

                    }


                    checkPause();
                    sleep(animationDelay);

                    if (!quiz)
                    {
                        visualizerWindow.resetVariableValue(1);
                    }


                    selected.clear();
                    selected.add(i);
                    selected.add(minIndex);
                    sleep(animationDelay);
                    repaint();


                    if (!quiz)
                    {
                        visualizerWindow.setCodeIndex1(8);

                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Tausche das Minimum " + randomNumbers.get(minIndex) + " mit der Zahl am Index i: " + randomNumbers.get(i));
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Swap the the minimum " + randomNumbers.get(minIndex) + " and the number at the index i: " + randomNumbers.get(i));
                    }

                    checkPause();
                    sleep(animationDelay);

                    swap(i, minIndex);


                    sorted.add(i);


                    if (!quiz)
                    {
                        visualizerWindow.resetVariableValue(2);
                    }

                    if (selected.size() >= 1)
                        selected.remove(selected.size()-1);

                    if (!quiz)
                        annotations1.clear();

                    sleep(animationDelay);
                    repaint();

                }

                sleep(animationDelay);
                repaint();

                selected.clear();

                    sorted.add(randomNumbers.size()-1);
                    repaint();

                    if (!quiz) {
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Die Zahlen sind jetzt komplett sortiert");
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("The numbers are entirely sorted now");

                        visualizerWindow.addRecord("SelectionSort", randomNumbers.size(), accessCount, compareCount, swapCount);
                    }

            }
        });

        selectionSortThread.start();

        currentVisualization = selectionSortThread;
    }

    /**
     * Visualize InsertionSort
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    public void insertionSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        reset = false;
        resetComplete = false;



        selected.clear();
        annotated1.clear();
        annotations1.clear();

        Thread insertionSortThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!quiz)
                {
                    selected.clear();
                    annotated1.clear();
                    annotations1.clear();

                    visualizerWindow.setCountAccesses(0);
                    visualizerWindow.setCountComparisons(0);
                    visualizerWindow.setCountSwaps(0);
                }


                sorted.add(0);
                sleep(animationDelay);


                visualizerWindow.setCodeIndex1(0);
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Setze i auf 1. Die erste Zahl wird als sortiert angenommen");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Set i to 1. The first number is perceived as ordered");

                checkPause();
                repaint();

                if (!quiz)
                {
                    visualizerWindow.resetVariableValue(1);
                    visualizerWindow.resetVariableValue(2);
                    visualizerWindow.updateVariable("i", 1);
                }

                for (int i = 1; i < randomNumbers.size() - 2; i++)
                {

                    if (i > 1)
                    {
                        visualizerWindow.setCodeIndex1(0);

                        annotated1.clear();
                        annotations1.clear();

                        selected.clear();
                        selected.add(i);
                        annotated1.add(i);
                        annotations1.add(new Pair(i,"i"));

                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Erhöhe i um 1");
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Increment i");
                    }

                    sleep(animationDelay);
                    repaint();
                    checkPause();

                    if(!quiz)
                    {
                        visualizerWindow.resetVariableValue(1);
                        visualizerWindow.resetVariableValue(2);
                        visualizerWindow.updateVariable("i", i);
                    }


                    selected.clear();

                    selected.add(i);




                    annotated1.clear();
                    annotations1.clear();


                    annotated1.add(i);
                    annotations1.add(new Pair(i,"i"));

                    visualizerWindow.setCodeIndex1(2);
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Speichere " + randomNumbers.get(i) + " als Zahl, die links einsortiert werden soll, unter der Variable key");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("Save " + randomNumbers.get(i) + " as number to be inserted left as the variable key");


                    repaint();
                    checkPause();


                    int key = randomNumbers.get(i);

                    visualizerWindow.updateVariable("key", randomNumbers.get(i));

                    accessCount++;
                    if (!quiz)
                        visualizerWindow.setCountAccesses(accessCount);


                    //
                    if (randomNumbers.size() == numberCount)
                    {
                        randomNumbers.add(numberCount, 0);
                        randomNumbers.add(numberCount+1, key);

                        variableIndicators.add(numberCount);

                        variables = 1;
                    }
                    else
                    {
                        randomNumbers.set(numberCount, 0);
                        randomNumbers.set(numberCount+1, key);
                    }

                    selected.add(numberCount+1);

                    annotated1.add(numberCount+1);
                    annotations1.add(new Pair(numberCount+1, "key"));


                    repaint();
                    checkPause();

                    repaint();
                    sleep(animationDelay);



                    visualizerWindow.setCodeIndex1(3);
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Setze j als den Index links neben i");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("Set j to the index left to i");

                    int j = i-1;

                    if(!quiz)
                    visualizerWindow.updateVariable("j", i-1);

                    annotated1.add(j);
                    annotations1.add(new Pair(j,"j"));



                    selected.clear();

                    selected.add(numberCount+1);

                    selected.add(j);

                    checkPause();
                    repaint();

                    repaint();

                    sleep(animationDelay);


                    visualizerWindow.setCodeIndex1(4);


                    if (randomNumbers.get(j) <= key)
                    {
                        visualizerWindow.setCodeIndex1(4);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Die einzufügende Zahl ist größer oder gleich " + randomNumbers.get(j));
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("The number to be inserted is larger or equal to " + randomNumbers.get(j));

                        repaint();
                        sleep(animationDelay);
                        checkPause();
                    }


                    if (j >= 0 && randomNumbers.get(j) > key)
                    {
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("j ist größer als 0 und " + randomNumbers.get(j) + " ist größer als die einzufügende Zahl " + key);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("j is larger than 0 and " + randomNumbers.get(j) + " is larger than the number to be inserted " + key);

                    }



                    checkPause();
                    repaint();



                    accessCount++;
                    compareCount++;

                    if (!quiz)
                    {
                        visualizerWindow.setCountAccesses(accessCount);
                        visualizerWindow.setCountComparisons(compareCount);
                    }


                    while (j >= 0 && randomNumbers.get(j) > key)
                    {

                           if (j < i-1)
                           {
                               accessCount++;
                               compareCount++;

                               if (!quiz)
                               {
                                   visualizerWindow.setCountAccesses(accessCount);
                                   visualizerWindow.setCountComparisons(compareCount);
                               }
                           }



                            visualizerWindow.setCodeIndex1(4);

                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("j ist größer gleich 0 und " + randomNumbers.get(j) + " ist größer als die einzufügende Zahl " + key);
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("j is larger than or equal to 0 and " + randomNumbers.get(j) + " is larger than the number to be inserted " + key);

                            selected.clear();
                            selected.add(numberCount+1);



                            selected.add(j);
                            selected.add(j+1);
                            sleep(animationDelay);
                            repaint();



                            annotated1.clear();
                            annotations1.clear();


                            annotated1.add(numberCount+1);
                            annotations1.add(new Pair(numberCount+1, "key"));

                            annotated1.add(j);
                            annotations1.add(new Pair(j, "j"));
                            annotated1.add(j+1);
                            annotations1.add(new Pair(j+1, "j+1"));

                             repaint();

                        if (j < i-1)
                            checkPause();



                        visualizerWindow.setCodeIndex1(6);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Kopiere " + randomNumbers.get(j) + " auf die Position rechts daneben");
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Copy " + randomNumbers.get(j) + " to the position right next to it");

                        sleep(animationDelay);
                        checkPause();
                        repaint();


                        randomNumbers.set(j+1, randomNumbers.get(j));


                        accessCount+=2;

                        if (!quiz)
                            visualizerWindow.setCountAccesses(accessCount);


                        visualizerWindow.setCodeIndex1(7);
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            visualizerWindow.setExplainField("Reduziere j um 1");
                        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            visualizerWindow.setExplainField("Decrement j");

                        repaint();
                        sleep(animationDelay);
                        checkPause();



                        j = j - 1;

                        visualizerWindow.updateVariable("j", j);

                        annotated1.clear();
                        annotations1.clear();


                        annotated1.add(numberCount+1);
                        annotations1.add(new Pair(numberCount+1, "key"));

                        annotated1.add(j);
                        annotations1.add(new Pair(j, "j"));
                        annotated1.add(j+1);
                        annotations1.add(new Pair(j+1, "j+1"));


                        selected.clear();

                        selected.add(numberCount+1);


                        selected.add(j);
                        selected.add(j+1);

                        sorted.add(j+2);

                        sleep(animationDelay);
                        repaint();


                        repaint();




                        if (j < 0)
                        {
                            visualizerWindow.setCodeIndex1(4);
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("j ist kleiner als 0. Also beende die Schleife");
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("j is smaller than 0. Therefore end the loop");

                            repaint();
                            sleep(animationDelay);
                            checkPause();

                        }


                        else if (randomNumbers.get(j) <= key)
                        {
                            visualizerWindow.setCodeIndex1(4);
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                                visualizerWindow.setExplainField("Die einzufügende Zahl ist größer oder gleich " + randomNumbers.get(j));
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                                visualizerWindow.setExplainField("The number to be inserted is larger or equal to " + randomNumbers.get(j));

                            repaint();
                            sleep(animationDelay);
                            checkPause();

                        }
                    }



                    visualizerWindow.setCodeIndex1(9);
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Setze die einzufügende Zahl " + key + " an die Stelle j+1");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("Place the number to be inserted " + key + " to the index j+1");

                    repaint();
                    sleep(animationDelay);
                    checkPause();

                    randomNumbers.set(j+1, key);

                    accessCount++;

                    if (!quiz)
                        visualizerWindow.setCountAccesses(accessCount);


                    sleep(animationDelay);

                    for(int k=0; k<= i; k++)
                        sorted.add(k);




                    randomNumbers.set(numberCount+1,0);


                    repaint();
                    sleep(animationDelay);
                    checkPause();


                    repaint();
                }

                    variables=0;
                    randomNumbers.remove(numberCount);
                    randomNumbers.remove(numberCount);

                    repaint();

                    selected.clear();
                    annotated1.clear();
                    annotations1.clear();

                    for (int i=0; i<randomNumbers.size();i++)
                    {
                        sorted.add(i);
                    }


                    visualizerWindow.addRecord("InsertionSort", randomNumbers.size(), accessCount, compareCount, swapCount);
            }
        });

        currentVisualization = insertionSortThread;
        currentVisualizationName = "insertionSort";

        insertionSortThread.start();

    }

    /**
     * Visualize MergeSort
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    public void mergeSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        reset = false;
        resetComplete = false;



        selected.clear();
        annotated1.clear();
        annotations1.clear();

        Thread mergeSortThread = new Thread(new Runnable()
        {
            public void run()
            {
                mergeSortFunction(0, randomNumbers.size()-1, quiz);


                visualizerWindow.addRecord("MergeSort", randomNumbers.size(), accessCount, compareCount, swapCount);

                repaint();

            }
        });


        currentVisualization = mergeSortThread;
        currentVisualizationName = "mergeSort";

        mergeSortThread.start();
    }


    /**
     * Actual function for executing MergeSort algorithm
     * @param l left border
     * @param r right border
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    void mergeSortFunction(int l, int r, boolean quiz)
    {
        visualizerWindow.setCodeIndex1(0);

        if (l == 0 && r == randomNumbers.size()-1)
        {

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe zu Beginn die Methode sort mit den äußeren Grenezen, also low=0 und high=" + (randomNumbers.size()-1) + " auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Initially call the method sort with the outer borders low=0 und high=" + (randomNumbers.size()-1));

            checkPause();
            sleep(1000);
            repaint();
        }
        else
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe Methode sort mit l=" + l + " und r=" + r + " auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the method sort with l=" + l + " and r=" + r);

            checkPause();
            sleep(animationDelay);
            repaint();
        }

        visualizerWindow.updateVariable("l",l);
        visualizerWindow.updateVariable("r",r);




        visualizerWindow.setCodeIndex1(3);



        annotated1.clear();
        annotations1.clear();
        annotated2.clear();
        annotations2.clear();

        annotated2.add(l);
        annotations2.add(new Pair<>(l,"l"));
        annotated2.add(r);

        if (l == r)
        {
            annotations2.add(new Pair<>(r, "l=r"));

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Die linke Grenze entspricht der rechten Grenze. Das bedeutet, dass aktuell nur eine einzige Zahl betrachet wird. Eine einzelne Zahl ist per Definition sortiert");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("The left border is qual to the right border. That means that only one number is currently observed. A single number is ordered per definition");



            checkPause();
            sleep(animationDelay);
            repaint();

            if(! quiz)
            annotationWindow2.repaint();
        }
        else
        {
            annotations2.add(new Pair<>(r,"r"));

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Die linke Grenze ist kleiner als die rechte Grenze");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("The left border is smaller than the right border");


        }


        if (l < r)
        {
            checkPause();
            sleep(animationDelay);
            repaint();


            visualizerWindow.setCodeIndex1(5);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Berechne m als Mitte der aktuellen linken und rechten Grenze. Diese Mitte wird eingesetzt, um die Zahlen (weiter) zu unterteilen");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Calculate m as the middle of the left and right border. This middle is used to (further) subdivide the numbers");

            // Find the middle point
            int m = (l+r)/2;

            visualizerWindow.updateVariable("m",m);

            annotated2.add(m);

            if (m == l)
                annotations2.add(new Pair<>(m,"l=m"));
            else
                annotations2.add(new Pair<>(m,"m"));

            checkPause();
            sleep(animationDelay);
            repaint();

            visualizerWindow.setCodeIndex1(6);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe die Methode sort rekrusiv auf dem linken Teil der Zahlen auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the method sort recursively on the left part of the numbers");

            if(annotationWindow2 != null)
                annotationWindow2.repaint();


            checkPause();
            sleep(animationDelay);
            repaint();


            // Sort first and second halves
            mergeSortFunction(l, m, quiz);

            visualizerWindow.updateVariable("l",l);
            visualizerWindow.updateVariable("r",r);
            visualizerWindow.updateVariable("m",m);

            visualizerWindow.setCodeIndex2(-1);



            annotated1.clear();
            annotations1.clear();
            annotated2.clear();
            annotations2.clear();

            annotated2.add(l);
            annotations2.add(new Pair<>(l,"l"));
            annotated2.add(r);
            annotations2.add(new Pair<>(r,"r"));
            annotated2.add(m);

            if (l == m)
                annotations2.add(new Pair<>(m,"l=m"));
            else
                annotations2.add(new Pair<>(m,"m"));

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe die Methode sort rekrusiv auf dem rechten Teil der Zahlen auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the method sort recursively on the right part of the numbers");

            visualizerWindow.setCodeIndex1(7);


            annotated2.add(m+1);

            if (m+1 == r)
                annotations2.add(new Pair<>(r,"r=m+1"));
            else
                annotations2.add(new Pair<>(m+1,"m+1"));


            if(!quiz)
            annotationWindow2.repaint();

            checkPause();
            sleep(animationDelay);
            repaint();




            mergeSortFunction(m+1, r, quiz);

            visualizerWindow.updateVariable("l",l);
            visualizerWindow.updateVariable("r",r);
            visualizerWindow.updateVariable("m",m);

            visualizerWindow.setCodeIndex2(-1);

            annotated1.clear();
            annotations1.clear();
            annotated2.clear();
            annotations2.clear();

            annotated2.add(l);
            annotations2.add(new Pair<>(l,"l"));
            annotated2.add(r);
            annotations2.add(new Pair<>(r,"r"));
            annotated2.add(m);

            visualizerWindow.setCodeIndex1(8);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Sortiere die Zahlen innerhalb der aktuellen Grenzen. Rufe dafür die Methode merge mit den Parametern l= " + l + ", m=" + m + " und r=" + r + " auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Sort the numbers within the current borders. Therefore call the method with the parameters l= " + l + ", m=" + m + " and r=" + r);





            visualizerWindow.setCodeIndex2(0);

            if (l == m)
                annotations2.add(new Pair<>(m,"l=m"));
            else
                annotations2.add(new Pair<>(m,"m"));

            // Merge the sorted halves

            merge(l, m, r);


            if (l == 0 && r == randomNumbers.size()-1)
            {
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Die Zahlen sind jetzt komplett sortiert");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("The numbers are now entirely ordered");

                for(int i=0; i<numberCount;i++)
                {
                    sorted.add(i);
                }

                sleep(animationDelay);
                repaint();
            }

            annotated1.clear();
            annotations1.clear();

            if(! quiz)
            annotationWindow1.repaint();

            visualizerWindow.setCodeIndex2(0);
        }
    }


    /**
     * Merge part of the numbers
     * @param l left border
     * @param r right border
     * @param m mitddle element
     */
    void merge(int l, int m, int r)
    {
        checkPause();
        sleep(animationDelay);
        repaint();
        // Find sizes of two subarrays to be merged

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Berechne die Größe der linken Zahlenmenge, die zusammen mit der rechten Zahlenmenge sortiert wird");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Calculate the size of the left set of numbers that is sorted together with the right set of numbers");

        visualizerWindow.setCodeIndex2(2);



        int n1 = m - l + 1;

        visualizerWindow.updateVariable("n1",n1);

        checkPause();
        sleep(animationDelay);
        repaint();


        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Berechne die Größe der rechten Zahlenmenge, die zusammen mit der linken Zahlenmenge sortiert wird");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Calculate the size of the right set of numbers that is sorted together with the left set of numbers");


        visualizerWindow.setCodeIndex2(3);

        int n2 = r - m;

        visualizerWindow.updateVariable("n2",n2);

        checkPause();
        sleep(animationDelay);
        repaint();

        /* Create temp arrays */

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Initialisiere den Behälter für die linke Zahlenmenge");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Initialize the array for the left set of numbers");




        visualizerWindow.setCodeIndex2(4);




        ArrayList<Integer> L = new ArrayList<Integer>(n1);

        checkPause();
        sleep(animationDelay);
        repaint();




        randomNumbers.add(numberCount,0);
        variables++;
        variableIndicators.add(numberCount);
        randomNumbers.add(numberCount+1,0);


        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Initialisiere den Behälter für die rechte Zahlenmenge");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Initialize the array for the right set of numbers");


        visualizerWindow.setCodeIndex2(5);

        ArrayList<Integer> R = new ArrayList<Integer>(n1);

        checkPause();
        sleep(animationDelay);
        repaint();

        randomNumbers.add(numberCount+2,0);
        variables++;
        variableIndicators.add(numberCount+2);
        randomNumbers.add(numberCount+3,0);



        visualizerWindow.setCodeIndex2(6);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Kopiere die linke Zahlenmenge in den linken Behälter");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Copy the left set of numbers into the left array");

        checkPause();
        sleep(animationDelay);
        repaint();

        // to visualize the two subsequent subsets of numbers
    //    randomNumbers.add(numberCount,0);
    //    variableIndicators.add(numberCount);


        /*Copy data to temp arrays*/
        for (int index1=0; index1<n1; index1++)
        {
            visualizerWindow.setCodeIndex2(7);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Füge die Zahl " + randomNumbers.get(l + index1) + " in den linken Behälter ein");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Insert the number " + randomNumbers.get(l + index1) + " into the left array");

            L.add(index1, randomNumbers.get(l + index1));


            accessCount ++;
            visualizerWindow.setCountAccesses(accessCount);
            visualizerWindow.updateVariable("index1",index1);

            if (index1 != 0)
                variables++;



            randomNumbers.set(numberCount+1+index1, randomNumbers.get(l + index1));






            variableIndicators.remove(1);
            variableIndicators.add(numberCount+1+index1+1);

            if(randomNumbers.size() > numberCount+1+index1)
            {
                randomNumbers.set(numberCount+1+index1+1,0);
            }
            else
            {
                randomNumbers.add(numberCount+1+index1+1,0);
            }


            if(randomNumbers.size() > numberCount+1+index1+1+1)
            {
                randomNumbers.set(numberCount+1+index1+1+1,0);
            }
            else
            {
                randomNumbers.add(numberCount+1+index1+1+1,0);
            }


     //       randomNumbers.add(numberCount+1+index1+1+2,0);

            checkPause();
            sleep(animationDelay);
            repaint();

            if(index1+1<n1)
            {
                visualizerWindow.setCodeIndex2(6);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("index1 ist noch kleiner als die Größe der linken Zahlenmenge. Kopiere deswegen auch die nächste Zahl");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("index1 is still smaller than the size of the left set of numbers. Therefore copy the next number as well");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
            else
            {
                visualizerWindow.setCodeIndex2(6);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("index1 ist jetzt genauso groß wie die Größe der linken Zahlenmenge. Es wurden folglich alle Zahlen kopiert");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("index1 is now equal to the size of the left set of numbers. Therefore all numbers have been copied");
            }

        }

        checkPause();
        sleep(animationDelay);
        repaint();

        visualizerWindow.setCodeIndex2(8);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Kopiere die rechte Zahlenmenge in den rechten Behälter");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Copy the right set of numbers into the right array");

        checkPause();
        sleep(animationDelay);
        repaint();

        // to draw the second line
    //    randomNumbers.add(numberCount+n1+1,0);
    //    variableIndicators.add(numberCount+n1+1);

        for (int index2=0; index2<n2; index2++)
        {
            visualizerWindow.setCodeIndex2(9);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Füge die Zahl " + randomNumbers.get(m + 1 + index2) + " in den linken Behälter ein");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Insert the number " + randomNumbers.get(m + 1 + index2) + " into the left array");


            R.add(index2, randomNumbers.get(m + 1 + index2));

            accessCount ++;
            visualizerWindow.setCountAccesses(accessCount);
            visualizerWindow.updateVariable("index2",index2);

            if(randomNumbers.size() > numberCount+n1+2+index2)
            {
                randomNumbers.set(numberCount+n1+2+index2, randomNumbers.get(m + 1 + index2));
            }
            else
            {
                randomNumbers.add(numberCount+n1+2+index2, randomNumbers.get(m + 1 + index2));
            }



            if(index2 != 0)
                variables++;

            checkPause();
            sleep(animationDelay);
            repaint();

            if(index2+1<n2)
            {
                visualizerWindow.setCodeIndex2(8);


                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("index2 ist noch kleiner als die Größe der rechten Zahlenmenge. Kopiere deswegen auch die nächste Zahl");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("index2 is still smaller than the size of the right set of numbers. Therefore copy the next number as well");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
            else
            {
                visualizerWindow.setCodeIndex2(8);


                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("index2 ist jetzt genauso groß wie die Größe der rechten Zahlenmenge. Es wurden folglich alle Zahlen kopiert");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("index2 is now equal to the size of the right set of numbers. Therefore all numbers have been copied");
            }
        }



        checkPause();
        sleep(animationDelay);
        repaint();
        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays

        visualizerWindow.setCodeIndex2(10);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Initiere i als Laufindex des Behälters der linken Zahlenmenge");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Ínitialize i as the running index of the array of the left set of numbers");

        checkPause();
        sleep(animationDelay);
        repaint();

        int i = 0;

        visualizerWindow.updateVariable("i",i);


        selected.add(numberCount+1);
        annotated1.add(numberCount+1);
        annotations1.add(new Pair<Integer, String>(numberCount+1,"i"));

        checkPause();
        sleep(animationDelay);
        repaint();




        visualizerWindow.setCodeIndex2(11);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Initiere j als Laufindex des Behälters der rechten Zahlenmenge");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Ínitialize j as the running index of the array of the right set of numbers");

        checkPause();
        sleep(animationDelay);
        repaint();


        int j = 0;

        visualizerWindow.updateVariable("j",j);

        selected.add(numberCount+n1+2);
        annotated1.add(numberCount+n1+2);
        annotations1.add(new Pair<Integer, String>(numberCount+n1+2,"j"));

        checkPause();
        sleep(animationDelay);
        repaint();

        visualizerWindow.setCodeIndex2(12);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Initiere k als Anfangsindex der aktuellen durch die Grenzen l und r definierten Zahlenmenge. Dieser Index wird genutzt, um die Zahlen der linken und rechten Teilmenge nun in der richtigen Reihenfolge einzufügen");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Initialize k as the intial index of the current set of numbers defined by the borders l and r. This index is used to insert the numbers of the left and right set of numbers in the correct order");



        checkPause();
        sleep(animationDelay);
        repaint();

        // Initial index of merged subarray
        int k = l;

        visualizerWindow.updateVariable("k",k);

        selected.add(k);
        annotated1.add(k);
        annotations1.add(new Pair<Integer, String>(k,"k"));


        checkPause();
        sleep(animationDelay);
        repaint();

        visualizerWindow.setCodeIndex2(13);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Füge solange Zahlen der beiden Zahlenmengen-Behälter in der richtigen Reihenfolge ein, bis entweder alle Zahlen der linken oder alle Zahlen der rechten Zahlenmengen-Behälter vollständig eingefügt worden sind");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Insert numbers from both arrays of set of numbers in the correct order as long as either all numbers of the left or all numbers of the right array of set of numbers are entirely inserted");





        while (i < n1 && j < n2)
        {
            checkPause();
            sleep(animationDelay);
            repaint();


            compareCount++;
            visualizerWindow.setCountComparisons(compareCount);

            if (L.get(i) <= R.get(j))
            {

                if(i != 0 || j != 0)
                {
                    visualizerWindow.setCodeIndex2(13);

                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Keine der beiden Behälter der Zahlenmengen wurde bislang vollständig durchlaufen. Setze die Schleife also fort");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("No array of set of numbers has been entirely passed through. STherefore continue the loop");



                    checkPause();
                    sleep(animationDelay);
                    repaint();

                }


                visualizerWindow.setCodeIndex2(15);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Die aktuell ausgewählte Zahl des Behälters der linken Zahlenmenge (" + L.get(i) + ") ist kleiner als oder genauso groß wie die aktuell ausgewählte Zahl des Behälters der rechten Zahlenmenge (" + R.get(j) + ").");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("The currently selected number of the array of the left set of numbers (" + L.get(i) + ") is smaller or equal to the currently selected number of the array of the right set of numbers (" + R.get(j) + ").");




                checkPause();
                sleep(animationDelay);
                repaint();

                visualizerWindow.setCodeIndex2(17);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Füge also " + L.get(i) + " an die Position des aktuellen Laufindex der gesamten Zahlenmenge ein");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("There insert " + L.get(i) + " into the position of the current running index of the entire set of numbers");



                checkPause();
                sleep(animationDelay);
                repaint();

                randomNumbers.set(k, L.get(i));

                accessCount++;
                visualizerWindow.setCountAccesses(accessCount);



                visualizerWindow.setCodeIndex2(18);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Erhöhe den Laufindex des Behälters der linken Zahlenmenge um 1");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Increment the running index of the array of the left set of numbers");


                checkPause();
                sleep(animationDelay);
                repaint();

                i++;

                visualizerWindow.updateVariable("i",i);

                selected.clear();
                annotated1.clear();
                annotations1.clear();


                selected.add(k);
                selected.add(numberCount+1+i);
                selected.add(numberCount+n1+2+j);
                annotated1.add(k);
                annotated1.add(numberCount+1+i);
                annotated1.add(numberCount+n1+2+j);
                annotations1.add(new Pair<>(k,"k"));
                annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
                annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));



                sleep(animationDelay);
                repaint();



            }
            else
            {
                if(i != 0 || j != 0)
                {
                    visualizerWindow.setCodeIndex2(13);


                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Keine der beiden Behälter der Zahlenmengen wurde bislang vollständig durchlaufen. Setze die Schleife also fort");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("No array of set of numbers has been entirely passed through. STherefore continue the loop");

                    checkPause();
                    sleep(animationDelay);
                    repaint();

                }


                visualizerWindow.setCodeIndex2(15);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Die aktuell ausgewählte Zahl des Behälters der linken Zahlenmenge (" + L.get(i) + ") ist größer als die aktuell ausgewählte Zahl des Behälters der rechten Zahlenmenge (" + R.get(j) + ").");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("The currently selected number of the array of the left set of numbers (" + L.get(i) + ") is larger than the currently selected number of the array of the right set of numbers (" + R.get(j) + ").");


                checkPause();
                sleep(animationDelay);
                repaint();

                visualizerWindow.setCodeIndex2(22);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Füge also " +  R.get(j) + " an die Position des aktuellen Laufindex der gesamten Zahlenmenge ein");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("There insert " +  R.get(j) + " into the position of the current running index of the entire set of numbers");


                checkPause();
                sleep(animationDelay);
                repaint();


                randomNumbers.set(k, R.get(j));

                accessCount++;
                visualizerWindow.setCountAccesses(accessCount);


                visualizerWindow.setCodeIndex2(23);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Erhöhe den Laufindex des Behälters der rechten Zahlenmenge um 1");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Increment the running index of the array of the right set of numbers");


                checkPause();
                sleep(animationDelay);
                repaint();


                j++;

                visualizerWindow.updateVariable("j",j);

                selected.clear();
                annotated1.clear();
                annotations1.clear();


                selected.add(k);
                selected.add(numberCount+1+i);
                selected.add(numberCount+n1+2+j);
                annotated1.add(k);
                annotated1.add(numberCount+1+i);
                annotated1.add(numberCount+n1+2+j);
                annotations1.add(new Pair<>(k,"k"));
                annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
                annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));



                sleep(animationDelay);
                repaint();
            }


            visualizerWindow.setCodeIndex2(25);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Erhöhe den Laufindex der gesamten Zahlenmenge um 1");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Increment the running index of entire set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();


            k++;

            visualizerWindow.updateVariable("k",k);


            selected.clear();
            annotated1.clear();
            annotations1.clear();


            selected.add(k);
            selected.add(numberCount+1+i);
            selected.add(numberCount+n1+2+j);
            annotated1.add(k);
            annotated1.add(numberCount+1+i);
            annotated1.add(numberCount+n1+2+j);
            annotations1.add(new Pair<>(k,"k"));
            annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
            annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));



            sleep(animationDelay);
            repaint();

        }

        /* Copy remaining elements of L[] if any */
        checkPause();
        sleep(animationDelay);
        repaint();


        if(i >= n1)
        {
            visualizerWindow.setCodeIndex2(13);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Der Laufindex des Behälters der linken Zahlenmenge ist vollständig durchgelaufen, also wurden alle Zahlen des Behälters der linken Zahlenmenge eingefügt. Beende deswegen die Schleife");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("The running index of the array of the left set of numbers has run through entirely, therefore all numbers of the array of the left set of numbers have been inserted. Therefore end the loop");


            checkPause();
            sleep(animationDelay);
            repaint();

        }
        else if( j >= n2)
        {
            visualizerWindow.setCodeIndex2(13);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Der Laufindex des Behälters der rechten Zahlenmenge ist vollständig durchgelaufen, also wurden alle Zahlen des Behälters der rechten Zahlenmenge eingefügt. Beende deswegen die Schleife");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("The running index of the array of the right set of numbers has run through entirely, therefore all numbers of the array of the right set of numbers have been inserted. Therefore end the loop");


            checkPause();
            sleep(animationDelay);
            repaint();

        }




        visualizerWindow.setCodeIndex2(27);

        if (i >= n1)
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Alle Zahlen des Behälters der linken Zahlenmenge wurden schon eingeordnet. Diese Schleife wird also nicht ausgeführt");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("All numbers of the array of the left set of numbers have been inserted. Therefore the loop is not executed");

        }
        else if(i < n1)
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Es wurden noch nicht alle Zahlen des Behälters der linken Zahlenmenge eingeordnet. Diese Schleife wird deswegen ausgeführt");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Not all numbers of the array of the left set of numbers have been inserted. Therefore the loop is executed");
        }


        checkPause();
        sleep(animationDelay);
        repaint();




        while (i < n1)
        {
            visualizerWindow.setCodeIndex2(29);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Füge die Zahl " + L.get(i) + " an die Position des aktuellen Laufindex der gesamten Zahlenmenge ein");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Insert the number " + L.get(i) + " into the position of the current running index of the entire set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();

            randomNumbers.set(k, L.get(i));

            accessCount++;
            visualizerWindow.setCountAccesses(accessCount);



            visualizerWindow.setCodeIndex2(30);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Erhöhe den Laufindex des Behälters der linken Zahlenmenge um 1");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Increment the running index of the array of the left set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();


            i++;

            visualizerWindow.updateVariable("i",i);

            selected.clear();
            annotated1.clear();
            annotations1.clear();


            selected.add(k);
            selected.add(numberCount+1+i);
            selected.add(numberCount+n1+2+j);
            annotated1.add(k);
            annotated1.add(numberCount+1+i);
            annotated1.add(numberCount+n1+2+j);
            annotations1.add(new Pair<>(k,"k"));
            annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
            annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));

            checkPause();
            sleep(animationDelay);
            repaint();


            visualizerWindow.setCodeIndex2(30);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Erhöhe den Laufindex gesamten Zahlenmenge um 1");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Increment the running index of the entire set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();

            k++;

            visualizerWindow.updateVariable("k",k);

            selected.clear();
            annotated1.clear();
            annotations1.clear();


            selected.add(k);
            selected.add(numberCount+1+i);
            selected.add(numberCount+n1+2+j);
            annotated1.add(k);
            annotated1.add(numberCount+1+i);
            annotated1.add(numberCount+n1+2+j);
            annotations1.add(new Pair<>(k,"k"));
            annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
            annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));


            if(i >= n1)
            {
                visualizerWindow.setCodeIndex2(27);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Alle Zahlen des Behälters der linken Zahlenmenge wurden eingeordnet. Diese Schleife wird also beendet");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("All numbers of the array of the left set of numbers have been inserted. Therefore the loop is ended");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
            else
            {
                visualizerWindow.setCodeIndex2(27);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Es wurden noch nicht alle Zahlen des Behälters der linken Zahlenmenge eingeordnet. Die Schleife wird also fortgesetzt");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Not all numbers of the array of the left set of numbers have been inserted. Therefore the loop is continued");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
        }

        checkPause();
        sleep(animationDelay);
        repaint();


        visualizerWindow.setCodeIndex2(33);

        if (j >= n2)
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Alle Zahlen des Behälters der rechten Zahlenmenge wurden schon eingeordnet. Diese Schleife wird also nicht ausgeführt");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("All numbers of the array of the right set of numbers have been inserted. Therefore the loop is not executed");
        }
        else if(j < n2)
        {

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Es wurden noch nicht alle Zahlen des Behälters der rechten Zahlenmenge eingeordnet. Diese Schleife wird deswegen ausgeführt");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Not all numbers of the array of the right set of numbers have been inserted. Therefore the loop is executed");
        }


        checkPause();
        sleep(animationDelay);
        repaint();


        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            visualizerWindow.setCodeIndex2(35);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Füge die Zahl " + R.get(j) + " an die Position des aktuellen Laufindex der gesamten Zahlenmenge ein");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Insert the number " + R.get(j) + " into the position of the current running index of the entire set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();


            randomNumbers.set(k, R.get(j));

            accessCount++;
            visualizerWindow.setCountAccesses(accessCount);

            visualizerWindow.setCodeIndex2(36);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Erhöhe den Laufindex des Behälters der rechten Zahlenmenge um 1");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Increment the running index of the array of the right set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();



            j++;

            visualizerWindow.updateVariable("j",j);

            selected.clear();
            annotated1.clear();
            annotations1.clear();


            selected.add(k);
            selected.add(numberCount+1+i);
            selected.add(numberCount+n1+2+j);
            annotated1.add(k);
            annotated1.add(numberCount+1+i);
            annotated1.add(numberCount+n1+2+j);
            annotations1.add(new Pair<>(k,"k"));
            annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
            annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));

            checkPause();
            sleep(animationDelay);
            repaint();


            visualizerWindow.setCodeIndex2(37);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Erhöhe den Laufindex gesamten Zahlenmenge um 1");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Increment the running index of the entire set of numbers");


            checkPause();
            sleep(animationDelay);
            repaint();


            k++;

            visualizerWindow.updateVariable("k",k);

            selected.clear();
            annotated1.clear();
            annotations1.clear();


            selected.add(k);
            selected.add(numberCount+1+i);
            selected.add(numberCount+n1+2+j);
            annotated1.add(k);
            annotated1.add(numberCount+1+i);
            annotated1.add(numberCount+n1+2+j);
            annotations1.add(new Pair<>(k,"k"));
            annotations1.add(new Pair<Integer, String>(numberCount+1+i,"i"));
            annotations1.add(new Pair<Integer, String>(numberCount+n1+2+j,"j"));


            if(j >= n2)
            {
                visualizerWindow.setCodeIndex2(33);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Alle Zahlen des Behälters der rechten Zahlenmenge wurden eingeordnet. Diese Schleife wird also beendet");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("All numbers of the array of the right set of numbers have been inserted. Therefore the loop is ended");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
            else
            {
                visualizerWindow.setCodeIndex2(33);


                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Es wurden noch nicht alle Zahlen des Behälters der rechten Zahlenmenge eingeordnet. Die Schleife wird also fortgesetzt");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Not all numbers of the array of the right set of numbers have been inserted. Therefore the loop is continued");

                checkPause();
                sleep(animationDelay);
                repaint();
            }
        }



        // just organizational stuff



        visualizerWindow.setCodeIndex2(39);


        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Alle Zahlen der linken und rechten Zahlenmenge wurden gemergt");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("All numbers of the left and right set of numbers have been merged");

        variableIndicators.clear();
        variables = 0;

        while(randomNumbers.size() > numberCount)
            randomNumbers.remove(numberCount);

        annotated1.clear();
        annotations1.clear();
        selected.clear();


        checkPause();
        sleep(animationDelay);
        repaint();




        visualizerWindow.resetVariableValuesAndPerformanceIndicators();

        visualizerWindow.setCountAccesses(accessCount);
        visualizerWindow.setCountComparisons(compareCount);
        visualizerWindow.setCountSwaps(swapCount);
        visualizerWindow.updateVariable("l",l);
        visualizerWindow.updateVariable("r",r);
        visualizerWindow.updateVariable("m",m);
    }


    /**
     * Visualize QuickSort
     * @param quiz indicates whether this  is called from the QuizWindow or not
     */
    public void quickSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        // ensure that everything has
        while(true)
        {
            if(resetComplete)
                break;
        }

        reset = false;
        resetComplete = false;


        selected.clear();
        annotated1.clear();
        annotations1.clear();

        Thread quickSortThread = new Thread(new Runnable()
        {
            public void run()
            {

                quickSortFunction(0, randomNumbers.size()-1, quiz);

                visualizerWindow.addRecord("QuickSort", randomNumbers.size(), accessCount, compareCount, swapCount);

                repaint();

            }
        });


        currentVisualization = quickSortThread;
        currentVisualizationName="quickSort";

        quickSortThread.start();

    }


    /**
     * Actual function for executing QuickSort
     * @param low left border
     * @param high right border
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    void quickSortFunction(int low, int high, boolean quiz)
    {

        if(! quiz)
            annotationWindow1.repaint();



        visualizerWindow.resetVariableValuesAndPerformanceIndicators();
        annotations1.clear();
        annotated1.clear();
        annotated2.clear();
        annotations2.clear();

        visualizerWindow.setCodeIndex1(0);

        if (low == 0 && high == randomNumbers.size()-1)
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe zu Beginn die Methode sort mit den äußeren Grenezen, also low=0 und high=" + (randomNumbers.size()-1) + " auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the method sort with the outer borders, meaning low=0 and high=" + (randomNumbers.size()-1) + " auf");

            checkPause();
            sleep(1000);
            repaint();
        }
        else
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Rufe Methode sort mit low=" + low + " und high=" + high + " auf");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the method sorth with low=" + low + " and high=" + high);


            checkPause();
            sleep(animationDelay);
            repaint();
        }

        visualizerWindow.updateVariable("low",low);
        visualizerWindow.updateVariable("high",high);

        annotated1.clear();
        annotations1.clear();

        annotated2.clear();
        annotations2.clear();

        annotated2.add(low);
        annotated2.add(high);
        annotations2.add(new Pair<>(low,"low"));

        if (high == low)
            annotations2.add(new Pair<>(high,"low=high"));
        else
            annotations2.add(new Pair<>(high,"high"));



        visualizerWindow.setCodeIndex1(2);
        if (low < high)
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("low ist kleiner als high. Deswegen müssen die Zahlen in diesem Bereich noch sortiert werden");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("low is smaller than high. Therefore the numbers in this are still need to be sorted");


        }
        else
        {
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("low ist genauso groß oder größer als high. Deswegen ist keine weitere Sortierung nötig");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("low is equal to or larger than high. Therefore no further sorting is necessary");
        }

        checkPause();
        sleep(animationDelay);
        repaint();



        if (low < high)
        {
            checkPause();
            sleep(animationDelay);
            repaint();

        /* pi is partitioning index, arr[p] is now
           at right place */

            visualizerWindow.setCodeIndex1(4);
            visualizerWindow.setCodeIndex2(0);


            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Bestimme den Index pi mithilfe der Funktion portion. Die Zahl an diesem Index wird sich anschließend an der richtigen Position gemäß Sortierung befinden.");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Determine the index pi by using the function portion. The number at this index will be at the correct postion ragarding the sorting afterwards.");



            checkPause();
            sleep(animationDelay);
            repaint();



            int pi = partition(low, high);

            visualizerWindow.updateVariable("pi",pi);

            selected.clear();
            annotated1.clear();
            annotations1.clear();
            sorted.add(pi);
            annotated1.add(pi);
            annotations1.add(new Pair<Integer, String>(pi,"pi"));

            visualizerWindow.removeAllAnnotations();

            checkPause();
            sleep(animationDelay);
            repaint();
            revalidate();





            // Separately sort elements before
            // partition and after partition


            visualizerWindow.setCodeIndex2(-1);
            visualizerWindow.setCodeIndex1(5);


            annotated1.add(pi-1);
            annotations1.add(new Pair<>(pi-1,"pi-1"));

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Führe die Funktion sort auf den Zahlen links von pi aus");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the function sort on the numbers left to pi");



            checkPause();
            sleep(animationDelay);
            repaint();

            annotated2.clear();
            annotations2.clear();




            quickSortFunction(low, pi - 1, quiz);

            selected.clear();
            annotated1.clear();
            annotations1.clear();
            sorted.add(pi);
            annotated1.add(pi);
            annotations1.add(new Pair<Integer, String>(pi,"pi"));

            visualizerWindow.removeAllAnnotations();

            checkPause();
            sleep(animationDelay);
            repaint();
            revalidate();



            visualizerWindow.updateVariable("low",low);
            visualizerWindow.updateVariable("high",high);
            visualizerWindow.updateVariable("pi",pi);



            checkPause();
            sleep(animationDelay);
            repaint();

            visualizerWindow.setCodeIndex1(6);

            annotated1.add(pi+1);
            annotations1.add(new Pair<Integer, String>(pi+1,"pi+1"));

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Führe die Funktion sort auf den Zahlen rechts von pi aus");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("Call the function sort on the numbers right to pi");

            checkPause();
            sleep(animationDelay);
            repaint();

            quickSortFunction(pi + 1, high, quiz);

            checkPause();
            sleep(animationDelay);
            repaint();
        }

        sorted.add(low);


        if(low == 0 && high == randomNumbers.size()-1)
        {
            visualizerWindow.setCodeIndex1(8);

            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                visualizerWindow.setExplainField("Die Zahlen sind jetzt komplett sortiert");
            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                visualizerWindow.setExplainField("The numbers are now entirely ordered");
        }


    }

    /**
     * Choosses a pivor element and rearranges the numbers according to this pivot element
     * @param low left border
     * @param high right border
     * @return
     */
    int partition (int low, int high)
    {
        visualizerWindow.setCodeIndex2(2);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Speichere die Zahl beim Index high als Pivot-Wert. Anhand dieser Referenzzahl werden im Folgenden Zahlen getauscht");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Save the number at index high as the pivot value. Based on this reference number numbers will be swapped in the following");

        checkPause();
        sleep(animationDelay);
        repaint();


        int pivot = randomNumbers.get(high);    // pivot

        randomNumbers.add(numberCount,0);
        variableIndicators.add(numberCount);
        randomNumbers.add(numberCount+1,pivot);
        variables=1;







        visualizerWindow.updateVariable("pivot",low);

        accessCount++;
        visualizerWindow.setCountAccesses(accessCount);


        visualizerWindow.updateVariable("pivot",pivot);

        selected.add(numberCount+1);
        annotated1.add(numberCount+1);
        annotations1.add(new Pair<>(numberCount+1, "pivot"));





        checkPause();
        sleep(animationDelay);
        repaint();

        visualizerWindow.setCodeIndex2(3);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Speichere i als Index links neben low. Alle Zahlen, die sich zwischen Index  i (inklusiv) und low befinden, sollen kleiner oder genauso groß wie der Pivot-Wert sein und alle Zahlen, die sich zwischen i (exklusiv) und high befinden, sollen größer sein als der Pivot-Wert.");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Save i as the index left to low. All numbers between i (inclusive) and low should be smaller or euqal to the pivot value and all numbers between i (exclusive) and high should be larger than the pivot value.");



        checkPause();
        sleep(animationDelay);
        repaint();


        int i = (low - 1);  // Index of smaller element

        visualizerWindow.updateVariable("i",i);

        selected.add(i);
        annotated1.add(i);
        annotations1.add(new Pair<>(i, "i"));


        visualizerWindow.setCodeIndex2(4);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Gehe alle Zahlen von low bis high-1 durch");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Go over all numbers from low to high-1");




        checkPause();
        sleep(animationDelay);
        repaint();


        for (int j = low; j <= high- 1; j++)
        {

            if(j != low)
            {
                visualizerWindow.setCodeIndex2(4);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Erhöhe j um 1");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Increment 1");





                checkPause();
                sleep(animationDelay);
                repaint();
            }

            visualizerWindow.updateVariable("j",j);

            selected.clear();
            annotated1.clear();




            selected.add(numberCount+1);
            annotated1.add(numberCount+1);
            annotations1.add(new Pair<>(numberCount+1, "pivot"));



            selected.add(j);
            annotated1.add(j);
            annotations1.add(new Pair<>(j,"j"));


            selected.add(i);
            annotated1.add(i);
            annotations1.add(new Pair<>(i, "i"));

            visualizerWindow.setCodeIndex2(6);

            if(randomNumbers.get(j) <= pivot)
            {
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Die Zahl am Index j (" + randomNumbers.get(j) + ") ist kleiner oder genauso groß wie der Pivot-Wert " + pivot);
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("The number at index j (" + randomNumbers.get(j) + ") is smaller or equal to the pivot value " + pivot);
            }
            else
            {
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Die Zahl am Index j (" + randomNumbers.get(j) + ") ist größer als der Pivot-Wert " + pivot);
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("The number at index j (" + randomNumbers.get(j) + ") islarger than the pivot value " + pivot);
            }


            checkPause();
            sleep(animationDelay);
            repaint();


            compareCount++;
            visualizerWindow.setCountComparisons(compareCount);

            // If current element is smaller than or
            // equal to pivot
            if (randomNumbers.get(j) <= pivot)
            {
                visualizerWindow.setCodeIndex2(8);

                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Erhöhe i deswegen um 1.");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("Therefore increment i");

                checkPause();
                sleep(animationDelay);
                repaint();


                i++;    // increment index of smaller element

                visualizerWindow.updateVariable("i",i);

                selected.remove(selected.size()-1);
                annotated1.remove(annotated1.size()-1);
                annotations1.remove(annotations1.size()-1);

                selected.add(i);
                annotated1.add(i);

                if (i == j)
                    annotations1.add(new Pair<>(i, "j=i"));
                else
                    annotations1.add(new Pair<>(i, "i"));


                visualizerWindow.setCodeIndex2(9);


                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("Und tausche die Zahl am Index i ("+ randomNumbers.get(i) + ") mit der Zahl am Index j (" + randomNumbers.get(j) + "), sodass dass alle Zahlen mit einem Index kleiner gleich i kleiner oder genauso groß wie der Pivot-Wert sind");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("And swap the number at index i ("+ randomNumbers.get(i) + ") with the number at index j (" + randomNumbers.get(j) + "so that all numbers with a smaller index than i are smaller or equal to the pivot value");



                checkPause();
                sleep(animationDelay);
                repaint();

                swap(i, j);
            }


            if(j+1 > high- 1)
            {
                visualizerWindow.setCodeIndex2(4);


                annotated1.clear();
                annotations1.clear();
                selected.clear();

                annotated1.add(i);
                annotated1.add(j+1);
                annotated1.add(numberCount+1);

                selected.add(i);
                selected.add(j+1);
                selected.add(numberCount+1);

                annotations1.add(new Pair<Integer, String>(i,"i"));
                annotations1.add(new Pair<Integer, String>(j+1,"j"));
                annotations1.add(new Pair<Integer, String>(numberCount+1,"pivot"));







                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                    visualizerWindow.setExplainField("j ist größer als high - 1. Deswegen wird die Schleife beendet");
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                    visualizerWindow.setExplainField("j is larger than high - 1. Therefore the loop is ended");



                checkPause();
                sleep(animationDelay);
                repaint();
            }

        }

        selected.clear();
        annotated1.clear();
        annotations1.clear();

        annotated1.add(i);
        annotations1.add(new Pair<>(i, "i"));

        selected.add(i+1);
        annotated1.add(i+1);
        annotations1.add(new Pair<>(i+1, "i+1"));

        selected.add(numberCount+1);
        annotated1.add(numberCount+1);
        annotations1.add(new Pair<>(numberCount+1, "pivot"));




        visualizerWindow.setCodeIndex2(12);

        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Vertausche den Pivot-Wert mit dem Wert am Index i+1. Dadurch wird gewährleistet, dass alle Zahlen links vom Pivot-Wert kleiner gleich und alle Zahlen rechts vom Pivot-Wert größer als der Pivot-Wert sind");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Swap the pivot value with the value at index i+1. Thereby it is guaranteed that all numbers left to the pivot value are smaller or eual to the pivot value and all numbers right to the pivot value are larger than the pivot value");


        checkPause();
        sleep(animationDelay);
        repaint();

        selected.add(i+1);
        annotated1.add(i+1);
        annotations1.add(new Pair<>(i+1,"i+1"));



        swap(i + 1, high);



        visualizerWindow.setCodeIndex2(13);


        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
            visualizerWindow.setExplainField("Gebe den Index des Pivot-Wertes zurück");
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
            visualizerWindow.setExplainField("Return the index of the pivot value");


        selected.clear();
        annotated1.clear();
        annotations1.clear();

        selected.add(i+1);
        annotated1.add(i+1);
        annotations1.add(new Pair<>(i+1,"i+1"));




        checkPause();
        sleep(animationDelay);
        repaint();

        variableIndicators.clear();
        variables=0;

        while(randomNumbers.size() > numberCount)
            randomNumbers.remove(numberCount);

        return (i + 1);
    }


    /**
     * Visualize BogoSort
     * @param quiz indicates whether this is called from the QuizWindow or not
     */
    public void bogoSort(boolean quiz)
    {
        selected.clear();
        annotated1.clear();
        annotations1.clear();

        reset = false;
        resetComplete = false;

        Thread bogoSortThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    visualizerWindow.setCodeIndex1(0);

                    selected.clear();
                    repaint();

                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Prüfe, ob die Zahlen alle sortiert sind");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("Check if all numbers are sorted");

                    checkPause();
                    sleep(animationDelay);
                    repaint();

                    // check if already sorted
                    boolean sorted = true;

                    int i = 0;


                    while(i <randomNumbers.size() -1)
                    {
                        selected.clear();

                        selected.add(i);
                        selected.add(i+1);
                        repaint();

                        accessCount += 2;
                        compareCount++;

                        visualizerWindow.setCountAccesses(accessCount);
                        visualizerWindow.setCountComparisons(compareCount);

                        if (randomNumbers.get(i) > randomNumbers.get(i+1))
                        {


                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            {
                                visualizerWindow.setExplainField(randomNumbers.get(i) + " ist größer als " + randomNumbers.get(i+1));
                                visualizerWindow.addTextToExplainField("Also sind aktuell nicht alle Zahlen sortiert");
                            }
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            {
                                visualizerWindow.setExplainField(randomNumbers.get(i) + " ist larger than " + randomNumbers.get(i+1));
                                visualizerWindow.addTextToExplainField("Therefore not all numbers are not ordered at the moment");
                            }

                            checkPause();
                            sleep(animationDelay);
                            repaint();

                            sorted = false; break;


                        }
                        else
                        {
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                            {
                                visualizerWindow.setExplainField(randomNumbers.get(i) + " ist kleiner oder gleich " + randomNumbers.get(i+1));
                                visualizerWindow.addTextToExplainField("Diese Zahlen sind also richtig sortiert");
                            }
                            if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                            {
                                visualizerWindow.setExplainField(randomNumbers.get(i) + " ist smaller than or euqal to " + randomNumbers.get(i+1));
                                visualizerWindow.addTextToExplainField("Therefore these numbers are ordered correctly");
                            }

                            checkPause();
                            sleep(animationDelay);
                            repaint();
                        }

                        i++;
                    }

                    if (sorted == true)
                        break;



                    // make a random swap
                    selected.clear();
                    repaint();

                    visualizerWindow.setCodeIndex1(1);
                    repaint();


                    int firstRandomNumber = (int)(Math.random()* numberCount);
                    int secondRandomNumber = (int)(Math.random()* numberCount);

                    selected.add(firstRandomNumber);
                    selected.add(secondRandomNumber);



                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Tausche zwei zufällige Zahlen");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("Swap to random numbers");

                    repaint();
                    sleep(animationDelay);
                    checkPause();
                    repaint();



                    swap(firstRandomNumber, secondRandomNumber);


                    repaint();
                    sleep(animationDelay);
                }



                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                        visualizerWindow.setExplainField("Alle Zahlen sind richtig sortiert");
                    if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                        visualizerWindow.setExplainField("All numbers are ordered correctly");

                    selected.clear();

                    for (int i=0; i<randomNumbers.size(); i++)
                    {
                        sorted.add(i);
                    }

                    repaint();

                    visualizerWindow.addRecord("BogoSort", randomNumbers.size(), accessCount, compareCount, swapCount);
            }



        });

        bogoSortThread.start();

        currentVisualization = bogoSortThread;
    }


    /**
     * Swap to numbers of given indices
     * @param i index of first number
     * @param j index of second number
     */
    public void swap(int i, int j)
    {
        int temp = randomNumbers.get(i);
        randomNumbers.set(i, randomNumbers.get(j));
        randomNumbers.set(j, temp);

        if (visualizerWindow != null)
        {
            swapCount++;
            accessCount+=2;

            visualizerWindow.setCountAccesses(accessCount);
            visualizerWindow.setCountSwaps(swapCount);
            visualizerWindow.getMainPanel().repaint();
        }
    }

    /**
     * sets the animation speed of the visualization
     * @param delay delay of the animation
     */
    public void setAnimationSpeed(int delay)
    {
        this.animationDelay = delay;
    }

    /**
     * starts or ends the pause of a visualization
     * @param paused indicates whether a pause should be started or ended
     */
    public void setPause(boolean paused)
    {
            pause = paused;
    }

    /**
     * show the next step of a sorting algorithm
     */
    public void showNextStep()
    {
        this.nextStep = true;
    }

    /**
     * visualzation sleeps for a given amoount of time
     * @param delay time in milliseconds that the visualization sleeps
     */
    public static void sleep(int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Check whether the visualization has been paused
     */
    public void checkPause()
    {
        if (pause == true)
        {
            while (pause == true && nextStep == false)
            {
                sleep(100);
            }


            if (pause == false || nextStep == true)
                nextStep = false;

        }
    }

    /**
     * Reset a visualization
     * @param quiz indicates whether this is called from the QuizWindow or not

     */
    public void reset(boolean quiz)
    {
        reset = true;
        accessCount = 0;
        compareCount = 0;
        swapCount = 0;

        selected.clear();
        sorted.clear();

        if (currentVisualization != null)
            currentVisualization.stop();

        if (!quiz)
        {
            annotated1.clear();
            annotations1.clear();

            annotated2.clear();
            annotations2.clear();

            visualizerWindow.setExplainField("");

            visualizerWindow.setCodeIndex1(-1);
            visualizerWindow.setCodeIndex2(-1);


            visualizerWindow.resetVariableValuesAndPerformanceIndicators();
        }

        repaint();


        // remove potential variables
        if (randomNumbers.size() > numberCount)
        {
            int variableCount = randomNumbers.size()-numberCount;

            for (int i=0; i< variableCount;i++)
            {
                randomNumbers.remove(numberCount);
            }
        }
        variables = 0;

        variableIndicators.clear();


        shuffle();

        repaint();

        resetComplete = true;

    }
}
