package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;
import util.Connection;
import util.ConnectionManager;
import util.JSONUtil;

import java.util.List;


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

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"delete");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO - get user follows and followed and delete the user from those users followed and follows list
    }

    public void dispatch(FollowSubject event)
    {
        // add user to subject relationship in user manager
        // add subject to user relationship in subject manager

    /*    Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
        Integer userIndex = userManager.getUserIndex(event.getUser());

        userManager.addUserAsSubjectFollower(event.getUser(),subjectIndex,event.getSubject());
        subjectManager.addFollower(userIndex, event.getSubject(),event.getUser());*/
    }

    public void dispatch(FollowUser event)
    {

       // userManager.addFollower(event.getSelf(),event.getTarget());

    }

    public void dispatch(UnFollowSubject event)
    {
  /*      Integer subjectIndex = subjectManager.getSubjectIndex(event.getSubject().getId());
        Integer userIndex = userManager.getUserIndex(event.getUser());

        userManager.deleteUserAsSubjectFollower(event.getUser(),subjectIndex,event.getSubject());
        subjectManager.deleteFollower(userIndex, event.getSubject(),event.getUser());*/

    }

    public void dispatch(UnFollowUser event)
    {
       // userManager.deleteFollower(event.getSelf(),event.getTarget());
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

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Subject,event.getSubject().getId());

        try {
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

        Connection connection = connectionManager.get(ConnectionManager.ServiceType.Post,event.getPost().getId());

        try {
            String response = connection.send(JSONUtil.toJSON(event),"post");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO - send to both user services for them to process the post

        //      postManager.addPost(event.getPost());

        //     userManager.queuePost(event.getPost());
    }

    public Posts dispatch(RetrievePost event)
    {

        try {
            // find which instance has the user and get post ids from that instance .
            // for each post ids - break into two lists for each post service
            // for each post service get the posts associated with the ids. posts may have been deleted and so the list will be smaller
            // combine the results into a single posts object and return it back

            Connection connection = connectionManager.get(ConnectionManager.ServiceType.User,event.getUser().getId());

            String response = connection.send(JSONUtil.toJSON(event),"retrieve");

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
