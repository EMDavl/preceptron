package ru.itis;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.random;

public class SecondPerceptron {
    List<KeyValuePair<Integer, Double[]>> values;
    double[] weights;

    public SecondPerceptron(List<KeyValuePair<Integer, Double[]>> values) {
        this.values = values;
        System.out.println(values.size() + " = values size");
        weights = new double[values.get(0).getValue().length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random()*0.3 + 0.1;
        }
    }

    public double activationFunction(Double[] entry) {
        double sum = 0;

        for (int i = 0; i < entry.length; i++) {
            sum += weights[i]*entry[i];
        }
        return thresholdActivationFunction(sum);
    }

    public void study() {
        double studySpeed = 0.0001;
        double positive = 0;
        long iterations = 0;
        while ((positive/values.size()) < 0.75 && iterations < 10_000){
            iterations++;
            if(iterations % 1000 == 0) System.out.println(iterations);
            positive = 0;
            for (KeyValuePair<Integer, Double[]> entry : values) {
                if (activationFunction(entry.getValue()) * entry.getKey() < 0) {
                    for (int i = 0; i < entry.getValue().length; i++) {
                        weights[i] += entry.getKey() * studySpeed * entry.getValue()[i];
                    }
                } else positive++;
            }
        }
    }

    public void test() {
        study();

        double st = 0;
        for (KeyValuePair<Integer, Double[]> entry : values) {
            if (activationFunction(entry.getValue()) * entry.getKey() < 0) {
               st++;
            }
        }
        System.out.printf("Кол-во правильных ответов: %.02f%n", (1 - (st/values.size())) * 100);
        System.out.println(Arrays.toString(weights));
        System.out.println();
    }

    private double thresholdActivationFunction(double sum) {
        return sum <= 0 ? -1 : 1;
    }
}