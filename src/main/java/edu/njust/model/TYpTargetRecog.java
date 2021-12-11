package edu.njust.model;

import java.util.Date;

public class TYpTargetRecog {
    private String ID;
    private String TargetID;
    private String PointID;
    private int TargetType;
    private String ArtifactID;
    private String TargetName;
    private int JudgeMeans;
    private double Probablity;
    private Date ImportTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getArtifactID() {
        return ArtifactID;
    }

    public void setArtifactID(String artifactID) {
        ArtifactID = artifactID;
    }

    public Date getImportTime() {
        return ImportTime;
    }

    public void setImportTime(Date importTime) {
        ImportTime = importTime;
    }

    public int getJudgeMeans() {
        return JudgeMeans;
    }

    public void setJudgeMeans(int judgeMeans) {
        JudgeMeans = judgeMeans;
    }

    public String getPointID() {
        return PointID;
    }

    public void setPointID(String pointID) {
        PointID = pointID;
    }

    public int getTargetType() {
        return TargetType;
    }

    public void setTargetType(int targetType) {
        TargetType = targetType;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String targetName) {
        TargetName = targetName;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public double getProbablity() {
        return Probablity;
    }

    public void setProbablity(double probablity) {
        Probablity = probablity;
    }
}
