package ru.itis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/main/resources/gazp.csv"));
        in.readLine();
        int index = 1;
        List<Value> valueList = new ArrayList<>();

        while (in.ready()) {
            String[] s = in.readLine().split(";");
            valueList.add(new Value(Double.parseDouble(s[7]), index++, s[3]));
        }
        double a = calcA(valueList);
        setClasses(valueList, a, calcB(valueList, a));
        valueList.forEach(System.out::println);
        Perceptron perceptron = new Perceptron(valueList);
        perceptron.test();
        in.close();
    }

    public static double calcA(List<Value> valueList){
        double xy = 0;
        double x2 = 0;
        double y = 0;
        double x = 0;
        for (Value value : valueList) {
            xy += calc(valueList.size(), value.getIndex(), value.getClose());
            y += calc(valueList.size(), value.getIndex());
            x += calc(valueList.size(), value.getClose());
            x2 += calc(valueList.size(), value.getClose()*value.getClose());
        }
        return (xy - x*y)/(x2 - x*x);
    }

    public static double calcB(List<Value> valueList, double a){
        double y = 0;
        double x = 0;
        for (Value value : valueList) {
            y += calc(valueList.size(), value.getIndex());
            x += calc(valueList.size(), value.getClose());
        }
        return y - a * x;
    }

    public static void setClasses(List<Value> valueList, double a, double b){
        for (Value value : valueList) {
            value.setClazz((a*value.getIndex() + b) >= 0 ? 1 : -1);
        }
    }

    public static double calc(int n, double... args){
            double mult = 1;

            for (double arg : args) {
                mult *= arg;
            }

            return mult/n;
    }
}
