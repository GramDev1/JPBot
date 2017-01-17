package me.jp78.spambot.Base;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * This file was created by @author thejp for the use of
 * SpamBot. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Friday, January, 2017
 */
public class AlertAppender extends AppenderBase<ILoggingEvent>
{

    @Override
    protected void append(ILoggingEvent eventObject)
    {
        if (! eventObject.getLevel().isGreaterOrEqual(Level.ERROR))
            return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("JP Bot");
        alert.setHeaderText(eventObject.getMessage());
        String message = ThrowableProxyUtil.asString(eventObject.getThrowableProxy());
        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.show();


    }

}
