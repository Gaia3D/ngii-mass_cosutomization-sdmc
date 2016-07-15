package kr.ngii.pilot.sdmc.tx.nfsd.service.vo;

public class NfsdUmdVO {
	
	private String name;
	private String bjcd;
	private String umdCode;
	private String parentSggCode;
	
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

	public void setUmdCode(String umdCode)
	{
		this.umdCode = umdCode;
	}
	
	public String getUmdCode()
	{
		return this.umdCode;
	}
	
	public void setParentSggCode(String sggCode)
	{
		this.parentSggCode = sggCode;
	}
	
	public String getParentSggCode()
	{
		return this.parentSggCode;
	}
}
