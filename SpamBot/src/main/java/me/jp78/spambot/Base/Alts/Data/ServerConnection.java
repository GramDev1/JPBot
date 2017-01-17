package me.jp78.spambot.Base.Alts.Data;

import lombok.Builder;
import lombok.Data;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Data
@Builder
public class ServerConnection
{
    private String host;
    private int port;
}
