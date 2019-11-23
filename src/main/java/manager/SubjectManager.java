package manager;

import data.Subject;
import data.SubjectInfo;
import data.Subjects;
import data.User;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.SubjectRecord;
import persistence.UserRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    Map<String,Integer> subjectids = new HashMap<>();

    List<String> subjectidList = new ArrayList<>();

    DAG followers = new DAG(100);

    DynamoDBManager manager = new DynamoDBManager();


    public void recoverSubject(Subject subject) {
        if (!subjectids.containsKey(subject.getId())) {

            int subjectNum = subjectidList.size();
            subjectidList.add(subject.getId());
            subjectids.put(subject.getId(), subjectNum);

        }
    }

    public void addSubject(Subject subject)
    {
        if (!subjectids.containsKey(subject.getId()))
        {

            int subjectNum = subjectidList.size();
            subjectidList.add(subject.getId());
            subjectids.put(subject.getId(), subjectNum);



              SubjectRecord subjectRecord = new SubjectRecord();
              subjectRecord.setSubjectId(subject.getId());
              subjectRecord.setName(subject.getName());

            manager.putSubject(subjectRecord);


            // persist the subject in database
        }
    }

    public void deleteSubject(Subject subject)
    {
        if (subjectids.containsKey(subject.getId()))
        {
            List<Integer> userIndices = followers.getEdges(subjectids.get(subject.getId()));

            subjectids.remove(subject.getId());
            subjectidList.remove(subject.getId());

            // delete user in database

            SubjectRecord subjectRecord = manager.getSubject(subject.getId());
            manager.removeSubject(subjectRecord);


            //delete from userrecords for all users following the subject.
            userIndices.stream().forEach(i->{

                UserManager userManager = UserManager.getInstance();
                String userId = userManager.useridList.get(i);
                UserRecord userRecord = manager.getUser(userId);
                userRecord.getFollowsSubject().remove(subject.getId());
                manager.putUser(userRecord);
            });


            // let the user stay in the DAG as the next restart will remove it .


        }
    }


    public Integer getSubjectIndex(String subjectId)
    {
        return subjectids.get(subjectId);
    }


   public SubjectInfo getSubject(String subjectId) {

        SubjectRecord subjectRecord = manager.getSubject(subjectId);

        SubjectInfo subjectInfo = new SubjectInfo();

        subjectInfo.setFollowedBy(subjectRecord.getFollowedBy());
        subjectInfo.setName(subjectRecord.getName());

        subjectInfo.setSubjectId(subjectRecord.getSubjectId());



        return subjectInfo;

    }

    public void recoverFollowers(String subjectId, List<String> followedBy) {

        int subjectToFollowIndex = subjectids.get(subjectId);
        followedBy.stream().forEach(f->{

          UserManager userManager = UserManager.getInstance();
          Integer userIndex = userManager.getUserIndex(f);

          followers.addEdge(subjectToFollowIndex,userIndex);

        });
    }


    public void addFollower(int selfIndex , Subject subjectToFollow, User user)
    {

        int subjectToFollowIndex = subjectids.get(subjectToFollow.getId());


        followers.addEdge(subjectToFollowIndex,selfIndex);

        SubjectRecord subjectRecord = manager.getSubject(subjectToFollow.getId());
        if (!subjectRecord.getFollowedBy().contains(user.getId()))
        {
            subjectRecord.getFollowedBy().add(user.getId());
            manager.putSubject(subjectRecord);
        }


    }

    public void deleteFollower(int selfIndex , Subject subjectToFollow, User user)
    {

        int subjectToFollowIndex = subjectids.get(subjectToFollow.getId());


        followers.removeEdge(subjectToFollowIndex,selfIndex);

        SubjectRecord subjectRecord = manager.getSubject(subjectToFollow.getId());

            subjectRecord.getFollowedBy().remove(user.getId());
            manager.putSubject(subjectRecord);


    }


    public Subjects getSubjects()
    {
        Subjects subjects = new Subjects();
        subjects.setSubjects((ArrayList)subjectidList);
        return subjects;
    }




}
