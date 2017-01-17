package me.jp78.spambot.Base.Alts;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import me.jp78.spambot.Base.Alts.Data.AltDetails;

import java.util.Optional;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@EqualsAndHashCode
public abstract class LoadedAccount implements ClientCreator
{

    protected AltDetails a;
    private String user;
    private String pass;

    /**
     * Puts in details
     *
     * @param a    the alt details (cannot be null);\
     * @param user the user
     * @param pass the poss
     */
    protected LoadedAccount( AltDetails a, String user, String pass)
    {
        if (a == null && (user == null && pass == null))
        {
            throw new NullPointerException("Alt Details or a user/pass must be provided!");
        }
        this.a = a;
        this.user = user;
        this.pass = pass;

    }


    /**
     * Gets the username (email) of the alt. Don't use this to log in!
     *
     * @return Optional of the username (May not be present due to info being directly inserted;
     */
    public Optional<String> getUsername()
    {
        return Optional.ofNullable(user);
    }

    /**
     * Gets the password of the alt. Don't use this to log in!
     *
     * @return Optional of the username (May not be present due to info being directly inserted;
     */
    public Optional<String> getPassword()
    {
        return Optional.ofNullable(pass);
    }

    /**
     * Gets the alt details. These should always be present.
     *
     * @return Alt Details
     */
    public Optional<AltDetails> getAltDetails()
    {
        return Optional.ofNullable(a);
    }

    private void checkUserPass(AltDetails a, String user, String pass)
    {
        Preconditions.checkNotNull(a, "AltDetails cannot be null!");
        Preconditions.checkNotNull(user, "The username cannot be null!");
        Preconditions.checkNotNull(pass, "The password cannot be null!");
    }

    private void checkAltDetails(AltDetails a)
    {
        Preconditions.checkNotNull(a, "AltDetails cannot be null!");
    }


}
