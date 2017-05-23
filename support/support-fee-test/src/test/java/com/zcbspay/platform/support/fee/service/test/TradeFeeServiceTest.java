package com.zcbspay.platform.support.fee.service.test;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.payment.fee.bean.FeeBean;
import com.zcbspay.platform.payment.fee.exception.TradeFeeException;
import com.zcbspay.platform.payment.fee.service.TradeFeeService;
import com.zcbspay.platform.support.cipher.hardware.api.MACHardwareService;

public class TradeFeeServiceTest extends BaseTest{

	@Reference(version="1.0")
	private TradeFeeService tradeFeeService;
	@Reference(version="1.0")
	private MACHardwareService hardwareService;
	
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
	@Test
	public void test_cipher(){
		String data = "3310062091201705230310003633101103016228480018543668976           郭佳                                                        3310130601213582310310001               RMB000000000100                    0000000000013                                50046";
		String mac = hardwareService.genANSI_x9_9_MAC(1, "E408C01308B5DFD1", data);
		System.out.println(mac);
		//1894C63A
	}
}
