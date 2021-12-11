package edu.njust.model.oracle;

import java.sql.Date;
public class ZSZF1 {
//    目标Z术Z法模型表，T_ZS_TacticsModels
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public short getTacticsType() {
        return TacticsType;
    }

    public void setTacticsType(short tacticsType) {
        TacticsType = tacticsType;
    }

    public String getTacticsDescribe() {
        return TacticsDescribe;
    }

    public void setTacticsDescribe(String tacticsDescribe) {
        TacticsDescribe = tacticsDescribe;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    String ID;
    short TacticsType;
    String TacticsDescribe;
    Date UpdateTime;

}
