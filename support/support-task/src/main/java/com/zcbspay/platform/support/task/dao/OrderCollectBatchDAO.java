package com.zcbspay.platform.support.task.dao;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectBatchDO;

public interface OrderCollectBatchDAO extends BaseDAO<OrderCollectBatchDO> {

	
	
	/**
	 * 通过tn获取代收批次数据
	 * @param tn
	 * @return
	 */
	public OrderCollectBatchDO getCollectBatchOrderByTn(String tn);
	
	
}
