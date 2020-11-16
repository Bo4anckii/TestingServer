package models;

import java.util.List;

public class Test {
    private final long id;
    private final List<Question> questions;
    private final String subject;

    public Test(long id, List<Question> questions, String subject) {
        this.id = id;
        this.questions = questions;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return subject+" ("+questions.size()+")";
    }
}
