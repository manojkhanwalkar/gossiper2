package data;



import java.util.ArrayList;
import java.util.List;


public class SubjectInfo {

    private String subjectId;

    private String name;



    private List<String> followedBy = new ArrayList<>();

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "subjectId='" + subjectId + '\'' +
                ", name='" + name + '\'' +
                ", followedBy=" + followedBy +
                '}';
    }
}
