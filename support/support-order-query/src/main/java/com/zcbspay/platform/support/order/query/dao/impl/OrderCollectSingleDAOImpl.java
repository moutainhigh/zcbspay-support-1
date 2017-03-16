package com.zcbspay.platform.support.order.query.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.order.query.dao.OrderCollectSingleDAO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectSingleDO;

@Repository
public class OrderCollectSingleDAOImpl extends HibernateBaseDAOImpl<OrderCollectSingleDO> implements OrderCollectSingleDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public OrderCollectSingleDO getOrderinfoByTn(String tn) {
		Criteria criteria = getSession().createCriteria(OrderCollectSingleDO.class);
		criteria.add(Restrictions.eq("tn", tn));
		OrderCollectSingleDO uniqueResult = (OrderCollectSingleDO) criteria.uniqueResult();
		return uniqueResult;
	}


}
