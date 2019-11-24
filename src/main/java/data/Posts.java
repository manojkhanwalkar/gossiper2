package data;

import java.util.ArrayList;

public class Posts {

    ArrayList<Post> posts = new ArrayList<>();

    public static Posts combine(Posts... posts) {

        Posts combinedPosts = new Posts();

        for (Posts p: posts)
        {
            combinedPosts.addPosts(p.getPosts());
        }

        return combinedPosts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void addPosts(ArrayList<Post> posts) {
        this.posts.addAll(posts);
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