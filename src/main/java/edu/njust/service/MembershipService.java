package edu.njust.service;

import edu.njust.mapper.oracle.MembershipMapper;
import edu.njust.model.oracle.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
    @Autowired
    private MembershipMapper membershipMapper;

    public Membership getMembershipById(int id){
        return membershipMapper.getMembershipById(id);
    }

    public int addMember(Membership membership){
        return membershipMapper.addMembership(membership);
    }

    public int updataMemberById(Membership membership){
        return membershipMapper.updateMembership(membership);
    }
}
