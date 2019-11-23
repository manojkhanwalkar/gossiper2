package data;

import java.util.ArrayList;

public class Posts {

    ArrayList<Post> posts = new ArrayList<>();

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }


    public void addPost(Post post)
    {
        posts.add(post);
    }

    @Override
    public String toString() {
        return "Posts{" +
                "posts=" + posts +
                '}';
    }
}