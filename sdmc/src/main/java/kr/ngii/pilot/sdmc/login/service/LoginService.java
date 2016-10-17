package kr.ngii.pilot.sdmc.login.service;

public interface LoginService {
	
	public String makeLoginValidationUrl(String snsType);
	
	public boolean updateUserOrderHistoryForAfterService(String userId);

	public String getEmailAddr(String code, String snsType);

	public boolean checkSignin(String id, String password);

	public void registUser(String id, String name, String password, String telNo);
}
