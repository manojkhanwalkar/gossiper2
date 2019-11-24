package data;

import java.util.ArrayList;

public class PostIds {

    ArrayList<String> postIds = new ArrayList<>();

    public ArrayList<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(ArrayList<String> postIds) {
        this.postIds = postIds;
    }


    public void addPostId(String postId)
    {
        postIds.add(postId);
    }


    @Override
    public String toString() {
        return "PostIds{" +
                "postIds=" + postIds +
                '}';
    }
}