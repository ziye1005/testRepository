package edu.njust.model.oracle;

public class OracleSelect {
//    查询oracle数据库的参数：表名，属性，值
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    String tableName;
    String property;
    String searchWord;

}
