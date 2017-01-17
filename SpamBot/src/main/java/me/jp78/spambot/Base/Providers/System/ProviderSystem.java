package me.jp78.spambot.Base.Providers.System;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import me.jp78.spambot.SpamBot;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Singleton
public class ProviderSystem
{

    private ConcurrentMap<Class<?>, Provider<?>> classObjectRelationship = Maps.newConcurrentMap();

    @SuppressWarnings ( "unchecked" )
    public <T> Optional<T> provide(Class<T> clazz)
    {
        if (classObjectRelationship.containsKey(clazz))
        {
            Provider<T> provider = (Provider<T>) classObjectRelationship.get(clazz);
            return Optional.of(provider.getInstance());
        }
        return Optional.empty();
    }
    public <T> void setProvider(Class<T> clazz, T instance, int priority)
    {

        if(!classObjectRelationship.containsKey(clazz)) classObjectRelationship.put(clazz,new Provider<>(instance,priority));
        if(classObjectRelationship.get(clazz).getPriority() < priority) {
            Provider<T> provider = new Provider<>(instance,priority);
            classObjectRelationship.replace(clazz,provider);
            SpamBot.instance().getBus().post(new ProviderSetEvent<>(clazz,provider));
        }

    }


}
