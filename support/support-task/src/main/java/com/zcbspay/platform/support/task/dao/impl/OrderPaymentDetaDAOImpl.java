package com.zcbspay.platform.support.task.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.support.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.support.task.dao.OrderPaymentDetaDAO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentDetaDO;
@Repository
public class OrderPaymentDetaDAOImpl extends HibernateBaseDAOImpl<OrderPaymentDetaDO> implements
		OrderPaymentDetaDAO {

	private static final Logger logger = LoggerFactory.getLogger(OrderPaymentDetaDAOImpl.class);
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> getCollectResult(long batchId) {
		String sql = "select t.status,count(*) counts,sum(t.amt) totalAmt from t_order_payment_deta t where t.batchtid=? group by status order by status asc";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, batchId);
		return (List<Map<String, Object>>) query.list();
	}
	@Transactional(readOnly=true)
	public List<OrderPaymentDetaDO> getPaymentList(long batchId){
		Criteria criteria = getSession().createCriteria(OrderPaymentDetaDO.class);
		criteria.add(Restrictions.eq("batchtid", batchId));
		return criteria.list();
	}
}
