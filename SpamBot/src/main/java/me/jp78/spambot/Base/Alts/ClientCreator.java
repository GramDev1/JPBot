package me.jp78.spambot.Base.Alts;

import me.jp78.spambot.Base.Alts.Data.ServerConnection;
import org.spacehq.packetlib.Client;

import java.net.Proxy;
import java.util.Map;
import java.util.Optional;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
public interface ClientCreator
{

    /**
     * Creates a client or gets it via the given information.
     * @param connection The server connection (Host/port). If connected it will check via this or connect to this server.
     * @param protocol The auth proxy to be used if not needs to be created (Can be null if you are sure its been connected!)
     * @param Client The game proxy to be used if needs to be created (Can be null if you are sure its been connected!)
     * @return A new client
     * @throws Exception If something goes wrong..
     */
    Client getOrCreateClient(ServerConnection connection, Proxy protocol, Proxy Client) throws Exception;

    /**
     * Replaces or sets the client connected to a given served
     * @param connection The server connection key.
     * @param protocol The replacing clients auth proxy
     * @param Client The replacing clients game proxu
     * @return The replacing client
     * @throws Exception If something goes wrong....
     */
    Client replaceClient(ServerConnection connection, Proxy protocol, Proxy Client) throws Exception;

    Map<ServerConnection, Client> getConnectedServers();

    default Optional<Client> getConnectedClient(ServerConnection connection)
    {
        return Optional.ofNullable(getConnectedServers().get(connection));
    }
}
