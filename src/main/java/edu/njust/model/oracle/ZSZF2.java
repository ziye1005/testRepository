package edu.njust.model.oracle;

import java.sql.Date;

public class ZSZF2 {
//    表 25Z术Z法匹配算法表的独立字段描述描述表
    String ID;
    String TargetID;
    String PointID;
    String TacticalMethodsID;
    short Probability;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public String getPointID() {
        return PointID;
    }

    public void setPointID(String pointID) {
        PointID = pointID;
    }

    public String getTacticalMethodsID() {
        return TacticalMethodsID;
    }

    public void setTacticalMethodsID(String tacticalMethodsID) {
        TacticalMethodsID = tacticalMethodsID;
    }

    public short getProbability() {
        return Probability;
    }

    public void setProbability(short probability) {
        Probability = probability;
    }

    public Date getImportTime() {
        return ImportTime;
    }

    public void setImportTime(Date importTime) {
        ImportTime = importTime;
    }

    Date ImportTime;
}
