package kr.ngii.pilot.sdmc.login.service.dao;

import java.util.ArrayList;
import java.util.List;

import kr.ngii.pilot.sdmc.core.annotation.DsApp;
import kr.ngii.pilot.sdmc.login.service.vo.AreaVO;
import kr.ngii.pilot.sdmc.login.service.vo.UserVO;


@DsApp
public interface LoginDao {

	public void selectLayerList(String userId);

	public List<AreaVO> selectAreaList(AreaVO areaVO);

	public String selectIntersectCount(AreaVO areaVO);

	public void updateUpdateStatus(AreaVO areaItemVO);

	public void insertUserInfo(String id, String name, String password, String telNo);

	public List<UserVO> selectUserInfo(String id, String password);

}
