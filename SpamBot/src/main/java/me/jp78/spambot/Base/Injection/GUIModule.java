package me.jp78.spambot.Base.Injection;

import com.google.inject.AbstractModule;
import me.jp78.spambot.Base.GUI.GuiService;
import me.jp78.spambot.Base.GUI.Impl.BaseGUI;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public class GUIModule extends AbstractModule
{
    public void configure()
    {
        bind(GuiService.class).to(BaseGUI.class);
    }
}
