package me.jp78.spambot.Base.Injection;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public class EventModule extends AbstractModule
{

    public void configure()
    {
        bind(EventBus.class).toInstance(new EventBus());
    }
}
