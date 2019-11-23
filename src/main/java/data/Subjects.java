package data;

import java.util.ArrayList;

public class Subjects {

    ArrayList<String> subjects = new ArrayList<>();

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(String subject)
    {
        subjects.add(subject);
    }


    @Override
    public String toString() {
        return "Subjects{" +
                "subjects=" + subjects +
                '}';
    }
}

