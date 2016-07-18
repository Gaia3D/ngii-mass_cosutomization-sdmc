package kr.ngii.pilot.sdmc.tx.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ngii.pilot.sdmc.main.service.MainService;
import kr.ngii.pilot.sdmc.main.service.vo.LoggerVO;
import kr.ngii.pilot.sdmc.tx.order.service.OrderService;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OrderVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OutputInfoVO;
import kr.ngii.pilot.sdmc.util.StringUtil;


/**
 * Handles requests for the application home page.
 */
@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;

	@Autowired
	MainService mainService;
	
	/**
	 * selectOption
	 * 주문 관련 페이지로 포워딩한다.
	 * @param none
	 * @return 주문 페이지 경로
	 * @deprecated
	 */
	@Deprecated
	@RequestMapping(value = "/selectOption.ngii", method = RequestMethod.GET)
	public String selectOption(Locale locale, Model model) {
		logger.info("Welcome order! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "order/selectOption";
	}
	
	/**
	 * selectKind
	 * 주문 관련 페이지로 포워딩한다.
	 * viewPage 로직 생성으로 인하여 사용하지 않음
	 * @param none
	 * @return 주문 페이지 경로
	 */
	@RequestMapping(value = "/putOrderItem.ngii", method = RequestMethod.POST)
	public String putOrderItem(HttpServletRequest request, Locale locale, Model model, OrderVO orderVO) {
		logger.debug("Welcome order! The client locale is {}.", locale);
		
		String email = (String) request.getSession().getAttribute("userEmail");
		
		if(StringUtil.isEmpty(email)){
			return "main/myPage";
		} else {


			
			//orderVO.setOrderDate(orderVO.getOrderDate());
			String strTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			if("1".equals(orderVO.getUpdateStatus())){
				orderVO.setOrderVersion(strTime);
			}
			orderVO.setOrderDate(strTime);
			orderVO.setUserId(email);
			orderVO.setRegUserId(email);
			
			String result = null;
			
			try {
				result = orderService.putOrderItem(orderVO);
			} catch (Exception e) {
				
				
				LoggerVO log = new LoggerVO();
				log.setLogKind("O");	// "L" : 로그인,  "D" : 다운로드, "O" : 주문, "Q" : 로그아웃
				log.setLogSummary(email + " ordered : " + orderVO.getOrderId());
				log.setLogUser(email);
				mainService.setLogItem(log);
				
				logger.debug("orderId (" + orderVO.getOrderId() + ") : error ");
				e.printStackTrace();
			}
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			
			String formattedDate = dateFormat.format(date);
			
			model.addAttribute("serverTime", formattedDate );
			model.addAttribute("result", result );
			
			return "main/myPage";
		}
	}

	
	/**
	 * getLayerList
	 * 레이어들의 목록을 반환한다.
	 * @param none
	 * @return indexPage 경로
	 */
	@RequestMapping(value = "/getOrderList.ngii", method = RequestMethod.GET)
	public @ResponseBody Object getOrderList(HttpServletRequest request, Locale locale, Model model, String email, String orderId) {
		
		logger.debug("getOrderList.controller");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		//System.out.println((String)request.getSession().getAttribute("userEmail"));
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderId(orderId);
		orderVO.setUserId((String)request.getSession().getAttribute("userEmail"));
		
		List<OrderVO> orderList = orderService.getOrderList(orderVO);
		
		model.addAttribute("serverTime", formattedDate );
		//model.addAttribute("layerList", layerList );
		
		return orderList;
	}

	@RequestMapping(value = "/download.ngii")
	public @ResponseBody void downloadData(HttpServletRequest request, HttpServletResponse response, String orderId) throws IOException
	{
		OutputInfoVO outputInfo = orderService.getOutputInfo(orderId);
		String filePath = outputInfo.getOutputInternalPath();
		String externalName = outputInfo.getOutputExternalName();
		
		// TODO - khj : local에서 테스트 하기 위해 임시로 만든 파일 경로
		//filePath = "G:/temp/output/data.zip";
		
		String email = (String) request.getSession().getAttribute("userEmail");
		LoggerVO log = new LoggerVO();
		log.setLogKind("D");	// "L" : 로그인,  "D" : 다운로드, "O" : 주문, "Q" : 로그아웃
		log.setLogSummary(email + " downloaded : " + orderId);
		log.setLogUser(email);
		mainService.setLogItem(log);
		
		File downfile = new File(filePath);
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		 
		try {

			if(!downfile.exists())
			{
				throw new FileNotFoundException(filePath);
			}
			
			
		        outStream = response.getOutputStream();
		        inputStream = new FileInputStream(downfile);               
		 
		        //Setting Response Header
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition",                     
		                           "attachment;filename=\""+externalName+"\"");
		              
		        //Writing InputStream to OutputStream
		        byte[] outByte = new byte[4096];
		        while(inputStream.read(outByte, 0, 4096) != -1)
		        {
		          outStream.write(outByte, 0, 4096);
		        }
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			inputStream.close();
			outStream.flush();
			outStream.close();
			throw new IOException();
		} finally {
			inputStream.close();
			outStream.flush();
			outStream.close();
		}
	}
}
