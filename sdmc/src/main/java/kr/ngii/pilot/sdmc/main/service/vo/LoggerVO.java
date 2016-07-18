package kr.ngii.pilot.sdmc.main.service.vo;

public class LoggerVO {

	private int logId = 0;
	private String logKind = null;
	private String logSummary = null;
	private String logUser = null;
	
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getLogKind() {
		return logKind;
	}
	public void setLogKind(String logKind) {
		this.logKind = logKind;
	}
	public String getLogSummary() {
		return logSummary;
	}
	public void setLogSummary(String logSummary) {
		this.logSummary = logSummary;
	}
	public String getLogUser() {
		return logUser;
	}
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}
	
	
}
