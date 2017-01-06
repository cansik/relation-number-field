package ch.fhnw.cuie.control;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by cansik on 06.01.17.
 */
public class RelationNumberField extends AnchorPane {
    private static final double PREFERRED_WIDTH = 200;
    private static final double MINIMUM_WIDTH = 30;
    private static final double MAXIMUM_WIDTH = 800;

    private static final double PREFERRED_HEIGHT = 70;
    private static final double MINIMUM_HEIGHT = 30;
    private static final double MAXIMUM_HEIGHT = 200;


    // value specific
    private final TextFormatter<Double> formatter;
    private final StringConverter<Number> converter;

    private DoubleProperty value;

    private DoubleProperty minimum;
    private DoubleProperty maximum;

    // controls
    private final TextField textField;
    private final Pane pane;

    // design specific
    private DoubleProperty minimumBarHeight = new SimpleDoubleProperty(10.0);

    public RelationNumberField() {
        this(0.0);
    }

    public RelationNumberField(double value) {
        this(value, 0.0, 100.0);
    }

    public RelationNumberField(double value, double minimum, double maximum) {
        this.value = new SimpleDoubleProperty(value);
        this.minimum = new SimpleDoubleProperty(minimum);
        this.maximum = new SimpleDoubleProperty(maximum);

        // init controls
        textField = new TextField();
        pane = new AnchorPane();

        formatter = new TextFormatter<>(new DoubleStringConverter());
        converter = new NumberStringConverter();

        initializeNumberField();
        initializeControls();
    }

    private void initializeControls() {
        // init ui
        setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);

        AnchorPane.setTopAnchor(textField, 0.0);
        AnchorPane.setLeftAnchor(textField, 0.0);
        AnchorPane.setRightAnchor(textField, 0.0);

        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);

        pane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setMinHeight(minimumBarHeight.doubleValue());

        getChildren().addAll(textField, pane);
    }

    private void initializeNumberField() {
        // create binding between value and text
        Bindings.bindBidirectional(textField.textProperty(), value, converter);

        // set number formatter
        textField.setTextFormatter(formatter);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public double getMinimum() {
        return minimum.get();
    }

    public DoubleProperty minimumProperty() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum.set(minimum);
    }

    public double getMaximum() {
        return maximum.get();
    }

    public DoubleProperty maximumProperty() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum.set(maximum);
    }
}
