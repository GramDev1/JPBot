package me.jp78.spambot.Base.GUI;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.util.function.Predicate;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public interface GuiService
{

    /**
     * Display the spambots GUI
     */
    ListView<String> loadedalts();
    void displayGUI(Stage primaryStage);


    default Node find(Parent root, String ID)
    {
        return root.getChildrenUnmodifiable().stream().filter(n ->
        {
            if (n.getId() == null)
                return false;
            return n.getId().equals(ID);
        }).findAny().orElseThrow(() -> new NullPointerException("ID: " + ID + " Could not be found!"));
    }

    default Predicate<Tab> notNullID(String id)
    {
        return node ->
        {
            if(node.getId() == null) return false;
            return node.getId().equals(id);
        };
    }
}
