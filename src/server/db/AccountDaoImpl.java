package server.db;

import models.Test;
import models.TestResult;

import java.util.List;

public class AccountDaoImpl implements AccountDao{

    private final DatabaseHandler databaseHandler = new DatabaseHandler();

    @Override
    public List<Test> getTests() {
        return null;
    }

    @Override
    public List<TestResult> getResults() {
        return null;
    }

    @Override
    public void postTest(Test test) {

    }

    @Override
    public void postResult(TestResult result) {

    }
}
