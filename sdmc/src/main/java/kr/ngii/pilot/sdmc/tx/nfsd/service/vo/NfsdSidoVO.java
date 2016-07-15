package kr.ngii.pilot.sdmc.tx.nfsd.service.vo;

public class NfsdSidoVO {
	
	private String name;
	private String bjcd;
	private String sidoCode;
	
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

	public void setSidoCode(String sidoCode)
	{
		this.sidoCode = sidoCode;
	}
	
	public String getSidoCode()
	{
		return this.sidoCode;
	}
}
