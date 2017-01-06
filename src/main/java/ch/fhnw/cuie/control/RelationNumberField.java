package ch.fhnw.cuie.control;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by cansik on 06.01.17.
 */
public class RelationNumberField extends TextField {
    private static String ALLOWED_KEYS = "0123456789.";

    private final TextFormatter<Double> formatter;
    private final StringConverter<Number> converter;

    private DoubleProperty value;

    private DoubleProperty minimum;
    private DoubleProperty maximum;

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

        formatter = new TextFormatter<>(new DoubleStringConverter());
        converter = new NumberStringConverter();

        initialiseNumberField();
    }

    private void initialiseNumberField() {
        // create binding between value and text
        Bindings.bindBidirectional(textProperty(), value, converter);

        // set number formatter
        setTextFormatter(formatter);
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
