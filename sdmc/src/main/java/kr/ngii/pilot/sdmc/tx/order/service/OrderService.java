package kr.ngii.pilot.sdmc.tx.order.service;

import java.util.List;

import kr.ngii.pilot.sdmc.tx.order.service.vo.OrderVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OutputInfoVO;


public interface OrderService {

	public String putOrderItem(OrderVO orderVO) throws Exception;

	public List<OrderVO> getOrderList(OrderVO orderVO);
	
	public OutputInfoVO getOutputInfo(String orderId);

}
