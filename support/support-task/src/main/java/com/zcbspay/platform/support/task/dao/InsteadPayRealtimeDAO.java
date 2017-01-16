package com.zcbspay.platform.support.task.dao;

import com.zcbspay.platform.support.common.dao.BaseDAO;
import com.zcbspay.platform.support.task.pojo.PojoInsteadPayRealtime;

/**
  * @ClassName: InsteadPayRealtimeDAO
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午7:43:35
  *
  */
public interface InsteadPayRealtimeDAO extends BaseDAO<PojoInsteadPayRealtime>{

	/**
	 * 
	  * getInsteadPayByTxnseqno
	  * @Title: getInsteadPayByTxnseqno
	  * @Description: TODO 通过交易序列号获取代付订单信息
	  * @param txnseqno 交易序列号
	  * @return PojoInsteadPayRealtime    实时代付订单pojo
	  * @throws
	 */
	public PojoInsteadPayRealtime getInsteadPayByTxnseqno(String txnseqno);
}
