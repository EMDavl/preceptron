package ru.itis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Runner {


    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 4; i++) {
            for (int j = i; j < 5; j++) {
                if (i == j) continue;
                System.out.printf("First key: %d%nSecond key: %d\n", i, j);
                List<KeyValuePair<Integer, Double[]>> valueList = prepareValues(i, j);

                SecondPerceptron perceptron = new SecondPerceptron(valueList);
                perceptron.test();
            }
        }

    }

    private static List<KeyValuePair<Integer, Double[]>> prepareValues(int key1, int key2) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/main/resources/vk_perc1.csv"));
        List<KeyValuePair<Integer, Double[]>> valueList = new ArrayList<>();
        in.readLine();
        String k;

        while (!isEmpty(k = in.readLine())) {
            String[] s = k.split(";");
            if (s.length != 6) continue;
            Double[] entries = new Double[4];
            for (int i = 0; i < s.length - 2; i++) {
                try {
                    entries[i] = Double.parseDouble(s[i+1]);
                } catch (NumberFormatException e){
                    entries[i] = 0d;
                }
            }
            int key = Integer.valueOf(s[0]);

            if (key == key1){
                valueList.add(new KeyValuePair<>(1, entries));
            } else if (key == key2){
                valueList.add(new KeyValuePair<>(-1, entries));
            }
        }
        in.close();
        return valueList;
    }

    public static double calcA(List<Value> valueList){
        double xy = 0;
        double x2 = 0;
        double y = 0;
        double x = 0;
        for (Value value : valueList) {
            xy += calc(valueList.size(), value.getIndex(), value.getClose());
            y += calc(valueList.size(), value.getClose());
            x += calc(valueList.size(), value.getIndex());
            x2 += calc(valueList.size(), value.getIndex()*value.getIndex());
        }
        return (xy - x*y)/(x2 - x*x);
    }

    public static double calcB(List<Value> valueList, double a){
        double y = 0;
        double x = 0;
        for (Value value : valueList) {
            x += calc(valueList.size(), value.getIndex());
            y += calc(valueList.size(), value.getClose());
        }
        return y - a * x;
    }

    public static void setClasses(List<Value> valueList, double a, double b){
        for (Value value : valueList) {
            value.setClazz((a*value.getIndex() + b - value.getClose()) >= 0 ? 1 : -1);
        }
    }

    public static double calc(int n, double... args){
            double mult = 1;

            for (double arg : args) {
                mult *= arg;
            }

            return mult/n;
    }
    public static boolean isEmpty(String str){
        return str == null || str.isEmpty();
    }
}
