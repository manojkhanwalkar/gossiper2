package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    Map<ServiceType, List<String>> connections = new HashMap<>();

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

        List<String> connectionList = connections.computeIfAbsent(type, k->new ArrayList<>());

        connectionList.add(url);
    }


    public Connection get(ServiceType type, String id)
    {
        List<String> connectionList = connections.get(type);

        String url = connectionList.get(Math.abs(id.hashCode()%2));

        return new Connection(url);


    }


    public List<Connection> get(ServiceType type)
    {

        return connections.get(type).stream().map(url->new Connection(url)).collect(Collectors.toList());
    }

}
