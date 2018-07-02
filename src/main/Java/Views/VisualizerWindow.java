package main.Java.Views;

import main.Java.Util.LanguageManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

/**
 * Visualizer Window which contains all elements for the visualization of sorting algorithms
 */
public class VisualizerWindow {

    /**
     * JComboBox for choosing the sorting algorithm to be visualized
     */
    private JComboBox algorithmSelector;

    /**
     * JPanel that contains all elements
     */
    private JPanel mainPanel;

    /**
     * JList that contains the code of the algorithm
     */
    private JList codeList1;

    /**
     * JList that contains additional code of the algorithm if it has more than one method
     */
    private JList codeList2;

    /**
     * JSlider for adjusting the speed of the animation
     */
    private JSlider speedSlider;

    /**
     * JButton for shuffling the numbers
     */
    private JButton shuffleButton;

    /**
     * JButton for starting or resetting the visualizaiton
     */
    private JButton startButton;

    /**
     * JButton for pausing the visualization
     */
    private JButton pauseButton;

    /**
     * JComboBox for choosing who many numbers should be sorted
     */
    private JComboBox numberCountSelector;

    /**
     * JButton for showing the next step of the sorting algorithm
     */
    private JButton nextStepButton;

    /**
     * JPanel that contains  the visualization
     */
    private JPanel sortingPanel;


    /**
     * AnnotationWindow which explains the functions of single numbers (for example variables) during the algorithm viusalization
     */
    private AnnotationWindow annotationWindow1;

    /**
     * AnnotationWindow which explains the functions of single numbers (for example variables) during the algorithm viusalization
     */
    private AnnotationWindow annotationWindow2;

    /**
     * ResourceBundle for the VisualizerWindow resources
     */
    private ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("VisualizerWindow");

    /**
     * Array which contains all column names for the record table
     */
    private String[] recordColumnNames = {bundle.getString("algorithm"), bundle.getString("dataCount"), bundle.getString("accesses"), bundle.getString("comparisons"), bundle.getString("swaps")};

    /**
     * DefaultTableModel for the record table
     */
    private DefaultTableModel recordTableModel;

    /**
     * JTable which stores the records of the single visualizations
     */
    private JTable recordTable;


    /**
     * Array which contains all column names for the variable table
     */
    private String[] variableColumnNames = {bundle.getString("variable"), bundle.getString("value")};

    /**
     * DefaultTableModel for the variable table
     */
    private DefaultTableModel variableTableModel;

    /**
     * JTable which stores the current values of the variables
     */
    private JTable variableTable;

    /**
     * Array which contains all column names for the performance table
     */
    private String[] performanceColumnNames = {bundle.getString("keyFigure"), bundle.getString("count")};

    /**
     * DefaultTableModel for the performance table
     */
    private DefaultTableModel performanceTableModel;

    /**
     * JTable which stores performance indicators during the visualization
     */
    private JTable performanceTable;


    /**
     * JPanel which contains the code and the explanation of single steps
     */
    private JPanel codeAndExplanationPanel;

    /**
     * JPanel which contains all control options for the user
     */
    private JPanel controlPanel;

    /**
     * JPanel which contains the records
     */
    private JPanel recordPanel;

    /**
     * JScrollPane which contains the recordTable
     */
    private JScrollPane recordTableScrollPane;

    /**
     * JButton to remove the records
     */
    private JButton removeRecords;

    /**
     * JCheckBox to show the code and explainations for single steps
     */
    private JCheckBox showCodeCheckBox;

    /**
     * JCheckBox to show the variables
     */
    private JCheckBox showVariablesCheckBox;

    /**
     * JCheckBox to show the records
     */
    private JCheckBox showRecordsCheckBox;

    /**
     * JCheckBox to show the performance
     */
    private JCheckBox showPerformanceCheckBox;

    /**
     * JScrollPane that contains the variableTable
     */
    private JScrollPane variableScrollPane;

    /**
     * JScrollPane that contains the performanceTable
     */
    private JScrollPane performanceScrollPane;

    /**
     * JPanel which contains the code
     */
    private JPanel codePanel;

    /**
     * JPanel which contains the first AnnotationWindow
     */
    private JPanel annotationPanel1;

    /**
     * JPanel which contains the second AnnotationWindow
     */
    private JPanel annotationPanel2;

    /**
     * JTextField which displays the colour of selected numbers
     */
    private JTextField selectedColour;

    /**
     * JTextField which displays the colour of sorted numbers
     */
    private JTextField sortedColour;

    /**
     * JTextPane which contains the explanations of single steps
     */
    private JTextPane explainField;

    /**
     * JLabel for selected numbers
     */
    private JLabel selected;

    /**
     * JLabel for sorted numbers
     */
    private JLabel sorted;

    /**
     * JLabel for slowing down the visualization
     */
    private JLabel slow;

    /**
     * JLabel for speeding up the visualization
     */
    private JLabel fast;

    /**
     * JLabel for code and explanation
     */
    private JLabel codeAndExplaination;

    /**
     * JLabel for recording
     */
    private JLabel recording;

    /**
     * JLabel for settings
     */
    private JLabel settings;

    /**
     * JLabel for showing the records larger
     */
    private JButton showRecordsLager;

    /**
     * JScrollPane which contains the first codeList
     */
    private JScrollPane codeList2ScrollPane;

    /**
     * JScrollPane which contains the second codeList
     */
    private JScrollPane codeList1ScrollPane;

    /**
     * JPanel which contains the variables
     */
    private JPanel variablePanel;

    /**
     * VisualizerPanel for the actual visualization of a sorting algorithm
     */
    private VisualizerPanel visualizerPanel;

    /**
     * Boolean that indicates whether the visualization has started
     */
    private boolean started;

    /**
     * Boolean that indicates whether the visualization has been paused
     */
    private boolean paused;


    /**
     * Colour of the selected numbers
     */
    private Color colSelected;

    /**
     * Color of the sorted numbers
     */
    private Color colSorted;

    /**
     * Boolean that indicates whether only a single row of the record table should be removed
     */
    private boolean deleteSingleEntry;

    /**
     * Constructor of the VisualizationWindow
     */
    public VisualizerWindow()
    {
        showCodeCheckBox.setSelected(true);

        showRecordsCheckBox.setSelected(true);

        variableScrollPane.setVisible(false);
        performanceScrollPane.setVisible(false);


        selectedColour.setBackground(Color.BLUE);
        sortedColour.setBackground(Color.GREEN);

        algorithmSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }
        });


        showCodeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (codePanel.isVisible())
                {
                    codePanel.setVisible(false);

                    if (codePanel.isVisible() == false && variableScrollPane.isVisible() == false && performanceScrollPane.isVisible() == false)
                        codeAndExplanationPanel.setVisible(false);
                }
                else
                {
                    codeAndExplanationPanel.setVisible(true);

                    codePanel.setVisible(true);
                }

                mainPanel.revalidate();
            }
        });


        showRecordsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordPanel.isVisible())
                    recordPanel.setVisible(false);
                else
                    recordPanel.setVisible(true);

                mainPanel.revalidate();
            }
        });

        showVariablesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (variableScrollPane.isVisible())
                {
                    variableScrollPane.setVisible(false);

                    if (codePanel.isVisible() == false && variableScrollPane.isVisible() == false && performanceScrollPane.isVisible() == false)
                        codeAndExplanationPanel.setVisible(false);
                }
                else
                {
                    codeAndExplanationPanel.setVisible(true);

                    variableScrollPane.setVisible(true);
                }

                mainPanel.revalidate();
            }
        });

        showPerformanceCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (performanceScrollPane.isVisible())
                {
                    performanceScrollPane.setVisible(false);

                    if (codePanel.isVisible() == false && variableScrollPane.isVisible() == false && performanceScrollPane.isVisible() == false)
                        codeAndExplanationPanel.setVisible(false);
                }
                else
                {
                    codeAndExplanationPanel.setVisible(true);

                    performanceScrollPane.setVisible(true);
                }


                mainPanel.revalidate();
            }
        });


        controlPanel.setMaximumSize(new Dimension(controlPanel.getWidth(), 100));
        recordPanel.setMaximumSize(new Dimension(recordPanel.getWidth(), 100));

        codeAndExplanationPanel.setMinimumSize(new Dimension(500,500));

        started  = false;
        paused = false;

        algorithmSelector.addItem("BubbleSort");
        algorithmSelector.addItem("SelectionSort");
        algorithmSelector.addItem("InsertionSort");
        algorithmSelector.addItem("MergeSort");
        algorithmSelector.addItem("QuickSort");
        algorithmSelector.addItem("BogoSort");


        numberCountSelector.addItem("5 " + bundle.getString("numbers"));
        numberCountSelector.addItem("10 " + bundle.getString("numbers"));
        numberCountSelector.addItem("15 " + bundle.getString("numbers"));
        numberCountSelector.addItem("20 " + bundle.getString("numbers"));
        numberCountSelector.addItem("25 " + bundle.getString("numbers"));
        numberCountSelector.addItem("30 " + bundle.getString("numbers"));

        numberCountSelector.setSelectedIndex(3);

        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(false);



        deleteSingleEntry = false;



        codeList1.setModel(getCode("BubbleSort"));
        codeList1.setFont(new Font(Font.DIALOG, 0, 14));
        codeList2.setFont(new Font(Font.DIALOG, 0, 14));
        codeList2ScrollPane.setVisible(false);

        explainField.setFont(new Font(Font.DIALOG, 0, 15));



        codeList1.setSelectedIndex(0);

        annotationWindow1 = new AnnotationWindow();
        annotationPanel1.add(annotationWindow1);
        annotationPanel1.setMinimumSize(new Dimension(500,20));

        annotationWindow2 = new AnnotationWindow();
        annotationPanel2.add(annotationWindow2);
        annotationPanel2.setMinimumSize(new Dimension(500,20));

        visualizerPanel = new VisualizerPanel(20, this, annotationWindow1, annotationWindow2);
        sortingPanel.add(visualizerPanel);



        nextStepButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                visualizerPanel.showNextStep();
            }
        });


        numberCountSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch (numberCountSelector.getSelectedIndex())
                {
                    case 0: visualizerPanel.changeNumberCount(5); break;
                    case 1: visualizerPanel.changeNumberCount(10); break;
                    case 2: visualizerPanel.changeNumberCount(15); break;
                    case 3: visualizerPanel.changeNumberCount(20); break;
                    case 4: visualizerPanel.changeNumberCount(25); break;
                    case 5: visualizerPanel.changeNumberCount(30); break;
                }
            }
        });

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizerPanel.shuffle();
            }
        });

        colSelected = new Color(Color.BLUE.getRGB());
        colSorted = new Color(Color.GREEN.getRGB());


        ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("VisualizerWindow");

        algorithmSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                while (variableTableModel.getRowCount() > 0)
                    variableTableModel.removeRow(0);

                switch (algorithmSelector.getSelectedIndex())
                {
                    case 0:
                    {
                        String[] tempRow1 = {"i"};
                        String[] tempRow2 = {"j"};

                        variableTableModel.addRow(tempRow1);
                        variableTableModel.addRow(tempRow2);

                        variableScrollPane.setMinimumSize(new Dimension(200,40));
                        variableScrollPane.setMaximumSize(new Dimension(200,40));

                   //     codeList1.setFont(new Font(Font.DIALOG, 0, 15));
                     //   explainField.setFont(new Font(Font.DIALOG, 0, 15));

                        codeList1.setModel(getCode("BubbleSort"));
                        codeList2ScrollPane.setVisible(false);
                        codeList1.setVisibleRowCount(8);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();
                        codePanel.revalidate();
                        codePanel.repaint();
                    } break;

                    case 1:
                    {
                        String[] tempRow1 = {"i"};
                        String[] tempRow2 = {"j"};
                        String[] tempRow3 = {"minIndex"};

                        variableTableModel.addRow(tempRow1);
                        variableTableModel.addRow(tempRow2);
                        variableTableModel.addRow(tempRow3);

                        variableScrollPane.setMinimumSize(new Dimension(200,83));
                        variableScrollPane.setMaximumSize(new Dimension(200,83));

                      //  codeList1.setFont(new Font(Font.DIALOG, 0, 15));
                      //  explainField.setFont(new Font(Font.DIALOG, 0, 15));

                        codeList1.setModel(getCode("SelectionSort"));
                        codeList2ScrollPane.setVisible(false);
                        codeList1.setVisibleRowCount(10);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();
                        codePanel.revalidate();
                        codePanel.repaint();
                    } break;

                    case 2:
                    {
                        String[] tempRow1 = {"i"};
                        String[] tempRow2 = {"j"};
                        String[] tempRow3 = {"key"};

                        variableTableModel.addRow(tempRow1);
                        variableTableModel.addRow(tempRow2);
                        variableTableModel.addRow(tempRow3);

                        variableScrollPane.setMinimumSize(new Dimension(200,83));
                        variableScrollPane.setMaximumSize(new Dimension(200,83));

                   //     codeList1.setFont(new Font(Font.DIALOG, 0, 15));
                    //    explainField.setFont(new Font(Font.DIALOG, 0, 15));

                        codeList1.setModel(getCode("InsertionSort"));
                        codeList2ScrollPane.setVisible(false);
                        codeList1.setVisibleRowCount(11);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();
                        codePanel.revalidate();
                        codePanel.repaint();

                    } break;
                    case 3:
                    {
                        String[] tempRow1 = {"l"};
                        String[] tempRow2 = {"r"};
                        String[] tempRow3 = {"m"};
                        String[] tempRow4 = {"n1"};
                        String[] tempRow5 = {"index1"};
                        String[] tempRow6 = {"index2"};
                        String[] tempRow7 = {"n2"};
                        String[] tempRow8 = {"i"};
                        String[] tempRow9 = {"j"};
                        String[] tempRow10 = {"k"};


                        variableTableModel.addRow(tempRow1);
                        variableTableModel.addRow(tempRow2);
                        variableTableModel.addRow(tempRow3);
                        variableTableModel.addRow(tempRow4);
                        variableTableModel.addRow(tempRow5);
                        variableTableModel.addRow(tempRow6);
                        variableTableModel.addRow(tempRow7);
                        variableTableModel.addRow(tempRow8);
                        variableTableModel.addRow(tempRow9);
                        variableTableModel.addRow(tempRow10);

                        variableScrollPane.setMinimumSize(new Dimension(200,223));
                        variableScrollPane.setMaximumSize(new Dimension(200,223));


                   //     codeList1.setFont(new Font(Font.DIALOG, 0, 11));
                   //     explainField.setFont(new Font(Font.DIALOG, 0, 11));

                        codeList1.setModel(getCode("MergeSort1"));
                        codeList2.setModel(getCode("MergeSort2"));
                    //    codeList2.setFont(new Font(Font.DIALOG, 0, 9));

                        codeList2ScrollPane.setVisible(false);
                        codeList2ScrollPane.setVisible(true);


                        codeList2.setVisibleRowCount(14);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();
                        codePanel.revalidate();
                        codePanel.repaint();
                    } break;

                    case 4:
                    {
                        String[] tempRow1 = {"low"};
                        String[] tempRow2 = {"high"};
                        String[] tempRow3 = {"pi"};
                        String[] tempRow4 = {"pivot"};
                        String[] tempRow5 = {"i"};
                        String[] tempRow6 = {"j"};

                        variableTableModel.addRow(tempRow1);
                        variableTableModel.addRow(tempRow2);
                        variableTableModel.addRow(tempRow3);
                        variableTableModel.addRow(tempRow4);
                        variableTableModel.addRow(tempRow5);
                        variableTableModel.addRow(tempRow6);

                        variableScrollPane.setMinimumSize(new Dimension(200,143));
                        variableScrollPane.setMaximumSize(new Dimension(200,143));

                    //    codeList1.setFont(new Font(Font.DIALOG, 0, 12));
                    //    explainField.setFont(new Font(Font.DIALOG, 0, 12));

                        codeList1.setModel(getCode("QuickSort1"));
                        codeList2.setModel(getCode("QuickSort2"));
                      //  codeList2.setFont(new Font(Font.DIALOG, 0, 14));

                        codeList2ScrollPane.setVisible(true);
                        codeList2.setVisibleRowCount(15);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();
                        codePanel.revalidate();
                        codePanel.repaint();
                    } break;

                    case 5:
                    {
                  //      codeList1.setFont(new Font(Font.DIALOG, 0, 15));
                  //      explainField.setFont(new Font(Font.DIALOG, 0, 15));

                        codeList1.setModel(getCode("BogoSort"));
                        codeList2ScrollPane.setVisible(false);

                        codeList2ScrollPane.revalidate();
                        codeList2ScrollPane.repaint();
                        codeList1ScrollPane.revalidate();
                        codeList1ScrollPane.repaint();

                        variableScrollPane.setMinimumSize(new Dimension(200,83));
                        variableScrollPane.setMaximumSize(new Dimension(200,83));


                        codePanel.revalidate();
                        codePanel.repaint();
                    } break;
                }
            }
        });


        // necessary for a GUI related bug
        codeList2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                sortingPanel.revalidate();
                sortingPanel.repaint();

                sortingPanel.revalidate();
                sortingPanel.repaint();

                codeList2.repaint();
                codeList2.revalidate();
                visualizerPanel.repaint();
                visualizerPanel.revalidate();

                visualizerPanel.repaint();
                visualizerPanel.revalidate();

                performanceTable.revalidate();
                performanceTable.repaint();

                variableTable.revalidate();
                variableTable.repaint();

                sortingPanel.revalidate();
                sortingPanel.repaint();

                visualizerPanel.repaint();
                visualizerPanel.revalidate();

                annotationWindow1.repaint();
                annotationWindow1.revalidate();
                annotationWindow2.repaint();
                annotationWindow2.revalidate();

                performanceTable.revalidate();
                performanceTable.repaint();

                variableTable.revalidate();
                variableTable.repaint();

                codeList2.ensureIndexIsVisible(codeList2.getSelectedIndex());
            }
        });


        algorithmSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (started == true)
                    algorithmSelector.setSelectedIndex(algorithmSelector.getSelectedIndex());
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (started == false)
                {
                    ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("VisualizerWindow");
                    startButton.setText(bundle.getString("reset"));

                    started = true;
                    pauseButton.setEnabled(true);
                    shuffleButton.setEnabled(false);
                    numberCountSelector.setEnabled(false);

                    algorithmSelector.setEnabled(false);
                    annotationWindow1.setVisible(true);
                    annotationWindow2.setVisible(true);


                    switch (algorithmSelector.getSelectedIndex())
                    {
                        case 0:
                        {
                            visualizerPanel.bubbleSort(false);

                        } break;

                        case 1:
                        {
                            visualizerPanel.selectionSort(false);
                        } break;

                        case 2:
                        {
                            visualizerPanel.insertionSort(false);
                        } break;
                        case 3:
                        {
                            visualizerPanel.mergeSort(false);
                        } break;

                        case 4:
                        {
                            visualizerPanel.quickSort(false);
                        } break;

                        case 5:
                        {
                            visualizerPanel.bogoSort(false);
                        } break;

                    }
                }
                else
                {
                    started = false;

                    pauseButton.setEnabled(false);
                    nextStepButton.setEnabled(false);
                    paused = false;
                    pauseButton.setText(bundle.getString("pause"));
                    visualizerPanel.setPause(false);
                    shuffleButton.setEnabled(true);
                    numberCountSelector.setEnabled(true);


                    algorithmSelector.setEnabled(true);

                    visualizerPanel.reset(false);

                    annotationWindow1.setVisible(false);
                    annotationWindow2.setVisible(false);


                    codeList1.setSelectedIndex(0);

                    startButton.setText(bundle.getString("start"));

                    visualizerPanel.repaint();
                }


            }
        });


        speedSlider.setMinimum(0);
        speedSlider.setMaximum(500);
        speedSlider.setValue(250);

        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                visualizerPanel.setAnimationSpeed(500- speedSlider.getValue());
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (paused == false)
                {
                    pauseButton.setText(bundle.getString("continue"));
                    paused = true;
                    visualizerPanel.setPause(true);

                    nextStepButton.setEnabled(true);
                }
                else
                {
                    pauseButton.setText(bundle.getString("pause"));
                    paused = false;
                    visualizerPanel.setPause(false);
                    nextStepButton.setEnabled(false);
                }
            }
        });


        recordTableModel = new DefaultTableModel(recordColumnNames, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

        recordTable.setModel(recordTableModel);
        recordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);




        variableTableModel = new DefaultTableModel(variableColumnNames, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        variableTable.setModel(variableTableModel);
        variableTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        String[] tempRow1 = {"i"};
        String[] tempRow2 = {"j"};

        variableTableModel.addRow(tempRow1);
        variableTableModel.addRow(tempRow2);



        performanceTableModel = new DefaultTableModel(performanceColumnNames, 0)
        {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        performanceTable.setModel(performanceTableModel);
        performanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        performanceTable.setRowHeight(performanceTable.getRowHeight()+4);


        String[] performanceRow1 = {bundle.getString("accesses")};
        String[] performanceRow2 = {bundle.getString("comparisons")};
        String[] performanceRow3= {bundle.getString("swaps")};

        performanceTableModel.addRow(performanceRow1);
        performanceTableModel.addRow(performanceRow2);
        performanceTableModel.addRow(performanceRow3);



        recordTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                removeRecords.setText(bundle.getString("deleteEntry"));
                deleteSingleEntry = true;
            }
        });

        removeRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (deleteSingleEntry == false)
                {
                    while (recordTableModel.getRowCount() > 0)
                        recordTableModel.removeRow(0);

                }
                else
                {
                    recordTableModel.removeRow(recordTable.getSelectedRow());
                }

                deleteSingleEntry = false;

                removeRecords.setText(bundle.getString("deleteEntries"));
            }
        });


        selectedColour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Color c = JColorChooser.showDialog(null, bundle.getString("colorSelection"), null);

                colSelected = c;

                selectedColour.setBackground(c);

                visualizerPanel.repaint();
            }
        });

        sortedColour.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Color c = JColorChooser.showDialog(null, bundle.getString("colorSelection"), null);

                colSorted = c;

                sortedColour.setBackground(c);

                visualizerPanel.repaint();
            }
        });





        showRecordsLager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame recordWindow = new JFrame();
                ImageIcon icon = new ImageIcon(this.getClass().getResource("48x48.png"));
                recordWindow.setIconImage(icon.getImage());

                JTable recordTableClone = new JTable();
                recordTableClone.setModel(recordTableModel);

                JScrollPane recordTableScrollPaneClone = new JScrollPane(recordTableClone);

                recordWindow.add(recordTableScrollPaneClone);


                recordWindow.setMinimumSize(new Dimension(1200,800));
                recordWindow.setLocationRelativeTo(null);


                recordWindow.setVisible(true);
                recordTableClone.setPreferredScrollableViewportSize(new Dimension(450, 400));


                recordTableClone.setRowHeight(recordTable.getRowHeight()+4);
                recordTableClone.setFont( new Font(Font.SANS_SERIF, 0, 15));

                recordWindow.repaint();
                recordWindow.pack();

                
            }
        });

        visualizerPanel.reset(false);

        algorithmSelector.setFont( new Font(Font.SANS_SERIF, 0, 15));
        selected.setFont( new Font(Font.SANS_SERIF, 0, 15));
        sorted.setFont( new Font(Font.SANS_SERIF, 0, 15));
        settings.setFont( new Font(Font.SANS_SERIF, 0, 15));
        startButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        pauseButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        nextStepButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        numberCountSelector.setFont( new Font(Font.SANS_SERIF, 0, 15));
        shuffleButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        slow.setFont( new Font(Font.SANS_SERIF, 0, 15));
        fast.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showCodeCheckBox.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showRecordsCheckBox.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showVariablesCheckBox.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showPerformanceCheckBox.setFont( new Font(Font.SANS_SERIF, 0, 15));
        codeAndExplaination.setFont( new Font(Font.SANS_SERIF, 0, 15));

        variableTable.setFont( new Font(Font.SANS_SERIF, 0, 15));
        variableTable.setRowHeight(variableTable.getRowHeight()+4);
        performanceTable.setFont( new Font(Font.SANS_SERIF, 0, 15));
        performanceTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        performanceTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        explainField.setFont( new Font(Font.SANS_SERIF, 0, 15));
        recording.setFont( new Font(Font.SANS_SERIF, 0, 15));
        recordTable.setRowHeight(recordTable.getRowHeight()+4);

        recordTable.setFont( new Font(Font.SANS_SERIF, 0, 15));
        removeRecords.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showRecordsLager.setFont( new Font(Font.SANS_SERIF, 0, 15));

    }

    /**
     * returns the code of a given sorting algorithm
     * @param sortingAlgorithm sorting algorithm whose code should be returned
     * @return code of a given sorting algorithm
     */
    private ListModel getCode(String sortingAlgorithm)
    {
        DefaultListModel<String> code = new DefaultListModel<>();

        switch(sortingAlgorithm)
        {
            case "BubbleSort":
            {
                code.addElement("for (int i = 1; i < randomNumbers.size(); i++)");
                code.addElement("{");
                code.addElement("   for (int j = 0; j < randomNumbers.size() - i; j++)");
                code.addElement("   {");
                code.addElement("       if (randomNumbers.get(j) > randomNumbers.get(j + 1)");
                code.addElement("           swap(j, j + 1);");
                code.addElement("    }");
                code.addElement("}");
            } break;

            case "SelectionSort":
            {
                code.addElement("for (int i = 0; i < randomNumbers.size()-1; i++)");
                code.addElement("{");
                code.addElement("   int minIndex = i;");
                code.addElement("   for (int j = i+1; j < randomNumbers.size(); j++)");
                code.addElement("   {");
                code.addElement("       if (randomNumbers.get(j) < randomNumbers.get(minIndex)");
                code.addElement("           minIndex = j;");
                code.addElement("    }");
                code.addElement("    swap(minIndex,j);");
                code.addElement("}");
            } break;

            case "InsertionSort":
            {
                code.addElement("for (int i = 1; i < randomNumbers.size(); i++)");
                code.addElement("{");
                code.addElement("   int key = randomNumbers.get(i);");
                code.addElement("   int j = i - 1;");
                code.addElement("   while (j >= 0 && randomNumbers.get(j) > key)");
                code.addElement("   {");
                code.addElement("       randomNumbers.set(j+1, randomNumbers.get(j))");
                code.addElement("       j = j - 1;");
                code.addElement("    }");
                code.addElement("    randomNumbers.set(j+1, key);");
                code.addElement("}");
            } break;

            case "MergeSort1":
            {
                code.addElement("void sort(int l, int r)");
                code.addElement("{");

                code.addElement("   {");
                code.addElement("       if (l < r)");
                code.addElement("       {");
                code.addElement("            int m = (l + r) / 2;");
                code.addElement("            sort(l, m);");
                code.addElement("            sort(m+1, r);");
                code.addElement("            merge(l, m, r);");
                code.addElement("       }");
                code.addElement("    }");
                code.addElement("}");
            } break;


            case "MergeSort2":
            {
                code.addElement("int merge(int l, int m, int r))");
                code.addElement("{");
                code.addElement("   int n1 = m - l + 1;");
                code.addElement("   int n2 = r - m;");
                code.addElement("   ArrayList<Integer> L = new ArrayList<Integer>(n1);");
                code.addElement("   ArrayList<Integer> R = new ArrayList<Integer>(n2);");
                code.addElement("   for(int index1=0; index1<n1; ++index1)");
                code.addElement("        L.add(index1) = randomNumbers.get(l + index1);");
                code.addElement("   for(int index2=0; index2<n2; ++j)");
                code.addElement("        R.add(index2) = randomNumbers.get(m + 1 + index2);");
                code.addElement("   int i = 0;");
                code.addElement("   int j = 0;");
                code.addElement("   int k = l;");
                code.addElement("   while(i < n1 && j < n2)");
                code.addElement("   {");
                code.addElement("       if (L.get(i) <= R.get(j))");
                code.addElement("       {");
                code.addElement("            randomNumbers.set(k, L.get(i));");
                code.addElement("            i++;");
                code.addElement("       }");
                code.addElement("       else");
                code.addElement("       {");
                code.addElement("            randomNumbers.set(k, R.get(j));");
                code.addElement("            j++;");
                code.addElement("       }");
                code.addElement("       k++;");
                code.addElement("    }");
                code.addElement("   while(i < n1)");
                code.addElement("   {");
                code.addElement("       randomNumbers.set(k, L.get(i);");
                code.addElement("       i++;");
                code.addElement("       k++;");
                code.addElement("   }");
                code.addElement("   while(j < n2)");
                code.addElement("   {");
                code.addElement("       randomNumbers.set(k, R.get(j);");
                code.addElement("       j++;");
                code.addElement("       k++;");
                code.addElement("   }");
                code.addElement("}");
            } break;

            case "QuickSort1":
            {
                code.addElement("void sort(int low, int high)");
                code.addElement("{");
                code.addElement("   if (low < high)");
                code.addElement("   {");
                code.addElement("       int pi = partition(randomNumbers, low, high);");
                code.addElement("       sort(randomNumbers, low, pi-1);");
                code.addElement("       sort(randomNumbers, pi+1, high);");
                code.addElement("    }");
                code.addElement("}");
            } break;

            case "QuickSort2":
            {
                code.addElement("int partition(int low, int high))");
                code.addElement("{");
                code.addElement("   int pivot = randomNumbers.get(high);");
                code.addElement("   int i = low - 1;");
                code.addElement("   for(int j=low; j<= high-1; j++)");
                code.addElement("   {");
                code.addElement("       if (randomNumbers.get(j) <= pivot)");
                code.addElement("       {");
                code.addElement("            i++;");
                code.addElement("            swap(i,j);");
                code.addElement("       }");
                code.addElement("   }");
                code.addElement("    swap(i+1,high);");
                code.addElement("    return(i+1);");
                code.addElement("}");
            } break;


            case "BogoSort":
            {
                code.addElement("while (isSorted() == false)");
                code.addElement("   randomSwap()");
            } break;
        }

        return code;
    }

    /**
     * sets the selected index of the first codeList
     * @param index index to be selected
     */
    public void setCodeIndex1(int index)
    {
        if(index == -1)
            codeList1.clearSelection();
        else
            codeList1.setSelectedIndex(index);
    }

    /**
     * sets the selected index of the second codeList
     * @param index index to be selected
     */
    public void setCodeIndex2(int index)
    {
        if(index == -1)
            codeList2.clearSelection();
        else
            codeList2.setSelectedIndex(index);
    }

    /**
     * sets the text of the explain field
     * @param text text of the explain field
     */
    public void setExplainField (String text)
    {
        explainField.setText(text);
    }

    /**
     * appends text to already existing text in the explain feld
     * @param text text to be appended
     */
    public void addTextToExplainField(String text)
    {
        explainField.setText(explainField.getText() + "\n" + text);
    }

    /**
     * sets the count of accesses of the currently visualized algorithm
     * @param count count of accesses of the currently visualized algorithm
     */
    public void setCountAccesses(int count)
    {
        performanceTableModel.setValueAt(count, 0, 1);
    }

    /**
     * sets the count of comparisons of the currently visualized algorithm
     * @param count count of comparisons of the currently visualized algorithm
     */
    public void setCountComparisons(int count)
    {
        performanceTableModel.setValueAt(count, 1, 1);
    }

    /**
     * sets the count of swaps of the currently visualized algorithm
     * @param count count of swaps of the currently visualized algorithm
     */
    public void setCountSwaps(int count)
    {
        performanceTableModel.setValueAt(count, 2, 1);
    }

    /**
     * adds a record to the record table
     * @param algorithm visualized algorithm
     * @param numberCount count of numbers that were sorted
     * @param countAccess count of necessary accesses
     * @param countComparisons count of necessary comparisons
     * @param countSwaps count of necessary swaps
     */
    public void addRecord(String algorithm, int numberCount, int countAccess, int countComparisons, int countSwaps)
    {
        String[] tempRow = new String[6];

        tempRow[0] = algorithm;
        tempRow[1] = numberCount+"";
        tempRow[2] = countAccess+"";
        tempRow[3] = countComparisons+"";
        tempRow[4] = countSwaps+"";

        recordTableModel.addRow(tempRow);
    }

    /**
     * update the value of a given variable
     * @param variable variable to be updated
     * @param value (new) value
     */
    public void updateVariable(String variable, int value)
    {
        switch (algorithmSelector.getSelectedIndex())
        {
            case 0:
            {
                switch (variable)
                {
                    case "i": variableTableModel.setValueAt(value, 0, 1); break;

                    case "j": variableTableModel.setValueAt(value, 1, 1); break;
                }

            } break;

            case 1:
            {
                switch (variable)
                {
                    case "i": variableTableModel.setValueAt(value, 0, 1); break;

                    case "j": variableTableModel.setValueAt(value, 1, 1); break;

                    case "minIndex": variableTableModel.setValueAt(value, 2, 1); break;
                }

            } break;

            case 2:
            {
                switch (variable)
                {
                    case "i": variableTableModel.setValueAt(value, 0, 1); break;

                    case "j": variableTableModel.setValueAt(value, 1, 1); break;

                    case "key": variableTableModel.setValueAt(value, 2, 1); break;
                }

            } break;

            case 3:
            {
                switch (variable)
                {
                    case "l": variableTableModel.setValueAt(value, 0, 1); break;

                    case "r": variableTableModel.setValueAt(value, 1, 1); break;

                    case "m": variableTableModel.setValueAt(value, 2, 1); break;

                    case "n1": variableTableModel.setValueAt(value, 3, 1); break;

                    case "n2": variableTableModel.setValueAt(value, 4, 1); break;

                    case "index1": variableTableModel.setValueAt(value, 5, 1); break;

                    case "index2": variableTableModel.setValueAt(value, 6, 1); break;

                    case "i": variableTableModel.setValueAt(value, 7, 1); break;

                    case "j": variableTableModel.setValueAt(value, 8, 1); break;

                    case "k": variableTableModel.setValueAt(value, 9, 1); break;
                }

            } break;

            case 4:
            {
                switch (variable)
                {
                    case "low": variableTableModel.setValueAt(value, 0, 1); break;

                    case "high": variableTableModel.setValueAt(value, 1, 1); break;

                    case "pi": variableTableModel.setValueAt(value, 2, 1); break;

                    case "pivot": variableTableModel.setValueAt(value, 3, 1); break;

                    case "i": variableTableModel.setValueAt(value, 4, 1); break;

                    case "j": variableTableModel.setValueAt(value, 5, 1); break;
                }

            } break;

            case 5:
            {
                switch (variable)
                {
                    case "i": variableTableModel.setValueAt(value, 0, 1); break;

                    case "j": variableTableModel.setValueAt(value, 1, 1); break;
                }

            } break;
        }
    }

    /**
     * reset variables and performance indicators
     */
    public void resetVariableValuesAndPerformanceIndicators()
    {
        for (int i=0; i < variableTableModel.getRowCount(); i++)
            variableTableModel.setValueAt("", i, 1);

        for (int i=0; i < performanceTableModel.getRowCount(); i++)
            performanceTableModel.setValueAt("", i, 1);
    }

    /**
     * reset a varible value at a given index
     * @param index index of the variable that should be resetted
     */
    public void resetVariableValue(int index)
    {
            variableTableModel.setValueAt("", index, 1);
    }

    /**
     * reset all annotations
     */
    public void removeAllAnnotations()
    {
        annotationWindow1.removeAllAnotations();
    }


    /**
     * return JPanel that contains all elements
     * @return JPanel that contains all elements
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }


    /**
     * get the Colour of sorted numbers
     * @return  Colour of sorted numbers
     */
    public Color getSortedColour()
    {
        return colSorted;
    }

    /**
     * get the Colour of selected numbers
     * @return  Colour of selected numbers
     */
    public Color getSelectedColour()
    {
        return colSelected;
    }

    /**
     * reset the VisualizationWindow
     */
    public void reset()
    {
            started = false;

            pauseButton.setEnabled(false);
            nextStepButton.setEnabled(false);
            paused = false;
            pauseButton.setText(bundle.getString("pause"));
            visualizerPanel.setPause(false);
            shuffleButton.setEnabled(true);
            numberCountSelector.setEnabled(true);


            algorithmSelector.setEnabled(true);

            visualizerPanel.reset(false);

            annotationWindow1.setVisible(false);
            annotationWindow2.setVisible(false);


            codeList1.setSelectedIndex(0);
            codeList2.setSelectedIndex(0);

            explainField.setText("");


            startButton.setText(bundle.getString("start"));

            visualizerPanel.repaint();
    }


    /**
     * Method for switching the language of the elements
     */
    public void updateLanguageResources()
    {
        ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("VisualizerWindow");

        this.bundle = bundle;

        selected.setText(bundle.getString("selected"));
        sorted.setText(bundle.getString("sorted"));

        settings.setText(bundle.getString("settings"));
        startButton.setText(bundle.getString("start"));
        pauseButton.setText(bundle.getString("pause"));
        nextStepButton.setText(bundle.getString("nextStep"));
        shuffleButton.setText(bundle.getString("shuffle"));
        slow.setText(bundle.getString("slow"));
        fast.setText(bundle.getString("fast"));
        showCodeCheckBox.setText(bundle.getString("showPseudoCodeAndExplaination"));
        showVariablesCheckBox.setText(bundle.getString("showVariableValues"));
        showRecordsCheckBox.setText(bundle.getString("showRecording"));
        showPerformanceCheckBox.setText(bundle.getString("showLiveKeyFigures"));
        codeAndExplaination.setText(bundle.getString("pseudoCodeAndExplaination"));
        recording.setText(bundle.getString("recording"));
        removeRecords.setText(bundle.getString("deleteEntries"));
        showRecordsLager.setText(bundle.getString("showRecordsLarger"));

        performanceTableModel.setValueAt(bundle.getString("accesses"),0,0);
        performanceTableModel.setValueAt(bundle.getString("comparisons"),1,0);
        performanceTableModel.setValueAt(bundle.getString("swaps"),2,0);


        String[] variableColumnNames = {bundle.getString("variable"), bundle.getString("value")};

        variableTableModel.setColumnIdentifiers(variableColumnNames);

        String[] performanceColumnNames = {bundle.getString("keyFigure"), bundle.getString("count")};

        performanceTableModel.setColumnIdentifiers(performanceColumnNames);

        performanceTable.getColumnModel().getColumn(0).setPreferredWidth(100);



        String[] resultColumnNames = {bundle.getString("algorithm"), bundle.getString("dataCount"), bundle.getString("accesses"), bundle.getString("comparisons"), bundle.getString("swaps")};

        recordTableModel.setColumnIdentifiers(resultColumnNames);


        started = false;
        pauseButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        paused = false;
        pauseButton.setText(bundle.getString("pause"));
        visualizerPanel.setPause(false);
        shuffleButton.setEnabled(true);
        numberCountSelector.setEnabled(true);
        algorithmSelector.setEnabled(true);
        visualizerPanel.shuffle();
        visualizerPanel.reset(false);
        codeList1.setSelectedIndex(0);
        startButton.setText(bundle.getString("start"));
        visualizerPanel.repaint();



        numberCountSelector.removeAllItems();

        numberCountSelector.addItem("5 " + bundle.getString("numbers"));
        numberCountSelector.addItem("10 " + bundle.getString("numbers"));
        numberCountSelector.addItem("15 " + bundle.getString("numbers"));
        numberCountSelector.addItem("20 " + bundle.getString("numbers"));
        numberCountSelector.addItem("25 " + bundle.getString("numbers"));
        numberCountSelector.addItem("30 " + bundle.getString("numbers"));

        numberCountSelector.setSelectedIndex(3);

    }
}
