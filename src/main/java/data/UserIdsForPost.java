package data;

import java.util.ArrayList;

public class UserIdsForPost {

    ArrayList<String> userIds = new ArrayList<>();

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public void addUserId(String userId)
    {
        this.userIds.add(userId);
    }

    Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}