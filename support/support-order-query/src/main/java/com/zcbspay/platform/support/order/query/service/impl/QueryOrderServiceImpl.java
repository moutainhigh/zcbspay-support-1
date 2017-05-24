/* 
 * QueryServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.order.query.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zcbspay.platform.support.order.query.dao.InsteadPayRealtimeDAO;
import com.zcbspay.platform.support.order.query.dao.OrderCollectBatchDAO;
import com.zcbspay.platform.support.order.query.dao.OrderCollectDetaDAO;
import com.zcbspay.platform.support.order.query.dao.OrderCollectSingleDAO;
import com.zcbspay.platform.support.order.query.dao.OrderPaymentBatchDAO;
import com.zcbspay.platform.support.order.query.dao.OrderPaymentDetaDAO;
import com.zcbspay.platform.support.order.query.dao.OrderPaymentSingleDAO;
import com.zcbspay.platform.support.order.query.dao.TxnsLogDAO;
import com.zcbspay.platform.support.order.query.dao.TxnsOrderinfoDAO;
import com.zcbspay.platform.support.order.query.enums.BusiTypeEnum;
import com.zcbspay.platform.support.order.query.enums.OrderStatusEnum;
import com.zcbspay.platform.support.order.query.enums.OrderType;
import com.zcbspay.platform.support.order.query.exception.QueryOrderException;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectBatchDO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectDetaDO;
import com.zcbspay.platform.support.order.query.pojo.OrderCollectSingleDO;
import com.zcbspay.platform.support.order.query.pojo.OrderPaymentBatchDO;
import com.zcbspay.platform.support.order.query.pojo.OrderPaymentDetaDO;
import com.zcbspay.platform.support.order.query.pojo.OrderPaymentSingleDO;
import com.zcbspay.platform.support.order.query.pojo.PojoInsteadPayRealtime;
import com.zcbspay.platform.support.order.query.pojo.PojoTxnsLog;
import com.zcbspay.platform.support.order.query.pojo.PojoTxnsOrderinfo;
import com.zcbspay.platform.support.order.query.query.bean.BatchResultBean;
import com.zcbspay.platform.support.order.query.query.bean.FileContentBean;
import com.zcbspay.platform.support.order.query.query.bean.OrderResultBean;
import com.zcbspay.platform.support.order.query.query.service.QueryOrderService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月17日 上午10:15:49
 * @since 
 */
@Service("queryOrderService")
public class QueryOrderServiceImpl implements QueryOrderService{

	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	@Autowired
	private OrderCollectSingleDAO orderCollectSingleDAO;
	@Autowired
	private OrderPaymentSingleDAO orderPaymentSingleDAO;
	@Autowired
	private OrderCollectBatchDAO orderCollectBatchDAO;
	@Autowired
	private OrderCollectDetaDAO orderCollectDetaDAO;
	@Autowired
	private OrderPaymentBatchDAO orderPaymentBatchDAO;
	@Autowired
	private OrderPaymentDetaDAO orderPaymentDetaDAO;
	/**
	 *
	 * @param merchNo
	 * @param orderId
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryOrder(String merchNo, String orderId) throws QueryOrderException {
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByOrderNoAndMerchNo(orderId, merchNo);
		if(orderinfo==null){
			throw new QueryOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getFirmemberno());
		order.setMerName(orderinfo.getFirmembername());
		order.setMerAbbr(orderinfo.getFirmembershortname());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getOrderamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getOrderdesc());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
	}
	/**
	 *
	 * @param merchNo
	 * @param orderId
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryInsteadPayOrder(String merchNo, String orderId) throws QueryOrderException {
		PojoInsteadPayRealtime orderinfo = insteadPayRealtimeDAO.getOrderinfoByOrderNoAndMerchNo(merchNo, orderId);//getOrderinfoByOrderNoAndMerchNo(orderId, merchNo);
		if(orderinfo==null){
			throw new QueryOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getTxnseqno());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getMerId());
		order.setMerName(orderinfo.getMerName());
		order.setMerAbbr(orderinfo.getMerNameAbbr());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getTransAmt()+"");
		order.setTxnTime(orderinfo.getOrderCommiTime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getNotes());
		order.setCurrencyCode(orderinfo.getCurrencyCode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
		
	}
	/**
	 *
	 * @param tn
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryOrderByTN(String tn) throws QueryOrderException {
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByTN(tn);
		if(orderinfo==null){
			throw new QueryOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getFirmemberno());
		order.setMerName(orderinfo.getFirmembername());
		order.setMerAbbr(orderinfo.getFirmembershortname());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getOrderamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getOrderdesc());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
	}
	@Override
	public OrderResultBean queryConcentrateCollectionOrder(String tn) throws QueryOrderException {
		OrderCollectSingleDO orderinfo = orderCollectSingleDAO.getOrderinfoByTn(tn);
		if(orderinfo==null){
			throw new QueryOrderException("PC004");
		}
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getMerid());
		order.setMerName(orderinfo.getMername());
		order.setMerAbbr(orderinfo.getMerabbr());
		order.setOrderId(orderinfo.getOrderid());
		order.setTxnAmt(orderinfo.getTxnamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getNotes());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		return order;
		
	}
	@Override
	public OrderResultBean queryConcentratePaymentOrder(String tn) throws QueryOrderException {
		OrderPaymentSingleDO orderinfo = orderPaymentSingleDAO.getOrderinfoByTn(tn);
		if(orderinfo==null){
			throw new QueryOrderException("PC029");
		}
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getMerid());
		order.setMerName(orderinfo.getMername());
		order.setMerAbbr(orderinfo.getMerabbr());
		order.setOrderId(orderinfo.getOrderid());
		order.setTxnAmt(orderinfo.getTxnamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getNotes());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		return order;
	}
	@Override
	public BatchResultBean queryConcentrateCollectionBatch(String merchNo,
			String batchNo, String txnDate) throws QueryOrderException{
		BatchResultBean resultBean = new BatchResultBean();
		OrderCollectBatchDO collectBatch = orderCollectBatchDAO.getCollectBatchOrder(merchNo, batchNo, txnDate);
		if(collectBatch==null){
			throw new QueryOrderException("PC029");
		}
		List<OrderCollectDetaDO> detaList = orderCollectDetaDAO.getDetaListByBatchtid(collectBatch.getTid());
		List<FileContentBean> fileContentList = Lists.newArrayList();
		long failedCount = 0L;
		long failedAmt = 0L;
		long successCount = 0L;
		long successAmt = 0L;
		long payingCount = 0L;
		long payingAmt = 0L;
		for(OrderCollectDetaDO collectDeta : detaList){
			FileContentBean bean = new FileContentBean();
			bean.setOrderId(collectDeta.getOrderid());
			bean.setCurrencyCode(collectDeta.getCurrencycode());
			bean.setAmt(collectDeta.getAmt());
			bean.setDebtorBank(collectDeta.getDebtorbank());
			bean.setDebtorAccount(collectDeta.getDebtoraccount());
			bean.setDebtorName(collectDeta.getDebtorname());
			bean.setDebtorConsign(collectDeta.getDebtorconsign());
			bean.setCreditorBank(collectDeta.getCreditorbank());
			bean.setCreditorAccount(collectDeta.getCreditoraccount());
			bean.setCreditorName(collectDeta.getCreditorname());
			bean.setProprietary(collectDeta.getProprietary());
			bean.setSummary(collectDeta.getSummary());
			OrderStatusEnum orderStatusEnum = OrderStatusEnum.fromValue(collectDeta.getStatus());
			if(orderStatusEnum==OrderStatusEnum.FAILED){
				failedCount++;
				failedAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("09");
			}else if (orderStatusEnum==OrderStatusEnum.PAYING) {
				successCount++;
				payingAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("11");
			}else if (orderStatusEnum==OrderStatusEnum.SUCCESS) {
				payingCount++;
				successAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("00");
			}
			bean.setRespMsg(collectDeta.getRespmsg());
			fileContentList.add(bean);
		}
		resultBean.setFileContentList(fileContentList);
		resultBean.setFailTotalAmt(failedAmt+"");
		resultBean.setFailTotalQty(failedCount+"");
		resultBean.setSuccTotalAmt(successAmt+"");
		resultBean.setSuccTotalQty(successCount+"");
		resultBean.setWaitTotalAmt(payingAmt+"");
		resultBean.setWaitTotalQty(payingCount+"");
		resultBean.setTotalAmt(collectBatch.getTotalamt()+"");
		resultBean.setTotalQty(collectBatch.getTotalqty()+"");
		return resultBean;
	}
	@Override
	public BatchResultBean queryConcentratePaymentBatch(String merchNo,
			String batchNo, String txnDate) throws QueryOrderException {
		BatchResultBean resultBean = new BatchResultBean();
		OrderPaymentBatchDO paymentBatch = orderPaymentBatchDAO.getPaymentBatchOrder(merchNo, batchNo, txnDate);
		if(paymentBatch==null){
			throw new QueryOrderException("PC004");
		}
		List<OrderPaymentDetaDO> detaList = orderPaymentDetaDAO.getDetaListByBatchtid(paymentBatch.getTid());
		List<FileContentBean> fileContentList = Lists.newArrayList();
		long failedCount = 0L;
		long failedAmt = 0L;
		long successCount = 0L;
		long successAmt = 0L;
		long payingCount = 0L;
		long payingAmt = 0L;
		for(OrderPaymentDetaDO paymentDeta : detaList){
			FileContentBean bean = new FileContentBean();
			bean.setOrderId(paymentDeta.getOrderid());
			bean.setCurrencyCode(paymentDeta.getCurrencycode());
			bean.setAmt(paymentDeta.getAmt());
			bean.setDebtorBank(paymentDeta.getDebtorbank());
			bean.setDebtorAccount(paymentDeta.getDebtoraccount());
			bean.setDebtorName(paymentDeta.getDebtorname());
			bean.setDebtorConsign(paymentDeta.getDebtorconsign());
			bean.setCreditorBank(paymentDeta.getCreditorbank());
			bean.setCreditorAccount(paymentDeta.getCreditoraccount());
			bean.setCreditorName(paymentDeta.getCreditorname());
			bean.setProprietary(paymentDeta.getProprietary());
			bean.setSummary(paymentDeta.getSummary());
			OrderStatusEnum orderStatusEnum = OrderStatusEnum.fromValue(paymentDeta.getStatus());
			if(orderStatusEnum==OrderStatusEnum.FAILED){
				failedCount++;
				failedAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("09");
			}else if (orderStatusEnum==OrderStatusEnum.PAYING) {
				successCount++;
				payingAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("11");
			}else if (orderStatusEnum==OrderStatusEnum.SUCCESS) {
				payingCount++;
				successAmt+=Long.valueOf(bean.getAmt()).longValue();
				bean.setRespCode("00");
			}
			bean.setRespMsg(paymentDeta.getRespmsg());
			fileContentList.add(bean);
		}
		resultBean.setFileContentList(fileContentList);
		resultBean.setFailTotalAmt(failedAmt+"");
		resultBean.setFailTotalQty(failedCount+"");
		resultBean.setSuccTotalAmt(successAmt+"");
		resultBean.setSuccTotalQty(successCount+"");
		resultBean.setWaitTotalAmt(payingAmt+"");
		resultBean.setWaitTotalQty(payingCount+"");
		resultBean.setTotalAmt(paymentBatch.getTotalamt()+"");
		resultBean.setTotalQty(paymentBatch.getTotalqty()+"");
		return resultBean;
	}
	

}
