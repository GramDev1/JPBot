package me.jp78.spambot.Base.Utils;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import lombok.EqualsAndHashCode;
import me.jp78.spambot.Base.Alts.AltService;
import me.jp78.spambot.Base.Alts.Data.AltDetails;
import me.jp78.spambot.Base.Alts.Impl.UserPass.UserPassAccount;
import me.jp78.spambot.Base.Alts.LoadedAccount;

import java.util.List;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Singleton
@EqualsAndHashCode
public class UserPassAltService implements AltService
{

    private List<LoadedAccount> accounts = Lists.newArrayList();

    @Override
    public List<LoadedAccount> getLoadedAccounts()
    {
        return accounts;
    }

    @Override
    public LoadedAccount addAlt(AltDetails a)
    {
        throw new UnsupportedOperationException("You cannot add an alt details account to a user pass alt service!");
    }

    @Override
    public LoadedAccount addAlt(String username, String password) throws Exception
    {
        LoadedAccount acc = accounts.stream().filter(l -> l.getUsername().get().equals(username)).findFirst().orElse(null);
        if (acc == null)
        {
            UserPassAccount account = new UserPassAccount(username,password);
            accounts.add(account);
            return account;
        }
        return acc;
    }

    @Override
    public void removeAlt(LoadedAccount account)
    {
        getLoadedAccounts().remove(account);
    }
}
