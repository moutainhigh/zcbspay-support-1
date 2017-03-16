package com.zcbspay.platform.support.order.query.dao;

import java.util.List;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.order.query.pojo.OrderPaymentDetaDO;

public interface OrderPaymentDetaDAO extends BaseDAO<OrderPaymentDetaDO> {

	/**
	 * 保存代付订单明细
	 * @param orderPaymentDeta
	 */
	public void savePaymentDetaOrder(OrderPaymentDetaDO orderPaymentDeta);
	
	/**
	 * 
	 * @param batchId
	 * @return
	 */
	public List<OrderPaymentDetaDO> getDetaListByBatchtid(Long batchId);
}
