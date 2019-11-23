package event;

import com.fasterxml.jackson.annotation.JsonProperty;
import data.User;

public class RetrievePost implements Event {

    @JsonProperty
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
