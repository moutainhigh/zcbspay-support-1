package com.zcbspay.platform.support.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.OrderCollectBatchDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectBatchDO;

@Repository
public class OrderCollectBatchDAOImpl extends HibernateBaseDAOImpl<OrderCollectBatchDO> implements
		OrderCollectBatchDAO {

	
	@Override
	@Transactional(readOnly=true)
	public OrderCollectBatchDO getCollectBatchOrderByTn(String tn) {
		Criteria criteria = getSession().createCriteria(OrderCollectBatchDO.class);
		criteria.add(Restrictions.eq("tn", tn));
		OrderCollectBatchDO uniqueResult = (OrderCollectBatchDO) criteria.uniqueResult();
		return uniqueResult;
	}

	
	
}
