package client.ui;

import models.Question;
import models.Test;

import java.util.List;

public class ClientModel {
    private List<Test> tests;
    private Test currentTest;
    private Question currentQuestion;
    private int currentQuestionIndex;
    private List<Integer> answers;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public Test getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(Test currentTest) {
        this.currentTest = currentTest;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public void setAnswer(int index, int answer) {
        answers.set(index, answer);
    }
}
