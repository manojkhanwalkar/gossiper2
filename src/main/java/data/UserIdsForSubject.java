package data;

import java.util.ArrayList;

public class UserIdsForSubject {

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

    String subjectId;


    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}