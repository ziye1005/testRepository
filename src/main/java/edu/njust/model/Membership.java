package edu.njust.model;

public class Membership {
    private int id;
    private int threshold;
    private double k;
    private int type;

    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getK() {
        return k;
    }

    public void setId(int id) {
        this.id = id;
    }
}
