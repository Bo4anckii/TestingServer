package client.ui;

import client.Client;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private final Client client = new Client();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client.start();
    }
}
