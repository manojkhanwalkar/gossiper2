package event;

import data.Post;

public class AddPost implements Event {

    Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
