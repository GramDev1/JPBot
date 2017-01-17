package me.jp78.spambot.Plugins;

import javafx.scene.layout.Pane;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginException;
import ro.fortsoft.pf4j.PluginWrapper;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public abstract class SpamMod extends Plugin
{

    public SpamMod(PluginWrapper wrapper)
    {
        super(wrapper);
    }

    @Override
    public void start() throws PluginException
    {
       onLoad();
    }

    @Override
    public void stop() throws PluginException
    {
       onUnload();
    }

    /**
     * Called when the plugin is loaded. Please note, injectable materials will be injected here! This is when you should do the "main" logic of your plugin, not in the declaration.
     */
    public abstract void onLoad();

    /**
     * If your plugin has a GUI, return A tab that contains your gui.. Ensure that your Tab conforms with the given width and height so it doesn't cause errors!
     * Please note, due to how this sets a plugin classpath, you must set the controller manually. Example
     * <pre>
     * {@Code
     * FXMLoader loader = new FXMLoader(path);
     * loader.setController(new MyController);
     * return loader.load;
     * }
     * </pre>
     * @return The Tab
     *
     */
    public abstract Pane displayGUI(Double width, Double height) throws Exception;
    public void onGuiClose(){}
    public abstract void onUnload();
}
