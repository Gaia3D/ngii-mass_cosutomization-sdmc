package kr.ngii.pilot.sdmc.login.service;

import kr.ngii.pilot.sdmc.login.service.vo.Uservo;

public interface LoginService {
	
	public String makeLoginValidationUrl(String snsType);
	
	public boolean updateUserOrderHistoryForAfterService(String userId);

	public String getEmailAddr(String code, String snsType);
	
	public boolean checkLogin(String id, String password);

	public boolean information(Uservo uservo);

}
