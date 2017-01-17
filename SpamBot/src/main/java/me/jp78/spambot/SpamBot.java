package me.jp78.spambot;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import me.jp78.spambot.Base.Alts.AltService;
import me.jp78.spambot.Base.GUI.GuiService;
import me.jp78.spambot.Base.Injection.EventModule;
import me.jp78.spambot.Base.Injection.GUIModule;
import me.jp78.spambot.Base.Injection.ProviderModule;
import me.jp78.spambot.Base.Providers.System.ProviderSystem;
import me.jp78.spambot.Base.Utils.UserPassAltService;
import me.jp78.spambot.Plugins.Utils.GradleDevPath;
import me.jp78.spambot.Plugins.Utils.ParentFirstClassLoader;
import ro.fortsoft.pf4j.*;

/**
 * This file was created by @author thejp for the use of
 * l33tTyper. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Sunday, January, 2017
 */
@Singleton
public class SpamBot extends Application
{

    @Getter
    @Inject
    private EventBus bus;

    @Getter
    @Inject
    private GuiService GUI;

    @Getter
    @Inject
    private ProviderSystem providerSystem;

    @Getter
    private PluginManager manager;

    private Injector injector;

    private static SpamBot instance;
    private Stage prim;


    public static SpamBot instance()
    {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.out.println("Started!");
        injector = Guice.createInjector(new GUIModule(), new ProviderModule(), new EventModule());
        injector.injectMembers(this);
        prim = primaryStage;
        instance = this;
        initG();
    }

    private void initG()
    {
        System.out.println("Init G called!");
        providerSystem.setProvider(AltService.class, new UserPassAltService(), 0);
        manager = new DefaultPluginManager()
        {
            @Override
            public RuntimeMode getRuntimeMode()
            {
                return RuntimeMode.DEVELOPMENT;
            }

            @Override
            protected PluginClasspath createPluginClasspath()
            {
                if (RuntimeMode.DEVELOPMENT.equals(getRuntimeMode()))
                {
                    return new GradleDevPath();
                }

                return new PluginClasspath();
            }

            @Override
            protected PluginClassLoader createPluginClassLoader(PluginDescriptor pluginDescriptor)
            {
                return new ParentFirstClassLoader(this, pluginDescriptor, this.getClass().getClassLoader());
            }
        };
        manager.loadPlugins();
        // manager.getPlugins().forEach(s -> injector.injectMembers(s.getPlugin()));
        GUI.displayGUI(prim);

    }

    public Injector injector()
    {
        return injector;
    }
}
