package main.Java.Views;

import main.Java.Util.LanguageManager;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ExplanationWindow which contains explanations for topics related to sorting algorithms
 */
public class ExplanationWindow extends JFrame
{
    /**
     * JComboBox for selecting a text
     */
    private JComboBox textSelector;

    /**
     * JEditorPane which contains the description
     */
    private JEditorPane description;


    /**
     * Panel which contains all elements
     */
    private JPanel mainPanel;

    /**
     * JEditorPane which contains the licenses
     */
    private JEditorPane licenseNote;

    /**
     * JPanel which contains the licenses
     */
    private JPanel licensePanel;

    /**
     * JTable which contains a comparing overview of all featured sorting algorithms
     */
    private JTable sortingAlgorithmOverviewTable;

    /**
     * ResourceBundle for the ExplanationWindow resources
     */
    private ResourceBundle bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("ExplanationWindow");

    /**
     * Array which contains all column names for the algorithm overview table
     */
    private String[] overviewColumnNames = {bundle.getString("sortingAlgorithm"), bundle.getString("time_complexity_best"), bundle.getString("time_complexity_average"), bundle.getString("time_complexity_worst"), bundle.getString("stable"), bundle.getString("inplace"), bundle.getString("spaceComplexity")};

    /**
     * DefaultTableModel for the algorithm overview table
     */
    private DefaultTableModel overViewTableModel = new DefaultTableModel(overviewColumnNames, 0){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };


    /**
     * JScrollPane for the overview table
     */
    private JScrollPane sortingAlgorithmOverViewPanel;

    /**
     * JScrollPane for the description text
     */
    private JScrollPane descriptionPanel;

    /**
     * Constructor of the ExplanationWindow
     */
    public ExplanationWindow()
    {
        // current number of descriptionary texts: 8
        textSelector.addItem(getTopic(0,0));
        textSelector.addItem(getTopic(1,0));
        textSelector.addItem(getTopic(2,0));
        textSelector.addItem(getTopic(3,0));
        textSelector.addItem(getTopic(4,0));
        textSelector.addItem(getTopic(5,0));
        textSelector.addItem(getTopic(6,0));
        textSelector.addItem(getTopic(7,0));
        textSelector.addItem(getTopic(8,0));
        textSelector.addItem(getTopic(9,0));
        textSelector.addItem(getTopic(10,0));

        textSelector.setFont( new Font(Font.SANS_SERIF, 0, 15));


        sortingAlgorithmOverViewPanel.setVisible(false);



        description.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        description.setFont(new Font(Font.SANS_SERIF, 0, 15));
        description.setOpaque(false);
        description.setContentType("text/html");
        description.setEditable(false);

        licenseNote.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        licenseNote.setFont(new Font(Font.SANS_SERIF, 0, 15));
        licenseNote.setOpaque(false);
        licenseNote.setContentType("text/html");
        licenseNote.setEditable(false);


        sortingAlgorithmOverviewTable.setFont(new Font(Font.SANS_SERIF, 0, 15));
        sortingAlgorithmOverviewTable.setRowHeight(sortingAlgorithmOverviewTable.getRowHeight()+4);


        description.setText(getText(0,0));
        licenseNote.setText(getSources(0,0));

        textSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(textSelector.getSelectedIndex()<10)
                {
                    descriptionPanel.setVisible(true);
                    sortingAlgorithmOverViewPanel.setVisible(false);
                    repaint();
                    revalidate();


                    description.setText(getText(textSelector.getSelectedIndex(), LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? 0:1));
                    licenseNote.setText(getSources(textSelector.getSelectedIndex(), LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? 0:1));
                }
                else
                {
                    descriptionPanel.setVisible(false);
                    sortingAlgorithmOverViewPanel.setVisible(true);
                    repaint();
                    revalidate();


                    while(overViewTableModel.getRowCount()>0)
                    {
                        overViewTableModel.removeRow(0);
                    }


                    bundle = LanguageManager.getLanguageManagerInstance().getResourceBundle("ExplanationWindow");


                    String[] overviewColumnNamesNew = {bundle.getString("sortingAlgorithm"), bundle.getString("time_complexity_best"), bundle.getString("time_complexity_average"), bundle.getString("time_complexity_worst"), bundle.getString("stable"), bundle.getString("inplace"), bundle.getString("spaceComplexity")};



                    DefaultTableModel overViewTableModelNew = new DefaultTableModel(overviewColumnNamesNew, 0){
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    sortingAlgorithmOverviewTable.setModel(overViewTableModelNew);


                    String[] tempRow = new String[7];

                    tempRow[0] = "BubbleSort";
                    tempRow[1] = "O(n^2)";
                    tempRow[2] = "O(n^2)";
                    tempRow[3] = "O(n^2)";
                    tempRow[4] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[6] = "-";

                    overViewTableModelNew.addRow(tempRow);

                    tempRow[0] = "SelectionSort";
                    tempRow[1] = "O(n^2)";
                    tempRow[2] = "O(n^2)";
                    tempRow[3] = "O(n^2)";
                    tempRow[4] = (LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString()))? "nein":"no";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[6] = "-";

                    overViewTableModelNew.addRow(tempRow);

                    tempRow[0] = "InsertionSort";
                    tempRow[1] = "O(n)";
                    tempRow[2] = "O(n^2)";
                    tempRow[3] = "O(n^2)";
                    tempRow[4] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[6] = "O(1)";

                    overViewTableModelNew.addRow(tempRow);

                    tempRow[0] = "MergeSort";
                    tempRow[1] = "O(n*log(n))";
                    tempRow[2] = "O(n*log(n))";
                    tempRow[3] = "O(n*log(n))";
                    tempRow[4] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "nein":"no";
                    tempRow[6] = "O(n)";

                    overViewTableModelNew.addRow(tempRow);

                    tempRow[0] = "QuickSort";
                    tempRow[1] = "O(n*log(n))";
                    tempRow[2] = "O(n*log(n))";
                    tempRow[3] = "O(n^2)";
                    tempRow[4] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "nein":"no";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[6] = "-";

                    overViewTableModelNew.addRow(tempRow);

                    tempRow[0] = "BogoSort";
                    tempRow[1] = "O(n)";
                    tempRow[2] = "O(n*n!)";
                    tempRow[3] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "unendlich":"infinite";
                    tempRow[4] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "nein":"no";
                    tempRow[5] = LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals(Locale.GERMAN.toString())? "ja":"yes";
                    tempRow[6] = "-";

                    overViewTableModelNew.addRow(tempRow);


                    licenseNote.setText(getSources(textSelector.getSelectedIndex(), LanguageManager.getLanguageManagerInstance().getCurrentLocale().equals(Locale.GERMAN.toString())? 1:0));
                }
            }
        });


        description.addHyperlinkListener(new HyperlinkListener() {
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

        licenseNote.addHyperlinkListener(new HyperlinkListener() {
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
    }

    /**
     * Returns the topic name of the selected topic in the selected language
     * @param topic numerical representation of the description text to be displayed
     * @param language 0 for German, 1 for English
     * @return topic name of the selected topic in the selected language
     */
    public String getTopic(int topic, int language)
    {
        String text="";

        switch (topic)
        {
            case 0:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Algorithmus";
                    } break;
                    case 1:
                    {
                        text = "Algorithm";
                    } break;
                }
            } break;
            case 1:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Sortieralgorithmus";
                    } break;
                    case 1:
                    {
                        text = "Sorting Algorithm";
                    } break;
                }
            } break;
            case 2:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Kategorisierung von Sortieralgorithmen";
                    } break;
                    case 1:
                    {
                        text = "Categorization of Sorting Algorithms";
                    } break;
                }
            } break;
            case 3:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Effizienz von Sortieralgorithmen";
                    } break;
                    case 1:
                    {
                        text = "Efficiency of Sorting Algorithms";
                    } break;
                }
            } break;
            case 4:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "BubbleSort";
                    } break;
                    case 1:
                    {
                        text = "BubbleSort";
                    } break;
                }
            } break;
            case 5:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "SelectionSort";
                    } break;
                    case 1:
                    {
                        text = "SelectionSort";
                    } break;
                }
            } break;
            case 6:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "InsertionSort";
                    } break;
                    case 1:
                    {
                        text = "InsertionSort";
                    } break;
                }
            } break;
            case 7:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "MergeSort";
                    } break;
                    case 1:
                    {
                        text = "MergeSort";
                    } break;
                }
            } break;
            case 8:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "QuickSort";
                    } break;
                    case 1:
                    {
                        text = "QuickSort";
                    } break;
                }
            } break;
            case 9:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "BogoSort";
                    } break;
                    case 1:
                    {
                        text = "BogoSort";
                    } break;
                }
            } break;
            case 10:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Vergleichende Übersicht der Sortieralgorithmen";
                    } break;
                    case 1:
                    {
                        text = "Comparing Overview of Sorting Algorithms";
                    } break;
                }
            } break;
        }

        return text;
    }

    /**
     * Returns the text of the selected topic in the selected language
     * @param topic numerical representation of the description text to be displayed
     * @param language 0 for German, 1 for English
     * @return text of the selected topic in the selected language
     */
    public String getText(int topic, int language)
    {
        String text="";

        switch (topic)
        {
            case 0:
            {
                switch(language)
                {
                    case 0:
                    {
                        text =  "Ein <b>Algorithmus</b> (auch Lösungsverfahren genannt) ist eine Handlungsvorschrift zur Lösung eines Problems" +
                                " in endlich vielen Schritten. Diese Verarbeitunsgsvorschrift besteht aus einer endlichen Folge von eindeutig" +
                                " ausführbaren Anweisungen, welche bei gleichen Voraussetzungen immer gleiche Ergebnise liefert. <br> <br>" +
                                " Algortihmen weisen fünf typische Eigenschatften auf: <br> <br>" +
                                "<b>Algemeingültigkeit:</b> Der Algorithmus löst eine Menge gleicher Probleme, einer ganzen Problemklasse. <br> <br>" +
                                "<b>Wiederholbarkeit:</b> Bei gleicher Voraussetzung liefert ein Algorithmus immer ein gleiches Ergebnis. <br> <br>" +
                                "<b>Eindeutigkeit:</b> An jeder Stelle ist der nachfolgende Schritt eindeutig definiert. <br> <br>"+
                                "<b>Endlichkeit:</b> Der Algorithmus besteht aus einer endlichen Anzahl von Schritten und kommt immer zu einem Ende. <br> <br>"+
                                "<b>Ausführbarkeit:</b> Die Anweisungen müssen verständlich formuliert und ausführbar sein. <br> <br>"
                        ;
                    } break;
                    case 1:
                    {
                        text =  "An <b>Algorithm</b> (also called solution process) is an action regulation to solve a problem" +
                                " in a finite amount of steps. This action regulation consists of a finite sequence of unambigiously" +
                                " executable instructions, which produces equal results under equal conditions. <br> <br>" +
                                " There are five typical properties of algorithms: <br> <br>" +
                                "<b>Universality:</b> The algorithm solves a set respectively class of equal problems. <br> <br>" +
                                "<b>Repeatability:</b> Under equal conditions the algorithm always produces te equal result. <br><br>" +
                                "<b>Unambiguity:</b> At each point the subsequent step is unambiguously defined. <br> <br>"+
                                "<b>Finiteness:</b> The algorithm consits if a finite amount of steps and always ends. <br> <br>"+
                                "<b>Executability:</b> The instructions have to be clearly formulated and executable. <br> <br>";

                    } break;
                }
            } break;
            case 1:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("Sorting_algorithm.png").toString();


                        text = "Ein <b>Sortieralgorithmus</b> ist ein Algorithmus, der dazu dient, eine Menge von Elementen zu sortieren.<br><br> " +
                                "Eine notwendige Bedingung für die Sortierbarkeit ist eine <b>Ordnung</b> der Menge von Elementen. <br> " +
                                "Beispielsweise können Zahlen durch ihre numerische Ordnung geordnet werden. <br>" +
                                "Bei Buchstaben stellt die lexikographische Ordnung eine Möglichkeit zum Sortieren dar. <br>\n" +
                                "Farben können widerum nicht einfach sortiert werden, da hier keine eindeutige Ordnung zwischen Farben vorliegt. <br> <br>" +
                                "Für die Visualisierung von Sortieralgorithmen können Zahlen in Form von unterschiedlich hohen Säulen repräsentiert werden. <br> <br>" +
                                "<img src="+   file + " height=400 width=1100></img>";
                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("Sorting_algorithm.png").toString();

                        text = "A <b>Sorting Algorithm</b> is an algorithm for sorting a set of elements.<br><br> " +
                                "A necessary condition for the sortibality is an <b>order</b> of the set of elements. <br> " +
                                "For instance, numbers can be sorted by their numeric order. <br>" +
                                "Letters can be sorted by their lexicographic order. <br>\n" +
                                "Colours, however, cannot be sorted easily as their is no clear order between colours. <br> <br>" +
                                "In order to visualize a sorting algorithm numbers can be represented by pillars with different heights. <br> <br>" +
                                "<img src="+   file + " height=400 width=1100></img>";
                    } break;
                }
            } break;
            case 2:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("BubbleSort.gif").toString();

                        text = "Es wird grundsätzliches zwischen <b>vergleichsbasierten</b> und <b>nicht-vergleichsbasierten</b> Sortieralgorihmen unterschieden. <br>" +
                                "Vergleichbasierte Sortieralgorithmen zeichnen sich dadurch aus, dass sie paarweise die einzelnen Elemente vergleichen. <br>" +
                                "Nicht-vergleichsbasierte Sortieralgorithmen nutzen dagegen andere Mechanismen zum Sortieren. <br>" +
                                "Die in diesem Programm integrierten Sortierlagorithmen <b>BubbleSort</b>, <b>SelectionSort</b>, <b>InsertionSort</b>, <b>MergeSort</b>, <b>Quicksort</b> und <b>BogoSort</b> sind <b>alle vergleichsbasiert</b>. <br> <br>" +
                                "Zusätzlich dazu unterscheidet man <b>stabile</b> und <b>instabile</b> Sortieralgorithmen. <br>" +
                                "Ein Sortieralgorithmus ist dann stabil, wenn die ursprüngliche Reihenfolge gleichwertiger Elemente auch nach der Sortierung erhalten bleibt.<br>" +
                                "Bei instabilen Sortieralgorithmen kann diese ursprüngliche Reihenfolge verloren gehen <br> <br>" +
                                "Außerdem wird unterschieden zwischen Soriteralgorithmen, die <b>in-place</b> arbeiten und Soriteralgorithmen, die <b>out-of-place</b> arbeiten<br>" +
                                "Wenn ein Algorithmus in-place arbeitet, benötigt er zusätzlich zu der Speicherung der zu sortierenden Elemente selbst nur einen konstanten, von der Eingabemenge unabhängigen Speicher.<br>" +
                                "Wenn ein Algorithmus out-of-place arbeitet, wird dagegegen zusätzlicher Speicher benötigt, der abhängig von der Anzahl der Elemente ist.";
                       //         + "<p style = \"text-align:right;\"> + <img class='alignright' src='"+   file + "'></img>"; // with='500', height='500'
                    } break;
                    case 1:
                    {
                        text = "Fundamentally, <b>comparison-based</b> and <b>not comparison-based</b> sorting algorithms can be differentiated. <br>" +
                                "Comparison-based sorting algorithms are characterized by pairwise coimparsions of single elements. <br>" +
                                "In contrast, not comparison-based sorting algorithms make use of other mechanisms for sorting. <br>" +
                                "The sorting algorithms Sortierlagorithmen <b>BubbleSort</b>, <b>SelectionSort</b>, <b>InsertionSort</b>, <b>MergeSort</b>, <b>Quicksort</b> and <b>BogoSort</b> which are included in this program are <b>all comparison-based</b>. <br> <br>" +
                                "Moreover,<b>stable</b> and <b>unstable</b> sorting algorithms are differentiated. <br>" +
                                "A sorting algorithm is stable, if the original order of equivalent elements stays the same after sorting.<br>" +
                                "When using an instable sorting algorithm this original order might get lost <br> <br>" +
                                "Furthermore one sorting algorithms are distinguished, that work either <b>in-place</b> or <b>out-of-place</b> <br>" +
                                "An algorithm that works in-place only requires a constant amount of storage independent of the amount of elements in addition to the storage required for the elements itself.<br>" +
                                "An algorithm that works out-of-place, however, requires an additional amount of storage dependent on the amount of elements.";

                    } break;
                }
            } break;
            case 3:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("SelectionSort.gif").toString();

                        text = "Die Effizienz eines Sortieralgorithmus wird durch zwei Eigenschaften quantifiziert: Die <b>Laufzeitkomplexität</b> und die <b>Speicherkomplexität</b>.<br><br>" +
                                "Die <b>Laufzeitkomplexität</b> gibt an, wie schnell ein Algorithmus terminiert. " +
                                "Zur Beschreibung der Laufzeit wird das Landau-Symbol O in der Informatik verwendet. Dies ist ein Maß für die Anzahl Schritte, die ein Algorithmus in Abhänigkeit der Eingabemenge benötigt. " +
                                "Der geringstmögliche Zeitaufwand entspricht O(1), also einer von der Eingabemenge unabhängigen Anzahl benötigter Schritte. Mit steigendem Aufwand beschreibt O(log n) einen logarithmischen, O(n) einen linearen, O(n log(n)) einen linearithmischen" +
                                " und O(n^2) einen quadratischen Zusammenhang der Laufzeit mit der Eingabemenge. Es wird zudem neben dem durchschnittlichen Zeitaufwand noch zwischen dem Zeitaufwand des besten Fall und schlechtesten Falles unterschieden." +
                                " So kann es zum Beispiel sein, dass ein Sortieralgorithmus unter idealen Bedingungen (zum Beispiel wenn die Zahlen schon vorsortiert sind) nur einen linearen Zeitaufwand benötigt, im Durchschnitt sowie im schlechtesten Fall" +
                                " jedoch einen quadratischer Zeitaufwand. Die Laufzeit der einzelnen Algorithmen wird in den jeweiligen Erklärungen näher erläutert. <br> <br>" +
                                "Die <b>Speicherkomplexität</b> eines Sortieralgorithmus kann ebenfalls mit dem Landau-Symbol O ausgedrückt werden und beschreibt den benötigten Speicherplatz des Algorithmus. Da jeder Sortieralgorithmus die zu sortierenden" +
                                " Zahlen speichern muss, beschränkt man sich meist darauf, den zusätzlichen Speicherbedarf mit der O-Notation zu erfassen. In diesem Fall drückt die Notation O(n) dann zum Beispiel aus, dass die gesamte Eingabemenge zusätzlich noch einmal extern gespeichert werden muss," +
                                " damit der Algorithmus funktioniert. Wie im vorangegangenen Text erläutert, handelt es sich in diesem Fall um einen Sortieralgorithmus, der out-of-place arbeitet.";
                    } break;
                    case 1:
                    {
                       text =  "The efficiency of a sorting algorithm is quantified by two properties: The <b>time complexity</b> and the <b>space complexity</b>.<br><br>" +
                                "The <b>time complexity</b> quantifies how quickly an algorithm terminates.  " +
                                "To describe the time complexity, the Landau symbol O is used in computer science. This is a measure of the number of steps an algorithm needs in dependence of the amount of input elements. " +
                                "The smallest possible time effort is O(1) meaning an amount of steps that is independent of the amount of input elements. With increasing effort O(log n) describes a logarithmic, O(n) a linear, O(n log(n)) a linearithmic" +
                                " and O(n^2) a quadratic relationship between the necessary time and the amount of input elements. Moreover, the time effort for the best case, the worst case and the average case are differentiated." +
                                " For instance, a sorting algorithm might only require a linear time effort under ideal circumstances (for instance when the numbers are already sorted), but a quadratic effort in the average and worst case." +
                                " The time complexity of the single sorting algorithms is explained in the respective explanations. <br> <br>" +
                                "The <b>space complexity</b> also can be expressed through the Landau symbol O. It describes the necessary space of a sorting algorithm. Since each sorting algorithm has to store the elements" +
                                " to be sorted, the O-notation usually captures the additional space required. In this case, the  notation O(n) means  that the entire amount of input elements have to be stored externally as well for the algorithm to work." +
                                " As already explained in the previous text, this would mean that the sorting algorithm works out-of-space.";

                    } break;
                }
            } break;
            case 4:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("BubbleSort.gif").toString();

                        text = "Bei <b>BubbleSort</b> wird die eingegebene Menge einmal komplett durchlaufen. Dabei werden immer die beiden benachbarten Elemente miteinander verglichen." +
                                " Sollte das linke Element größer sein als das rechte, werden die beiden Elemente getauscht. Dies wird nun immer wieder wiederholt, bis der Algorithmus am Ende der Elemente angekommen ist." +
                                " Nun startet der Algorithmus erneut bei dem vordersten Element und läuft durch die Eingabemenge. Das letzte Element aus dem vorherigen Durchgang muss dabei nicht mehr beachtet werden," +
                                " da dies schon als größtes Element erkannt wurde, und somit richtigerweise ganz rechts steht. Dies wird nun solange wiederholt, bis die Eingabenmenge vollständig sortiert ist. <br><br>" +
                                " <b>Ablauf</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "BubbleSort ist <b>stabil</b>, die Reihenfolge gleichwertiger Elemente ändert sich also nicht durch die Sortierung.<br> <br>" +
                                "BubbleSort arbeitet zudem <b>in-place</b>, da der Algorithmus keinen zusätzlichen Speicher benötigt.<br><br>" +
                                "Sowohl im besten als auch im schlechtesten Fall und somit auch im durchschnittlichen Fall hat BubbleSort bei einer Eingabemenge n eine <b>Laufzeit von O(n^2)</b>, da BubbleSort durch zwei ineinander verschachtelte Schleifen implementiert wird."; // with='500', height='500'
                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("BubbleSort.gif").toString();

                        text = "At the beginning of <b>BubbleSort</b> all input elements are passed. During this pass, the two neighbouring elements are compared each time." +
                                " If the left element is larger than the right element, the elements are swapped. This process is repeated until the algorithm reaches the end of the elements." +
                                " After that the algorithm once again starts at the first element and passes through the elements. This time the last element of the previous pass can be ignored this time " +
                                " as it is already recognized as the largest element and therefore placed on the right-most position. This process is reapeted until the input elements are entirely sorted. <br><br>" +
                                " <b>Procedure</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "BubbleSort is <b>stable</b>, therefore the original order of equivalent elements does not change through the sorting.<br> <br>" +
                                "Moreover, BubbleSort works <b>in-place</b>, as the algorithm does not require any additional space.<br><br>" +
                                "In the best, average and worst case BubbleSort has a <b>time complexity of O(n^2)</b> for n input elements, as BubbleSort is implemented by two nested loops."; // with='500', height='500'
                    } break;
                }
            } break;
            case 5:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("SelectionSort.gif").toString();

                        text = "Bei <b>SelectionSort</b> wird die Menge zu Beginn komplett durchlaufen und dabei das kleinste Element gesucht und an den Anfang platziert. In der Folge teilt sich die Menge in einen bereits sortierten und einen noch unsoriterten Teil auf." +
                                " In jedem nachfolgenden Durchlauf wird das kleinste Element der" +
                                " verbleibenden, unsortierten Menge gesucht und an den sortierten Teil der Liste angehangen. Dadurch sind am Ende schließlich alle Elemente sortiert. <br><br>" +
                                " <b>Ablauf</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "SelectionSort ist  <b>nicht stabil</b>, die Reihenfolge gleichwertiger Elemente kann sich durch die Sortierung also ändern.<br> <br>" +
                                "SelectionSort arbeitet <b>in-place</b>, da der Algorithmus keinen zusätzlichen Speicher benötigt.<br><br>" +
                                "Sowohl im besten als auch im schlechtesten Fall und somit auch im durchschnittlichen Fall hat SelectionSort bei einer Eingabemenge n eine <b>Laufzeit von O(n^2)</b>, da SelectionSort durch zwei ineinander verschachtelte Schleifen implementiert wird."; // with='500', height='500'
                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("SelectionSort.gif").toString();

                        text = "At the beginning of <b>SelectionSort</b> all input elements are passed. During this pass the smallest element is searched and placed at the beginning of the elements subsequently." +
                                " In each following pass the smallest element of the" +
                                " remaining, unsorted elements is searched und appended to the already sorted elements. By that, all elements are sorted at the end. <br><br>" +
                                " <b>Procedure</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "SelectionSort is  <b>instable</b>, therefore the orgignal order of equivalent elements might get lost through the sorting.<br> <br>" +
                                "SelectionSort works <b>in-place</b>, as the algorithm does not require any additional space.<br><br>" +
                                "In the best, average and worst case SelectionSort has a <b>time complexity of O(n^2)</b> for n input elements, as SelectionSort is implemented by two nested loops.";

                    } break;
                }
            } break;
            case 6:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("InsertionSort.png").toString();

                        text = "Bei <b>InsertionSort</b> wird zu Beginn das erste Element als sortiert angenommen, sodass es eine sortierten Teil der Menge und einen unsortierten Teil der Menge gibt." +
                                " Anschließend wird jeweils das erste Element aus dem unsortierten Teil entnommen und an der korrekten Position des sortierten Teils eingefügt. Hierbei müssen eventuell vorhandene Elemente des bereits sortierten Teils geeignet nach rechts verschoben werden. <br><br>" +
                                " <b>Ablauf</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "InsertionSort ist <b>stabil</b>, die Reihenfolge gleichwertiger Elemente ändert sich also nicht durch die Sortierung.<br> <br>" +
                                "InsertionSort arbeitet <b>in-place</b>, da der Algorithmus lediglich einen weiteren Speicherplatz unabhängig von der Menge der Eingabeelemente benötigt.<br><br>" +
                                "Im besten Fall, wenn alle Elemente bereits sortiert sind, beträgt die Laufzeit <b>O(n)</b>, da keine Elemente mehr links eingefügt werden müssen. " +
                                "Im durchschnittlichen sowie schlechtesten Fall beträgt die Laufzeit dagegen <b>O(n^2)</b>."; // with='500', height='500'

                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("InsertionSort.png").toString();

                        text = "At the beginning of <b>InsertionSort</b> the first element is assumed to be sorted resulting in a sorted part of the elements and an unsorted part of the elements." +
                                " Subsequently, the first element of the unsorted part is extracted and inserted into the correct position of the sorted part. For that some elements of the already sorted part might have to be shifted to the right. <br><br>" +
                                " <b>Procedure</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "InsertionSort is <b>stable</b>, therefore the original order of equivalent elements does not change through the sorting.<br> <br>" +
                                "InsertionSort works <b>in-place</b>, as the algorithm only requires one additional memory space independent of the amount of elements.<br><br>" +
                                "In the best case, when the elements are already sorted, the time complexity is <b>O(n)</b>, since no elements have to be inserted to the left. " +
                                "However, in the average case and the worst case the time complexity is <b>O(n^2)</b>."; // with='500', height='500'

                    } break;
                }
            } break;
            case 7:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("MergeSort.png").toString();

                        text = "<b>MergeSort</b> ist ein Divide-and-Conquer-Sortieralgorithmus.<br><br>" +
                                "Divide-and-Conquer beschreibt das Prinzip, dass ein gegebenes Problem in mehrere getrennte Teilprobleme aufgeteilt und einzeln gelöst wird und die Lösung des Problems aus den Teillösungen zusammengesetzt wird.<br><br>" +
                                " Bei MergeSort wird die Liste der Länge n mit den zu sortierenden Elementen solange rekursiv halbiert, bis n einelementige Teillisten entstehen." +
                                " Nun folgt der eigentliche Sortiervorgang: Man nimmt immer zwei Teillisten und fügt diese zu einer neuen sortierten Teilliste zusammen, indem man immer das kleinste Element der beiden Listen streicht und in die neue Teilliste einfügt." +
                                " Dieses Verfahren muss dann solange wiederholt werden, bis es nur noch eine Teilliste gibt.<br><br>" +
                                "<b>Ablauf</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "MergeSort ist <b>stabil</b>, die Reihenfolge gleichwertiger Elemente ändert sich also nicht durch die Sortierung.<br> <br>" +
                                "MergeSort arbeitet <b>out-of-place</b>, da zum Zusammenführen zweier Teillisten die Listen zunächst extern gespeichert werden müssen, was in einem <b>zusätzlichen Speicherbedarf von O(n)</b> resultiert.<br><br>" +
                                "Sowohl im besten als auch im schlechtesten Fall und somit auch im durchschnittlichen Fall hat MergeSort bei einer Eingabemenge n eine <b>Laufzeit von O(n*log(n))</b>." +
                                " Das liegt daran, dass für das sukzessive Halbieren ein logarithmischer Aufwand und für das Zusammenführen der Teillisten jeweils ein linearer Aufwand erforderlich ist.";
                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("MergeSort.png").toString();

                        text = "<b>MergeSort</b> is a Divide and Conquer sorting algorithm.<br><br>" +
                                "Divide and conquer is a principle, according to which a given problem is divided into sepearate partial problems. The solution of the problem is made up of the solutions of the partial problems.<br><br>" +
                                " MergeSort works by recursively halving the list of length n of the elements to be sorted, until n partial lists with one element each are formed." +
                                " After that the actual sorting takes place: Two partial lists are always merged into a new sorted partial list by repeatedly striking off the smallest element of both lists and inserting this element into the new partial list." +
                                " This process is repeated until there is only one partial list at the end.<br><br>" +
                                "<b>Procedure</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "MergeSort is <b>stable</b>, therefore the original order of equivalent elements does not change through the sorting.<br> <br>" +
                                "MergeSort works <b>out-of-place</b>, as the merge of two partial lists makes it necessary that these lists are stored externally, which results in an <b>additional space requirement of O(n)</b> <br><br>" +
                                "In the best, average and worst case SelectionSort has a <b>time complexity of O(n*log(n))</b> for n input elements." +
                                " The reason for this is, that the sucessive halving requrires a logarithmic effort and each merge of partial lists requires a linear effort.";

                    } break;
                }
            } break;
            case 8:
            {
                switch(language)
                {
                    case 0:
                    {
                        String file = this.getClass().getResource("QuickSort.png").toString();

                        text = "<b>QuickSort</b> ist ebenfalls ein Divide-and-Conquer-Sortieralgorithmus.<br><br> Bei QuickSort wird jeweils ein Pivot-Element gewählt, beispielsweise das letzte Element." +
                                " Auf Basis dieses Pivot-Elements wird eine Teilliste mit kleineren und eine Teilliste mit größeren Elementen ermittelt. Das Pivot-Element selbst wird zwischen diesen beiden Teillisten platziert und befindet" +
                                " sich dadurch bereits an der richtigen Position in der Sortierung. Für die Teilliste mit kleineren und die Teillsite mit größeren Elementen wird erneut ein Pivot-Element" +
                                " ausgewählt sowie Teillisten auf Basis dieses Pivot-Elements ermittelt. Dadurch wird die Menge immer weiter aufgeteilt, bis sich alle Elemente an der richtigen Stelle in der Sortierung befinden.<br><br>" +
                                 " <b>Ablauf</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "QuickSort ist <b>nicht stabil</b>,  die Reihenfolge gleichwertiger Elemente kann sich durch die Sortierung also ändern.<br> <br>" +
                                "QuickSort arbeitet <b>in-place</b>, da der Algorithmus keinen zusätzlichen Speicher benötigt.<br><br>" +
                                "Im besten sowie durchschnittlichen Fall beträgt die Laufzeit <b>O(n*log(n))</b>. " +
                                "Im schlechtesten Fall beträgt die Laufzeit dagegen <b>O(n^2)</b>. Der schlechtmöglichste Fall tritt dann ein, wenn als Pivot-Element immer das größte oder das " +
                                "kleinste Element gewählt wird, da dadurch keine effiziente Aufteilung in (annähernd gleich große) Teillisten erfolgen kann."; // with='500', height='500'

                    } break;
                    case 1:
                    {
                        String file = this.getClass().getResource("QuickSort.png").toString();

                        text = "<b>QuickSort</b> is a Divide and-Conquer sorting algorithm as well.<br><br> The central idea of QuickSort is the selection of a pivot element, for example the last element." +
                                " Based on this pivot element a partial list with smaller elements and a partial list with larger elements is ascertained. The pivot element itself is placed between these partial lists and therefore" +
                                " already is at the correct position in the sorting. In the partial list with smaller elements and the partial list with larger elements " +
                                " a pivot element is selected as well and based on that further partial lists are ascertained. Consequently, all elements are increasingly subdivided until each element is at the correct position in the sorting.<br><br>" +
                                " <b>Procedure</b> <br><img src='"+   file + "'></img> <br> <br>" +
                                "QuickSort is  <b>instable</b>, therefore the original order of equivalent elements might get lost through the sorting.<br> <br>" +
                                "QuickSort works <b>in-place</b>, as the algorithm only requires one additional memory space independent of the amount of elements.<br><br>" +
                                "In the best and average case the time complexity of SelectionSort is <b>O(n*log(n))</b>. " +
                                "However, in the worst case the time complexity is <b>O(n^2)</b>. The worst case occurs if either the smallest or the largest element" +
                                "are always chosen as the pivot element since this in these cases an efficient division in (roughly equally sized) partial lists is not possible."; // with='500', height='500'

                    } break;
                }
            } break;
            case 9:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "<b>BogoSort</b> ist ein bewusst ineffizienter Sortieralgorithmus. BogoSort fungiert als Negativbeispiel und soll die Effizienz der anderen Sortieralgorithmen hervorheben.<br><br> " +
                                "Bei BogoSort wird jeweils geprüft, ob die Menge der Elemente bereits sortiert ist." +
                                " Wenn dies nicht der Fall ist, wird die Menge beliebig neu gemischt, also zum Beispiel zwei zufällig ausgewählte Elemente getauscht. Dieses Logik wird solange wiederholt, bis die Menge sortiert ist.  <br><br>    " +
                                "QuickSort ist <b>nicht stabil</b>,  die Reihenfolge gleichwertiger Elemente kann sich durch die Sortierung also ändern.<br> <br>" +
                                "BogoSort arbeitet <b>in-place</b>, da der Algorithmus keinen zusätzlichen Speicher benötigt.<br><br>" +
                                "Im besten Fall beträgt die Laufzeit <b>O(n)</b>, wenn die Menge bereits sortiert wird. " +
                                "Im durchschnittlichen Fall beträgt die Laufzeit <b>O(n*n!)</b>. " +
                                " Im schlechtesten Fall wird BogoSort die Menge der Elemente niemals sortieren, die Laufzeit ist in diesem Fall <b>unendlich</b>."; // with='500', height='500'


                    } break;
                    case 1:
                    {
                        text = "<b>BogoSort</b> is a deliberately inefficient sorting algorithm. BogoSort acts as a negative example and should underscore the efficiency of other sorting algorithms.<br><br> " +
                                "BogoSort repeatedly checks if all elements are already sorted." +
                                " If this is not the case the elements are arbitrarily shuffled, for instance by swapping two random elements. This logic is repeated until all elements are sorted.  <br><br>    " +
                                "BogoSort is  <b>instable</b>, therefore the original order of equivalent elements might get lost through the sorting.<br> <br>" +
                                "BogoSort works <b>in-place</b>, as the algorithm only requires one additional memory space independent of the amount of elements.<br><br>" +
                                "In the best case BogoSort has a time complexity of <b>O(n)</b>, when the elements are already sorted in the beginning. " +
                                "In the average case the time complexity is <b>O(n*n!)</b>. " +
                                " In the worst case, BogoSort will never sort all elements. In this case, the time complexity is <b>infinite</b>."; // with='500', height='500'

                    } break;
                }
            } break;
        }

        return text;
    }


    /**
     * Returns the sources of the selected topic in the selected language
     * @param topic numerical representation of the description text to be displayed
     * @param language 0 for German, 1 for English
     * @return sources of the selected topic in the selected language
     */
    public String getSources(int topic, int language)
    {
        String text="";

        switch (topic)
        {
            case 0:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  Liste der Autoren</a>";
                    } break;
                    case 1:
                    {
                        text = "Source of the text: "
                                +" <a href = 'https://wiki.zum.de/wiki/Algorithmus' > Algorithmus</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=Algorithmus&action=history'>  list of authors</a>";
                    } break;
                }
            } break;
            case 1:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> <br>" +
                                "Quelle des Bildes: Sortieralgorithmus von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a>";

                    } break;
                    case 1:
                    {
                        text = "Source of the text: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> <br>" +
                                "Source of the image: Sortieralgorithmus von Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a>";
                    } break;
                }
            } break;
            case 2:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quellen des Textes: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> & " +
                                "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  Liste der Autoren</a> <br>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> & " +
                                "<a href = 'https://de.wikibooks.org/wiki/Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick' > Sortieralgorithmen/ Sortierverfahren im Überblick</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Sortieralgorithmen/_Sortierverfahren_im_%C3%9Cberblick&action=history'>  list of authors</a> <br>";
                    } break;
                }
            } break;
            case 3:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quellen des Textes: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> & " +
                                " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  Liste der Autoren</a> & " +
                                "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  Liste der Autoren</a>";
                    }
                    break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> & " +
                                " <a href = 'https://de.wikipedia.org/wiki/Platzkomplexit%C3%A4t' > Platzkomplexität</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Platzkomplexit%C3%A4t&action=history'>  list of authors</a> & " +
                                "<a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'> list of authors</a>";
                    } break;
                }
            } break;
            case 4:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quellen des Textes: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> vom ZUM-Wiki unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  Liste der Autoren</a> & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  Liste der Autoren</a> <br>" +
                                "Quelle des Bildes: <a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> von Swfung8 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://wiki.zum.de/wiki/PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren' > Sortierverfahren</a> from ZUM-Wiki under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://wiki.zum.de/index.php?title=PH_Heidelberg/Didaktik_der_ITG/Sortierverfahren&action=history'>  list of authors</a> & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Bubblesort#Stabilit%C3%A4t' > Algorithmen und Datenstrukturen in C/ Bubblesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Bubblesort&action=history'>  list of authors</a> <br>" +
                                "Source of the image: <a href = 'https://commons.wikimedia.org/wiki/File:Bubble-sort-example-300px.gif' > Bubble-sort-example-300px.gif</a> by Swfung8 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";

                    } break;
                }
            } break;
            case 5:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) <br>" +
                                "Quelle des Bildes: <a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> von Joestape89 unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";

                    } break;
                    case 1:
                    {
                        text = "Source of the text: "
                                +" <a href = 'https://www.geeksforgeeks.org/selection-sort/' > Selection Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) <br>" +
                                "Source of the image: <a href = 'https://commons.wikimedia.org/wiki/File:Selection-Sort-Animation.gif' > Selection-Sort-Animation.gif</a> by Joestape89 under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";


                    } break;
                }
            } break;
            case 6:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  Liste der Autoren</a> <br>" +
                                "Quelle des Bildes: <a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> von MrDrBob unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://www.geeksforgeeks.org/insertion-sort/' > Insertion Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Insertionsort' > Algorithmen und Datenstrukturen in C/ Insertionsort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Insertionsort&action=history'>  list of authors</a> <br>" +
                                "Source of the image: <a href = 'https://commons.wikimedia.org/wiki/File:Insertion-sort.svg' > Insertion-sort.svg</a> by MrDrBob under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a>";

                    } break;
                }
            } break;
            case 7:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  Liste der Autoren</a> <br>" +
                                "Quelle des Bildes: <a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> von VineetKumar unter <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://www.geeksforgeeks.org/merge-sort/' > Merge Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Mergesort' > Algorithmen und Datenstrukturen in C/ Mergesort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Mergesort&action=history'>  list of authors</a> <br>" +
                                "Sources of the image: <a href = 'https://commons.wikimedia.org/wiki/File:Merge_sort_algorithm_diagram.svg' > Merge sort algorithm diagram.svg</a> by VineetKumar under <a href = 'https://creativecommons.org/publicdomain/zero/1.0/deed.de'> Public Domain</a>";

                    } break;
                }
            } break;
            case 8:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  Liste der Autoren</a> <br>" +
                                "Quelle des Bildes: QuickSort von Aaron Hümmecke unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://www.geeksforgeeks.org/quick-sort/' > Quick Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & " +
                                " <a href = 'https://de.wikibooks.org/wiki/Algorithmen_und_Datenstrukturen_in_C/_Quicksort' > Algorithmen und Datenstrukturen in C/ Quicksort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikibooks.org/w/index.php?title=Algorithmen_und_Datenstrukturen_in_C/_Quicksort&action=history'>  list of authors</a> <br>" +
                                "Source of the image: QuickSort by Aaron Hümmecke under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a>";
                    } break;
                }
            } break;
            case 9:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle des Textes: "
                                +" <a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & " +
                                " <a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> von Wikibooks unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  Liste der Autoren</a> <br>";

                    } break;
                    case 1:
                    {
                        text = "Sources of the text: "
                                +" <a href = 'https://www.geeksforgeeks.org/bogosort-permutation-sort/' > BogoSort or Permutation Sort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & " +
                                " <a href = 'https://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Bogosort' > Algorithm Implementation/Sorting/Bogosort</a> from Wikibooks under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://en.wikibooks.org/w/index.php?title=Algorithm_Implementation/Sorting/Bogosort&action=history'>  list of authors</a> <br>";

                    } break;
                }
            } break;
            case 10:
            {
                switch(language)
                {
                    case 0:
                    {
                        text = "Quelle der Tabelle: "
                                +" <a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & "
                                + " <a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> von GeeksforGeeks unter <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (bearbeitet und übersetzt) & "
                                + " <a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> von Wikipedia unter <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (bearbeitet und übersetzt) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  Liste der Autoren</a> <br>";

                    } break;
                    case 1:
                    {
                        text = "Source of the table: "
                                +" <a href = 'https://www.geeksforgeeks.org/insertion-sort/' >InsertionSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & "
                                + " <a href = 'https://www.geeksforgeeks.org/quick-sort/' >QuickSort</a> from GeeksforGeeks under <a href = 'https://creativecommons.org/licenses/by-sa/4.0/'> CC BY-SA 4.0</a> (edited and translated) & "
                                + " <a href = 'https://de.wikipedia.org/wiki/Sortierverfahren' > Sortierverfahren</a> from Wikipedia under <a href = 'https://creativecommons.org/licenses/by-sa/3.0/'> CC BY-SA 3.0</a> (edited and translated) - <a href = 'https://de.wikipedia.org/w/index.php?title=Sortierverfahren&action=history'>  list of authors</a> <br>";

                    } break;
                }
            } break;
        }

        return text;
    }

    /**
     * Reference to the MainPanel
     * @return reference to the mainPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Method for switching the language of the elements
     */
    public void updateLanguageResources()
    {
        textSelector.removeAllItems();


        textSelector.addItem(getTopic(0,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(1,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(2,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(3,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(4,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(5,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(6,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(7,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(8,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(9,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        textSelector.addItem(getTopic(10,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));

        description.setText(getText(0,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));
        licenseNote.setText(getSources(0,LanguageManager.getLanguageManagerInstance().getCurrentLocale().toString().equals("de")?0:1));

    }


}

