package models;

import java.sql.Date;

public class TestResult {
    private final long id;
    private final int idTest;
    private Test test;
    private final String person;
    private final Date date;
    private final int result;

    public TestResult(long id, int idTest, String person, Date date, int result) {
        this.id = id;
        this.idTest = idTest;
        this.person = person;
        this.date = date;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public int getIdTest() {
        return idTest;
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

    public void setTest(Test test) {
        this.test = test;
    }

}
