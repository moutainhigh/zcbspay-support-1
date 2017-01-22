package com.zcbspay.platform.support.signaturn.util.security;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zcbspay.platform.support.signaturn.util.StringUtil;
import com.zcbspay.platform.support.signaturn.util.security.bean.ResultMessage;

// 守门员
public class GateKeeper {

	private static final Log log = LogFactory.getLog(GateKeeper.class);

	private RSAHelper rsa;
	private AbstractCryptor decryptor;
	private AbstractUnpacker packer;
	private AbstractVerifier verifier;
	private JSONObject sign;
	private JSONObject addit;
	private JSONObject data;
	private ResultMessage errorMsg;

	public GateKeeper() {
	}

	public GateKeeper(String data, String signStr, String addit, RSAHelper rsa) {

		this.rsa = rsa;

		// 解密AESKEY
		JSONObject jsonAddit = JSONObject.fromObject(addit);

		String encryptKey = (String) jsonAddit.get("encryKey");

		// 得到AES密钥
		decryptor = rsa;
		String aesKey = decryptor.decrypt(encryptKey);

		if (StringUtil.isEmpty(aesKey)) {
			this.errorMsg = new ResultMessage("21", "无法解出AES密钥");
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("【解析后AES密钥为】" + aesKey);
		}
		if ("01".equals(jsonAddit.getString("encryMethod"))) {
			packer = new AESHelper(aesKey);
		} else {
			// 默认为AES报文加密
			packer = new AESHelper(aesKey);
		}

		// 得到风控信息
		String riskInfo = packer.unpack(jsonAddit.getString("riskInfo"));
		if (log.isDebugEnabled()) {
			log.debug("【解析后风控信息为】" + riskInfo);
		}

		if (StringUtil.isEmpty(riskInfo)) {
			this.errorMsg = new ResultMessage("21", "无法解出风控信息");
			return;
		}
		// 得到业务数据
		JSONObject reqJsonData = null;
		try {
			reqJsonData = JSONObject.fromObject(packer.unpack(data));
			// reqJsonData = JSONObject.fromObject(new
			// String(AESUtil.decrypt(Base64Utils.decode(data), aesKey)));
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			reqJsonData = null;
		}
		if (log.isDebugEnabled()) {
			log.debug("【解析后业务数据为】" + reqJsonData.toString());
		}
		if (reqJsonData == null) {
			this.errorMsg = new ResultMessage("21", "无法解出业务数据");
			return;
		}
		log.info("data=" + reqJsonData.toString());
		this.data = reqJsonData;
		// 验签：
		jsonAddit.put("riskInfo", riskInfo); // 替换riskInfo 为 明文
		this.addit = jsonAddit;
		// 组成验签串
		String md5Str = aesKey + jsonAddit.toString() + reqJsonData.toString();

		if (log.isDebugEnabled()) {
			log.debug("【服务端参与计算MD5串】" + md5Str);
		}
		// 验签
		this.sign = JSONObject.fromObject(signStr);
		if ("01".equals(this.sign.getString("signMethod"))) {
			// RSA验签
			verifier = rsa;
		} else if ("02".equals(this.sign.getString("signMethod"))) {
			// MD5验签
			verifier = new MD5Helper();
		} else {
			verifier = rsa;
		}
		// 获取客户端签名
		String clientSign = this.sign.getString("signature");
		if (!verifier.verify(md5Str, clientSign)) {
			this.errorMsg = new ResultMessage("20", "验签失败");
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("【服务端验签成功】");
		}
		this.data = reqJsonData;
	}

	/**
	 * 加签
	 * 
	 * @param data
	 * @return
	 */
	public String sign(String data) {
		return rsa.sign(data);
	}

	/**
	 * 整体报文加密
	 * 
	 * @param data
	 * @return
	 */
	public String pack(String data) {
		return packer.pack(data);
	}

	/**
	 * 敏感数据解密
	 * 
	 * @param data
	 * @return
	 */
	public String decrypt(String data) {
		return decryptor.decrypt(data);
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public JSONObject getSign() {
		return sign;
	}

	public void setSign(JSONObject sign) {
		this.sign = sign;
	}

	public JSONObject getAddit() {
		return addit;
	}

	public void setAddit(JSONObject addit) {
		this.addit = addit;
	}

	public ResultMessage getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ResultMessage errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCoopInsti() {
		return this.addit.getString("coopInstiId");
	}

	public String getDevID() {
		JSONObject riskInfoJson = JSONObject.fromObject(this.addit.getString("riskInfo"));
		String deviceID = (String) riskInfoJson.get("deviceID");
		return deviceID;
	}
}
