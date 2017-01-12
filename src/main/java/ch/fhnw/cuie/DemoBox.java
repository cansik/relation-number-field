package ch.fhnw.cuie;

import ch.fhnw.cuie.control.RelationNumberField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by cansik on 06.01.17.
 */
public class DemoBox extends VBox {
    private Label title;
    private RelationNumberField numberField;
    private RelationNumberField numberField2;
    private TextArea infoArea;

    private Button demo1Button;
    private Button demo2Button;

    private DoubleProperty myNumber = new SimpleDoubleProperty(50);

    public DemoBox() {
        initializeParts();
        layoutParts();
    }

    private void initializeParts() {
        title = new Label("Relation Number Demo");

        numberField = new RelationNumberField();
        numberField2 = new RelationNumberField();

        numberField.setMinimum(-100);
        numberField.setMaximum(50);

        numberField.setMinimum(0);
        numberField.setMaximum(100);

        numberField2.setMinimum(-100);
        numberField2.setMaximum(200);

        myNumber.bindBidirectional(numberField.valueProperty());

        infoArea = new TextArea();

        numberField.valueProperty().addListener((o, oldVal, newVal) -> updateInfo());
        numberField2.setBarFill(new Color(0.901, 0.494, 0.133, 1.0));

        demo1Button = new Button("Demo 1");
        demo1Button.setOnAction((event) -> numberField.setValue(70.0));

        demo2Button = new Button("Demo 2");
        demo2Button.setOnAction((event) -> myNumber.setValue(30.0));

        //numberField.setValue(20);
        numberField2.setValue(50);
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

        HBox box = new HBox(demo1Button, demo2Button);
        box.setMargin(demo1Button, new Insets(0, 10, 0, 0));

        getChildren().addAll(title, numberField, numberField2, infoArea, box);
    }
}
