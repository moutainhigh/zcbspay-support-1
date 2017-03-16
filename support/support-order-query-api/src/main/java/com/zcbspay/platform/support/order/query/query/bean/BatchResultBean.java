package com.zcbspay.platform.support.order.query.query.bean;

import java.io.Serializable;
import java.util.List;

public class BatchResultBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9041198455616816910L;
	private String totalQty;// 总笔数
	private String totalAmt;// 总金额
	private String waitTotalQty;// 处理中总笔数
	private String waitTotalAmt;// 处理中总金额
	private String succTotalQty;// 成功总笔数
	private String succTotalAmt;// 成功总金额
	private String failTotalQty;// 失败总笔数
	private String failTotalAmt;// 失败总金额
	private List<FileContentBean> fileContentList;//批次明细
	
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
	public List<FileContentBean> getFileContentList() {
		return fileContentList;
	}
	public void setFileContentList(List<FileContentBean> fileContentList) {
		this.fileContentList = fileContentList;
	}
	
	
}
