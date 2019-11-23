package data;



import java.util.ArrayList;
import java.util.List;


public class UserInfo {

    private String userId;

    private String name;

    private List<String> follows = new ArrayList<>();

    private List<String> followedBy = new ArrayList<>();

    private List<String> followsSubject = new ArrayList<>();




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }





    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }




    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
    }




    public List<String> getFollowsSubject() {
        return followsSubject;
    }

    public void setFollowsSubject(List<String> followsSubject) {
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
