package com.zcbspay.platform.support.signaturn.util.security.bean;

import com.zcbspay.platform.support.signaturn.util.StringUtil;

/**
 * 结果报文 正常情况下 errorCode=null && errorMessage=null 异常情况下 errorCode<>null ||
 * errorMessage<>null
 */
public class ResultMessage {
	public static int MAX_MESSAGE_SIZE = 128; // 错大错误信息的长度
	private String errorCode; // 错误码
	private String errorMessage; // 错误信息

	/**
	 * 构造错误信息
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public ResultMessage(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		if (StringUtil.isEmpty(errorMessage)) {
			this.errorMessage = errorMessage;
		} else {
			this.errorMessage = errorMessage.substring(0,
					errorMessage.length() < MAX_MESSAGE_SIZE ? errorMessage.length() : MAX_MESSAGE_SIZE);
		}

	}

	/**
	 * 判断是否是错误信息
	 * 
	 * @return
	 */
	public boolean isError() {
		return StringUtil.isNotEmpty(this.errorMessage) || StringUtil.isNotEmpty(this.errorCode) ? true : false;
	}

	/**
	 * 判断是否是正常信息
	 * 
	 * @return
	 */
	public boolean isNormal() {
		return StringUtil.isEmpty(this.errorMessage) && StringUtil.isEmpty(this.errorCode) ? true : false;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
