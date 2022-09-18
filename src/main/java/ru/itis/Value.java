package ru.itis;

public class Value {

    private double close;
    private int index;
    private int clazz;

    public Value(double close, int index) {
        this.close = close;
        this.index = index;
    }

    public double getClose() {
        return close;
    }

    public int getIndex() {
        return index;
    }

    public void setClazz(int clazz){
        this.clazz = clazz;
    }

    public int getClazz(){
        return clazz;
    }

    @Override
    public String toString() {
        return String.format("{index %d ; close %.2f ; clazz %d}", index, close, clazz);
    }
}
