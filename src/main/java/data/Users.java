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

    public void addUsers(ArrayList<String> newusers) {


        this.users.addAll(newusers);
    }



    public static Users combine(Users... userlist)
    {

        Users users = new Users();

        for (Users user : userlist)
        {
            users.addUsers(user.getUsers());
        }

        return users;

    }


    @Override
    public String toString() {
        return "Users{" +
                "users=" + users +
                '}';
    }
}

