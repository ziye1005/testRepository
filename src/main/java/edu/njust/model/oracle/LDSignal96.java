package edu.njust.model.oracle;

import java.math.BigDecimal;
import java.sql.Date;

public class LDSignal96 {
//    表 96 LD信号特征扩展库的独立字段中文名称描述表
    String ID;
    String SynSituationID;
    BigDecimal RFType;
    Double RFValue;
    Double RFMinValue;
    Double RFMaxValue;
    Double RFValue1;
    Double RFValue2;
    Double RFValue3;
    BigDecimal PRIType;
    Double PRIValue;
    Double PRIMaxValue ;
    Double PRIMinValue;
    Double PRIValue1;
    Double PRIValue2;
    BigDecimal PRIValue3;
    BigDecimal PWType;
    Double PWValue;
    Double PWMaxValue;
    Double PWMinValue;
    BigDecimal PulseModuType;
    BigDecimal PulseNum;
    BigDecimal TrackBeginEndFlag;
    BigDecimal ScanChara;
    BigDecimal ScanCyc;
    Double WaveBindWidth;
    BigDecimal MainAuxRatio;
    BigDecimal RadarWorkMode;
    BigDecimal RFMeasureRMS;
    BigDecimal PRIMeasureRMS;
    BigDecimal PWMeasureRMS;
    Double PulseAmp;
    Double AmpMeasureRMS;
    String UnitName;
    BigDecimal UnitBelieve;
    String ModelName;
    BigDecimal ModelBelieve;
    Date UpdateTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSynSituationID() {
        return SynSituationID;
    }

    public void setSynSituationID(String synSituationID) {
        SynSituationID = synSituationID;
    }

    public BigDecimal getRFType() {
        return RFType;
    }

    public void setRFType(BigDecimal RFType) {
        this.RFType = RFType;
    }

    public Double getRFValue() {
        return RFValue;
    }

    public void setRFValue(Double RFValue) {
        this.RFValue = RFValue;
    }

    public Double getRFMinValue() {
        return RFMinValue;
    }

    public void setRFMinValue(Double RFMinValue) {
        this.RFMinValue = RFMinValue;
    }

    public Double getRFMaxValue() {
        return RFMaxValue;
    }

    public void setRFMaxValue(Double RFMaxValue) {
        this.RFMaxValue = RFMaxValue;
    }

    public Double getRFValue1() {
        return RFValue1;
    }

    public void setRFValue1(Double RFValue1) {
        this.RFValue1 = RFValue1;
    }

    public Double getRFValue2() {
        return RFValue2;
    }

    public void setRFValue2(Double RFValue2) {
        this.RFValue2 = RFValue2;
    }

    public Double getRFValue3() {
        return RFValue3;
    }

    public void setRFValue3(Double RFValue3) {
        this.RFValue3 = RFValue3;
    }

    public BigDecimal getPRIType() {
        return PRIType;
    }

    public void setPRIType(BigDecimal PRIType) {
        this.PRIType = PRIType;
    }

    public Double getPRIValue() {
        return PRIValue;
    }

    public void setPRIValue(Double PRIValue) {
        this.PRIValue = PRIValue;
    }

    public Double getPRIMaxValue() {
        return PRIMaxValue;
    }

    public void setPRIMaxValue(Double PRIMaxValue) {
        this.PRIMaxValue = PRIMaxValue;
    }

    public Double getPRIMinValue() {
        return PRIMinValue;
    }

    public void setPRIMinValue(Double PRIMinValue) {
        this.PRIMinValue = PRIMinValue;
    }

    public Double getPRIValue1() {
        return PRIValue1;
    }

    public void setPRIValue1(Double PRIValue1) {
        this.PRIValue1 = PRIValue1;
    }

    public Double getPRIValue2() {
        return PRIValue2;
    }

    public void setPRIValue2(Double PRIValue2) {
        this.PRIValue2 = PRIValue2;
    }

    public BigDecimal getPRIValue3() {
        return PRIValue3;
    }

    public void setPRIValue3(BigDecimal PRIValue3) {
        this.PRIValue3 = PRIValue3;
    }

    public BigDecimal getPWType() {
        return PWType;
    }

    public void setPWType(BigDecimal PWType) {
        this.PWType = PWType;
    }

    public Double getPWValue() {
        return PWValue;
    }

    public void setPWValue(Double PWValue) {
        this.PWValue = PWValue;
    }

    public Double getPWMaxValue() {
        return PWMaxValue;
    }

    public void setPWMaxValue(Double PWMaxValue) {
        this.PWMaxValue = PWMaxValue;
    }

    public Double getPWMinValue() {
        return PWMinValue;
    }

    public void setPWMinValue(Double PWMinValue) {
        this.PWMinValue = PWMinValue;
    }

    public BigDecimal getPulseModuType() {
        return PulseModuType;
    }

    public void setPulseModuType(BigDecimal pulseModuType) {
        PulseModuType = pulseModuType;
    }

    public BigDecimal getPulseNum() {
        return PulseNum;
    }

    public void setPulseNum(BigDecimal pulseNum) {
        PulseNum = pulseNum;
    }

    public BigDecimal getTrackBeginEndFlag() {
        return TrackBeginEndFlag;
    }

    public void setTrackBeginEndFlag(BigDecimal trackBeginEndFlag) {
        TrackBeginEndFlag = trackBeginEndFlag;
    }

    public BigDecimal getScanChara() {
        return ScanChara;
    }

    public void setScanChara(BigDecimal scanChara) {
        ScanChara = scanChara;
    }

    public BigDecimal getScanCyc() {
        return ScanCyc;
    }

    public void setScanCyc(BigDecimal scanCyc) {
        ScanCyc = scanCyc;
    }

    public Double getWaveBindWidth() {
        return WaveBindWidth;
    }

    public void setWaveBindWidth(Double waveBindWidth) {
        WaveBindWidth = waveBindWidth;
    }

    public BigDecimal getMainAuxRatio() {
        return MainAuxRatio;
    }

    public void setMainAuxRatio(BigDecimal mainAuxRatio) {
        MainAuxRatio = mainAuxRatio;
    }

    public BigDecimal getRadarWorkMode() {
        return RadarWorkMode;
    }

    public void setRadarWorkMode(BigDecimal radarWorkMode) {
        RadarWorkMode = radarWorkMode;
    }

    public BigDecimal getRFMeasureRMS() {
        return RFMeasureRMS;
    }

    public void setRFMeasureRMS(BigDecimal RFMeasureRMS) {
        this.RFMeasureRMS = RFMeasureRMS;
    }

    public BigDecimal getPRIMeasureRMS() {
        return PRIMeasureRMS;
    }

    public void setPRIMeasureRMS(BigDecimal PRIMeasureRMS) {
        this.PRIMeasureRMS = PRIMeasureRMS;
    }

    public BigDecimal getPWMeasureRMS() {
        return PWMeasureRMS;
    }

    public void setPWMeasureRMS(BigDecimal PWMeasureRMS) {
        this.PWMeasureRMS = PWMeasureRMS;
    }

    public Double getPulseAmp() {
        return PulseAmp;
    }

    public void setPulseAmp(Double pulseAmp) {
        PulseAmp = pulseAmp;
    }

    public Double getAmpMeasureRMS() {
        return AmpMeasureRMS;
    }

    public void setAmpMeasureRMS(Double ampMeasureRMS) {
        AmpMeasureRMS = ampMeasureRMS;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public BigDecimal getUnitBelieve() {
        return UnitBelieve;
    }

    public void setUnitBelieve(BigDecimal unitBelieve) {
        UnitBelieve = unitBelieve;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public BigDecimal getModelBelieve() {
        return ModelBelieve;
    }

    public void setModelBelieve(BigDecimal modelBelieve) {
        ModelBelieve = modelBelieve;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
}
