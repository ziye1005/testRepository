package edu.njust.controller;

import edu.njust.model.Membership;
import edu.njust.service.TargetRecogService;
import edu.njust.utils.AutoRecogResult;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Scope("prototype")
@RequestMapping("/targetRecog")
@CrossOrigin
public class TargetRecogController {

    @Resource
    private TargetRecogService recogService;

    @GetMapping("/autoRecog")
    public AutoRecogResult autoRecog() {
        // 调用研判服务
        AutoRecogResult recogResult = new AutoRecogResult();
        recogResult.setTargetModel("E-8");
        recogResult.setTargetType(1);
        recogResult.setCountryCode("美国");
        recogResult.setStartPlace("嘉手那");
        // 生成属性符合概率
        return recogResult;
    }
}
