package com.zcbspay.platform.support.trade.acc.service.test;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.support.trade.acc.service.TradeAccountingService;

public class TradeAccountingServiceTest extends BaseTest{
	
	@Reference(version="1.0")
	private TradeAccountingService tradeAccountingService;
	
	@Test
	public void test_trade_accounting_test(){
		String txnseqno = "1701169900000014";
		tradeAccountingService.accountingForSync(txnseqno);
	}

}