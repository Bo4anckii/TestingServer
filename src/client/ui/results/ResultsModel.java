package client.ui.results;

import models.TestResult;

import java.util.ArrayList;
import java.util.List;

public class ResultsModel {

    private List<TestResult> results = new ArrayList<>();

    public List<TestResult> getResults() {
        return results;
    }

    public void setResults(List<TestResult> results) {
        this.results = results;
    }
}
