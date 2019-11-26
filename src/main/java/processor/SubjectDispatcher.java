package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

public class SubjectDispatcher {




    SubjectManager subjectManager = SubjectManager.getInstance();




    public Subjects retrieveSubjects()
    {

        return subjectManager.getSubjects();
    }



    public SubjectInfo dispatch(GetSubject subject)
    {
        return subjectManager.getSubject(subject.getSubjectId());
    }



    public void dispatch(FollowSubject event)
    {

        subjectManager.addFollower(event.getSubject(),event.getUser());
    }


    public void dispatch(UnFollowSubject event)
    {


      //  userManager.deleteUserAsSubjectFollower(event.getUser(),subjectIndex,event.getSubject());
        subjectManager.deleteFollower(event.getSubject(),event.getUser());

    }



    public void dispatch(AddSubject event)
    {
        subjectManager.addSubject(event.getSubject());
    }


    public void dispatch(DeleteSubject event)
    {
        subjectManager.deleteSubject(event.getSubject());
    }

    public void dispatch(UserIdsForSubject event)
    {
        subjectManager.deleteUser(event);
    }




}
