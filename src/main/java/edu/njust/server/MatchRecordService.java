package edu.njust.server;

import edu.njust.mapper.oracle.MatchRecordMapper;
import edu.njust.model.oracle.OracleSelect;
import edu.njust.model.oracle.LDSignal96;
import edu.njust.model.oracle.TargetSituation89;
import edu.njust.model.oracle.ZSZF1;
import edu.njust.model.oracle.ZSZF2;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class MatchRecordService {
    @Resource
    private MatchRecordMapper matchRecordMapper;
    //查询目标态势数据
    public List<TargetSituation89> select1(OracleSelect oracleSelect) {
//        System.out.println("89 service运行");
        System.out.println(oracleSelect.getTableName()+","+ oracleSelect.getProperty()+","+ oracleSelect.getSearchWord());
        return matchRecordMapper.select1(oracleSelect);
    }
    //    查询雷达信号
    public List<LDSignal96> select2(OracleSelect oracleSelect) {
//        System.out.println("96 service运行");
        System.out.println(oracleSelect.getTableName()+","+ oracleSelect.getProperty()+","+ oracleSelect.getSearchWord());
        return matchRecordMapper.select2(oracleSelect);
    }
    //    查询目标战术战法模型ZSZF1
    public List<ZSZF1> select3(OracleSelect oracleSelect) {
//        System.out.println("1 service运行");
        System.out.println(oracleSelect.getTableName()+","+ oracleSelect.getProperty()+","+ oracleSelect.getSearchWord());
        return matchRecordMapper.select3(oracleSelect);
    }
    //    查询战术战法匹配算法表ZSZF2
    public List<ZSZF2> select4(OracleSelect oracleSelect) {
//        System.out.println("2 service运行");
        System.out.println(oracleSelect.getTableName()+","+ oracleSelect.getProperty()+","+ oracleSelect.getSearchWord());
        return matchRecordMapper.select4(oracleSelect);
    }
}
