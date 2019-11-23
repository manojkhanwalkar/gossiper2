package event;

import data.Subject;
import data.User;

public class UnFollowSubject implements Event {

    User user ;
    Subject subject;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
