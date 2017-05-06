package com.zcbspay.platform.support.cipher.software.impl;

import org.springframework.stereotype.Service;

import com.zcbspay.platform.support.cipher.ansi.MacUtil;
import com.zcbspay.platform.support.cipher.software.api.MACSoftwareService;

@Service("MACSoftwareService")
public class MACSoftwareServiceImpl implements MACSoftwareService{

	@Override
	public String genANSI_x9_9_MAC(String MAK, String data) {
		String mac = MacUtil.MAC(MAK, "0000000000000000", data, MacUtil.ASC);
		return mac.substring(0,8);
	}

}
