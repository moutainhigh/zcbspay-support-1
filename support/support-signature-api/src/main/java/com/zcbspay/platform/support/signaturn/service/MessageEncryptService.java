package com.zcbspay.platform.support.signaturn.service;

import com.zcbspay.platform.support.signaturn.bean.AdditBean;
import com.zcbspay.platform.support.signaturn.bean.MessageBean;

/**
 * 
 * 报文加密类
 *
 * @author guojia
 * @version
 * @date 2017年1月20日 下午4:07:27
 * @since
 */
public interface MessageEncryptService {

	/**
	 * 报文加密加签
	 * @param data
	 * @param additBean
	 * @return
	 * @throws Exception 
	 */
	public MessageBean encryptAndSigntrue(String data,AdditBean additBean) throws Exception;
	
	
}
