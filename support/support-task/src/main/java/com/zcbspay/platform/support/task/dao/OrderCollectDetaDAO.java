package com.zcbspay.platform.support.task.dao;

import java.util.List;
import java.util.Map;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectDetaDO;

public interface OrderCollectDetaDAO extends BaseDAO<OrderCollectDetaDO> {

	public List<Map<String, Object>> getCollectResult(long batchId);
	public List<OrderCollectDetaDO> getCollectList(long batchId);
}
