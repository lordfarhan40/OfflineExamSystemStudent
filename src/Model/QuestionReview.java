package Model;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Created by Farhan on 10-08-2015.
 */
public class QuestionReview {

    ArrayList<Boolean> markedForReview;
    GridPane gridPane;

    public QuestionReview() {
        markedForReview = new ArrayList<>();
    }

    public void setSize(int i) {
        for (int j = 0; j < i; ++j) {
            markedForReview.add(false);
        }
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void setMarkedForReview(int index) {
        int column = index % 5;
        int row = index / 5;
        Button button = getButtonFromGridPane(column, row);
        button.setStyle("-fx-background-color: #64FE2E");
        markedForReview.set(index, true);
    }

    public void unsetMarkedForReview(int index) {
        int column = index % 5;
        int row = index / 5;
        Button button = getButtonFromGridPane(column, row);
        button.setStyle("");
        markedForReview.set(index, false);
    }

    public boolean isMarkedForReview(int index) {
        return markedForReview.get(index);
    }

    public Button getButton(int index) {
        int column = index % 5;
        int row = index / 5;
        return getButtonFromGridPane(column, row);
    }

    private Button getButtonFromGridPane(int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (Button) node;
            }
        }
        return null;
    }

}
