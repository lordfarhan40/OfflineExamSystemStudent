package Controller;

import Helper.FileWorker;
import Model.AppInitialise;
import Model.MainExam;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * This Method is the starting method. This is initialise and see if there is any previous exam file saved.
 * If found it will load the StudentInfo screen
 * Else it will load the Welcome Screen so that the user can load a new exam.
 */
public class Main extends Application {

    //Define a static init file so that it can be used later.
    final static File initFile = new File("init.txt");

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        AppInitialise appInitialise = new AppInitialise(initFile);
        //If there is an exam loaded and it is not password protected
        if (appInitialise.isPasswordNotProtected()) {

            //Read the exam from the file
            MainExam mainExam = FileWorker.readFromFile(initFile, "");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/StudentInfo.fxml"));
            Parent parent = fxmlLoader.load();
            StudentInfoController studentInfoController = fxmlLoader.getController();

            //Now lets set the main exam
            studentInfoController.setMainExam(mainExam);
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
        } else {
            //If either the exam that is loaded in Protected or there is none loaded
            Parent parent = FXMLLoader.load(getClass().getResource("../View/Welcome.fxml"));
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
        }
    }
}
