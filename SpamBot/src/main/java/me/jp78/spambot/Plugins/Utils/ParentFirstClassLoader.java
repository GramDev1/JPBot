package me.jp78.spambot.Plugins.Utils;

import lombok.extern.slf4j.Slf4j;
import ro.fortsoft.pf4j.PluginClassLoader;
import ro.fortsoft.pf4j.PluginDescriptor;
import ro.fortsoft.pf4j.PluginManager;

/**
 * This file was created by @author thejp for the use of
 * SpamBot. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Slf4j
public class ParentFirstClassLoader extends PluginClassLoader
{

    public ParentFirstClassLoader(PluginManager p1, PluginDescriptor pluginDescriptor, ClassLoader parent)
    {
        super(p1, pluginDescriptor, parent);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException
    {
        try
        {
            //Parent first to avoid certain conflicts, like logging!
           return getParent().loadClass(className);
        }
        catch (ClassNotFoundException e)
        {
            //This doesn't matter to us, because we can look in plugin dependency. If that fails, THEN we have an issue;
        }
        return super.loadClass(className);

    }
}
