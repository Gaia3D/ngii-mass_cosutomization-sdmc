package kr.ngii.pilot.sdmc.tx.nfsd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ngii.pilot.sdmc.tx.nfsd.service.NfsdService;
import kr.ngii.pilot.sdmc.tx.nfsd.service.dao.NfsdSggDao;
import kr.ngii.pilot.sdmc.tx.nfsd.service.dao.NfsdSidoDao;
import kr.ngii.pilot.sdmc.tx.nfsd.service.dao.NfsdUmdDao;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSggVO;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdSidoVO;
import kr.ngii.pilot.sdmc.tx.nfsd.service.vo.NfsdUmdVO;


@Service
public class NfsdServiceImpl implements NfsdService {
	
	@Autowired
	protected NfsdSidoDao nfsdSidoDao;
	
	@Autowired
	protected NfsdSggDao nfsdSggDao;
	
	@Autowired
	protected NfsdUmdDao nfsdUmdDao;

	@Autowired
	public List<NfsdSidoVO> getAllSidos() {
		return nfsdSidoDao.selectAllSidos();
	}

	@Override
	public List<NfsdSggVO> getSggs(String sidoCode) {
		return nfsdSggDao.selectSggs(sidoCode);
	}

	@Override
	public List<NfsdUmdVO> getUmds(String sidoCode, String sggCode) {
		return nfsdUmdDao.selectUmds(sidoCode, sggCode);
	}

	@Override
	public String getSidoGeometry(String bjcd) {
		return nfsdSidoDao.selectGeometry(bjcd);
	}

	@Override
	public String getSggGeometry(String bjcd) {
		return nfsdSggDao.selectGeometry(bjcd);
	}

	@Override
	public String getUmdGeometry(String bjcd) {
		return nfsdUmdDao.selectGeometry(bjcd);
	}
}
