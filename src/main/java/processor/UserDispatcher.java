package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

public class UserDispatcher {


    UserManager userManager = UserManager.getInstance();



    public Users dispatch()
    {

        return userManager.getUsers();
    }




    public void dispatch(UserIdsForPost event)
    {


        userManager.queuePost(event);
    }

    public PostIds dispatch(RetrievePost event)
    {

        System.out.println(event);
        PostIds postIds = userManager.getPostsForUser(event.getUser().getId());
        if (postIds==null)
        {
            postIds = new PostIds();
        }
        return postIds;

    }

    public UserInfo dispatch(GetUser event)
    {
        return  userManager.getUser(event.getUserId());
    }


    public void dispatch(AddUser event)
    {
        userManager.addUser(event.getUser());
    }



    public void dispatch(DeleteUser event)
    {

        userManager.deleteUser(event.getUser());
    }

    public void dispatch(FollowSubject event)
    {

        userManager.addUserAsSubjectFollower(event.getUser(),event.getSubject());
/*

        subjectManager.addFollower(userIndex, event.getSubject(),event.getUser());*/
    }

    public void dispatch(FollowUser event)
    {

        userManager.addFollower(event.getSelf(),event.getTarget());

    }

    public void dispatch(UnFollowSubject event)
    {


        userManager.deleteUserAsSubjectFollower(event.getUser(),event.getSubject());

    }

    public void dispatch(UnFollowUser event)
    {
        userManager.deleteFollower(event.getSelf(),event.getTarget());
    }




    public void dispatch(UserIdsForSubject event)
    {
        userManager.deleteSubject(event);
    }




}
