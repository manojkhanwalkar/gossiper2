package data;

import java.util.ArrayList;

public class UserIdsForUser {

    ArrayList<String> follows = new ArrayList<>();

    ArrayList<String> followedBy = new ArrayList<>();

    String userId;

    public ArrayList<String> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<String> follows) {
        this.follows = follows;
    }

    public void addFollows(String id)
    {
        follows.add(id);
    }

    public ArrayList<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(ArrayList<String> followedBy) {
        this.followedBy = followedBy;
    }

    public void addFollowedBy(String id)
    {
        followedBy.add(id);
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserIdsForUser{" +
                "follows=" + follows +
                ", followedBy=" + followedBy +
                ", userId='" + userId + '\'' +
                '}';
    }
}