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


    public void dispatch(DeletePost event)
    {

        postManager.deletePost(event.getPost());

    }



}
