package manager;

import data.*;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.SubjectRecord;
import persistence.UserRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectManager {




    static class SubjectManagerHolder
    {
        static SubjectManager instance = new SubjectManager();
    }


    public static SubjectManager getInstance()
    {
        return SubjectManager.SubjectManagerHolder.instance;
    }

    private SubjectManager()
    {

    }



    DAG<String> followers = new DAG();  // subject id ->  userids

    DynamoDBManager manager = new DynamoDBManager();

    public void recover(SubjectRecord r) {

        System.out.println("Recovered " + r);

        followers.addNode(r.getSubjectId());
        r.getFollowedBy().stream().forEach(f->{

            followers.addEdge(r.getSubjectId(),f);
        });
    }


    public void addSubject(Subject subject)
    {
        if (!followers.existsNode(subject.getId())) {

            followers.addNode(subject.getId());
            SubjectRecord subjectRecord = new SubjectRecord();
            subjectRecord.setSubjectId(subject.getId());
            subjectRecord.setName(subject.getName());

            manager.putSubject(subjectRecord);
        }

    }

    public void deleteSubject(Subject subject)
    {
           // List<String> userIndices = followers.getEdges(subject.getId());

           followers.deleteNode(subject.getId());
            // delete user in database

            SubjectRecord subjectRecord = manager.getSubject(subject.getId());
            if (subject!=null)
                  manager.removeSubject(subjectRecord);


            //delete from userrecords for all users following the subject.
     /*       userIndices.stream().forEach(i->{

                UserManager userManager = UserManager.getInstance();
                String userId = userManager.useridList.get(i);
                UserRecord userRecord = manager.getUser(userId);
                userRecord.getFollowsSubject().remove(subject.getId());
                manager.putUser(userRecord);
            }); */


            // let the user stay in the DAG as the next restart will remove it .



    }


   public SubjectInfo getSubject(String subjectId) {

        SubjectRecord subjectRecord = manager.getSubject(subjectId);

        SubjectInfo subjectInfo = new SubjectInfo();

        if (subjectRecord!=null) {

            if (subjectRecord.getFollowedBy()!=null) {

                subjectInfo.setFollowedBy((ArrayList) subjectRecord.getFollowedBy());
            }
            subjectInfo.setName(subjectRecord.getName());

            subjectInfo.setSubjectId(subjectRecord.getSubjectId());

        }



        return subjectInfo;

    }



    public void addFollower(Subject subjectToFollow, User user)
    {


        followers.addEdge(subjectToFollow.getId(),user.getId());

        SubjectRecord subjectRecord = manager.getSubject(subjectToFollow.getId());
        if (!subjectRecord.getFollowedBy().contains(user.getId()))
        {
            subjectRecord.getFollowedBy().add(user.getId());
            manager.putSubject(subjectRecord);
        }


    }

    public void deleteUser(UserIdsForSubject event) {


        event.getUserIds().stream().forEach(id->{

            followers.removeEdge(event.getSubjectId(),id);

            SubjectRecord subjectRecord = manager.getSubject(event.getSubjectId());

            subjectRecord.getFollowedBy().remove(id);
            manager.putSubject(subjectRecord);


        });

    }

    public void deleteFollower( Subject subjectToFollow, User user)
    {



        followers.removeEdge(subjectToFollow.getId(),user.getId());

        SubjectRecord subjectRecord = manager.getSubject(subjectToFollow.getId());

            subjectRecord.getFollowedBy().remove(user.getId());
            manager.putSubject(subjectRecord);


    }


    public Subjects getSubjects()
    {
        Subjects subjects = new Subjects();
        subjects.setSubjects((ArrayList)followers.getAdjacencyList().keySet().stream().collect(Collectors.toList()));
        return subjects;
    }




}
