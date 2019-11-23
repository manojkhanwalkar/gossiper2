package client;

import data.User;
import event.AddUser;
import event.DeleteUser;
import event.FollowUser;
import event.UnFollowUser;
import processor.Dispatcher;

public class DispatcherTester {

    Dispatcher dispatcher = new Dispatcher();


    public void addUser(User user ) throws Exception
    {
        AddUser addUser = new AddUser();
        addUser.setUser(user);

        dispatcher.dispatch(addUser);


    }


    public void followUser(FollowUser followUser ) throws Exception
    {

        dispatcher.dispatch(followUser);

    }

    public void unFollowUser(UnFollowUser followUser ) throws Exception
    {

        dispatcher.dispatch(followUser);

    }

    public void deleteUser(User user) throws Exception
    {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.setUser(user);

        dispatcher.dispatch(deleteUser);


    }




    public void test() throws Exception
    {
        User user1 = new User("User111");

        User user2 = new User("User211");




        addUser(user1);
        addUser(user2);

        FollowUser followUser = new FollowUser();
        followUser.setSelf(user1);
        followUser.setTarget(user2);

        followUser(followUser);


        UnFollowUser unFollowUser = new UnFollowUser();
        unFollowUser.setSelf(user1);
        unFollowUser.setTarget(user2);

        unFollowUser(unFollowUser);


    }


    public static void main(String[] args) throws Exception {

        DispatcherTester dispatcherTester = new DispatcherTester();

        dispatcherTester.test();



    }

}
