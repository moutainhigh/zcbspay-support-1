/* 
 * TradeFeeServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年11月16日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.fee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.support.fee.bean.FeeBean;
import com.zcbspay.platform.support.fee.dao.TradeFeeDAO;
import com.zcbspay.platform.support.fee.exception.TradeFeeException;
import com.zcbspay.platform.support.fee.service.TradeFeeService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月16日 上午10:19:02
 * @since 
 */
@Service("tradeFeeService")
public class TradeFeeServiceImpl implements TradeFeeService {

	@Autowired
	private TradeFeeDAO tradeFeeDAO;
	/**
	 *
	 * @param feeBean
	 * @return
	 * @throws TradeFeeException
	 */
	@Override
	public long getCommonFee(FeeBean feeBean) throws TradeFeeException {
		return tradeFeeDAO.getTxnFee(feeBean);
	}

	/**
	 *
	 * @param feeBean
	 * @return
	 * @throws TradeFeeException
	 */
	@Override
	public long getRefundFee(FeeBean feeBean) throws TradeFeeException {
		return tradeFeeDAO.getRefundFee(feeBean);
	}

}
