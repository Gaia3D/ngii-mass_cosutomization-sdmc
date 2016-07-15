package kr.ngii.pilot.sdmc.tx.order.service.dao;

import java.util.List;

import kr.ngii.pilot.sdmc.core.annotation.DsApp;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OrderVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.OutputInfoVO;
import kr.ngii.pilot.sdmc.tx.order.service.vo.ProductVO;

@DsApp
public interface OrderDao {

	public void insertOrderItem(OrderVO orderVO);

	public void insertProductItem(ProductVO productVO);

	public List<OrderVO> selectOrderList(OrderVO orderVO);

	public OutputInfoVO getOutputInfo(String orderId);

	public void updateUpdateStatus(OrderVO orderVO);

}
