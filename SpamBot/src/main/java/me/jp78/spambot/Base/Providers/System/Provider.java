package me.jp78.spambot.Base.Providers.System;

import lombok.Getter;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public class Provider<T>
{
    @Getter
    private T instance;

    @Getter
    private int priority;
    protected Provider(T t, int priority)
    {
       instance = t;
       this.priority = priority;
    }
}
