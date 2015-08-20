package Controller;

import Helper.AnswerIndex;
import Helper.ExamTimer;
import Model.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Farhan on 07-08-2015.
 */
public class QuestionSolveController implements TaskEnder, QuestionSelector {

    @FXML
    Button backButton, nextButton, markButton;
    @FXML
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    RadioButton[] radioButtons;
    @FXML
    Label timer, question, questionNumber;
    @FXML
    GridPane gridPane;
    ExamTimer examTimer;
    private QuestionReview questionReview;

    private MainExam mainExam;
    private AnswerSheet answerSheet;
    private int counter;
    private int questionnaireSize;

    @FXML
    public void initialize() {
        questionReview = new QuestionReview();
        counter = 0;
        radioButtons = new RadioButton[]{radioButton1, radioButton2, radioButton3, radioButton4};
    }


    public void setOnRequest() {
        Stage stage = (Stage) timer.getScene().getWindow();
        stage.setOnCloseRequest(event -> examTimer.stopTimer());
    }

    public void setData(MainExam mainExam, AnswerSheet answerSheet) {
        this.mainExam = mainExam;
        questionnaireSize = mainExam.getQuestionBank().size();
        this.answerSheet = answerSheet;
        answerSheet.setSize(mainExam.getQuestionBank().size());
        questionReview.setSize(mainExam.getQuestionBank().size());
        questionReview.setGridPane(gridPane);
        examTimer = new ExamTimer(mainExam.getTimeMinutes(), timer, this);
        examTimer.startTimer();
        AnswerIndex answerIndex = new AnswerIndex(mainExam.getQuestionBank().size(), gridPane, this);
        setQuestion();
    }

    @FXML
    public void showStudentInfo() {
        StudentInfoWindow studentInfoWindow = new StudentInfoWindow(answerSheet.getStudentName(), answerSheet.getStudRoll());
        studentInfoWindow.show();
    }

    @Override
    public void endTask() {
        endExam(null);
    }

    //updates the questions based on the current counter
    public void setQuestion() {
        if (questionReview.isMarkedForReview(counter)) {
            markButton.setText("Un Mark it");
        } else {
            if (answerSheet.getAnswer(counter) != -1) {
                Button temp = questionReview.getButton(counter);
                temp.setStyle("-fx-background-color:#A9BCF5");
            } else {
                Button temp = questionReview.getButton(counter);
                temp.setStyle("");
            }
            markButton.setText("Mark for Review");
        }
        setNavigationBackAndNext();
        for (int i = 0; i < 4; ++i)
            radioButtons[i].setSelected(false);
        questionNumber.setText("Question number " + (counter + 1));
        displayOption();
        displayQuestion();

    }

    //This is used to display the question of the current counter
    public void displayQuestion() {
        String question = mainExam.getQuestionBank().get(counter).getQuestion();
        this.question.setText(question);

    }

    //This is used to display the options of the current counter
    public void displayOption() {
        int i = 0;
        ArrayList<String> options = mainExam.getQuestionBank().get(counter).getOptions();
        while (i < options.size()) {
            radioButtons[i].setOpacity(1);
            radioButtons[i].setText(options.get(i));
            ++i;
        }
        if (answerSheet.getAnswer(counter) != -1)
            radioButtons[answerSheet.getAnswer(counter)].setSelected(true);
        while (i < 4) {
            radioButtons[i].setOpacity(0);
            ++i;
        }
    }

    //Called when someone clicks the radio button, its job is just to change the
    //answer sheet
    @FXML
    public void setOption(Event e) {
        for (int i = 0; i < 4; ++i) {
            if (radioButtons[i].isSelected()) {
                answerSheet.setAnswer(counter, i);
                break;
            }
        }
        setQuestion();
    }

    //Load next question, and then change question
    @FXML
    public void nextQuestion(Event e) {
        ++counter;
        setQuestion();
    }

    @FXML
    public void previousQuestion(Event e) {
        --counter;
        setQuestion();
    }

    //Load custom number, and then show the changes
    @Override
    public void changeCounter(int i) {
        counter = i;
        setQuestion();
    }

    //Change the Disability of Back and Next Button
    private void setNavigationBackAndNext() {
        if (counter == 0)
            backButton.setDisable(true);
        else
            backButton.setDisable(false);
        if (counter < (questionnaireSize - 1))
            nextButton.setDisable(false);
        else
            nextButton.setDisable(true);
    }

    private void setMarkedForReview(int i) {

    }

    @FXML
    public void setMarkReview(Event event) {
        if (questionReview.isMarkedForReview(counter)) {
            questionReview.unsetMarkedForReview(counter);
        } else {
            questionReview.setMarkedForReview(counter);
        }
        setQuestion();
    }

    @FXML
    public void endExam(Event e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/EndExam.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = getStage();
            stage.setScene(new Scene(parent));
            EndExamController endExamController = fxmlLoader.getController();
            endExamController.setData(mainExam, answerSheet);
        } catch (Exception exception) {

        }
    }

    @FXML
    public void showAboutUs(Event event) {
        new AboutUsLoader().showAboutUs();
    }

    private Stage getStage() {
        return (Stage) timer.getScene().getWindow();
    }

}
