package services.post;

import com.codahale.metrics.annotation.Timed;
import data.*;
import event.*;
import processor.Dispatcher;
import processor.PostDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;



    PostDispatcher dispatcher = new PostDispatcher();


    public PostResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();



    }






    @POST
    @Timed
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public String post(AddPost post) {


         dispatcher.dispatch(post);

         return "posted";



    }


    @POST
    @Timed
    @Path("/deletepost")
    @Produces(MediaType.APPLICATION_JSON)
    public String post(DeletePost post) {


        dispatcher.dispatch(post);

        return "post deleted";



    }

    @POST
    @Timed
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public Posts post(PostIds postIds) {


       return  dispatcher.dispatch(postIds);




    }













}
