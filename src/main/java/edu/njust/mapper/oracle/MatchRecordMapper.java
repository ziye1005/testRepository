package edu.njust.mapper.oracle;

import edu.njust.model.oracle.OracleSelect;
import edu.njust.model.oracle.LDSignal96;
import edu.njust.model.oracle.TargetSituation89;
import edu.njust.model.oracle.ZSZF1;
import edu.njust.model.oracle.ZSZF2;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchRecordMapper {
    //    模糊查询，查询目标态势数据表
    @Select("SELECT * FROM ${tableName} WHERE \"${property}\" like \'%${searchWord}%\'")
    List<TargetSituation89> select1(OracleSelect oracleSelect);

    //    模糊查询，查询雷达信号
    @Select("SELECT * FROM ${tableName} WHERE \"${property}\" like \'%${searchWord}%\'")
    List<LDSignal96> select2(OracleSelect oracleSelect);

    //      查询目标战术战法模型ZSZF1
    @Select("SELECT * FROM ${tableName} WHERE \"${property}\" like \'%${searchWord}%\'")
    List<ZSZF1> select3(OracleSelect oracleSelect);

    //    查询战术战法匹配算法表ZSZF2
    @Select("SELECT * FROM ${tableName} WHERE \"${property}\" like \'%${searchWord}%\'")
    List<ZSZF2> select4(OracleSelect oracleSelect);



}
