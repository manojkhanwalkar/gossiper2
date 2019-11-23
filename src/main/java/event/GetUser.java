package event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUser implements Event {

   @JsonProperty
   String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
