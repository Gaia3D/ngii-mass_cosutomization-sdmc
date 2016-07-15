package kr.ngii.pilot.sdmc.tx.nfsd.service.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.ngii.pilot.sdmc.core.annotation.MybatisForSdmc;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdUmdVO;

@MybatisForSdmc
public interface NfsdUmdDao {
	
	public List<NfsdUmdVO> selectUmds(@Param("sidoCode") String sidoCode, @Param("sggCode") String sggCode);

	public String selectGeometry(String bjcd);
}
