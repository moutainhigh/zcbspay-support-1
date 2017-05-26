package com.zcbspay.platform.support.task.dao;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectSingleDO;

public interface OrderCollectSingleDAO extends BaseDAO<OrderCollectSingleDO>{

	
	/**
	 * 通过tn获取代收订单信息
	 * @param txnseqno
	 * @return
	 */
	public OrderCollectSingleDO getOrderinfoByTxnseqno(String txnseqno);
	
	
}
