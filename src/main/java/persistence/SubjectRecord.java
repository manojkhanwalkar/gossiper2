package persistence;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName="Subject")
public class SubjectRecord {

    private String subjectId;

    private String name;



    private List<String> followedBy = new ArrayList<>();

    @DynamoDBHashKey(attributeName = "SubjectId")

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }








    @DynamoDBAttribute(attributeName="followedBy")

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
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
        return "SubjectRecord{" +
                "subjectId='" + subjectId + '\'' +
                ", name='" + name + '\'' +
                ", followedBy=" + followedBy +
                '}';
    }
}
