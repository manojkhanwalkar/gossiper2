package data;

import java.util.ArrayList;

public class SubjectIdsForUser {

    ArrayList<String> subjectIDs = new ArrayList<>();

    public ArrayList<String> getSubjectIDs() {
        return subjectIDs;
    }

    public void setSubjectIDs(ArrayList<String> subjectIDs) {
        this.subjectIDs = subjectIDs;
    }

    public void addSubjectId(String subjectId)
    {
        subjectIDs.add(subjectId);
    }

    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserIdsForSubject{" +
                "subjectIDs=" + subjectIDs +
                ", userId='" + userId + '\'' +
                '}';
    }
}