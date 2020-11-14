package client.ui;

import client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.Test;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public Button addBtn;
    public Button deleteBtn;
    public ListView<Test> testList;
    public Button testBtn;
    public Button resultBtn;
    private final Client client = new Client();
    private final ClientModel model = new ClientModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client.start();
        model.setTests(client.getTests());
        testList.setItems(FXCollections.observableList(model.getTests()));

        addBtn.setOnAction(event -> {

        });
        deleteBtn.setOnAction(event -> {

        });
        testBtn.setOnAction(event -> {

        });
        resultBtn.setOnAction(event -> {

        });
    }
}
