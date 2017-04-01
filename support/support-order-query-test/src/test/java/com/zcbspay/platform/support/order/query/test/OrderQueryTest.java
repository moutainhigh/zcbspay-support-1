package com.zcbspay.platform.support.order.query.test;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.zcbspay.platform.support.order.query.exception.QueryOrderException;
import com.zcbspay.platform.support.order.query.query.bean.OrderResultBean;
import com.zcbspay.platform.support.order.query.query.service.QueryOrderService;

public class OrderQueryTest extends BaseTest{
	@Reference(version="1.0")
	private QueryOrderService queryOrderService; 
	@Test
	public void test(){
		//集中实时代付订单查询
		test_query_concentrate_payment_realtime("170322061000000004");
	}
	
	public void test_query_concentrate_payment_realtime(String tn){
		OrderResultBean queryConcentratePaymentOrder = null;
		try {
			queryConcentratePaymentOrder = queryOrderService.queryConcentratePaymentOrder(tn);
		} catch (QueryOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(queryConcentratePaymentOrder));
	}
}
