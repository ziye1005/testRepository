package edu.njust.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BasicAttributes {

    private String TargetID;
    private String PointID;
    private double Longitude;
    private double Latitude;
    private double Height;
    private double Speed;
    private String StartPlace;
    private String CountryCode;
    private Date UpdateTime;
}
