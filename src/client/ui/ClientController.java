package client.ui;

import client.Client;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import models.Test;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public Button addBtn;
    public Button deleteBtn;
    public ListView<Test> testList;
    public Button testBtn;
    public Button resultBtn;

    public AnchorPane testPane;
    public Label questionLabel;
    public ToggleGroup variants;
    public RadioButton var1RB;
    public RadioButton var2RB;
    public RadioButton var3RB;
    public RadioButton var4RB;
    public Button backBtn;
    public Button doneBtn;
    public Button forwardBtn;

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

        var1RB.setOnAction(event -> {

        });
        var2RB.setOnAction(event -> {

        });
        var3RB.setOnAction(event -> {

        });
        var4RB.setOnAction(event -> {

        });
        backBtn.setOnAction(event -> {

        });
        forwardBtn.setOnAction(event -> {

        });
        doneBtn.setOnAction(event -> {

        });
    }
}
