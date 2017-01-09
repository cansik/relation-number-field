package ch.fhnw.cuie;

import ch.fhnw.cuie.control.RelationNumberField;
import ch.fhnw.cuie.model.House;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by cansik on 06.01.17.
 */
public class DemoBox extends VBox {
    private Label title;
    private RelationNumberField numberField;
    private TextArea infoArea;

    private Button demo1Button;
    private Button demo2Button;
    private Button demo3Button;

    private ArrayList<House> houses;

    private House currentHouse = null;
    private int houseIndex = -1;

    public DemoBox() {
        houses = new ArrayList<>(IntStream.range(0, 100).mapToObj((i) -> House.random()).collect(Collectors.toList()));

        initializeParts();
        layoutParts();
        nextHouse();
    }

    private void initializeParts() {
        title = new Label("Relation Number Demo");
        numberField = new RelationNumberField();
        infoArea = new TextArea();

        // set binding
        numberField.setMinimum(houses.stream().mapToDouble(House::getPrice).min().getAsDouble());
        numberField.setMaximum(houses.stream().mapToDouble(House::getPrice).max().getAsDouble());

        numberField.valueProperty().addListener((o, oldVal, newVal) -> updateInfo());

        demo1Button = new Button("Demo 1");
        demo1Button.setOnAction((event) -> numberField.setValue(70.0));

        demo2Button = new Button("Demo 2");
        demo2Button.setOnAction((event) -> numberField.setValue(30.0));
    }

    private void updateInfo() {
        /*
        infoArea.setText("Information\n\nMinimum: " + numberField.getMinimum()
                + "\nMaximum: " + numberField.getMaximum()
                + "\n\nValue: " + numberField.getValue());
        */

        infoArea.setText("House " + houseIndex + "\n\nAge: " + currentHouse.getAge()
                + "\nSize: " + currentHouse.getSize()
                + " m\n\nPrice: " + currentHouse.getPrice() + " CHF");
    }

    private void nextHouse() {
        houseIndex = (houseIndex + 1) % houses.size();
        currentHouse = houses.get(houseIndex);
        numberField.setValue(currentHouse.getPrice());

        updateInfo();
    }

    private void layoutParts() {
        setPadding(new Insets(15));
        setSpacing(5);

        setVgrow(infoArea, Priority.ALWAYS);

        HBox box = new HBox(demo1Button, demo2Button);
        box.setMargin(demo1Button, new Insets(0, 10, 0, 0));

        getChildren().addAll(title, numberField, infoArea, box);
    }
}
