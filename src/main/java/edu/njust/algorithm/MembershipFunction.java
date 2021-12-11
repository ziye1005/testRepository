package edu.njust.algorithm;

import edu.njust.model.oracle.Membership;
import edu.njust.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.FlatteningPathIterator;

@Component
public class MembershipFunction {
    @Autowired
    private MembershipService membershipService;

    public float[] getMembership(int id, double value){
        Membership membership = membershipService.getMembershipById(id);
        if (membership.getType() == 0){
            if (value > membership.getThreshold()){
                return new float[]{1.0f,0.0f};
            }
            else {
                double tmp = Math.exp(-membership.getK() * (membership.getThreshold() - value) * (membership.getThreshold() - value));
                if (tmp < 0.01){
                    tmp = 0;
                }
                float floatTmp = new Double(tmp).floatValue();
                return new float[]{floatTmp, 1.0f-floatTmp};
            }
        }
        else {
            if (value < membership.getThreshold()){
                return new float[]{1.0f,0.0f};
            }
            else {
                double tmp = Math.exp(-membership.getK() * (membership.getThreshold() - value) * (membership.getThreshold() - value));
                if (tmp < 0.01){
                    tmp = 0;
                }
                float floatTmp = new Double(tmp).floatValue();
                return new float[]{floatTmp, 1.0f-floatTmp};
            }
        }

    }
}
