package ru.itis;

import java.util.List;

public class Perceptron {
    double[] x;
    double y;
    double[] w;
    double[][] pat = {{0, 0, 0}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};

    public Perceptron() {

        x = new double[2];
        w = new double[2];

        for (int i = 0; i < x.length; i++) {
            w[i] = 0;
            System.out.println("Начальные значения весов");
            System.out.println("w[" + i + "]=" + w[i]);
        }

    }

    public Perceptron(List<Value> valueList){

    }

    public void calculateY() {
        y = 0;
        for (int i = 0; i < x.length; i++) {
            y += x[i] * w[i];
        }
        System.out.println("Взвешенная сумма входных значений");
        System.out.println(y);
        if (y >= 0) {
            y = 1;
        } else {
            y = -1;
        }
    }

    public void study() {
        double gEr = 0;
        int m = 0;
        do {
            gEr = 0;
            for (int p = 0; p < pat.length; p++) {
                x = java.util.Arrays.copyOf(pat[p], pat[p].length - 1);
                calculateY() ;
                double er = pat[p][2];
                gEr += Math.abs(er);
                for (int i = 0; i < x.length; i++) {
                    w[i] += 0.1 * er * x[i];
                }
            }
            m++;
        } while (gEr != 0);
        System.out.println("m=" + m);
    }

    public void test() {
        study();
        for (int p = 0; p < pat.length; p++) {
            x = java.util.Arrays.copyOf(pat[p], pat[p].length - 1);
            calculateY();
            System.out.println("y=" + y);
        }
    }
}