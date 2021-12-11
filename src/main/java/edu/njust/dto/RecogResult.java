package edu.njust.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 身份研判结果（机型+概率）
 */
@Getter
@Setter
public class RecogResult {

    private String TargetModel1;
    private double probability1;
    private String TargetModel2;
    private double probability2;
    private String TargetModel3;
    private double probability3;
}
