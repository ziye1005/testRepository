package edu.njust.controller;

import edu.njust.model.TargetRecogWeights;
import edu.njust.service.WeightsService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.ValueExp;

@RestController
@Scope("prototype")
@RequestMapping("/weights")
@CrossOrigin
public class WeightsController {

    @Resource
    private WeightsService weightsService;

    @PostMapping("/insert")
    public int insert(@RequestBody TargetRecogWeights weights) {
        // 添加权值要素
        return weightsService.insert(weights);
    }
}
