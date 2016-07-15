package kr.ngii.pilot.sdmc.login.service.vo;

public class AreaVO {
	private String orderId;
	private String orderType;
	private String orderVersion;
	private String schemaName;
	private String tName;
	private String layerId;
	private String bjcds;
	private String bjcdGeoms;
	private String userId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderVersion() {
		return orderVersion;
	}
	public void setOrderVersion(String orderVersion) {
		this.orderVersion = orderVersion;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		this.tName = this.schemaName + "." + this.layerId;
	}
	public String getLayerId() {
		return layerId;
	}
	public void setLayerId(String layerId) {
		this.layerId = layerId;
		this.tName = this.schemaName + "." + this.layerId;
	}
	public String getBjcds() {
		return bjcds;
	}
	public void setBjcds(String bjcds) {
		this.bjcds = bjcds;
	}
	public String getBjcdGeoms() {
		return bjcdGeoms;
	}
	public void setBjcdGeoms(String bjcdGeoms) {
		this.bjcdGeoms = bjcdGeoms;
	}
	public String gettName() {
		return tName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
