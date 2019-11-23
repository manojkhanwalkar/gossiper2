package event;

import data.Post;

public class DeletePost implements Event {

    Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
