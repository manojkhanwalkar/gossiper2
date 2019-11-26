package services.user;

import com.codahale.metrics.annotation.Timed;
import data.*;
import event.*;
import processor.UserDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;



    UserDispatcher dispatcher = new UserDispatcher();


    public UserResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();



    }


    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(AddUser request) {


        dispatcher.dispatch(request);

       // return keyExchangeManager.processExchange(request);

        return "User added";


    }


    @POST
    @Timed
    @Path("/deleteSubject")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteSubject(UserIdsForSubject request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Subject  deleted";


    }

    @POST
    @Timed
    @Path("/deleteFollowsAndFollowedForUser")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(UserIdsForUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "User deleted";


    }


    @POST
    @Timed
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(DeleteUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "User deleted";


    }


    @POST
    @Timed
    @Path("/follow")
    @Produces(MediaType.APPLICATION_JSON)
    public String follow(FollowUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower added";


    }


    @POST
    @Timed
    @Path("/followsubject")
    @Produces(MediaType.APPLICATION_JSON)
    public String followSubject(FollowSubject request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower added";


    }

    @POST
    @Timed
    @Path("/unfollow")
    @Produces(MediaType.APPLICATION_JSON)
    public String unfollow(UnFollowUser request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower removed";


    }

    @POST
    @Timed
    @Path("/unfollowsubject")
    @Produces(MediaType.APPLICATION_JSON)
    public String unfollowSubject(UnFollowSubject request) {


        dispatcher.dispatch(request);

        // return keyExchangeManager.processExchange(request);

        return "Follower removed";


    }

    @GET
    @Timed
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Users users() {


        return dispatcher.dispatch();




    }



    @POST
    @Timed
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo user(GetUser user) {


        return dispatcher.dispatch(user);



    }






    @POST
    @Timed
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public PostIds post(RetrievePost retrievePost) {


       return  dispatcher.dispatch(retrievePost);




    }


    @POST
    @Timed
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public String post(UserIdsForPost event) {


        dispatcher.dispatch(event);

        return "posted";



    }








}
