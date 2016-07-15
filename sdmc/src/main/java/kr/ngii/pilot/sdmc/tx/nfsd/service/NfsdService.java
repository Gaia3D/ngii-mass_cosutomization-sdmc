package kr.ngii.pilot.sdmc.tx.nfsd.service;

import java.util.List;

import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSggVO;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSidoVO;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdUmdVO;

public interface NfsdService {
	
	public List<NfsdSidoVO> getAllSidos();
	
	public List<NfsdSggVO> getSggs(String sidoCode);
	
	public List<NfsdUmdVO> getUmds(String sidoCode, String sggCode);
	
	public String getSidoGeometry(String bjcd);
	
	public String getSggGeometry(String bjcd);

	public String getUmdGeometry(String bjcd);
}
