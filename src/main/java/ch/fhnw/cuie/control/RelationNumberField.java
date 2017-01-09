package ch.fhnw.cuie.control;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by cansik on 06.01.17.
 */
public class RelationNumberField extends TextField {
    private static final double PREFERRED_WIDTH = 200;
    private static final double MINIMUM_WIDTH = 30;
    private static final double MAXIMUM_WIDTH = 800;

    private static final double PREFERRED_HEIGHT = 30;
    private static final double MINIMUM_HEIGHT = 30;
    private static final double MAXIMUM_HEIGHT = 30;

    private static final double ANIMATION_DURATION = 500;
    private static final Interpolator ANIMATION_INTERPOLATOR = Interpolator.EASE_OUT;


    // value specific
    private final TextFormatter<Double> formatter;
    private final StringConverter<Number> converter;

    private DoubleProperty value;

    private DoubleProperty minimum;
    private DoubleProperty maximum;

    // controls
    private final AnchorPane pane;
    private final Rectangle valueRect;

    // animation


    // design specific
    private DoubleProperty minimumBarHeight = new SimpleDoubleProperty(4.0);

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
        pane = new AnchorPane();
        valueRect = new Rectangle(0, 0, 0, 4);
        valueRect.heightProperty().bindBidirectional(minimumBarHeight);

        formatter = new TextFormatter<>(new DoubleStringConverter());
        converter = new NumberStringConverter();

        initializeNumberField();
        initializeControls();

        this.value.addListener((o, oldVal, newVal) -> resizeAnimation());
        this.widthProperty().addListener((o, oldVal, newVal) -> resize());
    }

    private void initializeControls() {
        // init ui
        setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);

        setAlignment(Pos.TOP_RIGHT);

        valueRect.setFill(Color.CORNFLOWERBLUE);
        valueRect.setArcHeight(2);
        valueRect.setArcWidth(2);
        AnchorPane.setBottomAnchor(valueRect, 0.0);

        pane.getChildren().add(valueRect);
        getChildren().addAll(pane);
    }

    private void initializeNumberField() {
        // create binding between value and text
        Bindings.bindBidirectional(textProperty(), value, converter);

        // set number formatter
        setTextFormatter(formatter);
    }

    private double getPaneWidth() {
        return limit(map(value.getValue(), minimum.getValue(), maximum.getValue(), 0, pane.getWidth()), 0, pane.getWidth());
    }

    private void resize() {
        valueRect.setWidth(getPaneWidth());
    }

    private void resizeAnimation() {
        double position = getPaneWidth();

        final Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(valueRect.widthProperty(), position, ANIMATION_INTERPOLATOR);
        final KeyFrame kf = new KeyFrame(Duration.millis(ANIMATION_DURATION), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private static double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    private static double limit(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
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
