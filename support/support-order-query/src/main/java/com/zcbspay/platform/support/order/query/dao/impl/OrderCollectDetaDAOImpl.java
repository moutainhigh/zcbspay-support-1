package com.zcbspay.platform.support.order.query.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.order.query.dao.OrderCollectDetaDAO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectDetaDO;

@Repository
public class OrderCollectDetaDAOImpl extends HibernateBaseDAOImpl<OrderCollectDetaDO> implements
		OrderCollectDetaDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveCollectOrderDeta(OrderCollectDetaDO orderCollectDetaDO) {
		// TODO Auto-generated method stub
		saveEntity(orderCollectDetaDO);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public List<OrderCollectDetaDO> getDetaListByBatchtid(Long batchId){
		Criteria criteria = getSession().createCriteria(OrderCollectDetaDO.class);
		criteria.add(Restrictions.eq("batchtid", batchId));
		return criteria.list();
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public List<Map<String, Object>> sumCollectDeta(long batchId) {
		String sql = "select status,count(1) as total,sum(amt) as amount from T_ORDER_COLLECT_DETA t where t.batchtid=? group by status;";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, batchId);
		List<Map<String, Object>> sumMapList = query.list();
		return sumMapList;
	}

}
