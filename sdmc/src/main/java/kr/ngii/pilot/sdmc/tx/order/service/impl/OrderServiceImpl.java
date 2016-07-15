package kr.ngii.pilot.sdmc.tx.order.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ngii.pilot.sdmc.core.properties.BasicProperties;
import kr.ngii.pilot.sdmc.tx.order.service.OrderService;
import kr.ngii.pilot.sdmc.tx.order.service.dao.OrderDao;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OrderVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OutputInfoVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.ProductVO;
import kr.ngii.pilot.sdmc.util.StringUtil;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public String putOrderItem(OrderVO orderVO) throws Exception {
		logger.debug("getLayerList");
		
		//재요청인 경우 orderId가 실려왔을 것이며 이 때에는 기존 주문 id의 상태를 2로 바꾸어야 한다.
		if(!StringUtil.isEmpty(orderVO.getOrderId())){
			orderDao.updateUpdateStatus(orderVO);
		}
		
		
		orderDao.insertOrderItem(orderVO);
		if(StringUtil.isEmpty(orderVO.getOrderId()))
		{
			// TODO - khj : 여기서 transaction 처리를 해주든지 개별 예외처리를 해주든지 어느 것이라도 해야 한다.
			return BasicProperties.ORDER_RECEPTION_FAILURE;
		}
		
		String[] layerList = orderVO.getOrderedLayerCds();
		String[] areaCodeList = orderVO.getAreaCodeArray();
		//String orderId = orderVO.getOrderId();

		for (String layer : layerList) {
			for (String areaCode : areaCodeList) {
				
				ProductVO productVO = new ProductVO();
				productVO.setOrderId(orderVO.getOrderId());
				//this.setOrderProductId(orderVO.getOrderType());
				//this.setMapNum();
				//this.setMapName();
				//this.setShape();
				productVO.setSrs(orderVO.getSrs());
				productVO.setFormat(orderVO.getFormat());
				productVO.setRegDt(orderVO.getRegDt());
				productVO.setRegUserId(orderVO.getRegUserId());
				
				if("0".equals(orderVO.getOrderType())){
					productVO.setBjcd(areaCode);
				} else if("1".equals(orderVO.getOrderType())){
					productVO.setMapNum(areaCode);
				} else {
					throw new Exception("공간데이터 자료형이 잘못되었습니다. (0:행정구역, 1:도엽");
				}
				
				productVO.setLayerId(layer);

				//productVO.getOrderId();
				
				orderDao.insertProductItem(productVO);
			}
		}

		// khj : 여기서 REST API로 data processing을 trigger 한다.
		String stringApiUrl = BasicProperties.DATA_PROCESSING_API + "?orderId=" + orderVO.getOrderId();
		URL apiUrl = null;
		int responseStatus = -1;
		String response;
		HttpURLConnection connection = null;
		try
		{
			apiUrl = new URL(stringApiUrl);
			connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			responseStatus = connection.getResponseCode();
			response = connection.getResponseMessage();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return BasicProperties.ORDER_PROCESSING_FAILURE + " : wrong formatted API URL";
		} catch (IOException e) {
			e.printStackTrace();
			return BasicProperties.ORDER_PROCESSING_FAILURE + " : unreachable API URL";
		}
		
		if(responseStatus != 200)
			return BasicProperties.ORDER_PROCESSING_FAILURE + " : " + response;
		else
			return BasicProperties.ORDER_HANDLING_SUCCESS;
	}

	
	@Override
	public List<OrderVO> getOrderList(OrderVO orderVO) {
		logger.debug("getOrderList");
		// TODO Auto-generated method stub
		
		List<OrderVO> list = orderDao.selectOrderList(orderVO);
		
		return list;
	}

	@Override
	public OutputInfoVO getOutputInfo(String orderId) {
		
		OutputInfoVO result = orderDao.getOutputInfo(orderId);
		return result;
	}

}
