package ch.fhnw.cuie;

import ch.fhnw.cuie.control.RelationNumberField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by cansik on 06.01.17.
 */
public class DemoBox extends VBox {
    private Label title;
    private Button demo1Button;
    private RelationNumberField numberField;
    private TextArea infoArea;

    public DemoBox() {
        initializeParts();
        layoutParts();
    }

    private void initializeParts() {
        title = new Label("Relation Number Demo");
        numberField = new RelationNumberField();
        infoArea = new TextArea();

        // set binding
        numberField.valueProperty().addListener((o, oldVal, newVal) -> updateInfo());

        demo1Button = new Button("Demo 1");
        demo1Button.setOnAction((event) -> {
            numberField.setValue(70.0);
        });
    }

    private void updateInfo() {
        infoArea.setText("Information\n\nMinimum: " + numberField.getMinimum()
                + "\nMaximum: " + numberField.getMaximum()
                + "\n\nValue: " + numberField.getValue());
    }

    private void layoutParts() {
        setPadding(new Insets(15));
        setSpacing(5);

        setVgrow(infoArea, Priority.ALWAYS);

        getChildren().addAll(title, numberField, infoArea, demo1Button);
    }
}
