package com.zcbspay.platform.support.task.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.TxnsNotifyTaskDAO;
import com.zcbspay.platform.support.task.pojo.PojoTxnsNotifyTask;
@Repository("txnsNotifyTaskDAO")
public class TxnsNotifyTaskDAOImpl extends HibernateBaseDAOImpl<PojoTxnsNotifyTask> implements
		TxnsNotifyTaskDAO {


	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveTask(PojoTxnsNotifyTask task) {
		// TODO Auto-generated method stub
		saveEntity(task);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTask(PojoTxnsNotifyTask task) {
		// TODO Auto-generated method stub
		merge(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<PojoTxnsNotifyTask> findTaskByTxnseqno(String txnseqno,String memberId) {
		Criteria criteria = getSession().createCriteria(PojoTxnsNotifyTask.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		criteria.add(Restrictions.eq("memberId", memberId));
		return criteria.list();
	}

	@Override
	@Transactional(readOnly=true)
	public PojoTxnsNotifyTask getAsyncNotifyTask(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsNotifyTask.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoTxnsNotifyTask) criteria.uniqueResult();
	}

}
