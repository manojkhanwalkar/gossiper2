package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.ConnectionConstants.*;

public class ConnectionManager {

    public enum ServiceType { Post, Subject , User};

    static class ConnectionMnagerHolder {

        static ConnectionManager connectionManager = new ConnectionManager();
    }


    private  ConnectionManager()
    {

    }


    public static ConnectionManager getInstance()
    {
        return  ConnectionMnagerHolder.connectionManager;
    }

    Map<ServiceType, List<Connection>> connections = new HashMap<>();

    public void init()
    {
        add(ServiceType.Post, Post1Url);
        add(ServiceType.Post, Post2Url);
        add(ServiceType.Subject, Subject1Url);
        add(ServiceType.Subject, Subject2Url);
        add(ServiceType.User, User1Url);
        add(ServiceType.User, User2Url);
    }

    private void add(ServiceType type, String url)
    {
        List<Connection> connectionList = connections.get(type);
        if (connectionList==null)
        {
            connectionList = new ArrayList<>();
            connections.put(type,connectionList);
        }

        connectionList.add(new Connection(url));
    }


    public Connection get(ServiceType type, String id)
    {
        List<Connection> connectionList = connections.get(type);

        return connectionList.get(Math.abs(id.hashCode()%2));


    }

}
