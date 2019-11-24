package processor;

import data.*;
import event.*;
import manager.PostManager;
import manager.SubjectManager;
import manager.UserManager;

public class PostDispatcher {

  PostManager postManager = PostManager.getInstance();



    public void dispatch(AddPost event)
    {

        postManager.addPost(event.getPost());

    }

    public Posts dispatch(PostIds event)
    {

        Posts posts = new Posts();

        event.getPostIds().stream().forEach(id->{

           Post post =  postManager.getPost(id);
           if (post!=null)
               posts.addPost(post);
        });

       return posts;

    }



    public void dispatch(DeletePost event)
    {

        postManager.deletePost(event.getPost());

    }



}
