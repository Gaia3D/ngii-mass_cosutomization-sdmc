package kr.ngii.pilot.sdmc.tx.nfsd.service.dao;
import kr.ngii.pilot.sdmc.core.annotation.MybatisForSdmc;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSggVO;

import java.util.List;

@MybatisForSdmc
public interface NfsdSggDao {
	
	public List<NfsdSggVO> selectSggs(String sidoCode);

	public String selectGeometry(String bjcd);
}
