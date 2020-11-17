package client.ui.results;

import client.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Test;
import models.TestResult;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    public TableView<TestResult> table;
    public TableColumn<TestResult, String> resultPerson;
    public TableColumn<TestResult, Test> resultTest;
    public TableColumn<TestResult, Integer> resultResult;
    public TableColumn<TestResult, Date> resultDate;

    private final ResultsModel model = new ResultsModel();
    private final Client client = new Client();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

    private void initTable() {
        resultPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        resultTest.setCellValueFactory(new PropertyValueFactory<>("test"));
        resultResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        resultDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        updateData();
    }

    private void updateData() {
        model.setResults(client.getResults());
        table.getItems().setAll(model.getResults());
    }
}
