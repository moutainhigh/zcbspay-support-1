package com.zcbspay.platform.support.cipher.software.api;

public interface MACSoftwareService {

	/**
	 * 软加密计算MAC
	 * @param MAK 明文MAK
	 * @param data 原数据
	 * @return MAC
	 */
	public String genANSI_x9_9_MAC(String MAK,String data);
}
