package kr.ngii.pilot.sdmc.main.service.vo;

public class FileVO {

	private String fileId = null;
	private String fileStoredPath = null;
	private String fileOutputName = null;
	private String comment = null;
	private String regDt = null;
	
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileStoredPath() {
		return fileStoredPath;
	}
	public void setFileStoredPath(String fileStoredPath) {
		this.fileStoredPath = fileStoredPath;
	}
	public String getFileOutputName() {
		return fileOutputName;
	}
	public void setFileOutputName(String fileOutputName) {
		this.fileOutputName = fileOutputName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
		
}
