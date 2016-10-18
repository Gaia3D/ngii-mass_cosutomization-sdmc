package kr.ngii.pilot.sdmc.main;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ngii.pilot.sdmc.main.service.MainService;
import kr.ngii.pilot.sdmc.main.service.vo.DoyeupVO;
import kr.ngii.pilot.sdmc.main.service.vo.FormatVO;
import kr.ngii.pilot.sdmc.main.service.vo.LayerVO;
import kr.ngii.pilot.sdmc.main.service.vo.NoticeVO;
import kr.ngii.pilot.sdmc.main.service.vo.SrsVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	MainService mainService;
	
	/**
	 * indexPage
	 * 사이트 초기 진입 시 지정된 페이지로 포워딩한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/index.ngii", method = RequestMethod.GET)
	public String indexPage(Locale locale, Model model) {
		logger.info("Welcome smdc! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//return "main/index";	// 소셜로그인 차단으로 다른 페이지로 대체
		return "main/index_id";
	}
	
	/**
	 * viewPage
	 * 권한이 있는 사용자에게 해당 페이지로 포워드해준다.
	 * @param nextSubPage 전체 페이지 프레임 안에서 content 영역만을 리프레시할 수 있도록 해당 부분의 페이지만 호출하는 경로를 포워딩해준다.
	 * @param nextPageName 부분 페이지가 아닌 전체 페이지를 로드하는 경우 이 항목에 값을 전달하여 해당 페이지로 포워드해준다.
	 * @return 목적에 맞는 해당 페이지 경로
	 */
	@RequestMapping(value = "/viewPage.ngii")
	public String viewPage(Locale locale, Model model, String nextSubPageName, String nextPageName) {
		logger.debug("Welcome sdmc Main! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		

		if(nextPageName == null){
			model.addAttribute("nextSubPageName", nextSubPageName );
			nextPageName = "main/main";
		}
		
		model.addAttribute("serverTime", formattedDate );
		
		// TODO right now 0 : extract input parameters from client request
		//dataProcessService.processRequest(input);
		
		return nextPageName;
	}


	/**
	 * getLayerList
	 * 레이어들의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getLayerList.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getLayerList(Locale locale, Model model, LayerVO layerVO) {
		
		logger.debug("getLayerList.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<LayerVO> layerList = mainService.getLayerList(layerVO);
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return layerList;
	}

	/**
	 * getFormatList
	 * 결과물 파일 형식의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getFormatList.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getFormatList(Locale locale, Model model) {
		
		logger.debug("getFormatList.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<FormatVO> layerList = mainService.getFormatList();
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return layerList;
	}
	
	/**
	 * getSrsList
	 * 좌표계의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getSrsList.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getSrsList(Locale locale, Model model) {
		
		logger.debug("getSrsList.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<SrsVO> layerList = mainService.getSrsList();
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return layerList;
	}

	/**
	 * getDoyeupInfo
	 * 레이어들의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getDoyeupList.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getDoyeupInfo(Locale locale, Model model, String dyCd) {
		
		logger.debug("getDoyeupInfo.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<DoyeupVO> layerList = mainService.getDoyeupList(dyCd);
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return layerList;
	}
	
	/**
	 * getNoticeItem
	 * 레이어들의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getNoticeItem.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getNoticeItem(Locale locale, Model model, String userId) {
		
		logger.debug("getNoticeItem.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<NoticeVO> noticeList = mainService.getNoticeList(userId);
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return noticeList;
	}
	
}
