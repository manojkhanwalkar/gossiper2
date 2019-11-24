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




    public void dispatch(AddPost event)
    {

  //      postManager.addPost(event.getPost());

   //     userManager.queuePost(event.getPost());
    }

    public Posts dispatch(RetrievePost event)
    {
        return null;

   //     return userManager.getPostsForUser(event.getUser().getId());

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

    public void dispatch(DeletePost event)
    {

        //postManager.deletePost(event.getPost());

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



}
