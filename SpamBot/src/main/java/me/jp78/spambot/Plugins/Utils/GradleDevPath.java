package me.jp78.spambot.Plugins.Utils;

import ro.fortsoft.pf4j.PluginClasspath;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Thursday, January, 2017
 *
 * Class adds support for gradle builds, as currently it only uses maven.
 */
public class GradleDevPath extends PluginClasspath
{
    private static final String MAVEN_CLASSES_DIRECTORY = "target/classes";
    private static final String MAVEN_LIB_DIRECTORY = "target/lib";
    private static final String GRADLE_CLASSES_DIRECTORY = "build/classes";
    private static final String GRADLE_RESOURCES = "build/resources";
    private static final String GRADLE_LIB_DIRECTORY = "build/dependencies";

    @Override
    protected void addResources()
    {   //Maven
        classesDirectories.add(MAVEN_CLASSES_DIRECTORY);
        libDirectories.add(MAVEN_LIB_DIRECTORY);
        //Gradle
        classesDirectories.add(GRADLE_CLASSES_DIRECTORY + "/test");
        classesDirectories.add(GRADLE_CLASSES_DIRECTORY + "/main");
        classesDirectories.add(GRADLE_RESOURCES + "/test");
        classesDirectories.add(GRADLE_RESOURCES + "/main");
        libDirectories.add(GRADLE_LIB_DIRECTORY);
    }
}
