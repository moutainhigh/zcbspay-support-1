package com.zcbspay.platform.support.cipher.hardware.impl;

import org.springframework.stereotype.Service;

import com.westone.pboc.mina.client.ClientThreadPool;
import com.westone.pboc.service.imp.HSMZCBSApiServiceImp;
import com.zcbspay.platform.support.cipher.hardware.api.MACHardwareService;

@Service("MACHardwareService")
public class MACHardwareServiceImpl implements MACHardwareService {
	static HSMZCBSApiServiceImp imp = null;
	static {
		imp =  new HSMZCBSApiServiceImp();
	}
	
	private static ClientThreadPool connectPool =  ClientThreadPool.getInstance();
	@Override
	public String genANSI_x9_9_MAC(int MKIndex,String MAK, String data) {
		//原始测试用MAK E408C01308B5DFD14FE9A878A85CFAF3
		String mac = null;
		try {
			//初始化连接
			connectPool.initClient();
			mac= imp.hsmGenerateMAC9_9(MKIndex, MAK, 16, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return mac;
	}

}
