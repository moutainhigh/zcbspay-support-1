package com.zcbspay.platform.support.order.query.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectDetaDO;

public interface OrderCollectDetaDAO extends BaseDAO<OrderCollectDetaDO> {

	/**
	 * 保存代收订单明细
	 * @param orderCollectDetaDO
	 */
	public void saveCollectOrderDeta(OrderCollectDetaDO orderCollectDetaDO);
	
	
	public List<OrderCollectDetaDO> getDetaListByBatchtid(Long batchId);

	/**
	 * 统计批次成功失败处理中笔数和金额
	 * @param batchId
	 * @return
	 */
	public List<Map<String, Object>> sumCollectDeta(long batchId);
}
