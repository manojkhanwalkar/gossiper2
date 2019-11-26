package processor;

import data.*;
import event.*;
import util.Connection;
import util.ConnectionManager;
import util.JSONUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProxyDispatcher {

ConnectionManager connectionManager = ConnectionManager.getInstance();


    public Users dispatch()
    {

        Users users = new Users();
        List<Connection> connections = connectionManager.get(ConnectionManager.ServiceType.User);

        connections.stream().forEach(connection->{


            try {
                String response = connection.send("users");

                Users users1 = (Users)JSONUtil.fromJSON(response,Users.class);
                users.addUsers(users1.getUsers());

            } catch (Exception e) {
                e.printStackTrace();
            }



        });

        return users;


    }




    public UserInfo dispatch(GetUser event)
    {
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUserId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"user");
            UserInfo userInfo = (UserInfo) JSONUtil.fromJSON(response,UserInfo.class);

            return userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }





    public void dispatch(AddUser event)
    {
        //userManager.addUser(event.getUser());
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"create");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void dispatch(DeleteUser event)
    {
        // take the userlist of follows and send it to user services for those users to delete from followed list
        // take the userlist of followed and send it to user services for those users to delete from follows list
        // take the followsSubject list and send it to subject services to delete it from subjects followedby list
        try {

        GetUser getUser = new GetUser();
        getUser.setUserId(event.getUser().getId());

        UserInfo userInfo = dispatch(getUser);

        { // delete user from subject in subject service
            SubjectIdsForUser evenIds = new SubjectIdsForUser();
            SubjectIdsForUser oddIds = new SubjectIdsForUser();
            evenIds.setUserId(event.getUser().getId());
            oddIds.setUserId(event.getUser().getId());

            userInfo.getFollowsSubject().stream().forEach(sid -> {
                if(sid.hashCode()%2==0)
                {
                    evenIds.addSubjectId(sid);
                }
                else
                {
                    oddIds.addSubjectId(sid);
                }

            });

            List<Connection> subjectConnections = connectionManager.get(ConnectionManager.ServiceType.Subject);
            subjectConnections.get(0).send(JSONUtil.toJSON(evenIds),"delete");
            subjectConnections.get(1).send(JSONUtil.toJSON(oddIds),"delete");

        }

        // delete follows and followedby

            { // delete user from subject in subject service
                UserIdsForUser evenIds = new UserIdsForUser();
                UserIdsForUser oddIds = new UserIdsForUser();
                evenIds.setUserId(event.getUser().getId());
                oddIds.setUserId(event.getUser().getId());

                userInfo.getFollows().stream().forEach(sid -> {
                    if(sid.hashCode()%2==0)
                    {
                        evenIds.addFollows(sid);
                    }
                    else
                    {
                        oddIds.addFollows(sid);
                    }

                });

                userInfo.getFollowedBy().stream().forEach(sid -> {
                    if(sid.hashCode()%2==0)
                    {
                        evenIds.addFollowedBy(sid);
                    }
                    else
                    {
                        oddIds.addFollowedBy(sid);
                    }

                });



                List<Connection> subjectConnections = connectionManager.get(ConnectionManager.ServiceType.User);
                subjectConnections.get(0).send(JSONUtil.toJSON(evenIds),"deleteFollowsAndFollowedForUser");
                subjectConnections.get(1).send(JSONUtil.toJSON(oddIds),"deleteFollowsAndFollowedForUser");

            }



        // delete user
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

            String response = connection.send(JSONUtil.toJSON(event),"delete");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO - get user follows and followed and delete the user from those users followed and follows list
    }

    public void dispatch(FollowSubject event)
    {
        //followsubject
        // find user - send to that service
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"followsubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


         connection = connectionManager.get(ConnectionManager.ServiceType.Subject,event.getSubject().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"followsubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void dispatch(FollowUser event)
    {

        // send to both user service instances . each instance checks if user self and user target belongs to it .
        // self - update DAG and record with follows
        // target - update DAG and record with followed by

        List<Connection> connections = connectionManager.get(ConnectionManager.ServiceType.User);

        connections.stream().forEach(connection->{

            try {
                String response = connection.send(JSONUtil.toJSON(event),"follow");
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    public void dispatch(UnFollowSubject event)
    {
        //followsubject
        // find user - send to that service
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"unfollowsubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


        connection = connectionManager.get(ConnectionManager.ServiceType.Subject,event.getSubject().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"unfollowsubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void dispatch(UnFollowUser event)
    {

        List<Connection> connections = connectionManager.get(ConnectionManager.ServiceType.User);

        connections.stream().forEach(connection->{

            try {
                String response = connection.send(JSONUtil.toJSON(event),"unfollow");
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }




    public void dispatch(AddSubject event)
    {

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Subject,event.getSubject().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"createSubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dispatch(DeleteSubject event)
    {

        // TODO
        // get subject info
        // delete the subject
        // take the followed by list and for all users , send to user service to delete from the followsSubject list
        GetSubject getSubject = new GetSubject();
        getSubject.setSubjectId(event.getSubject().getId());
        SubjectInfo subjectInfo = dispatch(getSubject);

        UserIdsForSubject evenIds = new UserIdsForSubject();
        UserIdsForSubject oddIds = new UserIdsForSubject();

        evenIds.setSubjectId(event.getSubject().getId());
        oddIds.setSubjectId(event.getSubject().getId());

        subjectInfo.getFollowedBy().stream().forEach(u->{

            if (u.hashCode()%2==0)
            {
                evenIds.addUserId(u);
            }
            else
            {
                oddIds.addUserId(u);
            }
        });


        try {
        List<Connection> userConnections = connectionManager.get(ConnectionManager.ServiceType.User);
        userConnections.get(0).send(JSONUtil.toJSON(evenIds),"deleteSubject");
        userConnections.get(1).send(JSONUtil.toJSON(oddIds),"deleteSubject");

            Connection connection = connectionManager.get(ConnectionManager.ServiceType.Subject,event.getSubject().getId());
            String response = connection.send(JSONUtil.toJSON(event),"deleteSubject");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SubjectInfo dispatch(GetSubject subject)
    {
        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Subject,subject.getSubjectId());

        try {
            String response = connection.send(JSONUtil.toJSON(subject),"subject");
            SubjectInfo subjectInfo = (SubjectInfo) JSONUtil.fromJSON(response,SubjectInfo.class);

            return subjectInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Subjects retrieveSubjects()
    {

            Subjects subjects = new Subjects();

            List<Connection> connectionList = connectionManager.get(ConnectionManager.ServiceType.Subject);
            connectionList.stream().forEach(connection->{

                try {
                    String response = connection.send("subjects");

                    Subjects subjects1 = (Subjects) JSONUtil.fromJSON(response, Subjects.class);
                    subjects.addSubjects(subjects1.getSubjects());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });

            return subjects;

    }



    public void dispatch(AddPost event)
    {

        // get UserInfo and extract followedBy from there
        // get SubjectInfo and extract followedBy from there
        // make a set of userids and send that to both user services . Each will add them to list of the users they manage.

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Post,event.getPost().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"post");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetSubject getSubject = new GetSubject();
        getSubject.setSubjectId(event.getPost().getSubject().getId());

        SubjectInfo subjectInfo = dispatch(getSubject);

        GetUser getUser = new GetUser();
        getUser.setUserId(event.getPost().getPoster().getId());

        UserInfo userInfo = dispatch(getUser);

        Set<String> userIds = new HashSet<>();
        userIds.addAll(subjectInfo.getFollowedBy());

        userIds.addAll(userInfo.getFollowedBy());

        UserIdsForPost evenIds = new UserIdsForPost();
        UserIdsForPost oddIds = new UserIdsForPost();



        // split into two parts

        userIds.stream().forEach(id->{

            if (id.hashCode()%2==0)
            {
                evenIds.addUserId(id);
            }
            else
            {
                oddIds.addUserId(id);
            }
        });


        List<Connection> connections = connectionManager.get(ConnectionManager.ServiceType.User);
        evenIds.setPost(event.getPost());
        oddIds.setPost(event.getPost());

        try {
            connections.get(0).send(JSONUtil.toJSON(evenIds),"post");
            connections.get(1).send(JSONUtil.toJSON(oddIds),"post");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Posts dispatch(RetrievePost event)
    {
        System.out.println(event);

        try {
            // find which instance has the user and get post ids from that instance .
            // for each post ids - break into two lists for each post service
            // for each post service get the posts associated with the ids. posts may have been deleted and so the list will be smaller
            // combine the results into a single posts object and return it back

            Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

            String response = connection.send(JSONUtil.toJSON(event),"retrieve");

            System.out.println(response);

            PostIds postIds = (PostIds) JSONUtil.fromJSON(response,PostIds.class);

            PostIds evenIds = new PostIds();
            PostIds oddIds = new PostIds();

            System.out.println(postIds);

            // split into two parts

            postIds.getPostIds().stream().forEach(id->{

                if (id.hashCode()%2==0)
                {
                    evenIds.addPostId(id);
                }
                else
                {
                    oddIds.addPostId(id);
                }
            });


            List<Connection> connections = connectionManager.get(ConnectionManager.ServiceType.Post);


             response = connections.get(0).send(JSONUtil.toJSON(evenIds),"retrieve");

             Posts evenPosts = (Posts)JSONUtil.fromJSON(response,Posts.class);


            response = connections.get(1).send(JSONUtil.toJSON(oddIds),"retrieve");

            Posts oddPosts = (Posts)JSONUtil.fromJSON(response,Posts.class);


            Posts posts = Posts.combine(evenPosts,oddPosts);

            System.out.println(posts);


            return posts;


            //     return userManager.getPostsForUser(event.getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }




    public void dispatch(DeletePost event)
    {

        //postManager.deletePost(event.getPost());

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Post,event.getPost().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"deletepost");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
