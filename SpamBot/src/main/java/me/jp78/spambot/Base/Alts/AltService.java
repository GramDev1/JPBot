package me.jp78.spambot.Base.Alts;

import me.jp78.spambot.Base.Alts.Data.AltDetails;

import java.util.List;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public interface AltService
{

    /**
     * Add alt from given info, this should be used if you want to add from launcher profiles or AltDetails
     * Dispenser/MCLeaks
     *
     * @param a The AltDetails (Contains AltDetails Info)
     */
    LoadedAccount addAlt(AltDetails a) throws Exception;

    /**
     * Add alt from the given user and pass, from altlists or personal alts. This should add a new loaded alt with the
     * alt details or user and pass. To developers, check to make sure you don't already have it loaded before adding!
     *
     * @param username Username
     * @param password Password
     */
    LoadedAccount addAlt(String username, String password) throws Exception;

    /**
     * Get a list of all loaded accounts
     *
     * @return the loaded accounts
     */
    List<LoadedAccount> getLoadedAccounts();

    void removeAlt(LoadedAccount account);

    /**
     * Find a loaded account via it's username. Throws an exception if a Loaded Account doesn't have a username.
     *
     * @param username The username
     *
     * @return The loaded account, or null if it cannot be found.
     */
    default LoadedAccount getByUsername(String username) throws Exception
    {
        return getLoadedAccounts().stream().filter(loadedAccount -> loadedAccount.getAltDetails().orElseThrow(() -> new NullPointerException("Altdetails are not present! Cannot get username!")).getUsername().equals(username)).findAny().orElse(null);
    }

    /**
     * Find a loaded account via it's email. Throws an exception if a LoadedAccount doesn't have an email
     * @param email accounts email
     * @return the loaded account, null if it cannot be found.
     *
     */
    default LoadedAccount getByEmail(String email) throws Exception
    {
        return getLoadedAccounts().stream().filter(loadedAccount -> loadedAccount.getUsername().orElseThrow(() -> new NullPointerException("Email is not present! Cannot get alt!")).equals(email)).findAny().orElse(null);
    }



}
