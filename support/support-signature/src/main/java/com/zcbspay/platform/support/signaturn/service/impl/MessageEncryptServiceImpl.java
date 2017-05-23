package com.zcbspay.platform.support.signaturn.service.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.zcbspay.platform.member.coopinsti.bean.CoopInstiMK;
import com.zcbspay.platform.member.coopinsti.bean.enums.TerminalAccessType;
import com.zcbspay.platform.member.coopinsti.service.CoopInstiService;
import com.zcbspay.platform.member.merchant.bean.MerchMK;
import com.zcbspay.platform.member.merchant.service.MerchMKService;
import com.zcbspay.platform.support.signaturn.bean.AdditBean;
import com.zcbspay.platform.support.signaturn.bean.MessageBean;
import com.zcbspay.platform.support.signaturn.service.MessageEncryptService;
import com.zcbspay.platform.support.signaturn.util.AESUtil;
import com.zcbspay.platform.support.signaturn.util.Base64Utils;
import com.zcbspay.platform.support.signaturn.util.Md5Utils;
import com.zcbspay.platform.support.signaturn.util.security.RSAHelper;

@Service("messageEncryptService")
public class MessageEncryptServiceImpl implements MessageEncryptService{

	@Reference(version="1.0")
	private CoopInstiService coopInstiService;
	@Reference(version="1.0")
	private MerchMKService merchMKService;
	
	@Override
	public MessageBean encryptAndSigntrue(String data, AdditBean additBean) throws Exception {
		MessageBean messageBean = new MessageBean();
		String key = AESUtil.getAESKey();
		RSAHelper rsa = null;
		if ("0".equals(additBean.getAccessType())) {
			CoopInstiMK coopInstiMK = coopInstiService.getCoopInstiMK(additBean.getCoopInstiId(),
					TerminalAccessType.OPENAPI);
			rsa = new RSAHelper(coopInstiMK.getZplatformPubKey(), coopInstiMK.getInstiPriKey());
		} else {
			MerchMK merchMk = merchMKService.get(additBean.getMerId());
			rsa = new RSAHelper(merchMk.getLocalPubKey(), merchMk.getMemberPriKey());
		}
		additBean.setEncryKey(rsa.encrypt(key));
		String signData = Md5Utils.md5(key + JSON.toJSONString(additBean) + data, "UTF-8").toUpperCase();
		Map<String, Object> signMap = new TreeMap<String, Object>();
		signMap.put("signature", signData);
		signMap.put("signMethod", "02");
		messageBean.setSign(JSON.toJSONString(signMap));
		additBean.setRiskInfo(Base64Utils.encode(AESUtil.encrypt(additBean.getRiskInfo(), key)));
		messageBean.setData( Base64Utils.encode(AESUtil.encrypt(data, key)));
		messageBean.setAddit(JSON.toJSONString(additBean));
		return messageBean;
	}

}
