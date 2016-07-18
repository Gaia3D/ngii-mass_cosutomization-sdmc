package kr.ngii.pilot.sdmc.main;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ngii.pilot.sdmc.main.service.MainService;
import kr.ngii.pilot.sdmc.main.service.vo.LoggerVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoggerController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	MainService mainService;
	
	/**
	 * log 생성
	 * 로그 생성 테스트를 위한 컨트롤러. 실제 사용하지 않는다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/writeLog.ngii", method = RequestMethod.GET)
	public String indexPage(Locale locale, Model model) {
		//logger.info("Welcome smdc! The client locale is {}.", locale);

		LoggerVO log = new LoggerVO();
		log.setLogKind("L");	// "L" : 로그인,  "D" : 다운로드, "O" : 주문, "Q" : 로그아웃
		log.setLogSummary("로그테스트");
		log.setLogUser("hsson@gaia3d.com");
		
		mainService.setLogItem(log);
		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "main/index";
	}
	

}
