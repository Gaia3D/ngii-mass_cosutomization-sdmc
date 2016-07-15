package kr.ngii.pilot.sdmc.tx.order.service.vo;

public class ProductVO {

	private String orderId = null;
	private String orderProductId = null;
	private String bjcd = null;
	private String mapNum = null;
	private String mapName = null;
	private String shape = null;
	private String regDt = null;
	private String regUserId = null;
	private String srs = null;
	private String format = null;
	private String layerId = null;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getBjcd() {
		return bjcd;
	}

	public void setBjcd(String bjcd) {
		this.bjcd = bjcd.replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "");
	}

	public String getMapNum() {
		return mapNum;
	}

	public void setMapNum(String mapNum) {
		this.mapNum = mapNum.replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "");
		//this.mapNum = mapNum;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		
		this.layerId = layerId.replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "");
	}


}
