package edu.njust.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字段含义表，对目标库中的缩写字段进行对应含义描述，作为构建图谱的可理解属性
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/22
 */
@Entity
public class FieldMean implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ApiModelProperty(value = "表")
    @NotNull(message = "字段不能为空")
    private String tableName;

    @ApiModelProperty(value = "字段")
    @NotNull(message = "字段不能为空")
    private String field;

    @ApiModelProperty(value = "含义")
    @NotNull(message = "字段含义不能为空")
    @Column(length = 20)
    private String mean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "FieldMean{" +
                "id=" + id +
                ", table='" + tableName + '\'' +
                ", field='" + field + '\'' +
                ", mean='" + mean + '\'' +
                '}';
    }
}
