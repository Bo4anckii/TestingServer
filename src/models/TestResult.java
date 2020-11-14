package models;

import java.util.Date;

public class TestResult {
    private final int id;
    private final Test test;
    private final String person;
    private final Date date;
    private final int result;

    public TestResult(int id, Test test, String person, Date date, int result) {
        this.id = id;
        this.test = test;
        this.person = person;
        this.date = date;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    public int getResult() {
        return result;
    }

    public Test getTest() {
        return test;
    }
}
