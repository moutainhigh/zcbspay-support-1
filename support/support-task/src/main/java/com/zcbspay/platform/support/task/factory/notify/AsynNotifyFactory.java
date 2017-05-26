package com.zcbspay.platform.support.task.factory.notify;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zcbspay.platform.member.merchant.bean.MerchMK;
import com.zcbspay.platform.member.merchant.bean.Money;
import com.zcbspay.platform.member.merchant.service.MerchMKService;
import com.zcbspay.platform.support.task.bean.BatchDetailBean;
import com.zcbspay.platform.support.task.bean.BatchTradeNotifyBean;
import com.zcbspay.platform.support.task.bean.NotifyBean;
import com.zcbspay.platform.support.task.bean.TradeNotifyBean;
import com.zcbspay.platform.support.task.dao.InsteadPayRealtimeDAO;
import com.zcbspay.platform.support.task.dao.OrderCollectBatchDAO;
import com.zcbspay.platform.support.task.dao.OrderCollectDetaDAO;
import com.zcbspay.platform.support.task.dao.OrderCollectSingleDAO;
import com.zcbspay.platform.support.task.dao.OrderPaymentBatchDAO;
import com.zcbspay.platform.support.task.dao.OrderPaymentDetaDAO;
import com.zcbspay.platform.support.task.dao.OrderPaymentSingleDAO;
import com.zcbspay.platform.support.task.dao.TxnsOrderinfoDAO;
import com.zcbspay.platform.support.task.enums.BiztypeEnum;
import com.zcbspay.platform.support.task.enums.BusinessEnum;
import com.zcbspay.platform.support.task.factory.BeanFactory;
import com.zcbspay.platform.support.task.pojo.OrderCollectBatchDO;
import com.zcbspay.platform.support.task.pojo.OrderCollectDetaDO;
import com.zcbspay.platform.support.task.pojo.OrderCollectSingleDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentBatchDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentDetaDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentSingleDO;
import com.zcbspay.platform.support.task.util.security.AESHelper;
import com.zcbspay.platform.support.task.util.security.AESUtil;
import com.zcbspay.platform.support.task.util.security.RSAHelper;

/**
  * @ClassName: AsynNotifyFactory
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:40:31
  *
  */
@Service
public class AsynNotifyFactory implements BeanFactory{

	private static final Logger log = LoggerFactory.getLogger(AsynNotifyFactory.class);
	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private OrderCollectBatchDAO orderCollectBatchDAO;
	@Autowired
	private OrderCollectDetaDAO orderCollectDetaDAO;
	@Autowired
	private OrderPaymentBatchDAO orderPaymentBatchDAO;
	@Autowired
	private OrderPaymentDetaDAO orderPaymentDetaDAO;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	@Autowired
	private OrderPaymentSingleDAO orderPaymentSingleDAO;
	@Autowired
	private OrderCollectSingleDAO orderCollectSingleDAO;
	@Reference(version="1.0")
	private MerchMKService merchMKService;
	@Override
	public NotifyBean getBean(String txnseqno,BiztypeEnum biztypeEnum) {
		NotifyBean bean = null;
		/*if(biztypeEnum == BiztypeEnum.NM000210){
			bean = new NotifyBean();
			PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
			TradeNotifyBean tradeNotifyBean = new TradeNotifyBean(orderinfo);
			responseData(tradeNotifyBean, orderinfo.getFirmemberno(), orderinfo.getSecmemberno(), bean);
			bean.setNotifyURL(orderinfo.getBackurl());
		}else if(biztypeEnum == BiztypeEnum.NM000205){
			
			
		}*/
		if(biztypeEnum==BiztypeEnum.BE000003){
			
		}
		
		return bean;
	}
	
	public NotifyBean getBean(String txnseqno, BusinessEnum businessEnum){
		NotifyBean bean = null;
		String merchno = null;
		if(businessEnum==BusinessEnum.REAL_TIME_COLLECTION){//实时代收
			bean = new NotifyBean();
			OrderCollectSingleDO orderinfo = orderCollectSingleDAO.getOrderinfoByTxnseqno(txnseqno);
			TradeNotifyBean tradeNotifyBean = new TradeNotifyBean(orderinfo);
			if(StringUtils.isEmpty(orderinfo.getCoopinstiid())||"99999999999".equals(orderinfo.getCoopinstiid())){
				merchno = orderinfo.getMerid();
			}else{
				merchno = orderinfo.getCoopinstiid();
			}
			responseData(tradeNotifyBean, merchno, bean);
			bean.setNotifyURL(orderinfo.getBackurl());
		}else if(businessEnum==BusinessEnum.REAL_TIME_PAYMENT){//实时代付
			OrderPaymentSingleDO orderinfo = orderPaymentSingleDAO.getOrderinfoByTxnseqno(txnseqno);
			TradeNotifyBean tradeNotifyBean = new TradeNotifyBean(orderinfo);
			if(StringUtils.isEmpty(orderinfo.getCoopinstiid())||"99999999999".equals(orderinfo.getCoopinstiid())){
				merchno = orderinfo.getMerid();
			}else{
				merchno = orderinfo.getCoopinstiid();
			}
			responseData(tradeNotifyBean,  merchno, bean);
			bean.setNotifyURL(orderinfo.getBackurl());
		}
		
		return bean;
	}

	@Override
	public NotifyBean getInsteadPayBean(String txnseqno,BusinessEnum businessEnum) {
		
		
		return null;
	}

	
	@SuppressWarnings("unchecked")
	private void responseData(TradeNotifyBean respBean,String merchNo,NotifyBean task) {
        JSONObject jsonData = JSONObject.fromObject(respBean);
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);
        
        JSONObject addit = new JSONObject();
        addit.put("accessType", "1");
        addit.put("coopInstiId", "");
        addit.put("merId", merchNo);
        //MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        
        MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        //PojoMerchMK merchMk = merchMKDAO.get(addit.getString("merId"));
        RSAHelper rsa = new RSAHelper(merchMk.getMemberPubKey(), merchMk.getLocalPriKey());
        String aesKey = null;
        try {
            aesKey = AESUtil.getAESKey();
            log.debug("【AES KEY】" + aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addit.put("encryKey", rsa.encrypt(aesKey));
        addit.put("encryMethod", "01");

        // 加签名
        StringBuffer originData = new StringBuffer(addit.toString());//业务数据
        originData.append(jsonData.toString());// 附加数据
        log.debug("【应答报文】加签用字符串：" + originData.toString());
        // 加签
        String sign = rsa.sign(originData.toString());
        AESHelper packer = new AESHelper(aesKey);
        JSONObject rtnSign = new JSONObject();
        rtnSign.put("signature", sign);
        rtnSign.put("signMethod", "01");
        
        // 业务数据
        task.setData(packer.pack(jsonData.toString()));
        // 附加数据
        task.setAddit(addit.toString());
        // 签名数据
        task.setSign(rtnSign.toString());
        
        log.debug("【发送报文数据】【业务数据】："+task.getData());
        log.debug("【发送报文数据】【附加数据】："+task.getAddit());
        log.debug("【发送报文数据】【签名数据】："+ task.getSign());
    }

	@Override
	public NotifyBean getBatchTradeBean(String tn, BusinessEnum businessEnum) {
		BatchTradeNotifyBean tradeNotifyBean = new BatchTradeNotifyBean();
		NotifyBean bean = null;
		if(businessEnum==BusinessEnum.BATCH_COLLECTION){
			OrderCollectBatchDO orderCollectBatch = orderCollectBatchDAO.getCollectBatchOrderByTn(tn);
			//获取订单明细集合
			List<OrderCollectDetaDO> collectList = orderCollectDetaDAO.getCollectList(orderCollectBatch.getTid());
			List<BatchDetailBean> detailList = Lists.newArrayList();
			for(OrderCollectDetaDO collectDeta : collectList){
				BatchDetailBean batchDetailBean = new BatchDetailBean(collectDeta);
				detailList.add(batchDetailBean);
			}
			//计算交易成功笔数，失败笔数和处理中笔数
			List<Map<String, Object>> collectResult = orderCollectDetaDAO.getCollectResult(orderCollectBatch.getTid());
			for(Map<String, Object> valueMap : collectResult){
				if(valueMap.get("STATUS").toString().equals("00")){//交易成功笔数
					tradeNotifyBean.setSuccTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setSuccTotalQty(valueMap.get("COUNTS").toString());
				}else if(valueMap.get("STATUS").toString().equals("02")){//交易中
					tradeNotifyBean.setWaitTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setWaitTotalQty(valueMap.get("COUNTS").toString());
				}else if(valueMap.get("STATUS").toString().equals("03")){//交易失败
					tradeNotifyBean.setFailTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setFailTotalQty(valueMap.get("COUNTS").toString());
				}
			}
			tradeNotifyBean.setTotalAmt(orderCollectBatch.getTotalamt()+"");
			tradeNotifyBean.setTotalQty(orderCollectBatch.getTotalqty()+"");
			tradeNotifyBean.setFileContent(JSON.toJSONString(detailList));
			String merchNo = null;
			//有代理商代理或者代理商为空
			if(orderCollectBatch.getCoopinstiid().equals("99999999999")||StringUtils.isEmpty(orderCollectBatch.getCoopinstiid())){
				merchNo = orderCollectBatch.getMerid();
			}else{
				merchNo = orderCollectBatch.getCoopinstiid();
			}
			responseBatchData(tradeNotifyBean, merchNo, bean);
			bean.setNotifyURL(orderCollectBatch.getBackurl());
		}else if(businessEnum==BusinessEnum.BATCH_PAYMENT){
			OrderPaymentBatchDO paymentBatch = orderPaymentBatchDAO.getPaymentBatchOrderByTn(tn);
			//计算交易成功笔数，失败笔数和处理中笔数
			List<Map<String, Object>> paymentResult = orderPaymentDetaDAO.getCollectResult(paymentBatch.getTid());
			//获取订单明细集合
			List<OrderPaymentDetaDO> collectList = orderPaymentDetaDAO.getPaymentList(paymentBatch.getTid());
			List<BatchDetailBean> detailList = Lists.newArrayList();
			for(OrderPaymentDetaDO collectDeta : collectList){
				BatchDetailBean batchDetailBean = new BatchDetailBean(collectDeta);
				detailList.add(batchDetailBean);
			}
			for(Map<String, Object> valueMap : paymentResult){
				if(valueMap.get("STATUS").toString().equals("00")){//交易成功笔数
					tradeNotifyBean.setSuccTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setSuccTotalQty(valueMap.get("COUNTS").toString());
				}else if(valueMap.get("STATUS").toString().equals("02")){//交易中
					tradeNotifyBean.setWaitTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setWaitTotalQty(valueMap.get("COUNTS").toString());
				}else if(valueMap.get("STATUS").toString().equals("03")){//交易失败
					tradeNotifyBean.setFailTotalAmt(Money.valueOf(new BigDecimal(valueMap.get("TOTALAMT").toString())).toYuan());
					tradeNotifyBean.setFailTotalQty(valueMap.get("COUNTS").toString());
				}
			}
			tradeNotifyBean.setTotalAmt(paymentBatch.getTotalamt()+"");
			tradeNotifyBean.setTotalQty(paymentBatch.getTotalqty()+"");
			tradeNotifyBean.setFileContent(JSON.toJSONString(detailList));
			String merchNo = null;
			//有代理商代理或者代理商为空,此为无代理商的委托机构
			if(paymentBatch.getCoopinstiid().equals("99999999999")||StringUtils.isEmpty(paymentBatch.getCoopinstiid())){
				merchNo = paymentBatch.getMerid();
			}else{
				merchNo = paymentBatch.getCoopinstiid();
			}
			responseBatchData(tradeNotifyBean, merchNo, bean);
			bean.setNotifyURL(paymentBatch.getBackurl());
		}
		return bean;
	}
	private void responseBatchData(BatchTradeNotifyBean respBean,String merchNo,NotifyBean task) {
        JSONObject jsonData = JSONObject.fromObject(respBean);
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);
        
        JSONObject addit = new JSONObject();
        addit.put("accessType", "1");
        addit.put("coopInstiId", "");
        addit.put("merId", merchNo);
        //MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        
        MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        //PojoMerchMK merchMk = merchMKDAO.get(addit.getString("merId"));
        RSAHelper rsa = new RSAHelper(merchMk.getMemberPubKey(), merchMk.getLocalPriKey());
        String aesKey = null;
        try {
            aesKey = AESUtil.getAESKey();
            log.debug("【AES KEY】" + aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addit.put("encryKey", rsa.encrypt(aesKey));
        addit.put("encryMethod", "01");

        // 加签名
        StringBuffer originData = new StringBuffer(addit.toString());//业务数据
        originData.append(jsonData.toString());// 附加数据
        log.debug("【应答报文】加签用字符串：" + originData.toString());
        // 加签
        String sign = rsa.sign(originData.toString());
        AESHelper packer = new AESHelper(aesKey);
        JSONObject rtnSign = new JSONObject();
        rtnSign.put("signature", sign);
        rtnSign.put("signMethod", "01");
        
        // 业务数据
        task.setData(packer.pack(jsonData.toString()));
        // 附加数据
        task.setAddit(addit.toString());
        // 签名数据
        task.setSign(rtnSign.toString());
        
        log.debug("【发送报文数据】【业务数据】："+task.getData());
        log.debug("【发送报文数据】【附加数据】："+task.getAddit());
        log.debug("【发送报文数据】【签名数据】："+ task.getSign());
    }
	
}
