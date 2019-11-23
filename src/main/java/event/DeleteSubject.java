package event;

import com.fasterxml.jackson.annotation.JsonProperty;
import data.Subject;

public class DeleteSubject implements Event {

   @JsonProperty
   Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
