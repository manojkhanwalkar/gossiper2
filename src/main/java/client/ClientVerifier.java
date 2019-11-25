package client;

import data.*;
import event.*;
import util.Connection;
import util.JSONUtil;

import java.util.List;
import java.util.UUID;

import static util.ConnectionConstants.GossiperServiceUrl;

public class ClientVerifier {

   // public static final String GossiperServiceUrl = "https://172.17.0.1:8480/";


    Connection connection;


    public ClientVerifier()
    {
        connection = new Connection(GossiperServiceUrl);

    }

    public void addUser(User user ) throws Exception
    {
        AddUser addUser = new AddUser();
        addUser.setUser(user);

        String response = connection.send(JSONUtil.toJSON(addUser),"create");

        System.out.println(response);
    }

    public void addSubject(Subject subject )
    {
        try {
            AddSubject addSubject = new AddSubject();
            addSubject.setSubject(subject);

            String response = connection.send(JSONUtil.toJSON(addSubject),"createSubject");

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void followUser(FollowUser followUser ) throws Exception
    {


        String response = connection.send(JSONUtil.toJSON(followUser),"follow");

        System.out.println(response);
    }

    public void unFollowUser(UnFollowUser followUser ) throws Exception
    {


        String response = connection.send(JSONUtil.toJSON(followUser),"unfollow");

        System.out.println(response);
    }

    public void deleteUser(User user) throws Exception
    {
        DeleteUser deleteUser = new DeleteUser();
        deleteUser.setUser(user);

        String response = connection.send(JSONUtil.toJSON(deleteUser),"delete");

        System.out.println(response);
    }


    public void deleteSubject(Subject subject)
    {
        try {
            DeleteSubject deleteSubject = new DeleteSubject();
            deleteSubject.setSubject(subject);

            String response = connection.send(JSONUtil.toJSON(deleteSubject),"deleteSubject");

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users getUsers() throws Exception
    {

        String response = connection.send("users");

        Users users = (Users) JSONUtil.fromJSON(response, Users.class);

        System.out.println(response);

        return users;
    }


    public Subjects getSubjects() throws Exception
    {

        String response = connection.send("subjects");

        Subjects subjects = (Subjects) JSONUtil.fromJSON(response, Subjects.class);

        System.out.println(response);

        return subjects;
    }





    public void getUser(GetUser user) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(user),"user");

        System.out.println(response);
    }


    public void getSubject(String subjectId)
    {
        try {
            GetSubject getSubject = new GetSubject();
            getSubject.setSubjectId(subjectId);

            String response = connection.send(JSONUtil.toJSON(getSubject),"subject");

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void post(AddPost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"post");

        System.out.println(response);
    }

    public void post(DeletePost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"deletepost");

        System.out.println(response);
    }


    public void retrieve(RetrievePost post) throws Exception
    {

        String response = connection.send(JSONUtil.toJSON(post),"retrieve");

        System.out.println(response);
    }




    private void followSubject(User user1, Subject subject) {
        try {
            FollowSubject followSubject = new FollowSubject();
            followSubject.setSubject(subject);
            followSubject.setUser(user1);

            String response = connection.send(JSONUtil.toJSON(followSubject),"followsubject");

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UnFollowSubject(User user1, Subject subject) {
        try {
            UnFollowSubject unFollowSubject = new UnFollowSubject();
            unFollowSubject.setSubject(subject);
            unFollowSubject.setUser(user1);

            String response = connection.send(JSONUtil.toJSON(unFollowSubject),"unfollowsubject");

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws Exception {



        ClientVerifier verifier = new ClientVerifier();






       for (int i=0;i<4;i++)
            verifier.addUser(new User("A" + String.valueOf(i)));


        List.of("politics","technology","health").stream().forEach(s->{

            Subject subject = new Subject(s);
            verifier.addSubject(subject);

        });

        Post post = new Post();

        for (int i=0;i<5;i++) {
            post.setId(UUID.randomUUID().toString());
            post.setMessage("New Post " + System.nanoTime());
            post.setPoster(new User("A1"));
            post.setSubject(new Subject("health"));

            AddPost addPost = new AddPost();
            addPost.setPost(post);
            verifier.post(addPost);





        }

        {
            //System.out.println(subjects);
            Subject subject1 = new Subject("politics");
            Subject subject2 = new Subject("technology");
            Subject subject3 = new Subject("health");


            User user1 = new User("A1");
            User user2 = new User("A2");
            verifier.followSubject(user1,subject1);
            verifier.followSubject(user2,subject2);
            verifier.followSubject(user1,subject3);


            verifier.followSubject(user1,subject2);
            verifier.followSubject(user2,subject3);

            verifier.UnFollowSubject(user1,subject2);
            verifier.UnFollowSubject(user2,subject3);
        }


        Users users = verifier.getUsers();

        users.getUsers().stream().forEach(u->{

            GetUser getUser = new GetUser();
            getUser.setUserId(u);
            try {
                verifier.getUser(getUser);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        Subjects subjects = verifier.getSubjects();

        subjects.getSubjects().stream().forEach(s->{

            verifier.getSubject(s);

        });




     /*   FollowUser followUser = new FollowUser();
        followUser.setSelf(new User("A1"));
        followUser.setTarget(new User("A2"));

        verifier.followUser(followUser);




        for (int i=0;i<4;i++)
            verifier.deleteUser(new User("A"+ String.valueOf(i)));






        List.of("politics","technology","health").stream().forEach(s->{

            Subject subject = new Subject(s);
            verifier.deleteSubject(subject);

        });




        DeletePost deletePost = new DeletePost();
        deletePost.setPost(post);
        verifier.post(deletePost); */



 /*     User user1 = new User("User1191");
      User user2 = new User("User2191");


        verifier.addUser(user1);
        verifier.addUser(user2);


        Users users = verifier.getUsers();
        users.getUsers().stream().forEach(u->{

            GetUser getUser = new GetUser();
            getUser.setUserId(u);
            try {
                verifier.getUser(getUser);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });



        UnFollowUser unFollowUser = new UnFollowUser();
        unFollowUser.setSelf(user1);
        unFollowUser.setTarget(user2);

        verifier.unFollowUser(unFollowUser);






       Subject subject2 = new Subject("politics");
        verifier.deleteSubject(subject2);






        //System.out.println(subjects);
        Subject subject1 = new Subject("politics");
        Subject subject2 = new Subject("technology");
        Subject subject3 = new Subject("health");



        verifier.followSubject(user1,subject1);
        verifier.followSubject(user2,subject2);
        verifier.followSubject(user1,subject3);


    //    verifier.deleteUser(user1);
      //  verifier.deleteUser(user2);










        //  verifier.UnFollowSubject(user1,subject);*/



    }




}
