/* 
 * QueryService.java  
 * 
 * version TODO
 *
 * 2016年10月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.order.query.query.service;

import com.zcbspay.platform.support.order.query.exception.QueryOrderException;
import com.zcbspay.platform.support.order.query.query.bean.BatchResultBean;
import com.zcbspay.platform.support.order.query.query.bean.OrderResultBean;

/**
 * 交易订单查询接口
 *
 * @author guojia
 * @version
 * @date 2016年10月17日 上午10:03:21
 * @since 
 */
public interface QueryOrderService {

	/**
	 * 交易订单查询方法
	 * @param merchNo 商户号
	 * @param orderId 订单号
	 * @return 订单结果bean
	 */
	public OrderResultBean queryOrder(String merchNo,String orderId) throws QueryOrderException; 
	
	/**
	 *  交易订单查询方法
	 * @param tn 受理订单号
	 * @return
	 */
	public OrderResultBean queryOrderByTN(String tn) throws QueryOrderException ;
	
	/**
	 * 
	 * @param merchNo
	 * @param orderId
	 * @return
	 * @throws QueryOrderException
	 */
	public OrderResultBean queryInsteadPayOrder(String merchNo,String orderId)  throws QueryOrderException;
	
	/**
	 * 查询实时集中代收订单
	 * @param tn
	 * @return
	 * @throws QueryOrderException 
	 */
	public OrderResultBean queryConcentrateCollectionOrder(String tn) throws QueryOrderException;
	
	/**
	 * 查询实时集中代付订单 
	 * @param tn
	 * @return
	 * @throws QueryOrderException 
	 */
	public OrderResultBean queryConcentratePaymentOrder(String tn) throws QueryOrderException;
	/**
	 * 查询集中代收批次信息
	 * @param merchNo 商户号
	 * @param batchNo 批次号
	 * @param txnDate 交易日期
	 * @return
	 */
	public BatchResultBean queryConcentrateCollectionBatch(String merchNo,String batchNo,String txnDate);
	
	/**
	 * 查询集中代付批次信息
	 * @param merchNo 商户号
	 * @param batchNo 批次号
	 * @param txnDate 交易日期
	 * @return
	 */
	public BatchResultBean queryConcentratePaymentBatch(String merchNo,String batchNo,String txnDate);
}
