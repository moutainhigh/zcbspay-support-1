package com.zcbspay.platform.support.cipher.hardware.api;

public interface MACHardwareService {

	/**
	 * 软加密计算MAC
	 * @param MKIndex  秘钥索引
	 * @param MAK 明文MAK
	 * @param data 原数据
	 * @return MAC
	 */
	public String genANSI_x9_9_MAC(int MKIndex,String MAK,String data);
}
