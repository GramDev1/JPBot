package me.jp78.spambot.Base.Providers.System;

import lombok.Builder;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Builder
public class ProviderSetEvent<T>
{
    private Class<T> service;
    private Provider<T> provider;
}
