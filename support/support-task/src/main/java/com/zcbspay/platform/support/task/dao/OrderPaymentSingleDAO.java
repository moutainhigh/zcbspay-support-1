package com.zcbspay.platform.support.task.dao;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentSingleDO;

public interface OrderPaymentSingleDAO extends BaseDAO<OrderPaymentSingleDO> {

	
	/**
	 * 通过txnseqno获取代付订单信息
	 * @param txnseqno
	 * @return
	 */
	public OrderPaymentSingleDO getOrderinfoByTxnseqno(String txnseqno);
	
	
}
