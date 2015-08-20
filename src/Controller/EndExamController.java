package Controller;

import Helper.FileWorker;
import Model.AnswerSheet;
import Model.MainExam;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * This is the final screen of the Student Module.
 * It automatically saves the exam while it gives an option to the user
 * to save the Answer Copy
 */

public class EndExamController {
    //Initializing the FXML variables
    @FXML
    Label attemptedLabel;

    private MainExam mainExam;
    private AnswerSheet answerSheet;

    //Explicitly called by the QuestionSolveController
    public void setData(MainExam mainExam, AnswerSheet answerSheet) {
        this.mainExam = mainExam;
        this.answerSheet = answerSheet;
        attemptedLabel.setText("" + answerSheet.getNumberAnswered() + "/" + mainExam.getQuestionBank().size());
        saveAnswerSheet();
    }

    //This saves the AnswerSheet which is then used to calculate the marks
    public void saveAnswerSheet() {
        File file = new File(answerSheet.getStudRoll() + ".ans");
        FileWorker.writeAnswerSheet(file, answerSheet);
    }

    //Called when user wants to save his copy of answerSheet
    @FXML
    public void saveFile(Event event) {
        FileChooser fileChooser = new FileChooser();
        answerSheet.setFileChooser(fileChooser);
        File file = fileChooser.showSaveDialog(getStage());
        if (file != null)
            FileWorker.writeAnswerCopy(file, mainExam, answerSheet);
    }

    //Called to show about us
    @FXML
    public void showAboutUs(Event event) {
        new AboutUsLoader().showAboutUs();
    }

    public Stage getStage() {
        return (Stage) attemptedLabel.getScene().getWindow();
    }
}
