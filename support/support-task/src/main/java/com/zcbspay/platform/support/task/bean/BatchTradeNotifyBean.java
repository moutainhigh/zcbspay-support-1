/* 
 * PayNotifyBean.java  
 * 
 * version TODO
 *
 * 2016年10月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.task.bean;

import com.zcbspay.platform.support.task.pojo.PojoInsteadPayRealtime;
import com.zcbspay.platform.support.task.pojo.PojoTxnsOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月24日 下午5:22:24
 * @since
 */
public class BatchTradeNotifyBean {
	private String version;// 版本
	private String encoding;// 编码方式
	private String txnType;// 交易类型
	private String txnSubType;// 交易子类
	private String bizType;// 产品类型
	private String merId;//委托机构号
	private String batchNo;// 商户订单号
	private String txnTime;// 订单发送时间
	private String fileContent;//文件内容
	private String totalQty;//总笔数
	private String totalAmt;//总金额
	private String waitTotalQty;//处理中总笔数
	private String waitTotalAmt;//处理中总金额
	private String succTotalQty ;//成功总笔数
	private String succTotalAmt ;//成功总金额 
	private String failTotalQty ;//失败总笔数
	private String failTotalAmt ;//失败总金额 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getWaitTotalQty() {
		return waitTotalQty;
	}
	public void setWaitTotalQty(String waitTotalQty) {
		this.waitTotalQty = waitTotalQty;
	}
	public String getWaitTotalAmt() {
		return waitTotalAmt;
	}
	public void setWaitTotalAmt(String waitTotalAmt) {
		this.waitTotalAmt = waitTotalAmt;
	}
	public String getSuccTotalQty() {
		return succTotalQty;
	}
	public void setSuccTotalQty(String succTotalQty) {
		this.succTotalQty = succTotalQty;
	}
	public String getSuccTotalAmt() {
		return succTotalAmt;
	}
	public void setSuccTotalAmt(String succTotalAmt) {
		this.succTotalAmt = succTotalAmt;
	}
	public String getFailTotalQty() {
		return failTotalQty;
	}
	public void setFailTotalQty(String failTotalQty) {
		this.failTotalQty = failTotalQty;
	}
	public String getFailTotalAmt() {
		return failTotalAmt;
	}
	public void setFailTotalAmt(String failTotalAmt) {
		this.failTotalAmt = failTotalAmt;
	}
	
}
