package data;

import java.util.Objects;

public class Subject {

     String id ;
     String name ;

    //List<User> followers = new ArrayList<>();

    public Subject()
    {

    }

    public Subject(String name)
    {
        this.name = name;
        this.id = generateId();
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String generateId()
    {
        return String.valueOf(name.hashCode());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
