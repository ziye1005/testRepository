package edu.njust.controller;

import edu.njust.model.oracle.Membership;
import edu.njust.service.MembershipService;
import edu.njust.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/membership")
public class MembershipController {
    @Autowired
    MembershipService membershipService;

    @Autowired
    NodeService nodeService;


    @PostMapping("/setMembership")
    public void setMembership(@RequestParam("type") int type, @RequestParam("name") String name, @RequestBody Membership membership){
        int id = nodeService.findIdByNameAndType(name, type);
        membership.setId(id);

        if (membershipService.getMembershipById(id) != null){
            membershipService.updataMemberById(membership);
        }
        else {
            membershipService.addMember(membership);
        }
    }
}
