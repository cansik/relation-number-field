package ch.fhnw.cuie.util;

/**
 * Created by cansik on 07.01.17.
 */
public class RandomUtil {

    public static int randomInteger() {
        return randomInteger(0, 1);
    }

    public static int randomInteger(int upperBound) {
        return randomInteger(0, upperBound);
    }

    public static int randomInteger(int lowerBound, int upperBound) {
        return (int) (lowerBound + (Math.random() * (upperBound - lowerBound)));
    }

    public static float randomFloat() {
        return randomFloat(0, 1);
    }

    public static float randomFloat(float upperBound) {
        return randomFloat(0, upperBound);
    }

    public static float randomFloat(float lowerBound, float upperBound) {
        return (float) (lowerBound + (Math.random() * (upperBound - lowerBound)));
    }

    public static double randomDouble() {
        return randomDouble(0, 1);
    }

    public static double randomDouble(double upperBound) {
        return randomDouble(0, upperBound);
    }

    public static double randomDouble(double lowerBound, double upperBound) {
        return lowerBound + (Math.random() * (upperBound - lowerBound));
    }
}
