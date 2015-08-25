package Controller;

import Helper.FileWorker;
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
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Farhan on 05-08-2015.
 */
public class WelcomeController {

    //Initialising Variables of FXML variables
    @FXML
    private Label examLoadedText, passwordAlert, passwordInstruction;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loadButton, checkButton;

    private MainExam mainExam;
    private boolean passwordProtected;
    private File file;
    private AppInitialise appInitialise;

    public WelcomeController() {
        mainExam = null;
        passwordProtected = false;
        file = Main.initFile;
        appInitialise = new AppInitialise(file);
    }

    public void initialize() {
        setFile();
    }

    public void setFile() {
        if (file.exists()) {
            String arg = FileWorker.readFirst(file);
            if (arg.contains(FileWorker.PASSWORD_PROTECTED)) {
                setupForPassword();
            }
        } else {
            setupForLoad();
        }
    }

    public void setupForPassword() {
        makeInvisible(loadButton);
        makeInvisible(examLoadedText);
        makeVisible(checkButton);
        makeVisible(passwordField);
        makeVisible(passwordInstruction);
    }

    public void setupForLoad() {
        makeVisible(loadButton);
        makeVisible(examLoadedText);
        makeInvisible(checkButton);
        makeInvisible(passwordField);
        makeInvisible(passwordAlert);
        makeInvisible(passwordInstruction);
    }

    public void makeInvisible(Node node) {
        node.setOpacity(0);
    }

    public void makeVisible(Node node) {
        node.setOpacity(1);
    }

    @FXML
    private void loadExam(Event e) {
        FileChooser fileChooser = new FileChooser();
        MainExam.examFileChooserSettings(fileChooser);
        File f = fileChooser.showOpenDialog(getStage());
        if (f == null)
            return;
        System.out.print(appInitialise);
        appInitialise.loadExam(f);
        System.out.print(appInitialise);
        if (appInitialise.isPasswordProtected()) {
            setupForPassword();
        } else {
            try {
                MainExam mainExam = FileWorker.readFromFile(file, "");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("StudentInfo.fxml"));
                Parent parent = fxmlLoader.load();
                StudentInfoController studentInfoController = fxmlLoader.getController();
                studentInfoController.setMainExam(mainExam);
                getStage().setScene(new Scene(parent));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    private void passwordCheck(Event e) {
        try {
            MainExam mainExam = FileWorker.readFromFile(file, passwordField.getText());
            if (mainExam == null) {
                passwordField.setText("");
                makeVisible(passwordAlert);
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StudentInfo.fxml"));
            Parent parent = fxmlLoader.load();
            StudentInfoController studentInfoController = fxmlLoader.getController();
            studentInfoController.setMainExam(mainExam);
            getStage().setScene(new Scene(parent));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void unloadExam(Event e) {
        appInitialise.unloadExam();
        setupForLoad();
    }

    @FXML
    public void showAboutUs(Event event) {
        new AboutUsLoader().showAboutUs();
    }

    private Stage getStage() {
        return (Stage) loadButton.getScene().getWindow();
    }
}
