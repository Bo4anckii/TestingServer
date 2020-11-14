package models;

import java.util.List;

public class Test {
    private final int id;
    private final List<Question> questions;
    private final String subject;

    public Test(int id, List<Question> questions, String subject) {
        this.id = id;
        this.questions = questions;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getSubject() {
        return subject;
    }
}
