package com.zcbspay.platform.support.signaturn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zcbspay.platform.support.signaturn.service.MessageDecodeService;
import com.zcbspay.platform.support.signaturn.util.StringUtil;
import com.zcbspay.platform.support.signaturn.util.security.GateKeeper;
import com.zcbspay.platform.support.signaturn.util.security.RSAHelper;

@Service("messageDecodeService")
public class MessageDecodeServiceImpl implements MessageDecodeService{
	
	private static final Logger logger = LoggerFactory.getLogger(MessageDecodeServiceImpl.class); 
	@Reference(version="1.0")
	private CoopInstiService coopInstiService;
	@Reference(version="1.0")
	private MerchMKService merchMKService;
	@Override
	public MessageBean decodeAndVerify(MessageBean messageBean) throws IllegalArgumentException {
		RSAHelper rsa = null;
		AdditBean additBean = JSON.parseObject(messageBean.getAddit(), AdditBean.class);
		if ("0".equals(additBean.getAccessType())) {
			CoopInstiMK coopInstiMK = coopInstiService.getCoopInstiMK(additBean.getCoopInstiId(), TerminalAccessType.OPENAPI);
			rsa = new RSAHelper(coopInstiMK.getInstiPubKey(), coopInstiMK.getZplatformPriKey());
		} else if ("1".equals(additBean.getAccessType())) {
			MerchMK merchMk = merchMKService.get(additBean.getMerId());
			rsa = new RSAHelper(merchMk.getMemberPubKey(), merchMk.getLocalPriKey());
		} else {
			logger.error("未知的接入方式");
			return null;
		}
		GateKeeper keeper = new GateKeeper(messageBean.getData(), messageBean.getSign(), messageBean.getAddit(), rsa);
		if (keeper.getErrorMsg() == null || keeper.getErrorMsg().isNormal())
			
		// 异常情况
		if (StringUtil.isNotEmpty(keeper.getErrorMsg().getErrorCode())){
			throw new IllegalArgumentException(keeper.getErrorMsg().getErrorMessage());
		}
		MessageBean returnMessageBean = new MessageBean();
		returnMessageBean.setAddit(keeper.getAddit().toString());
		returnMessageBean.setData(keeper.getData().toString());
		returnMessageBean.setSign(messageBean.getSign());
		return returnMessageBean;
	}
	

}
