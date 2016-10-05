package kr.ngii.pilot.sdmc.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.daum.blog.connect.DaumBlogConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.kakao.connect.KakaoConnectionFactory;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import kr.ngii.pilot.sdmc.core.properties.BasicProperties;
import kr.ngii.pilot.sdmc.login.service.LoginService;
import kr.ngii.pilot.sdmc.login.service.dao.LoginDao;
import kr.ngii.pilot.sdmc.login.service.vo.AreaVO;
import kr.ngii.pilot.sdmc.util.StringUtil;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
	@Autowired
	private FacebookConnectionFactory facebookConnectionFactory;
	@Autowired
	private OAuth2Parameters facebookOAuth2Parameters;
	
	@Autowired
	private KakaoConnectionFactory kakaoConnectionFactory;
	@Autowired
	private OAuth2Parameters kakaoOAuth2Parameters;
	
	@Autowired
	private DaumBlogConnectionFactory daumConnectionFactory;
	@Autowired
	private OAuth1Parameters daumOAuth1Parameters;

	@Autowired
	private LoginDao loginDao;
	
	@Override
	public String makeLoginValidationUrl(String snsType) {

		if(StringUtil.isEmpty(snsType))
		{
			return "/loginCallback.ngii";
		}
		else
		{
			switch(snsType)
			{
			case "google":
			{
				OAuth2Operations oauthOperations = null;

				oauthOperations = googleConnectionFactory.getOAuthOperations();
				
				return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
			}
			case "facebook":
			{
				OAuth2Operations oauthOperations = null;
				
				oauthOperations = facebookConnectionFactory.getOAuthOperations();

				//facebookOAuth2Parameters.setScope("email");
				return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, facebookOAuth2Parameters);
			}
			case "daum":
			{
				OAuth1Operations oauthOperations = null;
				
				oauthOperations = daumConnectionFactory.getOAuthOperations();

				daumOAuth1Parameters.set("scope", "email");
				daumOAuth1Parameters.set("callbackUri", BasicProperties.THIS_URL);
				
				return oauthOperations.buildAuthorizeUrl("", daumOAuth1Parameters);
			}
			case "kakao":
			{
				OAuth2Operations oauthOperations = null;
				
				oauthOperations = kakaoConnectionFactory.getOAuthOperations();

				kakaoOAuth2Parameters.setScope("email");
				return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, kakaoOAuth2Parameters);
			}
			default:
				return "/loginCallback.ngii";
			}
		}
	}

	@Override
	public String getEmailAddr(String snsType, String code) {
		
		if(StringUtil.isEmpty(snsType))
		{
			return null;
		}
		else
		{
			switch(snsType)
			{
			case "google":
			{
				OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
				AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(), null);
				String accessToken = accessGrant.getAccessToken();
				Long expireTime =  accessGrant.getExpireTime();
				if (expireTime != null && expireTime < System.currentTimeMillis()) {
					accessToken = accessGrant.getRefreshToken();
					//logger.info("accessToken is expired. refresh token = {}" , accessToken);
				}
						
				Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
				Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
						
				PlusOperations plusOperations = google.plusOperations();
				Person person = plusOperations.getGoogleProfile();
				
				return person.getAccountEmail();
			}
			case "facebook":
			{
				OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
				AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, facebookOAuth2Parameters.getRedirectUri(), null);
				String accessToken = accessGrant.getAccessToken();
				Long expireTime =  accessGrant.getExpireTime();
				if (expireTime != null && expireTime < System.currentTimeMillis()) {
					accessToken = accessGrant.getRefreshToken();
					//logger.info("accessToken is expired. refresh token = {}" , accessToken);
				}
						
				Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
				Facebook facebook = connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
						
				UserOperations userOperations = facebook.userOperations();
				FacebookProfile profile = userOperations.getUserProfile();

				//restOperations.get
				System.out.println(profile.getId());
				System.out.println(profile.getName());
				System.out.println(profile.getEmail());
				System.out.println(profile.getGender());
				
				return profile.getEmail();
			}
			case "daum":
			{
				return null;
			}
			case "kakao":
			{
				return null;
			}
			default:
				return null;
			}
		}
	}
	
	@Override
	public boolean updateUserOrderHistoryForAfterService(String userId) {
		// TODO Auto-generated method stub
		
		AreaVO areaVO = new AreaVO();
		areaVO.setUserId(userId);
		List<AreaVO> list = loginDao.selectAreaList(areaVO);
		//ArrayList<String> updateList = new ArrayList<String>();
		
		for (AreaVO areaItemVO : list) {
			
			//String orderId = areaItemVO.getOrderId();
			
			if(	false
				||"ort_orientmap_as".equals(areaItemVO.getLayerId())	// 정사영상
				||"scp".equals(areaItemVO.getLayerId())					// 츨량기준점
				||"dem".equals(areaItemVO.getLayerId())					// dem
				||"cbnd_201511".equals(areaItemVO.getLayerId())			// 연속지적도
				||"tn_npoibass".equals(areaItemVO.getLayerId())			// poi정보
				){
				continue;
			}
			
			//String tName = areaVO.getSchemaName() + "." + areaVO.getLayerId();
			
						
			int cnt = 0;
			System.out.println(areaItemVO.getBjcds());
			cnt = Integer.parseInt(loginDao.selectIntersectCount(areaItemVO));
			
			if(cnt > 0){
				System.out.println("업데이트 대상 주문번호 : " + areaItemVO.getOrderId() + "(" + cnt + "건)");
				//System.out.println(areaItemVO.getLayerId());
				//System.out.println(areaItemVO.getOrderVersion());
				//System.out.println(areaItemVO.getBjcdGeoms());
				loginDao.updateUpdateStatus(areaItemVO);
				//break;
			}
			
			
		}
		
		return false;
	}
}
