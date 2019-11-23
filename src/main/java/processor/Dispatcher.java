package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

public class Dispatcher {


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

        postManager.addPost(event.getPost());

        userManager.queuePost(event.getPost());
    }

    public Posts dispatch(RetrievePost event)
    {

        return userManager.getPostsForUser(event.getUser().getId());

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
        // add user to subject relationship in user manager
        // add subject to user relationship in subject manager

        Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
        Integer userIndex = userManager.getUserIndex(event.getUser());

        userManager.addUserAsSubjectFollower(event.getUser(),subjectIndex,event.getSubject());
        subjectManager.addFollower(userIndex, event.getSubject(),event.getUser());
    }

    public void dispatch(FollowUser event)
    {

        userManager.addFollower(event.getSelf(),event.getTarget());

    }

    public void dispatch(UnFollowSubject event)
    {
        Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
        Integer userIndex = userManager.getUserIndex(event.getUser());

        userManager.deleteUserAsSubjectFollower(event.getUser(),subjectIndex,event.getSubject());
        subjectManager.deleteFollower(userIndex, event.getSubject(),event.getUser());

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
