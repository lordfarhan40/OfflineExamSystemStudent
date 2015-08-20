package Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This window Displays the instructions that may come attached with the Exam File
 */
public class InstructionWindowController {

    //Initializing FXML Variables
    @FXML
    Label instructionLabel;

    private String instruction;

    public void setTitle() {
        getStage().setTitle("Read Instructions for Exam");
    }

    //Called by StudentInfoController
    public void setInstruction(String instruction) {
        this.instruction = instruction;
        instructionLabel.setText(instruction);
    }

    //Close the window
    @FXML
    public void okClicked(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public Stage getStage() {
        return (Stage) instructionLabel.getScene().getWindow();
    }
}
