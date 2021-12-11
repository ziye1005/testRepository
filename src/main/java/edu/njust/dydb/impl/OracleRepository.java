package edu.njust.dydb.impl;

import edu.njust.dydb.DyDatabaseRepository;
import edu.njust.entity.oracle.UserConsColumn;
import edu.njust.entity.oracle.UserConstraints;
import edu.njust.util.DateUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Oracle数据库访问类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/5
 */
@Repository("oracleRepository")
public class OracleRepository implements DyDatabaseRepository {
    public final static String queryTable = "SELECT t.table_name,t.comments FROM user_tab_comments t";
    public final static String queryRowFormat = "SELECT * FROM \"%s\"";
    public final static String queryRowFormatByColumn = "SELECT * FROM \"%s\" WHERE %s=?";
    public final static String queryColumns = "SELECT u.COLUMN_NAME FROM user_tab_columns u WHERE table_name=?";
    public final static String queryTableFK = "select c.CONSTRAINT_NAME,c.CONSTRAINT_NAME,c.CONSTRAINT_TYPE,c.R_CONSTRAINT_NAME,c.TABLE_NAME from user_constraints c where c.constraint_type = 'R' and c.table_name=?";
    public final static String queryConstraint = "select cl.table_name, cl.column_name, cl.constraint_name from user_cons_columns cl where cl.constraint_name =?";
    public final static String queryInner = "SELECT COUNT(*) FROM \"%s\" t1, \"%s\" t2 WHERE t1.%s = t2.%s";

    @Override
    public List<String> getAllTable(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(queryTable, (resultSet, i) -> resultSet.getString(1));
    }

    @Override
    public List<String> getAllColumns(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.queryForList(queryColumns, new Object[]{table}, String.class);
    }

    @Override
    public List<UserConstraints> getTableRConstraint(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.query(queryTableFK, new Object[]{table}, new BeanPropertyRowMapper<UserConstraints>(UserConstraints.class));
    }


    @Override
    public List<Map<String, Object>> queryTable(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.query(String.format(queryRowFormat, table), (resultSet, idx) -> {
            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();
            Map<String, Object> rowData = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                // 过滤Neo4j 转换的Oracle 数据库值
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
                // 过滤Neo4j 转换的Oracle 数据库值
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
        return jdbcTemplate.queryForObject(queryConstraint, new Object[]{constraintName}, new BeanPropertyRowMapper<UserConsColumn>(UserConsColumn.class));
    }

    @Override
    public UserConsColumn getConsReferencedColumn(JdbcTemplate jdbcTemplate, String constraintName) {
        return null;
    }

    @Override
    public String queryFieldMean(JdbcTemplate jdbcTemplate, String field) {

        String wordTable = "字典表";
        List<String> columns = getAllColumns(jdbcTemplate, wordTable);
        String queryForWord = String.format(queryTable, wordTable);
        StringBuilder sb = new StringBuilder();
        for (String column : columns) {
            // 拼接语句
            sb.append(queryForWord);
            sb.append(" ");
            sb.append(column);
            sb.append("=?");
            sb.append(" ");
            sb.append("limit 1");
            // 查询
            String mean = jdbcTemplate.queryForObject(sb.toString(), new Object[]{sb.toString()}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // TODO
                    return null;
                }
            });
            // 清除语句
            sb.delete(0, sb.length());
            if (mean != null) {
                return mean;
            }
        }
        // 没有查到则返回原始字段
        return field;
    }

    @Override
    public Long queryRelCount(JdbcTemplate jdbcTemplate, String table1, String table1Column, String table2, String table2Column) {
        return jdbcTemplate.queryForObject(String.format(queryInner, table1, table2, table1Column, table2Column), Long.class);
    }

    public final static String QUERY_COLUMN_COMMENT = "SELECT COMMENTS FROM USER_COL_COMMENTS WHERE TABLE_NAME=? AND COLUMN_NAME=? AND COMMENTS IS NOT  NULL";

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
