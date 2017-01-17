package me.jp78.spambot.Base.GUI.Impl;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import me.jp78.spambot.Base.Alts.AltService;
import me.jp78.spambot.Base.Alts.Impl.UserPass.Wrapper;
import me.jp78.spambot.Base.Alts.LoadedAccount;
import me.jp78.spambot.Base.GUI.GuiService;
import me.jp78.spambot.Base.Utils.UserPassAltService;
import me.jp78.spambot.Plugins.SpamMod;
import me.jp78.spambot.SpamBot;
import ro.fortsoft.pf4j.PluginState;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Singleton
@Slf4j
public class BaseGUI implements GuiService
{

    Integer failed = 0;
    private ListView<String> loadedAltsList;

    @Override
    @SuppressWarnings ( "unchecked" )
    public void displayGUI(Stage primaryStage)
    {
        try
        {

            System.out.println("Displayin GUI...");
            TabPane root = FXMLLoader.load(BaseGUI.class.getClassLoader().getResource("default.fxml"));
            primaryStage.setResizable(false);

            Tab first = root.getTabs().stream().filter(notNullID("acclistv")).findAny().orElseThrow(NullPointerException::new);
            Tab plugins = root.getTabs().stream().filter(notNullID("plugins")).findAny().orElseThrow(NullPointerException::new);
            root.tabClosingPolicyProperty().setValue(TabPane.TabClosingPolicy.SELECTED_TAB);
            first.setClosable(false);
            plugins.setClosable(false);
            loadedAltsList = (ListView) findFromTab(first, "LoadedAcc");
            loadedAltsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            Button unload = (Button) findFromTab(first, "UnlDAcc");
            log.debug(String.valueOf(((AnchorPane) first.getContent()).getWidth()));
            log.debug(String.valueOf(((AnchorPane) first.getContent()).getHeight()));

            log.debug(String.format("Tab Height Max %s Min %s", root.getTabMaxHeight(), root.getTabMinHeight()));
            log.debug(String.format("Tab Width Max %s Min %s", root.getTabMaxWidth(), root.getTabMinWidth()));
            initPlugins(root,plugins);

            unload.setOnAction(e ->
            {
                try
                {
                    AltService service = SpamBot.instance().getProviderSystem().provide(AltService.class).orElseThrow(() -> new NullPointerException("Cannot find an instance!"));
                    String s = loadedAltsList.getSelectionModel().getSelectedItem();
                    service.removeAlt(service.getByUsername(s));
                    loadedAltsList.getItems().remove(s);
                }
                catch (Exception e1)
                {
                    log.error("Error occured whilst removing alt", e1);
                }
            });
            if (SpamBot.instance().getProviderSystem().provide(AltService.class).get() instanceof UserPassAltService)
            {
                initUserPass(root);
            }
            else
            {
                root.getTabs().removeIf(t -> t.getId().equals("userpasstab"));
            }
            Scene scene = new Scene(root, 664, 497);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            log.error("Could not init", e);
        }
        System.out.println("derp");
    }


    public static Node findFromTab(Tab tab, String ID)
    {
        AnchorPane tabAnchor = ((AnchorPane) tab.getContent());
        return tabAnchor.getChildrenUnmodifiable().stream().filter(n ->
        {
            if (n.getId() == null)
                return false;
            return n.getId().equals(ID);
        }).findAny().orElseThrow(() -> new NullPointerException("ID: " + ID + " Could not be found!"));
    }

    private void initUserPass(TabPane root)
    {
        Tab UserPass = root.getTabs().stream().filter(notNullID("userpasstab")).findAny().orElse(null);
        Button Load = (Button) findFromTab(UserPass, "LoadBTN");
        TextField filePath = (TextField) findFromTab(UserPass, "AccFile");
        ListView<String> listView = (ListView<String>) findFromTab(UserPass, "Userlist");
        loadedAltsList.getItems().addListener((ListChangeListener<String>) c ->
        {
            c.next();
            listView.getItems().removeAll(c.getRemoved());
        });
        ProgressBar bar = (ProgressBar) findFromTab(UserPass, "LoadProgress");
        Load.setOnAction(e ->
        {
            AltService service = SpamBot.instance().getProviderSystem().provide(AltService.class).get();
            Wrapper<List<String>> filelines = new Wrapper<>();
            try
            {
                filelines.setValue(java.nio.file.Files.readAllLines(new File(filePath.getText()).toPath()));
            }
            catch (IOException e1)
            {
                log.error("Could not find the specified file!", e1);
                return;
            }
            Map<String, String> userPass = Maps.newHashMap();
            filelines.getValue().forEach(s ->
            {
                String[] strings = s.split(":");
                userPass.put(strings[0], strings[1]);
            });
            userPass.entrySet().forEach(s ->
            {
                try
                {
                    service.addAlt(s.getKey(), s.getValue());
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                    failed++;
                    return;
                }
                LoadedAccount loadedAccount;
                try
                {
                    loadedAccount = service.getByEmail(s.getKey());
                }
                catch (Exception e1)
                {
                    log.error("Could not get loaded account!", e1);
                    return;
                }
                listView.getItems().add(loadedAccount.getAltDetails().get().getUsername());
                loadedAltsList.getItems().add(loadedAccount.getAltDetails().get().getUsername());
                bar.autosize();
                bar.setProgress((SpamBot.instance().getProviderSystem().provide(AltService.class).get().getLoadedAccounts().size() + failed) / filelines.getValue().size());

            });
        });
    }

    @Override
    public ListView<String> loadedalts()
    {
        return loadedAltsList;
    }

    @SuppressWarnings ( "unchecked" )
    private void initPlugins(TabPane pane, Tab plugins)
    {
        ListView<String> plugs = (ListView) findFromTab(plugins, "pluginslist");
        SpamBot.instance().getManager().getPlugins(PluginState.CREATED).forEach(s ->
        {
            SpamBot.instance().injector().injectMembers(s.getPlugin());
            log.debug("Injected & Starting Plugin: " + s.getPluginId());
            SpamBot.instance().getManager().startPlugin(s.getPluginId());
            plugs.getItems().add(s.getPluginId());
        });
        plugs.setOnMouseClicked(e ->
        {
            if (e.getClickCount() == 2)
            {
                SpamMod mod = (SpamMod) SpamBot.instance().getManager().getPlugin(plugs.getSelectionModel().getSelectedItem()).getPlugin();
                if (pane.getTabs().stream().anyMatch(t -> t.getText().equals(mod.getWrapper().getPluginId())))
                    return;
                Pane p;
                try
                {
                    p = mod.displayGUI(664D, 468D);
                }
                catch (Exception e1)
                {
                    log.error("Could not init gui!", e1);
                    p = null;
                }
                if (p == null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Plugin does not have a gui!");
                    alert.setHeaderText("No GUI!");
                    alert.setContentText("Sorry! It appears the plugin you tried to open doesn't have a GUI :/ Talk to the dev about that!");
                    alert.showAndWait();
                    return;
                }
                Tab net = new Tab(mod.getWrapper().getPluginId());
                net.setClosable(true);
                net.setOnClosed(e1 -> mod.onGuiClose());
                net.setContent(p);
                pane.getTabs().add(net);
            }
        });
    }
}
