package kr.ngii.pilot.sdmc.tx.order.service.vo;

public class OutputInfoVO {
	
	private String outputInternalPath;
	private String outputExternalName;
	
	
	public void setOutputInternalPath(String path)
	{
		this.outputInternalPath = path;
	}
	
	public String getOutputInternalPath()
	{
		return this.outputInternalPath;
	}

	public void setOutputExternalName(String name)
	{
		this.outputExternalName = name;
	}
	
	public String getOutputExternalName()
	{
		return this.outputExternalName;
	}
}
