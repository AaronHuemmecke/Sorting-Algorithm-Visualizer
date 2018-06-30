package main.Java.Views;

import main.Java.Util.LanguageManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * QuizWindow which shows the visualization of a random sorting algorithm and the user have to guess from four answer options
 */
public class QuizWindow
{

    /**
     * Array of sorint algorithm names
     */
    String[] algorithms = {"BubbleSort", "SelectionSort", "InsertionSort", "MergeSort", "QuickSort", "BogoSort"};

    /**
     * Index of the correct answer
     */
    private int correctAnswerButton;

    /**
     * Boolean that indicates if this is the first quiz
     */
    boolean firstQuiz;

    /**
     * Reference to the MainPanel
     * @return MainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * JPanel that contains all elements
     */
    private JPanel mainPanel;

    /**
     * JButton that contains the first algorithm option
     */
    private JButton algorithm1Button;

    /**
     * JButton that contains the second algorithm option
     */
    private JButton algorithm2Button;

    /**
     * JButton that contains the third algorithm option
     */
    private JButton algorithm3Button;

    /**
     * JButton that contains the fourth algorithm option
     */
    private JButton algorithm4Button;

    /**
     * JPanel containing the visualization
     */
    private JPanel sortingPanel;

    /**
     * JButton to start or end the quiz
     */
    private JButton start;

    /**
     * Boolean that indicates that the visualization will be stopped
     */
    private boolean visualizationToBeStopped;

    /**
     * VisualizerPanel in which the visualization takes place
     */
    private VisualizerPanel visualizerPanel;

    /**
     * Constructor of QuizWindow
     * @param visualizerWindow ReferenceToTheWindow
     */
    public QuizWindow(VisualizerWindow visualizerWindow)
    {
        UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));

        firstQuiz = true;

        visualizerPanel = new VisualizerPanel(20, visualizerWindow);
        sortingPanel.add(visualizerPanel);


        algorithm1Button.setFont( new Font(Font.SANS_SERIF, 0, 15));
        algorithm2Button.setFont( new Font(Font.SANS_SERIF, 0, 15));
        algorithm3Button.setFont( new Font(Font.SANS_SERIF, 0, 15));
        algorithm4Button.setFont( new Font(Font.SANS_SERIF, 0, 15));
        start.setFont( new Font(Font.SANS_SERIF, 0, 15));

        start.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("QuizWindow");

                if (visualizationToBeStopped == false)
                {
                    visualizationToBeStopped = true;


                    int selectedIndex = (int)((Math.random()) * 6);

                    String selectedAlgorithm = algorithms[selectedIndex];

                    ArrayList<String> candidates = new ArrayList<String>();

                    for (int i=0; i<algorithms.length; i++)
                    {
                        if (i != selectedIndex)
                            candidates.add(algorithms[i]);
                    }

                    int candidateIndex1 = (int)((Math.random()) * 5);
                    String candidate1 = candidates.get(candidateIndex1);
                    candidates.remove(candidateIndex1);

                    int candidateIndex2 = (int)((Math.random()) * 4);
                    String candidate2 = candidates.get(candidateIndex2);
                    candidates.remove(candidateIndex2);

                    int candidateIndex3 = (int)((Math.random()) * 3);
                    String candidate3 = candidates.get(candidateIndex3);
                    candidates.remove(candidateIndex3);

                    int candidateIndex4 = (int)((Math.random()) * 2);
                    String candidate4 = candidates.get(candidateIndex4);
                    candidates.remove(candidateIndex4);



                    algorithm1Button.setText(candidate1);
                    algorithm2Button.setText(candidate2);
                    algorithm3Button.setText(candidate3);
                    algorithm4Button.setText(candidate4);


                    int replaceIndex = (int)((Math.random()) * 4);

                    switch (replaceIndex)
                    {
                        case 0:  algorithm1Button.setText(selectedAlgorithm); break;
                        case 1:  algorithm2Button.setText(selectedAlgorithm); break;
                        case 2:  algorithm3Button.setText(selectedAlgorithm); break;
                        case 3:  algorithm4Button.setText(selectedAlgorithm); break;
                    }


                    correctAnswerButton = replaceIndex+1;

                    switch (selectedAlgorithm)
                    {
                        case "BubbleSort": visualizerPanel.bubbleSort(true); break;
                        case "SelectionSort": visualizerPanel.selectionSort(true); break;
                        case "InsertionSort": visualizerPanel.insertionSort(true); break;
                        case "MergeSort": visualizerPanel.mergeSort(true); break;
                        case "QuickSort": visualizerPanel.quickSort(true); break;
                        case "BogoSort": visualizerPanel.bogoSort(true); break;

                    }

                    firstQuiz = false;
                    start.setText(bundle.getString("stopVisualization"));
                }
                else
                {
                    visualizerPanel.reset(true);
                    visualizationToBeStopped = false;

                    algorithm1Button.setEnabled(true);
                    algorithm1Button.setBackground(new JButton().getBackground());
                    algorithm2Button.setEnabled(true);
                    algorithm2Button.setBackground(new JButton().getBackground());
                    algorithm3Button.setEnabled(true);
                    algorithm3Button.setBackground(new JButton().getBackground());
                    algorithm4Button.setEnabled(true);
                    algorithm4Button.setBackground(new JButton().getBackground());

                    algorithm1Button.setText(bundle.getString("algorithm1"));
                    algorithm2Button.setText(bundle.getString("algorithm2"));
                    algorithm3Button.setText(bundle.getString("algorithm3"));
                    algorithm4Button.setText(bundle.getString("algorithm4"));

                    start.setText(bundle.getString("nextAlgorithm"));


                }
            }
        });

        algorithm1Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(visualizationToBeStopped == true)
                {
                    if (correctAnswerButton == 1)
                    {
                        algorithm1Button.setBackground(Color.GREEN);
                    }
                    else
                    {
                        algorithm1Button.setBackground(Color.red);
                        algorithm1Button.setEnabled(false);
                    }
                }
            }
        });

        algorithm2Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(visualizationToBeStopped == true)
                {
                    if (correctAnswerButton == 2)
                    {
                        algorithm2Button.setBackground(Color.GREEN);
                    } else {
                        algorithm2Button.setBackground(Color.red);
                        algorithm2Button.setEnabled(false);
                    }
                }
            }
        });

        algorithm3Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(visualizationToBeStopped == true)
                {
                    if (correctAnswerButton == 3) {
                        algorithm3Button.setBackground(Color.GREEN);
                    } else {
                        algorithm3Button.setBackground(Color.red);
                        algorithm3Button.setEnabled(false);
                    }
                }
            }
        });

        algorithm4Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(visualizationToBeStopped == true)
                {
                    if (correctAnswerButton == 4) {
                        algorithm4Button.setBackground(Color.GREEN);
                    } else {
                        algorithm4Button.setBackground(Color.red);
                        algorithm4Button.setEnabled(false);
                    }
                }
            }
        });


    }
}
