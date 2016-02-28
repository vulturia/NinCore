package me.Ninjoh.NinCore.util;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationUtils
{
    public static String transWithArgs(String bundleName, Locale locale, Object[] args, String msg)
    {
        ResourceBundle messages = ResourceBundle.getBundle(bundleName, locale);
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);


        formatter.applyPattern(messages.getString("commandHelp.commandHelpFor"));
        return formatter.format(args);
    }

    public static String getStaticMsg(ResourceBundle bundle, String msg)
    {
        return bundle.getString(msg);
    }
}
