package persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName="User")
public class UserRecord {

    private String userId;

    private String name;

    private List<String> follows = new ArrayList<>();

    private List<String> followedBy = new ArrayList<>();

    private List<String> followsSubject = new ArrayList<>();


    @DynamoDBHashKey(attributeName = "UserId")

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    @DynamoDBAttribute(attributeName="follows")

    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }


    @DynamoDBAttribute(attributeName="followedBy")

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
    }


    @DynamoDBAttribute(attributeName="followsSubject")

    public List<String> getFollowsSubject() {
        return followsSubject;
    }

    public void setFollowsSubject(List<String> followsSubject) {
        this.followsSubject = followsSubject;
    }


    @DynamoDBAttribute(attributeName="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "UserRecord{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", follows=" + follows +
                ", followedBy=" + followedBy +
                ", followsSubject=" + followsSubject +
                '}';
    }
}
