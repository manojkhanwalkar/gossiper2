package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

import java.util.List;

public class UserDispatcher {


    UserManager userManager = UserManager.getInstance();

    PostManager postManager = PostManager.getInstance();

    SubjectManager subjectManager = SubjectManager.getInstance();

    public Users dispatch()
    {

        return userManager.getUsers();
    }


    public Subjects retrieveSubjects()
    {

        return subjectManager.getSubjects();
    }

    public void dispatch(AddPost event)
    {


        userManager.queuePost(event.getPost());
    }

    public PostIds dispatch(RetrievePost event)
    {

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



    public SubjectInfo dispatch(GetSubject subject)
    {
        return subjectManager.getSubject(subject.getSubjectId());
    }

    public void dispatch(AddUser event)
    {
        userManager.addUser(event.getUser());
    }

    public void dispatch(DeletePost event)
    {

        postManager.deletePost(event.getPost());

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


    public void dispatch(AddSubject event)
    {
        subjectManager.addSubject(event.getSubject());
    }


    public void dispatch(DeleteSubject event)
    {
        subjectManager.deleteSubject(event.getSubject());
    }




}
