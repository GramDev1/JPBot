package me.jp78.spambot.Base.Alts.Impl.UserPass;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.jp78.spambot.Base.Alts.Data.AltDetails;
import me.jp78.spambot.Base.Alts.Data.ServerConnection;
import me.jp78.spambot.Base.Alts.LoadedAccount;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.event.session.*;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;
import java.util.Map;
import java.util.UUID;

/**
 * This file was created by @author thejp for the use of
 * Jpsy. Please note, all rights to code are retained by
 * afore mentioned thejp unless otherwise stated.
 * File created: Monday, January, 2017
 */
@Slf4j
public class UserPassAccount extends LoadedAccount
{

    private Map<ServerConnection, Client> serverConnectionClientMap = Maps.newConcurrentMap();
    private MinecraftProtocol protocol1;

    public UserPassAccount(String user, String pass) throws Exception
    {
        super(null, user, pass);
        ;
        Preconditions.checkNotNull(user, "The username cannot be null!");
        Preconditions.checkNotNull(pass, "The password cannot be null!");
         protocol1 = new MinecraftProtocol(getUsername().orElseThrow(() -> new NullPointerException("No username!")), getPassword().orElseThrow(() -> new NullPointerException("No password"))); //Ensure we can search by username.
        a = AltDetails.builder().access_token(protocol1.getAccessToken()).client_token(UUID.randomUUID().toString()).username(protocol1.getProfile().getName()).username(protocol1.getProfile().getName()).build(); //Ensure we can search by username
    }

    @Override
    public Map<ServerConnection, Client> getConnectedServers()
    {
        return serverConnectionClientMap;
    }

    @Override
    public Client getOrCreateClient(@NonNull ServerConnection connection, @NonNull Proxy protocol, @NonNull Proxy Client) throws Exception
    {
        if (serverConnectionClientMap.containsKey(connection))
            return serverConnectionClientMap.get(connection);
      //  MinecraftProtocol protocol1 = new MinecraftProtocol(new GameProfile(a.getUuid(), a.getUsername()), a.getAccess_token());
        Client newClient = new Client(connection.getHost(), connection.getPort(), protocol1, new TcpSessionFactory(Client));
        final Wrapper<Boolean> con = new Wrapper<>();
        newClient.getSession().addListener(new SessionListener()
        {
            @Override
            public void packetReceived(PacketReceivedEvent packetReceivedEvent)
            {

            }

            @Override
            public void packetSent(PacketSentEvent packetSentEvent)
            {

            }

            @Override
            public void connected(ConnectedEvent connectedEvent)
            {
                log.debug("Connected to server!");
                con.setValue(true);
        //        connectedEvent.getSession().send(new ServerChatPacket("Hey dere."));
            }

            @Override
            public void disconnecting(DisconnectingEvent disconnectingEvent)
            {

            }

            @Override
            public void disconnected(DisconnectedEvent disconnectedEvent)
            {
              log.debug("Disconnected: " + disconnectedEvent.getReason());
            }
        });
        newClient.getSession().connect();
        while ( ! con.getValue() )
        {
            Thread.sleep(1000);
        }
        serverConnectionClientMap.put(connection, newClient);
        return newClient;
    }

    @Override
    public Client replaceClient(ServerConnection connection, Proxy protocol, Proxy Client) throws Exception
    {
        if (! serverConnectionClientMap.containsKey(connection))
            return getOrCreateClient(connection, protocol, Client);
        MinecraftProtocol protocol1 = new MinecraftProtocol(new GameProfile(a.getUuid(), a.getUsername()), a.getAccess_token());
        Client newClient = new Client(connection.getHost(), connection.getPort(), protocol1, new TcpSessionFactory(Client));
        final Wrapper<Boolean> con = new Wrapper<>();
        newClient.getSession().addListener(new SessionListener()
        {
            @Override
            public void packetReceived(PacketReceivedEvent packetReceivedEvent)
            {

            }

            @Override
            public void packetSent(PacketSentEvent packetSentEvent)
            {

            }

            @Override
            public void connected(ConnectedEvent connectedEvent)
            {
                con.setValue(true);
            }

            @Override
            public void disconnecting(DisconnectingEvent disconnectingEvent)
            {

            }

            @Override
            public void disconnected(DisconnectedEvent disconnectedEvent)
            {

            }
        });
        newClient.getSession().connect();
        while ( ! con.getValue() )
        {
            Thread.sleep(1000);
        }
        serverConnectionClientMap.replace(connection, newClient);
        return newClient;
    }
}
