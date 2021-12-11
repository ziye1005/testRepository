package edu.njust.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Neo4j 前端显示的列映射表
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/10/16
 */
@ApiModel("数据库显示字段映射表")
@Entity
public class TableDisplayName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ApiModelProperty(value = "数据库表名")
    @NotNull(message = "表名不能为空")
    @Column(nullable = false, unique = true)
    private String tableName;

    @ApiModelProperty(value = "Neo4j Node 显示的列")
    @NotNull(message = "显示的列")
    @Column(nullable = false)
    private String displayColumn;

    @Override
    public String toString() {
        return "TableDisplayName{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", displayColumn='" + displayColumn + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisplayColumn() {
        return displayColumn;
    }

    public void setDisplayColumn(String displayColumn) {
        this.displayColumn = displayColumn;
    }
}
