package client.ui.add;

import client.Client;
import client.ui.main.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Question;
import models.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    public AnchorPane initialPane;
    public TextField subjectTF;
    public Spinner<Integer> amountSpinner;
    public Button startBtn;

    public AnchorPane testPane;
    public TextArea questionText;
    public TextField var1TF;
    public TextField var2TF;
    public TextField var3TF;
    public TextField var4TF;
    public TextField correctTF;
    public Button backBtn;
    public Button doneBtn;
    public Button forwardBtn;

    private MainController parent;
    private final AddModel model = new AddModel();
    private final Client client = new Client();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startBtn.setOnAction(event -> {
            if (subjectTF.getText().length() < 4) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Название темы должно состоять минимум из 4 букв");
                alert.showAndWait();
                return;
            }
            initData();
            setQuestion(0);
            initialPane.setVisible(false);
            testPane.setVisible(true);
        });

        questionText.textProperty().addListener((observable, oldValue, newValue) -> model.getQuestionList().get(model.getCurrentQuestionIndex()).setText(questionText.getText()));
        var1TF.textProperty().addListener((observable, oldValue, newValue) -> model.getQuestionList().get(model.getCurrentQuestionIndex()).setVariant1(var1TF.getText()));
        var2TF.textProperty().addListener((observable, oldValue, newValue) -> model.getQuestionList().get(model.getCurrentQuestionIndex()).setVariant2(var2TF.getText()));
        var3TF.textProperty().addListener((observable, oldValue, newValue) -> model.getQuestionList().get(model.getCurrentQuestionIndex()).setVariant3(var3TF.getText()));
        var4TF.textProperty().addListener((observable, oldValue, newValue) -> model.getQuestionList().get(model.getCurrentQuestionIndex()).setVariant4(var4TF.getText()));
        correctTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d")){
                model.getQuestionList().get(model.getCurrentQuestionIndex()).setCorrect(Integer.parseInt(correctTF.getText()));
            } else {
                try {
                    correctTF.setText(newValue.replaceAll("[^\\d]", ""));
                } catch (NumberFormatException ignored) {}
            }
        });

        backBtn.setOnAction(event -> {
            model.setCurrentQuestionIndex(model.getCurrentQuestionIndex() - 1);
            setQuestion(model.getCurrentQuestionIndex());
            if (model.getCurrentQuestionIndex() == 0) {
                backBtn.setDisable(true);
            }
            if (model.getCurrentQuestionIndex() - 1 < model.getNumberOfQuestions()) {
                forwardBtn.setDisable(false);
            }
        });
        forwardBtn.setOnAction(event -> {
            model.setCurrentQuestionIndex(model.getCurrentQuestionIndex() + 1);
            setQuestion(model.getCurrentQuestionIndex());
            if (model.getCurrentQuestionIndex() == 1) {
                backBtn.setDisable(false);
            }
            if (model.getCurrentQuestionIndex() + 1 == model.getNumberOfQuestions()) {
                forwardBtn.setDisable(true);
            }
        });
        doneBtn.setOnAction(event -> {
            if (checkDataFullness()) {
                client.postTest(new Test(model.getTestId(), model.getQuestionList(), model.getSubject()));
                refreshTestListView();
                ((Stage)(this.testPane.getScene().getWindow())).close();
            }
        });
    }

    private void initData() {
        model.setNumberOfQuestions(amountSpinner.getValue());
        model.setTestId((long)(System.currentTimeMillis()+Math.random()*100000000));
        model.setSubject(subjectTF.getText());
        model.setQuestionList(new ArrayList<>());
        for (int i = 0; i < amountSpinner.getValue(); i++) {
            model.getQuestionList().add(new Question((long)(System.currentTimeMillis()+Math.random()*100000000), model.getTestId(), "", "", "", "", "", 0));
        }
    }

    private void setQuestion(int index) {
        questionText.setText(model.getQuestionList().get(index).getText());
        var1TF.setText(model.getQuestionList().get(index).getVariant1());
        var2TF.setText(model.getQuestionList().get(index).getVariant2());
        var3TF.setText(model.getQuestionList().get(index).getVariant3());
        var4TF.setText(model.getQuestionList().get(index).getVariant4());
        correctTF.setText(String.valueOf(model.getQuestionList().get(index).getCorrect()));
    }

    private boolean checkDataFullness() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Внимание");
        int i = 0;
        for (Question q : model.getQuestionList()) {
            if (q.getVariant1().length() != 0 || q.getVariant2().length() != 0 || q.getVariant3().length() != 0 || q.getVariant4().length() != 0) {
                if (q.getText().length() != 0) {
                    if (q.getCorrect() < 1 || q.getCorrect() > 4) {
                        alert.setHeaderText("В вопросе №" + (i + 1) + " не выбран правильный ответ");
                        alert.showAndWait();
                        return false;
                    }
                } else {
                    alert.setHeaderText("В вопросе №" + (i + 1) + " не заполнено поле с текстом");
                    alert.showAndWait();
                    return false;
                }
            } else {
                alert.setHeaderText("В вопросе №" + (i + 1) + " заполнены не все варианты ответа");
                alert.showAndWait();
                return false;
            }
            i++;
        }
        return true;
    }

    public void setParent(MainController parent) {
        this.parent = parent;
    }

    private void refreshTestListView(){
        parent.testList.setItems(FXCollections.observableList(client.getTests()));
    }
}