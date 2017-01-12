package ch.fhnw.cuie.control;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private static final double PREFERRED_HEIGHT = 32;
    private static final double MINIMUM_HEIGHT = 32;
    private static final double MAXIMUM_HEIGHT = 32;

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

    private final Label minimumLabel;
    private final Label maximumLabel;

    // animation
    private final Timeline inLabelAnimation = new Timeline();
    private final Timeline outLabelAnimation = new Timeline();

    // design specific
    private DoubleProperty minimumBarHeight = new SimpleDoubleProperty(4.0);

    private BooleanProperty showRange = new SimpleBooleanProperty(true);

    private ObjectProperty<Paint> barFill = new SimpleObjectProperty<>(new Color(0.203, 0.596, 0.858, 1.0));

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
        valueRect = new Rectangle(0, 0, 0, 0);
        maximumLabel = new Label();
        minimumLabel = new Label();

        formatter = new TextFormatter<>(new DoubleStringConverter());
        converter = new NumberStringConverter();

        initializeNumberField();
        initializeControls();

        // setup animations
        setupLabelAnimation(inLabelAnimation, 0, 1);
        setupLabelAnimation(outLabelAnimation, -20, 0);

        this.value.addListener((o, oldVal, newVal) -> resizeAnimation());
        this.widthProperty().addListener((o) -> resize());

        this.value.setValue(this.value.get());
        this.minimum.setValue(this.minimum.get());
        this.maximum.setValue(this.maximum.get());
    }

    private void initializeControls() {
        // init ui
        setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);

        setAlignment(Pos.TOP_RIGHT);

        applyLabelStyle(minimumLabel);
        applyLabelStyle(maximumLabel);

        AnchorPane.setTopAnchor(maximumLabel, 7.0);

        valueRect.fillProperty().bindBidirectional(barFill);
        valueRect.heightProperty().bindBidirectional(minimumBarHeight);
        valueRect.setArcHeight(2);
        valueRect.setArcWidth(2);

        // maximum label show
        setOnMouseEntered(me -> fadeLabelsIn());
        setOnMouseExited(me -> fadeLabelsOut());

        AnchorPane.setBottomAnchor(valueRect, 0.0);
        AnchorPane.setLeftAnchor(valueRect, 0.0);
        AnchorPane.setRightAnchor(valueRect, 0.0);

        pane.getChildren().addAll(valueRect, maximumLabel, minimumLabel);
        getChildren().addAll(pane);
    }

    private void setupLabelAnimation(Timeline timeline, double xPosition, double opacity) {
        final KeyValue kvMin = new KeyValue(minimumLabel.layoutXProperty(), xPosition, ANIMATION_INTERPOLATOR);
        final KeyValue kvMax = new KeyValue(maximumLabel.layoutXProperty(), xPosition, ANIMATION_INTERPOLATOR);

        final KeyValue opMin = new KeyValue(minimumLabel.opacityProperty(), opacity, ANIMATION_INTERPOLATOR);
        final KeyValue opMax = new KeyValue(maximumLabel.opacityProperty(), opacity, ANIMATION_INTERPOLATOR);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), kvMin));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), kvMax));

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), opMin));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), opMax));
    }

    private void fadeLabelsIn() {
        if (showRange.getValue()) {
            outLabelAnimation.stop();
            inLabelAnimation.play();
        }
    }

    private void fadeLabelsOut() {
        inLabelAnimation.stop();
        outLabelAnimation.play();
    }

    private void applyLabelStyle(Label label) {
        label.setTextFill(Color.web("#2c3e50"));
        label.setStyle("-fx-font-size: 8;");
        label.setClip(this.getClip());
    }

    private void initializeNumberField() {
        // create binding between value and text
        Bindings.bindBidirectional(textProperty(), value, converter);

        minimum.addListener((obs, o, n) -> minimumLabel.setText("Min: " + n));
        maximum.addListener((obs, o, n) -> maximumLabel.setText("Max: " + n));

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

    public double getMinimumBarHeight() {
        return minimumBarHeight.get();
    }

    public DoubleProperty minimumBarHeightProperty() {
        return minimumBarHeight;
    }

    public void setMinimumBarHeight(double minimumBarHeight) {
        this.minimumBarHeight.set(minimumBarHeight);
    }

    public Paint getBarFill() {
        return barFill.get();
    }

    public ObjectProperty<Paint> barFillProperty() {
        return barFill;
    }

    public void setBarFill(Paint barFill) {
        this.barFill.set(barFill);
    }

    public boolean isShowRange() {
        return showRange.get();
    }

    public BooleanProperty showRangeProperty() {
        return showRange;
    }

    public void setShowRange(boolean showRange) {
        this.showRange.set(showRange);
    }
}
