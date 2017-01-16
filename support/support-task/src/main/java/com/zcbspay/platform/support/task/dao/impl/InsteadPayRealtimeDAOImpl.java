package com.zcbspay.platform.support.task.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.InsteadPayRealtimeDAO;
import com.zcbspay.platform.support.task.pojo.PojoInsteadPayRealtime;

@Repository("insteadPayRealtimeDAO")
public class InsteadPayRealtimeDAOImpl extends HibernateBaseDAOImpl<PojoInsteadPayRealtime>
		implements InsteadPayRealtimeDAO {

	

	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayRealtime getInsteadPayByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoInsteadPayRealtime.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoInsteadPayRealtime) criteria.uniqueResult();
	}

}
