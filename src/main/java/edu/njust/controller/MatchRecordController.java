package edu.njust.controller;
import edu.njust.model.oracle.OracleSelect;
import edu.njust.model.oracle.LDSignal96;
import edu.njust.model.oracle.TargetSituation89;
import edu.njust.model.oracle.ZSZF1;
import edu.njust.model.oracle.ZSZF2;
import edu.njust.service.MatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/match")//一级URL
public class MatchRecordController {
    @Autowired
    private MatchRecordService matchRecordService;

    //    查询目标态势数据
    @GetMapping(value = "/select89")
    public List<TargetSituation89> select1(@RequestBody OracleSelect oracleSelect) throws Exception {
//        System.out.println("89 controller运行");
//        System.out.println(oracleSelect.getTableName() + "," + oracleSelect.getProperty() + "," + oracleSelect.getSearchWord());
        return matchRecordService.select1(oracleSelect);
    }

    //    查询雷达
    @GetMapping(value = "/select96")
    public List<LDSignal96> select2(@RequestBody OracleSelect oracleSelect) throws Exception {
//        System.out.println("96 controller运行");
//        System.out.println(oracleSelect.getTableName() + "," + oracleSelect.getProperty() + "," + oracleSelect.getSearchWord());
        return matchRecordService.select2(oracleSelect);
    }

    //    查询目标战术战法模型ZSZF1
    @GetMapping(value = "/select1")
    public List<ZSZF1> select3(@RequestBody OracleSelect oracleSelect) throws Exception {
//        System.out.println("1 controller运行");
//        System.out.println(oracleSelect.getTableName() + "," + oracleSelect.getProperty() + "," + oracleSelect.getSearchWord());
        return matchRecordService.select3(oracleSelect);
    }

    //    查询战术战法匹配算法表ZSZF2
    @GetMapping(value = "/select2")
    public List<ZSZF2> select4(@RequestBody OracleSelect oracleSelect) throws Exception {
//        System.out.println("2 controller运行");
//        System.out.println(oracleSelect.getTableName() + "," + oracleSelect.getProperty() + "," + oracleSelect.getSearchWord());
        return matchRecordService.select4(oracleSelect);
    }
}
