package kr.ngii.pilot.sdmc.login.service;

public interface LoginService {
	
	public String makeLoginValidationUrl(String snsType);
	
	public boolean updateUserOrderHistoryForAfterService(String userId);
}
