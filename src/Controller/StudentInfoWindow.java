package Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Farhan on 08-08-2015.
 */
public class StudentInfoWindow {
    private String studentName, studentRoll;

    public StudentInfoWindow(String name, String roll) {
        studentName = name;
        studentRoll = roll;
    }

    public void show() {
        VBox vbox = new VBox();
        Label labelStudentName = new Label();
        Label labelStudentRollNo = new Label();
        Button button = new Button("OK");
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        hbox1.setPadding(new Insets(20, 20, 0, 20));
        hbox2.setPadding(new Insets(20, 20, 0, 20));
        hbox3.setPadding(new Insets(20, 20, 20, 20));
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(labelStudentName);
        hbox2.getChildren().addAll(labelStudentRollNo);
        hbox3.getChildren().addAll(button);
        labelStudentName.setText("Student Name : " + studentName);
        labelStudentRollNo.setText("Student Roll Number : " + studentRoll);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox1, hbox2, hbox3);
        Stage stage = new Stage();
        stage.setScene(new Scene(vbox, 300, 200));
        stage.show();
        button.setOnAction(event -> stage.close());
        vbox.getChildren().add(button);
        stage.setTitle("Student ID");
    }
}
