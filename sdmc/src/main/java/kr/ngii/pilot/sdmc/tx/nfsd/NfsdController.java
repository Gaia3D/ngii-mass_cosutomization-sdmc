package kr.ngii.pilot.sdmc.tx.nfsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ngii.pilot.sdmc.tx.nfsd.service.NfsdService;
import kr.ngii.pilot.sdmc.util.StringUtil;

@Controller
public class NfsdController {
	
	@Autowired
	protected NfsdService nfsdService;

	@RequestMapping(value = "/nfsd/sidos.ngii")
	public @ResponseBody Object getSidoList()
	{
		return nfsdService.getAllSidos();
	}
	
	@RequestMapping(value = "/nfsd/sidoGeometry.ngii")
	public @ResponseBody Object getSidoGeometry(String bjcd) throws Exception
	{
		if(StringUtil.isEmpty(bjcd))
			throw new Exception("null parameter");

		return nfsdService.getSidoGeometry(bjcd);
	}
	
	@RequestMapping(value = "/nfsd/sggs.ngii")
	public @ResponseBody Object getSggList(String sidoCode) throws Exception
	{
		if(StringUtil.isEmpty(sidoCode))
			throw new Exception("null parameter");

		return nfsdService.getSggs(sidoCode);
	}
	
	@RequestMapping(value = "/nfsd/sggGeometry.ngii")
	public @ResponseBody Object getSggGeometry(String bjcd) throws Exception
	{
		if(StringUtil.isEmpty(bjcd))
			throw new Exception("null parameter");

		return nfsdService.getSggGeometry(bjcd.toString());
	}
	
	@RequestMapping(value = "/nfsd/umds.ngii")
	public @ResponseBody Object getUmdList(String sidoCode, String sggCode) throws Exception
	{
		if(StringUtil.isEmpty(sidoCode) || StringUtil.isEmpty(sggCode))
			throw new Exception("null parameter");

		return nfsdService.getUmds(sidoCode, sggCode);
	}
	
	@RequestMapping(value = "/nfsd/umdGeometry.ngii")
	public @ResponseBody Object getUmdGeometry(String bjcd) throws Exception
	{
		if(StringUtil.isEmpty(bjcd))
			throw new Exception("null parameter");

		return nfsdService.getUmdGeometry(bjcd);
	}
}
