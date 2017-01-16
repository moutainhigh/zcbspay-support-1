package com.zcbspay.platform.support.fee.service.test;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.support.fee.bean.FeeBean;
import com.zcbspay.platform.support.fee.exception.TradeFeeException;
import com.zcbspay.platform.support.fee.service.TradeFeeService;

public class TradeFeeServiceTest extends BaseTest{

	@Reference(version="1.0")
	private TradeFeeService tradeFeeService;
	@Test
	public void test_trade_fee(){
		try {
			FeeBean feeBean = new FeeBean();
			feeBean.setBusiCode("10000001");
			feeBean.setCardType("1");
			feeBean.setFeeVer("90000002");
			feeBean.setMerchNo("200000000000610");
			feeBean.setTxnAmt("2");
			feeBean.setTxnseqno("1701169900000015");
			feeBean.setTxnseqnoOg("");
			long commonFee = tradeFeeService.getCommonFee(feeBean);
			System.out.println("commonFee:"+commonFee);
		} catch (TradeFeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
