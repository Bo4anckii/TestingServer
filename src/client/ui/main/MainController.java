package client.ui.main;

import client.Client;
import client.ui.add.AddController;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Question;
import models.Test;
import models.TestResult;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button addBtn;
    public Button deleteBtn;
    public ListView<Test> testList;
    public Button testBtn;
    public Button resultBtn;
    public TextField fioTF;

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
    private final MainModel model = new MainModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client.start();
        model.setTests(client.getTests());
        testList.setItems(FXCollections.observableList(model.getTests()));

        addBtn.setOnAction(event -> openAddForm());
        deleteBtn.setOnAction(event -> {});
        testBtn.setOnAction(event -> {
            if (testList.getSelectionModel().getSelectedIndex() == -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Сначала выберите тест");
                alert.showAndWait();
                return;
            }
            model.setCurrentTest(testList.getSelectionModel().getSelectedItem());
            model.setAnswers(new ArrayList<>());
            for (Question q : model.getCurrentTest().getQuestions()) {
                model.getAnswers().add(0);
            }
            model.setCurrentQuestionIndex(0);
            setQuestion(testList.getSelectionModel().getSelectedItem().getQuestions().get(0));
            testPane.setVisible(true);
        });
        resultBtn.setOnAction(event -> openResultsForm());

        var1RB.setOnAction(event -> {
            model.setAnswer(model.getCurrentQuestionIndex(), 1);
            System.out.println("Question " + model.getCurrentQuestionIndex() + " = 1");
            checkAnswers();
        });
        var2RB.setOnAction(event -> {
            model.setAnswer(model.getCurrentQuestionIndex(), 2);
            System.out.println("Question " + model.getCurrentQuestionIndex() + " = 2");
            checkAnswers();
        });
        var3RB.setOnAction(event -> {
            model.setAnswer(model.getCurrentQuestionIndex(), 3);
            System.out.println("Question " + model.getCurrentQuestionIndex() + " = 3");
            checkAnswers();
        });
        var4RB.setOnAction(event -> {
            model.setAnswer(model.getCurrentQuestionIndex(), 4);
            System.out.println("Question " + model.getCurrentQuestionIndex() + " = 4");
            checkAnswers();
        });
        backBtn.setOnAction(event -> {
            model.setCurrentQuestionIndex(model.getCurrentQuestionIndex() - 1);
            setQuestion((model.getCurrentTest().getQuestions().get(model.getCurrentQuestionIndex())));
            if (model.getCurrentQuestionIndex() == 0) {
                backBtn.setDisable(true);
            }
            if (model.getCurrentQuestionIndex() - 1 < model.getCurrentTest().getQuestions().size()) {
                forwardBtn.setDisable(false);
            }
        });
        forwardBtn.setOnAction(event -> {
            model.setCurrentQuestionIndex(model.getCurrentQuestionIndex() + 1);
            setQuestion(model.getCurrentTest().getQuestions().get(model.getCurrentQuestionIndex()));
            if (model.getCurrentQuestionIndex() == 1) {
                backBtn.setDisable(false);
            }
            if (model.getCurrentQuestionIndex() + 1 == model.getCurrentTest().getQuestions().size()) {
                forwardBtn.setDisable(true);
            }
        });
        doneBtn.setOnAction(event -> {
            if (fioTF.getText().length() < 6) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("ФИО должно быть длиной не менее 6 букв");
                alert.showAndWait();
                return;
            }
            int percent = 0;
            for (int i = 0; i < model.getAnswers().size(); i++) {
                if (model.getCurrentTest().getQuestions().get(i).getCorrect() == model.getAnswers().get(i)) {
                    percent += 100;
                }
            }
            percent /= model.getAnswers().size();
            TestResult result = new TestResult(System.currentTimeMillis(), model.getCurrentTest().getId(), fioTF.getText(), new Date(System.currentTimeMillis()), percent);
            client.postResult(result);
            testPane.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание");
            alert.setHeaderText("Тест пройден (" + percent + "%)");
            alert.showAndWait();
        });
    }

    private void setQuestion(Question question) {
        model.setCurrentQuestion(question);
        questionLabel.setText(question.getText());
        var1RB.setText(question.getVariant1());
        var1RB.setSelected(false);
        var2RB.setText(question.getVariant2());
        var2RB.setSelected(false);
        var3RB.setText(question.getVariant3());
        var3RB.setSelected(false);
        var4RB.setText(question.getVariant4());
        var4RB.setSelected(false);
    }

    private void checkAnswers() {
        for (Integer answer : model.getAnswers()) {
            if (answer == 0) {
                return;
            }
        }
        doneBtn.setVisible(true);
    }

    private void openAddForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/add_view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Создание теста");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();
            AddController children = loader.getController();
            children.setParent(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openResultsForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/results_view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Результаты");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
