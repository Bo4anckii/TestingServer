package client.ui;

import models.Test;

import java.util.List;

public class ClientModel {
    private List<Test> tests;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
