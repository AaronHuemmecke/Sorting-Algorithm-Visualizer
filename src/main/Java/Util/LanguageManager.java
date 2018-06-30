package main.Java.Util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class manages the different languages that the program supports
 */
public class LanguageManager
{
    /**
     * Currently activated Locale
     */
    private Locale currentLocale;

    /**
     * Singleton Instance of the LanguageManager so that it can be used from everywhere
     */
    private static LanguageManager languageManager;

    /**
     * Constructor for the LanguageManager. The current Locale is set by default
     */
    private LanguageManager()
    {
            this.currentLocale = new Locale(Locale.getDefault().getLanguage());
    }

    /**
     * Getter-Method for the Singleton-Instance of LanguageManager
     * @return Singleton-Instance of LanguageManager
     */
    public static LanguageManager getLanguageManagerInstance() {
        if(languageManager == null) {
            languageManager = new LanguageManager();
        }
        return languageManager;
    }


    /**
     * returns the current Locale
     * @return current Locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * sets the current Locale
     * @param currentLocale new Locale
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }


    /**
     * returs the ResourceBundle for a specific Window
     * @param window Window whose resources are required
     * @return ResourceBundle for a specific Window
     */
    public ResourceBundle getResourceBundle(String window) {
       return ResourceBundle.getBundle("main.resources.language." + window, this.currentLocale);
    }
}
