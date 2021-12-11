package edu.njust.entity;

import java.io.Serializable;

/**
 * @author gudongxian
 * @version 0.1
 * @date 2020/11/11
 */
public class DataMap<S, T> implements Serializable {
    private S origin;
    private T map;

    public DataMap() {
    }

    public DataMap(S origin, T map) {
        this.origin = origin;
        this.map = map;
    }

    public S getOrigin() {
        return origin;
    }

    public void setOrigin(S origin) {
        this.origin = origin;
    }

    public T getMap() {
        return map;
    }

    public void setMap(T map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "DataMap{" +
                "origin=" + origin +
                ", map=" + map +
                '}';
    }
}
