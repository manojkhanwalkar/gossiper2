package manager;

import data.*;
import graph.DAG;
import persistence.DynamoDBManager;
import persistence.SubjectRecord;
import persistence.UserRecord;

import java.util.*;
import java.util.stream.Collectors;

public class UserManager {




    static class UserManagerHolder
    {
        static UserManager instance = new UserManager();
    }


    public static UserManager getInstance()
    {
        return UserManagerHolder.instance;
    }

    private UserManager()
    {

    }




    Set<String> userids = new HashSet<>();

    DAG<String> followers = new DAG();
    DAG<String> follows = new DAG();
    DAG<String> followsSubject = new DAG();

    DynamoDBManager manager = new DynamoDBManager();

    Map<String,List<String>> userPosts = new HashMap<>();





    public Users getUsers() {
        Users users = new Users();
        users.setUsers(userids.stream().collect(Collectors.toCollection(ArrayList::new)));
        return users;
    }

    public void recoverUser(User user)
    {
        if (!userids.contains(user.getId()))
        {
            userids.add(user.getId());
        }
    }


    public void addUser(User user)
    {
        if (!userids.contains(user.getId()))
        {


            userids.add(user.getId());

            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(user.getId());
            userRecord.setName(user.getName());
            manager.putUser(userRecord);

            // persist the user in database
        }
        else
        {
            System.out.println("User already exists " + user);
        }
    }

    public UserInfo getUser(String userId) {

        UserRecord userRecord = manager.getUser(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setFollowedBy((ArrayList)userRecord.getFollowedBy());
        userInfo.setFollows((ArrayList)userRecord.getFollows());
        userInfo.setName(userRecord.getName());
        userInfo.setUserId(userRecord.getUserId());

        return userInfo;

    }

    public void deleteUser(User user)
    {

        if (userids.contains(user.getId()))
        {
           // List<Integer> subjectIndices = followsSubject.getEdges(user.getId());
            userids.remove(user.getId());


            UserRecord userRecord = manager.getUser(user.getId());
            manager.removeUser(userRecord);

            //TODO - process the follows and followed lists and remove the persistence from there

         /*   subjectIndices.stream().forEach(i->{

                SubjectManager subjectManager = SubjectManager.getInstance();
                String subjectId = subjectManager.subjectidList.get(i);
                SubjectRecord subjectRecord = manager.getSubject(subjectId);
                subjectRecord.getFollowedBy().remove(user.getId());
                manager.putSubject(subjectRecord);
            });*/



            // let the user stay in the DAG as the next restart will remove it .


        }
        else
        {
            System.out.println("User not found " + user);
        }
    }


    public void recoverFollowersAndFollows(String selfId , List<String> followerIds, List<String> followsIds, List<String> followsSubjectIds)
    {
   /*     int selfIndex = userids.get(selfId);
        followerIds.stream().forEach(follower->{
            int other = userids.get(follower);
            followers.addEdge(other,selfIndex);

        });

        followsIds.stream().forEach(follow->{

            int other = userids.get(follow);

            follows.addEdge(selfIndex,other);

        });

        followsSubjectIds.stream().forEach(f->{
            SubjectManager subjectManager = SubjectManager.getInstance();
            Integer subjectIndex = subjectManager.getSubjectIndex(f);
            followsSubject.addEdge(selfIndex,subjectIndex);

        });

*/



    }

    public void addFollower(User self , User userToFollow)
    {
  /*      int selfIndex = userids.get(self.getId());
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.addEdge(selfIndex,userToFollowIndex);
        followers.addEdge(userToFollowIndex,selfIndex);

        UserRecord userRecord = manager.getUser(self.getId());
        if (!userRecord.getFollows().contains(userToFollow.getId()))
        {
            userRecord.getFollows().add(userToFollow.getId());
            manager.putUser(userRecord);

            userRecord = manager.getUser(userToFollow.getId());
            userRecord.getFollowedBy().add(self.getId());
            manager.putUser(userRecord);
        }
        else
        {
            System.out.println("Follower already exists " + self + "  " + userToFollow);
        }*/



    }

    public void deleteFollower(User self , User userToFollow)
    {
    /*    Integer selfIndex = userids.get(self.getId());
        if (selfIndex==null)
        {
            System.out.println("Relationship not found " + self + "  "+ userToFollow);
            return;
        }
        int userToFollowIndex = userids.get(userToFollow.getId());

        follows.removeEdge(selfIndex,userToFollowIndex);
        followers.removeEdge(userToFollowIndex,selfIndex);


        UserRecord userRecord = manager.getUser(self.getId());
        userRecord.getFollows().remove(userToFollow.getId());
        manager.putUser(userRecord);

        userRecord = manager.getUser(userToFollow.getId());
        userRecord.getFollowedBy().remove(self.getId());
        manager.putUser(userRecord);

        //TODO - get followed by users list and delete this user from their follows list
*/

    }


    public void queuePost(Post post) {

        String posterUserId = post.getPoster().getId();

        String subjectId = post.getSubject().getId();


        SubjectManager subjectManager = SubjectManager.getInstance();


        Set<String> userIndices = new HashSet<>();

        subjectManager.followers.getEdges(subjectId).stream().forEach(i->{

            userIndices.add(i);
        });





        followers.getEdges(posterUserId).stream().forEach(num->{

            userIndices.add(num);

        });


        // common users across user followers and subject followers . now process that set .

        userIndices.stream().forEach(num->{
            List<String> posts = userPosts.get(num);
            if (posts==null)
            {
                posts = new ArrayList<>();
                userPosts.put(num,posts);
            }

            posts.add(post.getId());

        });


    }



    public PostIds getPostsForUser(String id) {

        PostIds postIds = new PostIds();



            List<String> posts = userPosts.get(id);
            if (posts != null) {

                postIds.setPostIds((ArrayList) posts);
            }




        return postIds;
    }





    public void addUserAsSubjectFollower(User self , Integer subjectIndex, Subject subject)
    {
       /* int selfIndex = userids.get(self.getId());
        followsSubject.addEdge(selfIndex,subjectIndex);
        UserRecord userRecord = manager.getUser(self.getId());
        if (!userRecord.getFollowsSubject().contains(subject.getId()))
        {
            userRecord.getFollowsSubject().add(subject.getId());
            manager.putUser(userRecord);
        }
*/

    }

    public void deleteUserAsSubjectFollower(User self , Integer subjectIndex, Subject subject)
    {
    /*    int selfIndex = userids.get(self.getId());
        followsSubject.removeEdge(selfIndex,subjectIndex);

        UserRecord userRecord = manager.getUser(self.getId());
        userRecord.getFollowsSubject().remove(subject.getId());
        manager.putUser(userRecord);*/


    }


}
