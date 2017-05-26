package com.zcbspay.platform.support.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.OrderPaymentSingleDAO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentSingleDO;
@Repository
public class OrderPaymentSingleDAOImpl extends HibernateBaseDAOImpl<OrderPaymentSingleDO>
		implements OrderPaymentSingleDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public OrderPaymentSingleDO getOrderinfoByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(OrderPaymentSingleDO.class);
		criteria.add(Restrictions.eq("txnseno", txnseqno));
		OrderPaymentSingleDO uniqueResult = (OrderPaymentSingleDO) criteria.uniqueResult();
		return uniqueResult;
	}

	
	
	
	

}
