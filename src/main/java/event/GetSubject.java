package event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSubject implements Event {

   @JsonProperty
   String subjectId;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
