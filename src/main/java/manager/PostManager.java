package manager;

import data.Post;

import java.util.HashMap;
import java.util.Map;

public class PostManager {



    static class PostManagerHolder
    {
        static PostManager instance = new PostManager();
    }


    public static PostManager getInstance()
    {
        return PostManagerHolder.instance;
    }

    private PostManager()
    {

    }


    //TODO - implement a TTL cache that cleans up older posts

    Map<String, Post> posts = new HashMap<>();

    public void addPost(Post post)
    {
        posts.put(post.getId(),post);

        System.out.println(posts);
    }

    public Post getPost(String postId)
    {
        return posts.get(postId);
    }

    public void deletePost(Post post) {

        posts.remove(post.getId());
    }

    public boolean isExists(String postId)
    {
        return posts.containsKey(postId);
    }

}
