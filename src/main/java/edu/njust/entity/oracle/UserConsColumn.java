package edu.njust.entity.oracle;

/**
 * Oracle  user_cons_columns 用户约束列信息
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/6
 */
public class UserConsColumn {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 约束名
     */
    private String constraintName;

    @Override
    public String toString() {
        return "UserConsColumn{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", constraintName='" + constraintName + '\'' +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }
}
