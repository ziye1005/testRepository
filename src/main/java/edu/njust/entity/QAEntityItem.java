package edu.njust.entity;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class QAEntityItem implements Serializable{
	private long uuid;
	private String name;//显示名称
	private String type;
	private transient Map<String,Object> property;
	/*private String x;
	private String y;*/
	public long getUuid() {
		return uuid;
	}
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String,Object> getProperty() {
		return property;
	}
	public void setProperty(Map<String,Object> property) {
		this.property = property;
	}
	/*public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}*/
	
}
