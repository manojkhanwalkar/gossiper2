package manager;

import data.*;
import graph.DAG;
import persistence.DynamoDBManager;
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


    public void recover(UserRecord r) {

        System.out.println("User record recovered for " + r);
        if (!userids.contains(r.getUserId()))
        {
            userids.add(r.getUserId());
        }

        r.getFollowedBy().stream().forEach(follower->{
            followers.addEdge(follower,r.getUserId());

        });

        r.getFollows().stream().forEach(follow->{

            follows.addEdge(r.getUserId(),follow);
        });

        r.getFollowsSubject().stream().forEach(subject->{
            followsSubject.addEdge(r.getUserId(),subject);

        });


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
        if (userRecord==null)
        {
            System.out.println("User not found for " + userId);
            return userInfo;
        }
        userInfo.setFollowedBy((ArrayList)userRecord.getFollowedBy());
        userInfo.setFollows((ArrayList)userRecord.getFollows());
        userInfo.setFollowsSubject((ArrayList)userRecord.getFollowsSubject());
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



    }

    public void addFollower(User self , User userToFollow)
    {

        if (userids.contains(self.getId()))
        {
            follows.addEdge(self.getId(),userToFollow.getId());
            UserRecord userRecord = manager.getUser(self.getId());
            if (!userRecord.getFollows().contains(userToFollow.getId())) {
                userRecord.getFollows().add(userToFollow.getId());
                manager.putUser(userRecord);
            }
        }

        if (userids.contains(userToFollow.getId()))
        {
            followers.addEdge(userToFollow.getId(),self.getId());
            UserRecord userRecord = manager.getUser(userToFollow.getId());
            if (!userRecord.getFollowedBy().contains(self.getId()))
            {
                userRecord.getFollowedBy().add(self.getId());
                manager.putUser(userRecord);
            }
        }

    }


    public void deleteFollower(User self , User userToFollow)
    {

        if (userids.contains(self.getId()))
        {
            follows.removeEdge(self.getId(),userToFollow.getId());
            UserRecord userRecord = manager.getUser(self.getId());
            if (userRecord.getFollows().contains(userToFollow.getId())) {
                userRecord.getFollows().remove(userToFollow.getId());
                manager.putUser(userRecord);
            }
        }

        if (userids.contains(userToFollow.getId()))
        {
            followers.removeEdge(userToFollow.getId(),self.getId());
            UserRecord userRecord = manager.getUser(userToFollow.getId());
            if (userRecord.getFollowedBy().contains(self.getId()))
            {
                userRecord.getFollowedBy().remove(self.getId());
                manager.putUser(userRecord);
            }
        }

    }



    public void queuePost(UserIdsForPost userIdsForPost) {

        Post post = userIdsForPost.getPost();

            userIdsForPost.getUserIds().stream().forEach(id->{

                List<String> posts = userPosts.get(id);
                if (posts==null)
                {
                    posts = new ArrayList<>();
                    userPosts.put(id,posts);
                }

                posts.add(post.getId());


            });



    }



    public PostIds getPostsForUser(String id) {

        System.out.println("User is " + id);

        System.out.println(userPosts);

        PostIds postIds = new PostIds();



            List<String> posts = userPosts.get(id);
            if (posts != null) {

                postIds.setPostIds((ArrayList) posts);
            }




        return postIds;
    }





    public void addUserAsSubjectFollower(User self , Subject subject)
    {
        followsSubject.addEdge(self.getId(),subject.getId());
        UserRecord userRecord = manager.getUser(self.getId());
        if (!userRecord.getFollowsSubject().contains(subject.getId()))
        {
            userRecord.getFollowsSubject().add(subject.getId());
            manager.putUser(userRecord);
        }

    }

    public void deleteSubject(UserIdsForSubject event) {

        event.getUserIds().stream().forEach(id->{

            followsSubject.removeEdge(id,event.getSubjectId());
            UserRecord userRecord = manager.getUser(id);
            if (userRecord.getFollowsSubject().contains(event.getSubjectId()))
            {
                userRecord.getFollowsSubject().remove(event.getSubjectId());
                manager.putUser(userRecord);
            }

        });

    }

    public void deleteUserAsSubjectFollower(User self ,Subject subject)
    {
        followsSubject.removeEdge(self.getId(),subject.getId());

        UserRecord userRecord = manager.getUser(self.getId());
        userRecord.getFollowsSubject().remove(subject.getId());
        manager.putUser(userRecord);


    }


}
