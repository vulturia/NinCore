package me.ninjoh.nincore.command;


import me.ninjoh.nincore.api.util.TranslationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandBase implements me.ninjoh.nincore.api.command.CommandBase
{
    private final String name;

    private String staticDescription;
    private String descriptionKey;
    private String descriptionBundleBaseName;

    private String requiredPermission;
    private String usage;

    private List<String> aliases = new ArrayList<>();

    boolean useStaticDescription;


    public CommandBase(String name, boolean useStaticDescription, String staticDescription, String descriptionKey, String descriptionBundleBaseName, String requiredPermission, String usage, List<String> aliases)
    {
        this.name = name;
        this.staticDescription = staticDescription;
        this.useStaticDescription = useStaticDescription;
        this.descriptionKey = descriptionKey;
        this.descriptionBundleBaseName = descriptionBundleBaseName;
        this.requiredPermission = requiredPermission;
        this.usage = usage;
        this.aliases = aliases;
    }


    @Override
    public String getName()
    {
        return name;
    }


    public String getName(boolean forceLowercase)
    {
        if(forceLowercase) return this.name.toLowerCase();
        else return this.name;
    }


    @Override
    public boolean hasDescription()
    {
        if(useStaticDescription)
        {
            return staticDescription != null;
        }
        else
        {
            return ((descriptionKey != null) && (descriptionBundleBaseName != null));
        }
    }


    @Override
    public String getDescription()
    {
        if(useStaticDescription)
        {
            return staticDescription;
        }
        else
        {
            return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(descriptionBundleBaseName), descriptionKey);
        }
    }


    @Override
    public String getDescription(Locale locale)
    {
        if(useStaticDescription)
        {
            return staticDescription;
        }
        else
        {
            return TranslationUtils.getStaticMsg(ResourceBundle.getBundle(descriptionBundleBaseName, locale), descriptionKey);
        }
    }


    @Override
    public List<String> getAliases(boolean withMainAlias)
    {
        if(withMainAlias)
        {
            List<String> copy = new ArrayList<>(this.aliases);
            copy.add(0, this.getName(true));

            return copy;
        }

        return new ArrayList<>(aliases);
    }


    @Override
    public boolean hasAliases()
    {
        return !aliases.isEmpty();
    }


    @Override
    public boolean requiresPermission()
    {
        return this.requiredPermission != null;
    }


    @Override
    public String getRequiredPermission()
    {
        return this.requiredPermission;
    }


    @Override
    public String getUsage()
    {
        return this.usage;
    }


    @Override
    public void setUsage(String s)
    {
        this.usage = s;
    }


    @Override
    public boolean descriptionIsStatic()
    {
        return useStaticDescription;
    }
}
