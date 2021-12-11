package edu.njust.entity;

/**
 * @author gudongxian
 * @version 0.1
 * @date 2020/11/14
 */
public class DataGraph<T> {
    T node;
    T link;

    public DataGraph() {
    }

    public DataGraph(T node, T link) {
        this.node = node;
        this.link = link;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public T getLink() {
        return link;
    }

    public void setLink(T link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DataGraph{" +
                "node=" + node +
                ", link =" + link +
                '}';
    }
}
