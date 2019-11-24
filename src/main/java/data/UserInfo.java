package data;



import java.util.ArrayList;
import java.util.List;


public class UserInfo {

    private String userId;

    private String name;

    private ArrayList<String> follows = new ArrayList<>();

    private ArrayList<String> followedBy = new ArrayList<>();

    private ArrayList<String> followsSubject = new ArrayList<>();




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }





    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<String> follows) {
        this.follows = follows;
    }




    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(ArrayList<String> followedBy) {
        this.followedBy = followedBy;
    }




    public List<String> getFollowsSubject() {
        return followsSubject;
    }

    public void setFollowsSubject(ArrayList<String> followsSubject) {
        this.followsSubject = followsSubject;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", follows=" + follows +
                ", followedBy=" + followedBy +
                ", followsSubject=" + followsSubject +
                '}';
    }
}
