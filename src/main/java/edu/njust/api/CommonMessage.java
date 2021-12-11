package edu.njust.api;

/**
 * 地图展示部分公共接口
 * */
public class CommonMessage<T> {
    public static final Integer HEATMAP = 1;
    public static final Integer BMTRACE = 2;
    public static final Integer FIXEDPLACE = 3;
    private Integer id;
    private Integer type;
    private T data;



    public void CommonMessage() {
    }

    public void CommonMessage(Integer id, Integer type) {
        this.id = id;
        this.type = type;
    }

    public void CommonMessage(Integer id, Integer type, T data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", type=" + type +
                ", data=" + data +
                '}';
    }
}
