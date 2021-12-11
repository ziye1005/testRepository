package edu.njust.dal;

import edu.njust.entity.dto.Table2NodeLabel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 表名节点映射
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/4
 */
public interface Table2NodelLabelRepository extends JpaRepository<Table2NodeLabel, Long> {
    /**
     * 根据表名查询映射Label
     *
     * @param tableName 表名
     * @return 数据列表
     */
    Table2NodeLabel getFirstByTableName(String tableName);
}
