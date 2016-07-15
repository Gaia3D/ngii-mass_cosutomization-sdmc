package kr.ngii.pilot.sdmc.tx.order.service.vo;

public class OrderVO {

	private String orderId = null;
	private String orderType = null;
	private String orderDate = null;
	private String orderVersion = null;
	private String srs = null;
	private String srsName = null;
	private String format = null;
	private String formatName = null;
	private String status = null;
	private String updateStatus = null;
	private String userId = null;
	private String fileUrl = null;
	private String regDt = null;
	private String regUserId = null;
	private String[] areaCodeArray = null;
	private String[] orderedLayers = null;
	private String[] orderedLayerCds = null;
	private String orderedLayer = null;
	private String orderedLayerName = null;
	private String orderedAreaCode = null;
	
	private String geoName = null;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderVersion() {
		return orderVersion;
	}
	public void setOrderVersion(String orderVersion) {
		this.orderVersion = orderVersion;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String[] getAreaCodeArray() {
		return areaCodeArray;
	}
	public void setAreaCodeArray(String[] areaCodeArray) {
		for(int i = 0 ; i < areaCodeArray.length ; i++){
			areaCodeArray[i] = areaCodeArray[i].replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "");
		}
		this.areaCodeArray = areaCodeArray;
	}
	public String[] getOrderedLayers() {
		return orderedLayers;
	}
	public void setOrderedLayers(String[] orderedLayers) {
		this.orderedLayers = orderedLayers;
	}
	public String[] getOrderedLayerCds() {
		return orderedLayerCds;
	}
	public void setOrderedLayerCds(String[] orderedLayerCds) {
		this.orderedLayerCds = orderedLayerCds;
	}

	public String getGeoName() {
		return geoName;
	}
	public void setGeoName(String geoName) {
		//this.geoName = geoName.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "_");
		this.geoName = geoName.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "");
	}
	public String getSrsName() {
		return srsName;
	}
	public void setSrsName(String srsName) {
		this.srsName = srsName;
	}
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public String getOrderedLayer() {
		return orderedLayer;
	}
	public void setOrderedLayer(String orderedLayer) {
		//this.orderedLayer = orderedLayer.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "_");
		this.orderedLayer = orderedLayer.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "");
	}
	public String getOrderedLayerName() {
		return orderedLayerName;
	}
	public void setOrderedLayerName(String orderedLayerName) {
		//this.orderedLayerName = orderedLayerName.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "_");
		this.orderedLayerName = orderedLayerName.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "");
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getOrderedAreaCode() {
		return orderedAreaCode;
	}
	public void setOrderedAreaCode(String orderedAreaCode) {
		this.orderedAreaCode = orderedAreaCode.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "");
		//this.orderedAreaCode = orderedAreaCode.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "_");
		//this.orderedAreaCode = orderedAreaCode;
	}
	
}
