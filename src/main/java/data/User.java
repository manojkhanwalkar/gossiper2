package data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

//TODO - for ease of testing duplicate names are assumed not to exist and id is hash of name.

public class User {

@JsonProperty
     String id ;

@JsonProperty
     String name ;



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User()
    {

    }

    public User(String name)
    {
        this.name = name;
        this.id = generateId();
    }

    private String generateId()
    {
        return String.valueOf(name.hashCode());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
