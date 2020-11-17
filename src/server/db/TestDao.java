package server.db;

import models.Test;
import models.TestResult;

import java.util.List;

public interface TestDao {

    List<Test> getTests();

    List<TestResult> getResults();

    void postTest(Test test);

    void postResult(TestResult result);

    void deleteTest(String id);
}