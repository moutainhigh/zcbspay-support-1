package com.zcbspay.platform.support.task.dao;

import java.util.List;
import java.util.Map;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentDetaDO;

public interface OrderPaymentDetaDAO extends BaseDAO<OrderPaymentDetaDO> {

	public List<Map<String, Object>> getCollectResult(long batchId);
	
	public List<OrderPaymentDetaDO> getPaymentList(long batchId);
}
