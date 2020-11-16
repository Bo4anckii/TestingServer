package server.db;

public class Const {
    public static String TESTS_TABLE       = "tests";
    public static String TEST_ID           = "id";
    public static String TEST_SUBJECT      = "subject";
    public static String TESTS_GET_ALL     = "Select * From " + TESTS_TABLE;
    public static String TEST_POST         = "Insert into " + TESTS_TABLE + " (" + TEST_SUBJECT + ") Values(?)";
    public static String TEST_MAX_ID       = "Select MAX(" + TEST_ID + ") max_id FROM " + TESTS_TABLE;

    public static String QUESTIONS_TABLE   = "questions";
    public static String QUESTION_ID       = "id";
    public static String QUESTION_ID_TEST  = "id_test";
    public static String QUESTION_TEXT     = "text";
    public static String QUESTION_VARIANT1 = "variant1";
    public static String QUESTION_VARIANT2 = "variant2";
    public static String QUESTION_VARIANT3 = "variant3";
    public static String QUESTION_VARIANT4 = "variant4";
    public static String QUESTION_CORRECT  = "correct";
    public static String QUESTIONS_BY_TEST = "Select * From " + QUESTIONS_TABLE + " Where " + QUESTION_ID_TEST + " = ?";
    public static String QUESTION_POST     = "Insert into " + QUESTIONS_TABLE + " (" + QUESTION_ID_TEST + ", " +
                                             QUESTION_TEXT + ", " + QUESTION_VARIANT1 + ", " + QUESTION_VARIANT2 + ", " +
                                             QUESTION_VARIANT3 + ", " + QUESTION_VARIANT4 + ", " + QUESTION_CORRECT + ") Values(?,?,?,?,?,?,?)";

    public static String RESULTS_TABLE     = "results";
    public static String RESULT_ID         = "id";
    public static String RESULT_ID_TEST    = "id_test";
    public static String RESULT_PERSON     = "person";
    public static String RESULT_DATE       = "date";
    public static String RESULT_RESULT     = "result";
    public static String RESULT_POST       = "Insert into " + RESULTS_TABLE + " (" + RESULT_ID_TEST + ", " +
                                             RESULT_PERSON + ", " + RESULT_DATE + ", " + RESULT_RESULT + ") Values(?,?,?,?)";
}
