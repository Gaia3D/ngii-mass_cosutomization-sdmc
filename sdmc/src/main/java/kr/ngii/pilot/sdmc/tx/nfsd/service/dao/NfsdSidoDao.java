package kr.ngii.pilot.sdmc.tx.nfsd.service.dao;
import kr.ngii.pilot.sdmc.core.annotation.MybatisForSdmc;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSidoVO;

import java.util.List;

@MybatisForSdmc
public interface NfsdSidoDao {
	
	public List<NfsdSidoVO> selectAllSidos();
	
	public String selectGeometry(String bjcd);
}
