package main.Java.Views;

import main.Java.Util.LanguageManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * MainWindow which contains all the features
 */
public class MainWindow extends JFrame
{
    /**
     * MainPanel of the Mainwindow
     */
    private JPanel mainPanel;

    /**
     * JTabbedPane used for selecting the different features of the program
     */
    private JTabbedPane tabbedPane1;

    /**
     * JEditorPane in which a welcomeText is shown
     */
    private JEditorPane welcomeText;

    /**
     * JRadioButton for chosing German as language
     */
    private JRadioButton germanRadioButton;

    /**
     * JRadioButton for chosing English as language
     */
    private JRadioButton englishRadioButton;

    /**
     * JLabel for languages
     */
    private JLabel language;

    /**
     * JLabel for licenses
     */
    private JLabel licencelable;

    /**
     * JEditorPane in which the licenses are shown
     */
    private JEditorPane licenseInfo;

    /**
     * JCheckBox that can be used for showing the material used for this program
     */
    private JCheckBox showUsedMaterial;

    /**
     * ExplanationWindow which contains explanations for topics related to sorting algorithms
     */
    private ExplanationWindow explanationWindow;

    /**
     * VisualizerWindow for visualizing sorting algorithms
     */
    private VisualizerWindow visualizerWindow;

    /**
     * QuizWindow for letting the user identify sorting algorithms based on their visualizations
     */
    private QuizWindow quizWindow;

    /**
     * JPanel for Setting
     */
    private JPanel settingsPanel;

    /**
     * JPanel for Licenses
     */
    private JPanel licensePanel;


    /**
     * Constructor of MainWindow
     * @param explanationWindow ExplanationWindow which contains explanations for topics related to sorting algorithms
     * @param visualizerWindow VisualizerWindow for visualizing sorting algorithms
     * @param quizWindow QuizWindow for letting the user identify sorting algorithms based on their visualizations
     */
    public MainWindow(ExplanationWindow explanationWindow, VisualizerWindow visualizerWindow, QuizWindow quizWindow)
    {
        this.setTitle("Sorting Algorithm Visualizer");
        ImageIcon icon = new ImageIcon(this.getClass().getResource("48x48.png"));
        this.setIconImage(icon.getImage());
        this.setVisible(true);
        setContentPane(mainPanel);

        this.explanationWindow = explanationWindow;
        this.visualizerWindow = visualizerWindow;
        this.quizWindow = quizWindow;

        tabbedPane1.setFont( new Font(Font.SANS_SERIF, 0, 15));
        language.setFont( new Font(Font.SANS_SERIF, 0, 15));
        germanRadioButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        englishRadioButton.setFont( new Font(Font.SANS_SERIF, 0, 15));
        licencelable.setFont( new Font(Font.SANS_SERIF, 0, 15));
        showUsedMaterial.setFont( new Font(Font.SANS_SERIF, 0, 15));


        ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("MainWindow");

        tabbedPane1.setTitleAt(0, bundle.getString("start"));
        tabbedPane1.addTab(bundle.getString("explaination"), explanationWindow.getMainPanel());
        tabbedPane1.addTab(bundle.getString("algorithmVisualization"), visualizerWindow.getMainPanel());
        tabbedPane1.addTab(bundle.getString("algorithmQuiz"), quizWindow.getMainPanel());


        tabbedPane1.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if(tabbedPane1.getSelectedIndex() == 2 || tabbedPane1.getSelectedIndex() == 3)
                {
                    visualizerWindow.reset();
                    quizWindow.reset();
                }
            }
        });


        welcomeText.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        welcomeText.setFont(new Font(Font.SANS_SERIF, 0, 15));
        welcomeText.setOpaque(false);
        welcomeText.setContentType("text/html");
        welcomeText.setEditable(false);


        welcomeText.setText("Willkommen zum Sorting Algorithm Visualizer. <br> <br>" +
                "Dieses Programm dient dazu, Ihnen Sortieralgotithmen anschaulich und Schritt für Schritt zu erklären. <br> <br>" +
                "Das Programm ist in drei verschiedene Abschnitte unterteilt, durch die Sie sich beliebig navigieren können: <br> <br>" +
                "1. In dem Abschnitt <b>Erklärung</b> befinden sich Erklärungstexte zu den Grundlagen von Sortieralgorithmen. Außerdem werden einige Sortieralgorithmen textuell und anhand von Grafiken beschrieben. <br>" +
                "2. In dem Abschnitt <b>Algorithmen-Visualisierung</b> bedindet sich ein Tool, mit welchem Sie verschiedene Sortieralgorithmen ausführen können und dabei eine grafische Visualisierung, Erklärungen und weitere Informationen erhalten. <br>" +
                "3. In dem Abschnitt <b>Algorithmen-Quiz</b> können Sie testen, ob Sie Sortieralgorithmen anhand ihrer Visualisierung erkennen können und Sie somit das Prinzip der einzelnen Sortieralgorihmen verstanden haben. <br> <br>" +
                "Das Programm unterstützt die Sprachen Deutsch und Englisch. Sie können die Sprache in der oberen linken Ecke auswählen. <br> <br>" +
                "Unten befinden sich Lizenzinformationen, sowohl zur Lizenz dieses Programms selbst als auch zu den Lizenzen der verwendeten Materialien.");



        licenseInfo.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 15));
        licenseInfo.setContentType("text/html");
        licenseInfo.setEditable(false);
        licenseInfo.setOpaque(false);

        licenseInfo.addHyperlinkListener(new HyperlinkListener() {
            @Override
                public void hyperlinkUpdate(HyperlinkEvent hle) {
                    if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                        System.out.println(hle.getURL());
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(hle.getURL().toURI());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });


        String authorInfo = "Dieses Programm “Sorting Algorithm Visualizer” ist von Aaron Hümmecke erstellt worden und ist unter der Creative Commons Lizenz" +
                " <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> veröffentlicht.";

        licenseInfo.setText(authorInfo);

        showUsedMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
                {

                    String authorInfo = "Dieses Programm “Sorting Algorithm Visualizer” ist von Aaron Hümmecke erstellt worden und ist unter der Creative Commons Lizenz" +
                            " <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> veröffentlicht.";

                    String usedMaterial =  " <br> <br> Die folgenden Materialien wurden in diesem Programm verwendet:<br>" +
                            "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  Liste der Autoren</a> für die Erklärung “Algorithmus”,<br> " +
                            " <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Sortieralgorithmus”,<br> " +
                            "Sortieralgorithmus von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> für die Erklärung “Sortieralgorithmus”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Kategorisierung von Sortieralgorithmen”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  Liste der Autoren</a> für die Erklärung “Kategorisierung von Sortieralgorithmen”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                            " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  Liste der Autoren</a> für die Erklärung “BubbleSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  Liste der Autoren</a> für die Erklärung “BubbleSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> von Swfung8 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “BubbleSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “SelectionSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> von Joestape89 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “SelectionSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “InsertionSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  Liste der Autoren</a> für die Erklärung “SelectionSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> von MrDrBob unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “InserionSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “MergeSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  Liste der Autoren</a> für die Erklärung “MergeSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> von VineetKumar unter <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a> für die Erklärung “MergeSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “QuickSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  Liste der Autoren</a> für die Erklärung “QuickSort”,<br> " +
                            "QuickSort von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> für die Erklärung “QuickSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “BogoSort”,<br> " +
                            "<a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  Liste der Autoren</a> für die Erklärung “BogoSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +
                            "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  Liste der Autoren</a> für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +

                            "<br>" +
                            "<a href='https://www.geeksforgeeks.org/bubble-sort/'>Java program for implementation of Bubble Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus BubbleSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/selection-sort/'>Java program for implementation of Selection Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus SelectionSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/insertion-sort/'>Java program for implementation of Insertion Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus InsertionSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/merge-sort/'>Java program for Merge Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus MergeSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/quick-sort/'>Java program for implementation of QuickSort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus QuickSort, <br>" +
                            "<a href='https://www.geeksforgeeks.org/bogosort-permutation-sort/'>Java Program to implement BogoSort</a> von Rahul Agrawal unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus BogoSort";

                    if(showUsedMaterial.isSelected())
                    {
                        licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 10));
                        licenseInfo.setText(authorInfo+usedMaterial);
                    }
                    else
                    {
                        licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 15));
                        licenseInfo.setText(authorInfo);
                    }
                }
                if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
                {
                    String authorInfo = "This program “Sorting Algorithm Visualizer” ist made by Aaron Hümmecke and published under the Creative Commons licence" +
                            " <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a>.";

                    String usedMaterial =  " <br> <br> The following materials have been used for this program:<br>" +

                            "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  list of authors</a> for the explanation “Algorithm”,<br> " +
                            " <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Sorting Algorithm”,<br> " +
                            "Sortieralgorithmus by Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> for the explanation “Sorting Algorithm”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Categorization of Sorting Algorithms”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  list of authors</a> for the explanation “Categorization of Sorting Algorithm”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                            " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                            "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  list of authors</a> for the explanation “BubbleSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  list of authors</a> for the explanation “BubbleSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> by Swfung8 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “BubbleSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “SelectionSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> by Joestape89 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “SelectionSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “InsertionSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  list of authors</a> for the explanation “SelectionSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> by MrDrBob under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “InserionSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “MergeSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  list of authors</a> for the explanation “MergeSort”,<br> " +
                            "<a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> by VineetKumar under <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a> for the explanation “MergeSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “QuickSort”,<br> " +
                            "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  list of authors</a> for the explanation “QuickSort”,<br> " +
                            "QuickSort by Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> for the explanation “QuickSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “BogoSort”,<br> " +
                            "<a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  list of authors</a> for the explanation “BogoSort”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the table “Comparing Overview of Sorting Algorithms”,<br> " +
                            "<a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the table  “Comparing Overview of Sorting Algorithms”,<br> " +
                            "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  list of authors</a> for the table  “Comparing Overview of Sorting Algorithms”,<br> " +

                            "<br>" +
                            
                            
                            "<a href='https://www.geeksforgeeks.org/bubble-sort/'>Java program for implementation of Bubble Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited and translated) for the explainatory text “What is sorting?”\",<br> " +
                            "<a href='https://www.geeksforgeeks.org/bubble-sort/'>Java program for implementation of Bubble Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm BubbleSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/selection-sort/'>Java program for implementation of Selection Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm SelectionSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/insertion-sort/'>Java program for implementation of Insertion Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm InsertionSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/merge-sort/'>Java program for Merge Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) for the code of the algorithm MergeSort, <br> " +
                            "<a href='https://www.geeksforgeeks.org/quick-sort/'>Java program for implementation of QuickSort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm QuickSort, <br>" +
                            "<a href='https://www.geeksforgeeks.org/bogosort-permutation-sort/'>Java Program to implement BogoSort</a> by Rahul Agrawal under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm BogoSort";

                    if(showUsedMaterial.isSelected())
                    {
                        licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 10));
                        licenseInfo.setText(authorInfo+usedMaterial);
                    }
                    else
                    {
                        licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 15));
                        licenseInfo.setText(authorInfo);
                    }
                }
            }
        });


        germanRadioButton.setSelected(true);

        germanRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (germanRadioButton.isSelected() == false)
                    germanRadioButton.setSelected(true);
                else
                {
                    englishRadioButton.setSelected(false);
                    LanguageManager.getLanguageManagerInstance().setCurrentLocale(Locale.GERMAN);
                    updateLanguageResources();

                    ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("SettingsWindow");

                    language.setText(bundle.getString("language"));
                    germanRadioButton.setText(bundle.getString("german"));
                    englishRadioButton.setText(bundle.getString("english"));
                }
            }});

        englishRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (englishRadioButton.isSelected() == false)
                    englishRadioButton.setSelected(true);
                else
                {
                    englishRadioButton.setSelected(true);
                    germanRadioButton.setSelected(false);
                    LanguageManager.getLanguageManagerInstance().setCurrentLocale(Locale.ENGLISH);
                    updateLanguageResources();

                    ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("SettingsWindow");

                    language.setText(bundle.getString("language"));
                    germanRadioButton.setText(bundle.getString("german"));
                    englishRadioButton.setText(bundle.getString("english"));
                }
            }
        });

    }

    /**
     * Method for switching the language of the elements
     */
    public void updateLanguageResources() {

        ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("MainWindow");



        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
        {
            welcomeText.setText("Willkommen zum Sorting Algorithm Visualizer. <br> <br>" +
                    "Dieses Programm dient dazu, Ihnen Sortieralgotithmen anschaulich und Schritt für Schritt zu erklären. <br> <br>" +
                    "Das Programm ist in drei verschiedene Abschnitte unterteilt, durch die Sie sich beliebig navigieren können: <br> <br>" +
                    "1. In dem Abschnitt <b>Erklärung</b> befinden sich Erklärungstexte zu den Grundlagen von Sortieralgorithmen. Außerdem werden einige Sortieralgorithmen textuell und anhand von Grafiken beschrieben. <br>" +
                    "2. In dem Abschnitt <b>Algorithmen-Visualisierung</b> bedindet sich ein Tool, mit welchem Sie verschiedene Sortieralgorithmen ausführen können und dabei eine grafische Visualisierung, Erklärungen und weitere Informationen erhalten. <br>" +
                    "3. In dem Abschnitt <b>Algorithmen-Quiz</b> können Sie testen, ob Sie Sortieralgorithmen anhand ihrer Visualisierung erkennen können und Sie somit das Prinzip der einzelnen Sortieralgorihmen verstanden haben. <br> <br>" +
                    "Das Programm unterstützt die Sprachen Deutsch und Englisch. Sie können die Sprache in der oberen linken Ecke auswählen. <br> <br>" +
                    "Unten befinden sich Lizenzinformationen, sowohl zur Lizenz dieses Programms selbst als auch zu den Lizenzen der verwendeten Materialien.");
        }
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
        {
            welcomeText.setText("Welcome to the Sorting Algorithm Visualizer. <br> <br>" +
                    "This program aims to explain sorting algorithms to you in a clear way step by step. <br> <br>" +
                    "The program is divided into three different sections that you can freely navigate through: <br> <br>" +
                    "1. In the section <b>Explanation</b> there are explanatory texts regarding the basics of sorting algorithms. Moreover, some sorting algorithms are described textually and graphically.<br>" +
                    "2. In the section <b>Algorithm Visualization</b> there is a tool that you can use to execute different sorting algorithms and see a graphic visualization, explanations and further information at the same time. <br>" +
                    "3. In the section <b>Algorithm Quiz</b> you can check if you can recognize a sorting algorithm by its visualization and thereby test your understanding of the different sorting algorithms. <br> <br>" +
                    "The program supports the languages German and English. You can choose the language in the top left corner. <br> <br>" +
                    "At the bottom there are license information, both for the license of the program itself and the licenses of the used materials.");
        }




    //    welcomeText.setText(bundle.getString("welcomeMessage"));
        licencelable.setText(bundle.getString("licenseInformation"));

        tabbedPane1.setTitleAt(0, bundle.getString("start"));
        tabbedPane1.setTitleAt(1, bundle.getString("explaination"));
        tabbedPane1.setTitleAt(2, bundle.getString("algorithmVisualization"));
        tabbedPane1.setTitleAt(3, bundle.getString("algorithmQuiz"));

        showUsedMaterial.setText(bundle.getString("detailled"));


        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))
        {

            String authorInfo = "Dieses Programm “Sorting Algorithm Visualizer” ist von Aaron Hümmecke erstellt worden und ist unter der Creative Commons Lizenz" +
                    " <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> veröffentlicht.";

            String usedMaterial =  " <br> <br> Die folgenden Materialien wurden in diesem Programm verwendet:<br>" +
                    "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  Liste der Autoren</a> für die Erklärung “Algorithmus”,<br> " +
                    " <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Sortieralgorithmus”,<br> " +
                    "Sortieralgorithmus von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> für die Erklärung “Sortieralgorithmus”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Kategorisierung von Sortieralgorithmen”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  Liste der Autoren</a> für die Erklärung “Kategorisierung von Sortieralgorithmen”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                    " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> für die Erklärung “Effizienz von Sortieralgorithmen”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  Liste der Autoren</a> für die Erklärung “BubbleSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  Liste der Autoren</a> für die Erklärung “BubbleSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> von Swfung8 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “BubbleSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “SelectionSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> von Joestape89 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “SelectionSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “InsertionSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  Liste der Autoren</a> für die Erklärung “SelectionSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> von MrDrBob unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> für die Erklärung “InserionSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “MergeSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  Liste der Autoren</a> für die Erklärung “MergeSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> von VineetKumar unter <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a> für die Erklärung “MergeSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “QuickSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  Liste der Autoren</a> für die Erklärung “QuickSort”,<br> " +
                    "QuickSort von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> für die Erklärung “QuickSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Erklärung “BogoSort”,<br> " +
                    "<a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  Liste der Autoren</a> für die Erklärung “BogoSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +
                    "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  Liste der Autoren</a> für die Tabelle “Vergleichende Übersicht der Sortieralgorithmen”,<br> " +

                    "<br>" +
                    "<a href='https://www.geeksforgeeks.org/bubble-sort/'>Java program for implementation of Bubble Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus BubbleSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/selection-sort/'>Java program for implementation of Selection Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus SelectionSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/insertion-sort/'>Java program for implementation of Insertion Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus InsertionSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/merge-sort/'>Java program for Merge Sort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus MergeSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/quick-sort/'>Java program for implementation of QuickSort</a> von Rajat Mishra unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus QuickSort, <br>" +
                    "<a href='https://www.geeksforgeeks.org/bogosort-permutation-sort/'>Java Program to implement BogoSort</a> von Rahul Agrawal unter <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (bearbeitet) für den Code des Algorithmus BogoSort";

            if(showUsedMaterial.isSelected())
            {
                licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 10));
                licenseInfo.setText(authorInfo+usedMaterial);
            }
            else
            {
                licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 15));
                licenseInfo.setText(authorInfo);
            }
        }
        if (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.ENGLISH.toString()))
        {
            String authorInfo = "This program “Sorting Algorithm Visualizer” ist made by Aaron Hümmecke and published under the Creative Commons licence" +
                    " <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a>.";

            String usedMaterial =  " <br> <br> The following materials have been used for this program:<br>" +

                    "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  list of authors</a> for the explanation “Algorithm”,<br> " +
                    " <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Sorting Algorithm”,<br> " +
                    "Sortieralgorithmus by Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> for the explanation “Sorting Algorithm”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Categorization of Sorting Algorithms”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  list of authors</a> for the explanation “Categorization of Sorting Algorithm”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                    " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> for the explanation “Efficiency of Sorting Algorithms”,<br> " +
                    "<a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  list of authors</a> for the explanation “BubbleSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  list of authors</a> for the explanation “BubbleSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> by Swfung8 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “BubbleSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “SelectionSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> by Joestape89 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “SelectionSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “InsertionSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  list of authors</a> for the explanation “SelectionSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> by MrDrBob under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> for the explanation “InsertionSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “MergeSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  list of authors</a> for the explanation “MergeSort”,<br> " +
                    "<a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> by VineetKumar under <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a> for the explanation “MergeSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “QuickSort”,<br> " +
                    "<a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  list of authors</a> for the explanation “QuickSort”,<br> " +
                    "QuickSort by Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> for the explanation “QuickSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the explanation “BogoSort”,<br> " +
                    "<a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  list of authors</a> for the explanation “BogoSort”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the table “Comparing Overview of Sorting Algorithms”,<br> " +
                    "<a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) for the table  “Comparing Overview of Sorting Algorithms”,<br> " +
                    "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  list of authors</a> for the table  “Comparing Overview of Sorting Algorithms”,<br> " +

                    "<br>" +


                    "<a href='https://www.geeksforgeeks.org/bubble-sort/'>Java program for implementation of Bubble Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm BubbleSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/selection-sort/'>Java program for implementation of Selection Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm SelectionSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/insertion-sort/'>Java program for implementation of Insertion Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm InsertionSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/merge-sort/'>Java program for Merge Sort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm MergeSort, <br> " +
                    "<a href='https://www.geeksforgeeks.org/quick-sort/'>Java program for implementation of QuickSort</a> by Rajat Mishra under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm QuickSort, <br>" +
                    "<a href='https://www.geeksforgeeks.org/bogosort-permutation-sort/'>Java Program to implement BogoSort</a> by Rahul Agrawal under <a href='https://creativecommons.org/licenses/by-sa/4.0/'>CC BY-SA 4.0</a> (edited) for the code of the algorithm BogoSort";

            if(showUsedMaterial.isSelected())
            {
                licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 10));
                licenseInfo.setText(authorInfo+usedMaterial);
            }
            else
            {
                licenseInfo.setFont(new Font(Font.SANS_SERIF, 0, 15));
                licenseInfo.setText(authorInfo);
            }
        }

        explanationWindow.updateLanguageResources();
        visualizerWindow.updateLanguageResources();
    }
}
