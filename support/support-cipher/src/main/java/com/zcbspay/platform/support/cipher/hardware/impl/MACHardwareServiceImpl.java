package com.zcbspay.platform.support.cipher.hardware.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.westone.pboc.mina.client.ClientThreadPool;
import com.westone.pboc.service.imp.HSMZCBSApiServiceImp;
import com.zcbspay.platform.support.cipher.hardware.api.MACHardwareService;

@Service("MACHardwareService")
public class MACHardwareServiceImpl implements MACHardwareService {
	private static final Logger logger = LoggerFactory.getLogger(MACHardwareServiceImpl.class);
	static HSMZCBSApiServiceImp imp = null;
	static {
		imp =  new HSMZCBSApiServiceImp();
	}
	
	private static ClientThreadPool connectPool =  ClientThreadPool.getInstance();
	@Override
	public String genANSI_x9_9_MAC(int MKIndex,String MAK, String data) {
		//E408C01308B5DFD14FE9A878A85CFAF3
		String mac = null;
		try {
			connectPool.initClient();
			logger.info(data.getBytes("GBK").length+"");
			mac= imp.hsmGenerateMAC9_9(MKIndex, MAK, data.getBytes("GBK").length, data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mac.substring(0,8).toUpperCase();
	}

}
