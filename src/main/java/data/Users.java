package data;

import java.util.ArrayList;

public class Users {

    ArrayList<String> users = new ArrayList<>();

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void addUser(User user)
    {
        users.add(user.getId());
    }


}

