package com.zcbspay.platform.support.order.query.dao;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectSingleDO;

public interface OrderCollectSingleDAO extends BaseDAO<OrderCollectSingleDO>{

	
	/**
	 * 通过tn获取代收订单信息
	 * @param tn
	 * @return
	 */
	public OrderCollectSingleDO getOrderinfoByTn(String tn);
	
	
}
