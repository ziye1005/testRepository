package edu.njust.controller;

import edu.njust.algorithm.IntentionAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/Intention")
@CrossOrigin
public class IntentionController {
    @Autowired
    private IntentionAnalysis intentionAnalysis;

    @GetMapping("getIntentionPlace")
    public String getIntentionPlace(@RequestParam(value = "lng") double lng,@RequestParam(value = "lat") double lat){
        List<Map.Entry<String, Double>> distances=intentionAnalysis.getDistances(lng,lat);
        System.out.println(distances);
        String event="经研判，飞机即将抵达"+distances.get(0).getKey();
        System.out.println("军事事件:"+event);
        return event;
    }

    @GetMapping("getIntention")
    public Map<String,Double> getIntention(@RequestParam(value = "place") String place){
        Map<String,Double> intention=intentionAnalysis.getIntention(place);
        return intention;
    }



}
