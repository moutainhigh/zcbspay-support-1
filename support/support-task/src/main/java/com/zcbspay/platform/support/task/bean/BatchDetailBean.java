package com.zcbspay.platform.support.task.bean;

import java.io.Serializable;

import com.zcbspay.platform.support.task.pojo.OrderCollectDetaDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentDetaDO;

public class BatchDetailBean implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6444413675096945794L;
	private String orderId;// 委托机构订单号
	private String currencyCode;// 交易币种
	private String amt;// 单笔金额
	private String debtorBank;// 付款人银行号
	private String debtorAccount;// 付款人账号
	private String debtorName;// 付款人名称
	private String debtorConsign;// 付款合同号
	private String creditorBank;// 收款人银行号
	private String creditorAccount;// 收款人账号
	private String creditorName;// 收款人名称
	private String summary;// 摘要
	private String respCode;// 响应码
	private String respMsg;// 应答信息
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getDebtorBank() {
		return debtorBank;
	}
	public void setDebtorBank(String debtorBank) {
		this.debtorBank = debtorBank;
	}
	public String getDebtorAccount() {
		return debtorAccount;
	}
	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorConsign() {
		return debtorConsign;
	}
	public void setDebtorConsign(String debtorConsign) {
		this.debtorConsign = debtorConsign;
	}
	public String getCreditorBank() {
		return creditorBank;
	}
	public void setCreditorBank(String creditorBank) {
		this.creditorBank = creditorBank;
	}
	public String getCreditorAccount() {
		return creditorAccount;
	}
	public void setCreditorAccount(String creditorAccount) {
		this.creditorAccount = creditorAccount;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public BatchDetailBean(OrderCollectDetaDO collectDeta) {
		super();
		this.orderId = collectDeta.getOrderid();
		this.currencyCode = collectDeta.getCurrencycode();
		this.amt = collectDeta.getAmt();
		this.debtorBank = collectDeta.getDebtorbank();
		this.debtorAccount = collectDeta.getDebtoraccount();
		this.debtorName = collectDeta.getDebtorname();
		this.debtorConsign = collectDeta.getDebtorconsign();
		this.creditorBank = collectDeta.getCreditorbank();
		this.creditorAccount = collectDeta.getCreditoraccount();
		this.creditorName = collectDeta.getCreditorname();
		this.summary = collectDeta.getSummary();
		this.respCode = collectDeta.getStatus();
		this.respMsg = collectDeta.getRespmsg();
	}
	public BatchDetailBean(OrderPaymentDetaDO paymentDeta) {
		super();
		this.orderId = paymentDeta.getOrderid();
		this.currencyCode = paymentDeta.getCurrencycode();
		this.amt = paymentDeta.getAmt();
		this.debtorBank = paymentDeta.getDebtorbank();
		this.debtorAccount = paymentDeta.getDebtoraccount();
		this.debtorName = paymentDeta.getDebtorname();
		this.debtorConsign = paymentDeta.getDebtorconsign();
		this.creditorBank = paymentDeta.getCreditorbank();
		this.creditorAccount = paymentDeta.getCreditoraccount();
		this.creditorName = paymentDeta.getCreditorname();
		this.summary = paymentDeta.getSummary();
		this.respCode = paymentDeta.getStatus();
		this.respMsg = paymentDeta.getRespmsg();
	}
	
}
