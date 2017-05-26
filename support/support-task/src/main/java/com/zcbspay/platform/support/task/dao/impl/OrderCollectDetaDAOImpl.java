package com.zcbspay.platform.support.task.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.OrderCollectDetaDAO;
import com.zcbspay.platform.support.task.pojo.OrderCollectDetaDO;

@Repository
public class OrderCollectDetaDAOImpl extends HibernateBaseDAOImpl<OrderCollectDetaDO> implements
		OrderCollectDetaDAO {

	private static final Logger logger = LoggerFactory.getLogger(OrderCollectDetaDAOImpl.class);

	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> getCollectResult(long batchId) {
		String sql = "select t.status,count(*) counts,sum(t.amt) totalAmt from t_order_collect_deta t where t.batchtid=? group by status order by status asc";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, batchId);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	@Transactional(readOnly=true)
	public List<OrderCollectDetaDO> getCollectList(long batchId) {
		Criteria criteria = getSession().createCriteria(OrderCollectDetaDO.class);
		criteria.add(Restrictions.eq("batchtid", batchId));
		return criteria.list();
	}
	
}
