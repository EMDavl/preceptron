package ru.itis;

import java.util.Arrays;
import java.util.List;

public class Perceptron {
    List<Value> valueList;
    double[] weights;

    public Perceptron(List<Value> valueList) {
        this.valueList = valueList;
        weights = new double[]{0, 0, 1};
    }

    public double activationFunction(double x1, double x2) {
        return thresholdActivationFunction(weights[0] * x1 + weights[1] * x2 + weights[2]*(-1));
    }

    public void study() {
        double studySpeed = 0.00001;
        double[] weightsOld;
        int iterations = 0;
        do {
            iterations++;
            if (iterations % 1000 == 0) System.out.println(iterations + " " + Arrays.toString(weights));
            weightsOld = Arrays.copyOf(weights, weights.length);
            for (Value value : valueList) {
                if (activationFunction(value.getIndex(), value.getClose()) * value.getClazz() < 0) {
                    weights[0] += studySpeed * value.getIndex() * value.getClazz();
                    weights[1] += studySpeed * value.getClose() * value.getClazz();
                    weights[2] += studySpeed * (-1) * value.getClazz();
                }
            }
        } while (!Arrays.equals(weights, weightsOld));
    }

    public void test() {
        study();
        long st = 0;
        for (Value value : valueList) {
            if (activationFunction(value.getIndex(), value.getClose()) * value.getClazz() < 0) st++;
        }
        System.out.println(st + " " + valueList.size());
    }

    private double thresholdActivationFunction(double sum) {
        return sum <= 0 ? -1 : 1;
    }
}