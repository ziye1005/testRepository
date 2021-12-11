package edu.njust.model;

public class TargetRecogWeights {
    private String id;
    private double weight_basic;
    private double weight_rcs;
    private double weight_trackline;
    private double weight_tactics;

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getWeight_basic() {
        return this.weight_basic;
    }
    public void setWeight_basic(double weight_basic) {
        this.weight_basic = weight_basic;
    }
    public double getWeight_rcs() {
        return this.weight_rcs;
    }
    public void setWeight_rcs(double weight_rcs) {
        this.weight_rcs = weight_rcs;
    }
    public double getWeight_trackline() {
        return this.weight_trackline;
    }
    public void setWeight_trackline(double weight_trackline) {
        this.weight_trackline = weight_trackline;
    }
    public double getWeight_tactics() {
        return this.weight_tactics;
    }
    public void setWeight_tactics(double weight_tactics) {
        this.weight_tactics = weight_tactics;
    }
}
