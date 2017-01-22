package com.zcbspay.platform.support.signaturn.service;

import com.zcbspay.platform.support.signaturn.bean.MessageBean;

public interface MessageDecodeService {

	public MessageBean decodeAndVerify(MessageBean messageBean);
}
