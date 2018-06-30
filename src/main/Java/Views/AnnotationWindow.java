package main.Java.Views;

import main.Java.Util.Triplet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is used for displaying the functions of particular numbers (for instance variables) during the visualization
 */
public class AnnotationWindow extends JPanel
{
    /**
     * Graphics2d object
     */
    private Graphics2D g2;

    /**
     * Lists of annotations consisting of the left border of the text, the right border of the text as well as the text itself to be displayed
     */
    private ArrayList<Triplet<Float, Float, String>> annotations;

    /**
     * Constructor of AnnotationWindow
     */
    public AnnotationWindow()
    {
        setVisible(true);

        annotations = new ArrayList<Triplet<Float, Float, String>>();
    }

    /**
     * Add a new annotation to the AnnotationWindow
     * @param leftBorder left border of the annotation
     * @param rightBorder right border of the annotaiton
     * @param text text to be displayed
     */
    public void addAnnotation(float leftBorder, float rightBorder, String text)
    {
        annotations.add(new Triplet<>(leftBorder, rightBorder, text));
    }

    /**
     * Remove all annotations of the AnnotationWindow
     */
    public void removeAllAnotations()
    {
        annotations.clear();
    }

    /**
     * Method to draw the Annotation Window
     * @param g Graphics object
     */
    protected void paintComponent(Graphics g)
    {
        g2 = (Graphics2D) g;
        super.paintComponent(g);

        for (Triplet<Float, Float, String> annotation: annotations)
            drawCenteredString(g, annotation.getRight(), new Rectangle(Math.round(annotation.getLeft()), 0, Math.round(annotation.getMiddle()-annotation.getLeft()), 9), new Font(Font.DIALOG, 0, 12));
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
        // Determine the Y coordinate for the text
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
}