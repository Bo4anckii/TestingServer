package server.db;

import models.Question;
import models.Test;
import models.TestResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {

    private final DatabaseHandler databaseHandler = new DatabaseHandler();

    @Override
    public List<Test> getTests() {
        ArrayList<Test> tests = new ArrayList<>();
        try (
                Statement statement = databaseHandler.getConnection().createStatement();
                ResultSet set = statement.executeQuery(Const.TESTS_GET_ALL)
        ) {
            while (set.next()) {
                tests.add(new Test(
                        set.getInt(Const.TEST_ID),
                        new ArrayList<>(),
                        set.getString(Const.TEST_SUBJECT)
                ));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        for (Test test : tests) {
            test.getQuestions().addAll(getTestQuestions(test.getId()));
        }
        return tests;
    }

    @Override
    public List<TestResult> getResults() {
        ArrayList<TestResult> results = new ArrayList<>();
        try (
                Statement statement = databaseHandler.getConnection().createStatement();
                ResultSet set = statement.executeQuery(Const.RESULTS_GET_ALL)
        ) {
            while (set.next()) {
                results.add(new TestResult(
                        set.getLong(Const.RESULT_ID),
                        set.getLong(Const.RESULT_ID_TEST),
                        set.getString(Const.RESULT_PERSON),
                        set.getDate(Const.RESULT_DATE),
                        set.getInt(Const.RESULT_RESULT)
                ));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        for (TestResult result : results) {
            result.setTest(getTest(result.getIdTest()));
        }
        return results;
    }

    @Override
    public void postTest(Test test) {
        try {
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(Const.TEST_POST);
            statement.setString(1, test.getSubject());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        long test_id;
        try (
                Statement statement = databaseHandler.getConnection().createStatement();
                ResultSet set = statement.executeQuery(Const.TEST_MAX_ID)
        ) {
            if(set.next()){
                test_id = set.getLong("max_id");
            } else return;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        for (Question question : test.getQuestions()) {
            try {
                PreparedStatement statement = databaseHandler.getConnection().prepareStatement(Const.QUESTION_POST);
                statement.setLong(1, test_id);
                statement.setString(2, question.getText());
                statement.setString(3, question.getVariant1());
                statement.setString(4, question.getVariant2());
                statement.setString(5, question.getVariant3());
                statement.setString(6, question.getVariant4());
                statement.setInt(7, question.getCorrect());
                statement.executeUpdate();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void postResult(TestResult result) {
        try {
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(Const.RESULT_POST);
            statement.setLong(1, result.getIdTest());
            statement.setString(2, result.getPerson());
            statement.setDate(3, result.getDate());
            statement.setInt(4, result.getResult());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteTest(String id) {
        try {
            PreparedStatement statement = databaseHandler.getConnection().prepareStatement(Const.TEST_DELETE);
            statement.setLong(1, Long.parseLong(id));
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private List<Question> getTestQuestions(long idTest) {
        ArrayList<Question> questions = new ArrayList<>();
        try (PreparedStatement statement = databaseHandler.getConnection().prepareStatement(Const.QUESTIONS_BY_TEST)) {
            statement.setLong(1, idTest);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                questions.add(new Question(
                        set.getInt(Const.QUESTION_ID),
                        set.getInt(Const.QUESTION_ID_TEST),
                        set.getString(Const.QUESTION_TEXT),
                        set.getString(Const.QUESTION_VARIANT1),
                        set.getString(Const.QUESTION_VARIANT2),
                        set.getString(Const.QUESTION_VARIANT3),
                        set.getString(Const.QUESTION_VARIANT4),
                        set.getInt(Const.QUESTION_CORRECT)
                ));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    private Test getTest(long id) {
        Test test = null;
        String command = "Select * From " + Const.TESTS_TABLE + " Where " + Const.TEST_ID + " = " + id;
        try (
                Statement statement = databaseHandler.getConnection().createStatement();
                ResultSet set = statement.executeQuery(command)
        ) {
            while (set.next()){
                test = new Test(
                        set.getInt(Const.TEST_ID),
                        new ArrayList<>(),
                        set.getString(Const.TEST_SUBJECT)
                );
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        if (test != null) {
            test.getQuestions().addAll(getTestQuestions(test.getId()));
        }
        return test;
    }
}
