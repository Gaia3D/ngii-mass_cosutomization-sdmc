package kr.ngii.pilot.sdmc.login.service;

import kr.ngii.pilot.sdmc.login.service.vo.Uservo;

public interface LoginService {
	
	public String makeLoginValidationUrl(String snsType);
	
	public boolean updateUserOrderHistoryForAfterService(String userId);

	public String getEmailAddr(String code, String snsType);
	
	public boolean checkLogin(Uservo user);

	public boolean information(String email, String name, String password, String confirmPassword, String telNo);

}
