package Controller;

import Model.AnswerSheet;
import Model.AppInitialise;
import Model.MainExam;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Farhan on 07-08-2015.
 */
public class StudentInfoController {
    @FXML
    Label examName, timeAllotted, warningLabel;
    @FXML
    TextField studentName, studentRollNumber;
    @FXML
    Button instructionButton;
    private MainExam mainExam;
    private AnswerSheet answerSheet;
    private AppInitialise appInitialise;

    public StudentInfoController() {
        mainExam = new MainExam();
        answerSheet = new AnswerSheet();
        appInitialise = new AppInitialise(Main.initFile);
    }

    public void setMainExam(MainExam mainExam) {
        this.mainExam = mainExam;
        examName.setText(mainExam.getExamTitle());
        timeAllotted.setText("Time Allotted: " + mainExam.getTimeMinutes() + " Minutes");
        if (mainExam.getExamInstructions().equals("0")) {
            instructionButton.setDisable(true);
        }
    }

    @FXML
    public void closeExam(Event e) {
        try {
            appInitialise.unloadExam();
            Parent parent = FXMLLoader.load(getClass().getResource("../View/Welcome.fxml"));
            Stage primaryStage = getStage(examName);
            primaryStage.setScene(new Scene(parent));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    public void startExam(Event e) {
        try {
            if (setStudentInfo()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../View/QuestionSolve.fxml"));
                Parent parent = fxmlLoader.load();
                getStage((Node) e.getSource()).setScene(new Scene(parent));
                QuestionSolveController questionSolveController = fxmlLoader.getController();
                questionSolveController.setData(mainExam, answerSheet);
                questionSolveController.setOnRequest();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean setStudentInfo() {
        if (studentName.getText().equals("") && studentName.getText().equals("")) {
            warningLabel.setOpacity(1);
            return false;
        }
        answerSheet.setStudentName(studentName.getText());
        answerSheet.setStudRoll(studentRollNumber.getText());
        return true;
    }

    @FXML
    public void readInstructions(Event event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/InstructionWindow.fxml"));
            Parent parent = fxmlLoader.load();
            InstructionWindowController instructionWindowController = fxmlLoader.getController();
            instructionWindowController.setInstruction(mainExam.getExamInstructions());
            stage.setScene(new Scene(parent));
            stage.show();
            instructionWindowController.setTitle();
        } catch (Exception e) {
            System.out.println("Problem loading instruction.");
            e.printStackTrace();
        }
    }

    @FXML
    public void showAboutUs(Event event) {
        new AboutUsLoader().showAboutUs();
    }

    private Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }


}
