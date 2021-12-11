package edu.njust.dydb.impl;

import edu.njust.dydb.DyDatabaseRepository;
import edu.njust.entity.oracle.UserConsColumn;
import edu.njust.entity.oracle.UserConstraints;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * SQLServer数据库访问类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/14
 */
@Repository("sqlServerRepository")
public class SQLServerRepository implements DyDatabaseRepository {
    @Override
    public List<String> getAllTable(JdbcTemplate jdbcTemplate) {
        return null;
    }

    @Override
    public List<String> getAllColumns(JdbcTemplate jdbcTemplate, String table) {
        return null;
    }

    @Override
    public List<UserConstraints> getTableRConstraint(JdbcTemplate jdbcTemplate, String table) {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table) {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table, String column, Object value) {
        return null;
    }

    @Override
    public UserConsColumn getConsColumn(JdbcTemplate jdbcTemplate, String constraintName) {
        return null;
    }

    @Override
    public UserConsColumn getConsReferencedColumn(JdbcTemplate jdbcTemplate, String constraintName) {
        return null;
    }

    @Override
    public String queryFieldMean(JdbcTemplate jdbcTemplate, String field) {
        return null;
    }

    @Override
    public Long queryRelCount(JdbcTemplate jdbcTemplate, String table1, String table1Column, String table2, String table2Column) {
        return null;
    }

    @Override
    public String queryColumnComment(JdbcTemplate jdbcTemplate, String table, String column) {
        return null;
    }
}
