package edu.njust.dydb;

import edu.njust.entity.oracle.UserConsColumn;
import edu.njust.entity.oracle.UserConstraints;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 动态数据源访问类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/5
 */
public interface DyDatabaseRepository {

    /**
     * 获取当前数据库表格信息
     *
     * @param jdbcTemplate jdbcTemplate
     * @return 表名称列表
     */
    List<String> getAllTable(JdbcTemplate jdbcTemplate);

    /**
     * 查询表的所有列名
     *
     * @param jdbcTemplate jdbcTemplate
     * @param table        表名
     * @return 列名的列表
     */
    List<String> getAllColumns(JdbcTemplate jdbcTemplate, String table);

    /**
     * 获取表的外键约束
     *
     * @param jdbcTemplate jdbcTemplate
     * @param table        表名
     * @return 外键约束列表
     */
    List<UserConstraints> getTableRConstraint(JdbcTemplate jdbcTemplate, String table);

    /**
     * 查询数据
     *
     * @param jdbcTemplate jdbcTemplate
     * @param table        表名
     * @return 数据记录
     */
    List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table);

    /**
     * 查询数据
     *
     * @param jdbcTemplate jdbcTemplate
     * @param table        表名
     * @param column       列名
     * @param value        列值
     * @return 数据记录
     */
    List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table, String column, Object value);

    /**
     * 查询约束相关的列
     *
     * @param jdbcTemplate   jdbcTemplate
     * @param constraintName 约束名
     * @return 约束列信息
     */
    UserConsColumn getConsColumn(JdbcTemplate jdbcTemplate, String constraintName);

    /**
     * 查询约束相关的列 MySQL需要
     *
     * @param jdbcTemplate   jdbcTemplate
     * @param constraintName 约束名
     * @return 约束列信息
     */
    UserConsColumn getConsReferencedColumn(JdbcTemplate jdbcTemplate, String constraintName);

    /**
     * 查询字段含义
     *
     * @param jdbcTemplate jdbcTemplate
     * @param field        字段名
     * @return 字段含义
     */
    String queryFieldMean(JdbcTemplate jdbcTemplate, String field);

    /**
     * 根据headTable.headColumn and tailTable.tailColumn 作为连接条件。
     * 判断是否数据
     *
     * @param jdbcTemplate 查询jdbc
     * @param table1       表
     * @param table1Column 表列
     * @param table2       表
     * @param table2Column 表列
     * @return 返回查询个数
     */
    Long queryRelCount(JdbcTemplate jdbcTemplate, String table1, String table1Column, String table2, String table2Column);

    /**
     * 查询字段含义
     *
     * @param jdbcTemplate jdbcTemplate
     * @param table        表名
     * @param column       字段名
     * @return 列注释
     */
    String queryColumnComment(JdbcTemplate jdbcTemplate, String table, String column);

}
