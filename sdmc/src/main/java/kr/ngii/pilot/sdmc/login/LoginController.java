package kr.ngii.pilot.sdmc.login;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ngii.pilot.sdmc.login.service.LoginService;
import kr.ngii.pilot.sdmc.main.service.MainService;
import kr.ngii.pilot.sdmc.main.service.vo.LoggerVO;
import kr.ngii.pilot.sdmc.util.StringUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	@Autowired
	LoginService loginService = null; 
	

	@Autowired
	MainService mainService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private int inteval = 30 * 60;			// 세션 만료될 시간 (30분)
	
	/**
	 * indexPage
	 * 사이트 초기 진입 시 지정된 페이지로 포워딩한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/login.ngii", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Locale locale, Model model, String social, String email, String name)
	throws UnsupportedEncodingException
	{
		logger.info("Welcome smdc! The client locale is {}.", locale);
		
		// TODO - khj : 임시로 소셜 로그인을 막는다. 나중에 소셜 로그인 기능을 구현할거면 제대로 구현 된상태서 막은걸 풀어야 한다.
		//social = "";

		String url = loginService.makeLoginValidationUrl(social);
		
		if(StringUtil.isEmpty(email))
		{
			if(!url.substring(0, 4).equals("http"))
				model.addAttribute("error", "empty email");
		}
		else
		{
			model.addAttribute("email", URLDecoder.decode(email, "UTF-8"));
		}
			
		return "redirect:" + url;
	}
	
	/**
	 * loginCallback
	 * 사이트 초기 진입 시 지정된 페이지로 포워딩한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/loginCallback.ngii")
	public String loginCallback(HttpServletRequest request, Locale locale, Model model, String code, String social, String error, String name, String email)
	throws UnsupportedEncodingException
	{
		HttpSession session = request.getSession();
		String identifiedEmail = "";

		if(error == null)
		{
			model.addAttribute("login", "success");
			model.addAttribute("error", null );
			
			if(StringUtil.isEmpty(email))// SNS redirection case
			{
				// TODO - khj : 여기서 인증 받은 이메일 계정을 추출할 것.
				// identifiedEmail = something;
				identifiedEmail = loginService.getEmailAddr(social, code);
			}
			else	// basic login
			{
				identifiedEmail = URLEncoder.encode(email, "UTF-8");
			}
			
			if(!StringUtil.isEmpty(identifiedEmail)){
				session.setAttribute("userEmail", identifiedEmail);
				
				LoggerVO log = new LoggerVO();
				log.setLogKind("L");	// "L" : 로그인,  "D" : 다운로드, "O" : 주문, "Q" : 로그아웃
				log.setLogSummary(identifiedEmail + " login");
				log.setLogUser(identifiedEmail);
				mainService.setLogItem(log);
				
				// 세션 타임아웃 설정
				session.setMaxInactiveInterval(30 * 60);
			}
			
			loginService.updateUserOrderHistoryForAfterService(identifiedEmail);
		}
		else
		{
			model.addAttribute("login", "failure" );
			model.addAttribute("error", error );
		}
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		session.setMaxInactiveInterval(this.inteval);

		return "redirect:/index.ngii";
	}
	
	/**
	 * viewPage
	 * 권한이 있는 사용자에게 해당 페이지로 포워드해준다.
	 * @param nextSubPage 전체 페이지 프레임 안에서 content 영역만을 리프레시할 수 있도록 해당 부분의 페이지만 호출하는 경로를 포워딩해준다.
	 * @param nextPageName 부분 페이지가 아닌 전체 페이지를 로드하는 경우 이 항목에 값을 전달하여 해당 페이지로 포워드해준다.
	 * @return 목적에 맞는 해당 페이지 경로
	 */
	@RequestMapping(value = "/logout.ngii")
	public String logout(HttpServletRequest request, Locale locale, Model model) {
		logger.debug("Welcome sdmc Main! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		HttpSession session = request.getSession();
		
		LoggerVO log = new LoggerVO();
		log.setLogKind("Q");	// "L" : 로그인,  "D" : 다운로드, "O" : 주문, "Q" : 로그아웃
		log.setLogSummary(session.getAttribute("userEmail") + " logout");
		log.setLogUser((String)session.getAttribute("userEmail"));
		mainService.setLogItem(log);
		
		session.removeAttribute("userEmail");

		if(session != null){
			session.invalidate();
		}
		
		session = null;
		System.gc();
		
		
		
		model.addAttribute("serverTime", formattedDate );
		
		return "redirect:/index.ngii";
	}
	
}
