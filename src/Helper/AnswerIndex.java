package Helper;

import Model.QuestionSelector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Created by Farhan on 08-08-2015.
 */
public class AnswerIndex {
    private GridPane gridPane;
    private int size;
    private QuestionSelector questionSelector;

    public AnswerIndex(int size, GridPane gridPane, QuestionSelector questionSelector) {
        this.questionSelector = questionSelector;
        this.size = size;
        System.out.print("" + size);
        this.gridPane = gridPane;
        initIndex();
    }

    private void initIndex() {
        int column = 0;
        int row = 0;
        for (int i = 0; i < size; ++i) {
            Button button = new Button("" + (i + 1));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    questionSelector.changeCounter(Integer.parseInt(button.getText()) - 1);
                }
            });
            gridPane.add(button, row, column);
            ++row;
            if (row >= 5) {
                row = 0;
                column++;
            }
        }
    }
}
