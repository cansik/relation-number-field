package ch.fhnw.cuie.model;

import ch.fhnw.cuie.util.RandomUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by cansik on 07.01.17.
 */
public class House {
    private IntegerProperty age;
    private DoubleProperty size;
    private DoubleProperty price;

    public House() {
        this(0, 0, 0);
    }

    public House(int age, double size, double price) {
        this.age = new SimpleIntegerProperty(age);
        this.size = new SimpleDoubleProperty(size);
        this.price = new SimpleDoubleProperty(size);
    }

    public static House random() {
        House h = new House();
        h.setAge(RandomUtil.randomInteger(1920, 2016));
        h.setSize(RandomUtil.randomDouble(10, 300));
        h.setPrice(RandomUtil.randomDouble(10000, 100000));
        return h;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public double getSize() {
        return size.get();
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
