package server.ui;

import javafx.fxml.Initializable;
import server.RequestListener;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    private final RequestListener server = new RequestListener();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.start();
    }
}
