package edu.njust.vo;

import java.util.Map;

/**
 * 节点显示对象
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/24
 */
public class NodeVO {

    /**
     * 节点ID
     */
    private Long id;
    /**
     * 节点名称，用于显示
     */
    private String name;
    /**
     * 节点属性
     */
    private Map<String, Object> properties;

    public String getType() {
        return type;
    }

    public NodeVO(Long id, String name, Map<String, Object> properties, String type) {
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public NodeVO() {
    }

    public NodeVO(Long id, String name, Map<String, Object> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "NodeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", type='" + type + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
