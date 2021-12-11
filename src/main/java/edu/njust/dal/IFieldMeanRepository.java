package edu.njust.dal;

import edu.njust.entity.dto.FieldMean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/15
 */
public interface IFieldMeanRepository extends JpaRepository<FieldMean, Long> {
    /**
     * 根据字段获取数据
     *
     * @param field 字段
     * @return 返回含义
     */
    FieldMean getFirstByField(String field);

    /**
     * 根据表名和字段获取数据
     *
     * @param table 表名
     * @param field 字段
     * @return 返回含义
     */
    FieldMean getFirstByTableNameAndField(String table, String field);
}
