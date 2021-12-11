package edu.njust.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据库表名映射成neo4j节点类型
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/10/16
 */
@ApiModel("数据库Neo4jLabel映射表")
@Entity
public class Table2NodeLabel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ApiModelProperty(value = "数据库表名")
    @NotNull(message = "表名不能为空")
    @Column(nullable = false, unique = true)
    private String tableName;

    @ApiModelProperty(value = "Neo4j Node Label")
    @NotNull(message = "节点类型不能为空")
    @Column(nullable = false)
    private String nodeLabel;

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

    public String getNodeLabel() {
        return nodeLabel;
    }

    public void setNodeLabel(String nodeLabel) {
        this.nodeLabel = nodeLabel;
    }

    @Override
    public String toString() {
        return "Table2NodeLabel{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", nodeLabel='" + nodeLabel + '\'' +
                '}';
    }
}
