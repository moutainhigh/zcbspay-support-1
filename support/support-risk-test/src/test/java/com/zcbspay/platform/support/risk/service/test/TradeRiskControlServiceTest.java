package com.zcbspay.platform.support.risk.service.test;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.payment.risk.bean.RiskBean;
import com.zcbspay.platform.payment.risk.exception.TradeRiskException;
import com.zcbspay.platform.payment.risk.service.TradeRiskControlService;

public class TradeRiskControlServiceTest extends BaseTest{

	@Reference(version="1.0")
	private TradeRiskControlService tradeRiskControlService;
	
	@Test
	public void test_trade_risk(){
		try {
			RiskBean riskBean = new RiskBean();
			riskBean.setBusiCode("10000001");
			riskBean.setCardNo("6228480018543668979");
			riskBean.setCardType("1");
			riskBean.setCoopInstId("300000000000004");
			riskBean.setMemberId("999999999999999");
			riskBean.setMerchId("200000000000610");
			riskBean.setTxnAmt("2");
			riskBean.setTxnseqno("1701169900000015");
			tradeRiskControlService.realTimeTradeRiskControl(riskBean );
		} catch (TradeRiskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
