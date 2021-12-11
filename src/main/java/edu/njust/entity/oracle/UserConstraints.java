package edu.njust.entity.oracle;

/**
 *
 * Oracle user_constraints 用户约束表 对应实体类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/6
 */
public class UserConstraints {
    /**
     * 约束名
     */
    private String constraintName;
    /**
     * 引用约束名
     */
    private String rConstraintName;
    /**
     * 约束类型
     */
    private String constraintType;
    /**
     * 相关表名
     */
    private String tableName;

    @Override
    public String toString() {
        return "UserConstraints{" +
                "constraintName='" + constraintName + '\'' +
                ", rConstraintName='" + rConstraintName + '\'' +
                ", constraintType='" + constraintType + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getrConstraintName() {
        return rConstraintName;
    }

    public void setrConstraintName(String rConstraintName) {
        this.rConstraintName = rConstraintName;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
