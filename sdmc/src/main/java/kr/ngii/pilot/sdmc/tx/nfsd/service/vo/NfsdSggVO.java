package kr.ngii.pilot.sdmc.tx.nfsd.service.vo;

public class NfsdSggVO {
	
	private String name;
	private String bjcd;
	private String sggCode;
	private String parentSidoCode;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setBjcd(String bjcd)
	{
		this.bjcd = bjcd;
	}
	
	public String getBjcd()
	{
		return this.bjcd;
	}

	public void setSggCode(String sggCode)
	{
		this.sggCode = sggCode;
	}
	
	public String getSggCode()
	{
		return this.sggCode;
	}
	
	public void setParentSidoCode(String sidoCode)
	{
		this.parentSidoCode = sidoCode;
	}
	
	public String getParentSidoCode()
	{
		return this.parentSidoCode;
	}
}
