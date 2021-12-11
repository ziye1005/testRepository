package edu.njust.dydb.impl;

import edu.njust.dydb.DyDatabaseRepository;
import edu.njust.entity.oracle.UserConsColumn;
import edu.njust.entity.oracle.UserConstraints;
import edu.njust.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL数据库访问类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/14
 */
@Repository("mysqlRepository")
public class MySQLRepository implements DyDatabaseRepository {


    public final static String queryTable = "SHOW TABLES";
    public final static String queryRowFormat = "SELECT * FROM %s";
    public final static String queryRowFormatByColumn = "SELECT * FROM %s WHERE %s=?";
    public final static String queryColumns = "desc %s";
    public final static String queryInner = "SELECT COUNT(*) FROM %s t1 JOIN %s t2 ON t1.%s = t2.%s;";

    // TODO 适配MySQL的查询语句

    public final static String queryTableNameByFKName = "SELECT REFERENCED_TABLE_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE  where CONSTRAINT_NAME=?";
    public final static String queryTableFK = "SELECT * FROM information_schema.TABLE_CONSTRAINTS\n" +
            "WHERE information_schema.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'FOREIGN KEY'\n" +
            "AND information_schema.TABLE_CONSTRAINTS.TABLE_NAME = ?";
    //    public final static String queryConstraint = "select REFERENCED_TABLE_NAME from INFORMATION_SCHEMA.KEY_COLUMN_USAGE  where CONSTRAINT_NAME=?";
    public final static String QUERY_CONSTRAINT = "SELECT TABLE_NAME, COLUMN_NAME,CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE  where CONSTRAINT_NAME=?";
    // 查询约束相关的表和列：目前MySQL需要这样查询
    public final static String QUERY_REFERENCED_CONSTRAINT = "SELECT REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME,CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE  where CONSTRAINT_NAME=?";


    @Override
    public List<String> getAllTable(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(queryTable, (resultSet, i) -> resultSet.getString(1));
    }

    @Override
    public List<String> getAllColumns(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.query(String.format(queryColumns, table), new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("Field");
            }
        });
    }

    @Override
    public List<UserConstraints> getTableRConstraint(JdbcTemplate jdbcTemplate, String table) {
        List<UserConstraints> res = jdbcTemplate.query(queryTableFK, new Object[]{table}, new RowMapper<UserConstraints>() {
            @Override
            public UserConstraints mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserConstraints constraints = new UserConstraints();
                // MySQL 的 外键约束里面包含了当前表的列信息和外表的列信息，所以第一个外键约束名和关联表的约束名是一直的
                constraints.setConstraintName(rs.getString("CONSTRAINT_NAME"));
                constraints.setrConstraintName(constraints.getConstraintName());
                constraints.setConstraintType(rs.getString("CONSTRAINT_TYPE"));
                return constraints;
            }
        });
        for (UserConstraints item : res) {
            item.setTableName(table);
        }
        return res;
    }


    @Override
    public List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.query(String.format(queryRowFormat, table), (resultSet, idx) -> {
            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();
            Map<String, Object> rowData = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                if (resultSet.getObject(i) instanceof Blob) {
                    continue;
                }
                if (resultSet.getObject(i) instanceof Clob) {
                    continue;
                }
                if (resultSet.getObject(i) instanceof Timestamp) {
                    rowData.put(md.getColumnName(i), DateUtil.formatTimestamp((Timestamp) resultSet.getObject(i)));//获取键名及值
                    continue;
                }
                rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
            }
            return rowData;
        });
    }

    @Override
    public List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table, String column, Object value) {
        return jdbcTemplate.query(String.format(queryRowFormatByColumn, table, column), new Object[]{value}, (resultSet, idx) -> {
            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();
            Map<String, Object> rowData = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                if (resultSet.getObject(i) instanceof Blob) {
                    continue;
                }
                if (resultSet.getObject(i) instanceof Clob) {
                    continue;
                }
                if (resultSet.getObject(i) instanceof Timestamp) {
                    rowData.put(md.getColumnName(i), DateUtil.formatTimestamp((Timestamp) resultSet.getObject(i)));//获取键名及值
                    continue;
                }
                rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
            }
            return rowData;
        });
    }

    @Override
    public UserConsColumn getConsColumn(JdbcTemplate jdbcTemplate, String constraintName) {
        UserConsColumn userConsColumn = jdbcTemplate.queryForObject(QUERY_CONSTRAINT, new Object[]{constraintName}, new RowMapper<UserConsColumn>() {
            @Override
            public UserConsColumn mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserConsColumn userConsColumn = new UserConsColumn();
                userConsColumn.setTableName(rs.getString(1));
                userConsColumn.setColumnName(rs.getString(2));
                userConsColumn.setConstraintName(rs.getString(3));
                return userConsColumn;
            }
        });
        return userConsColumn;
    }

    @Override
    public UserConsColumn getConsReferencedColumn(JdbcTemplate jdbcTemplate, String constraintName) {
        UserConsColumn userConsColumn = jdbcTemplate.queryForObject(QUERY_REFERENCED_CONSTRAINT, new Object[]{constraintName}, new RowMapper<UserConsColumn>() {
            @Override
            public UserConsColumn mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserConsColumn userConsColumn = new UserConsColumn();
                userConsColumn.setTableName(rs.getString(1));
                userConsColumn.setColumnName(rs.getString(2));
                userConsColumn.setConstraintName(rs.getString(3));
                return userConsColumn;
            }
        });
        return userConsColumn;
    }

    @Override
    public String queryFieldMean(JdbcTemplate jdbcTemplate, String field) {
        return null;
    }

    @Override
    public Long queryRelCount(JdbcTemplate jdbcTemplate, String table1, String table1Column, String table2, String table2Column) {
        return jdbcTemplate.queryForObject(String.format(queryInner, table1, table2, table1Column, table2Column), Long.class);
    }

    public final static String QUERY_COLUMN_COMMENT = "SELECT  COLUMN_COMMENT 字段注释\n" +
            "FROM INFORMATION_SCHEMA.COLUMNS\n" +
            "WHERE TABLE_NAME = ?\n" +
            "AND TABLE_SCHEMA = (SELECT DATABASE())\n" +
            "AND COLUMN_NAME = ? AND COLUMN_NAME IS NOT NULL";

    @Override
    public String queryColumnComment(JdbcTemplate jdbcTemplate, String table, String column) {
        try {
            return jdbcTemplate.queryForObject(QUERY_COLUMN_COMMENT, new Object[]{table, column}, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
