package com.zcbspay.platform.support.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.OrderCollectSingleDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectSingleDO;

@Repository
public class OrderCollectSingleDAOImpl extends HibernateBaseDAOImpl<OrderCollectSingleDO> implements OrderCollectSingleDAO {

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public OrderCollectSingleDO getOrderinfoByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(OrderCollectSingleDO.class);
		criteria.add(Restrictions.eq("relatetradetxn", txnseqno));
		OrderCollectSingleDO uniqueResult = (OrderCollectSingleDO) criteria.uniqueResult();
		return uniqueResult;
	}

	

	

}
