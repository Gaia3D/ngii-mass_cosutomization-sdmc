package kr.ngii.pilot.sdmc.main.service;

import java.util.List;

import kr.ngii.pilot.sdmc.main.service.vo.DoyeupVO;
import kr.ngii.pilot.sdmc.main.service.vo.FormatVO;
import kr.ngii.pilot.sdmc.main.service.vo.LayerVO;
import kr.ngii.pilot.sdmc.main.service.vo.LoggerVO;
import kr.ngii.pilot.sdmc.main.service.vo.SrsVO;

public interface MainService {

	public List<LayerVO> getLayerList(LayerVO layerVO);

	public List<FormatVO> getFormatList();

	public List<SrsVO> getSrsList();

	public List<DoyeupVO> getDoyeupList(String dycd);
	
	public void setLogItem(LoggerVO log);

}
