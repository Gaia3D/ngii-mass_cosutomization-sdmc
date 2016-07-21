package kr.ngii.pilot.sdmc.main.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ngii.pilot.sdmc.main.service.MainService;
import kr.ngii.pilot.sdmc.main.service.dao.MainDao;
import kr.ngii.pilot.sdmc.main.service.vo.DoyeupVO;
import kr.ngii.pilot.sdmc.main.service.vo.FormatVO;
import kr.ngii.pilot.sdmc.main.service.vo.LayerVO;
import kr.ngii.pilot.sdmc.main.service.vo.LoggerVO;
import kr.ngii.pilot.sdmc.main.service.vo.NoticeVO;
import kr.ngii.pilot.sdmc.main.service.vo.SrsVO;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	private MainDao mainDao;
	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

	@Override
	public List<LayerVO> getLayerList(LayerVO layerVO) {
		logger.debug("getLayerList");

		
		List<LayerVO> list = mainDao.selectLayerList(layerVO);
		
		return list;
	}

	@Override
	public List<FormatVO> getFormatList() {
		logger.debug("getFormatList");
		List<FormatVO> list = mainDao.selectFormatList();
		
		return list;
	}

	@Override
	public List<SrsVO> getSrsList() {
		logger.debug("getSrsList");
		List<SrsVO> list = mainDao.selectSrsList();
		
		return list;
	}

	@Override
	public List<DoyeupVO> getDoyeupList(String dyCd) {
		logger.debug("getSrsList");
		List<DoyeupVO> list = mainDao.selectDoyeupList(dyCd);
		return list;
	}

	@Override
	public void setLogItem(LoggerVO log) {
		logger.debug("log writing...");
		
		mainDao.insertLog(log);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NoticeVO> getNoticeList(String userId) {
		logger.debug("getSrsList");
		List<NoticeVO> list = mainDao.selectNoticeList(userId);
		return list;
	}




}
