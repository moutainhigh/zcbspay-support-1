package com.zcbspay.platform.support.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zcbspay.platform.support.task.bean.NotifyBean;
import com.zcbspay.platform.support.task.bean.NotifyQueueBean;
import com.zcbspay.platform.support.task.dao.OrderCollectBatchDAO;
import com.zcbspay.platform.support.task.dao.OrderCollectSingleDAO;
import com.zcbspay.platform.support.task.dao.OrderPaymentBatchDAO;
import com.zcbspay.platform.support.task.dao.OrderPaymentSingleDAO;
import com.zcbspay.platform.support.task.dao.TxnsLogDAO;
import com.zcbspay.platform.support.task.dao.TxnsNotifyTaskDAO;
import com.zcbspay.platform.support.task.dao.TxnsOrderinfoDAO;
import com.zcbspay.platform.support.task.enums.BiztypeEnum;
import com.zcbspay.platform.support.task.enums.BusiTypeEnum;
import com.zcbspay.platform.support.task.enums.BusinessEnum;
import com.zcbspay.platform.support.task.enums.TradeQueueEnum;
import com.zcbspay.platform.support.task.enums.TradeTypeEnum;
import com.zcbspay.platform.support.task.factory.BeanFactory;
import com.zcbspay.platform.support.task.pojo.OrderCollectBatchDO;
import com.zcbspay.platform.support.task.pojo.OrderCollectSingleDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentBatchDO;
import com.zcbspay.platform.support.task.pojo.OrderPaymentSingleDO;
import com.zcbspay.platform.support.task.pojo.PojoTxnsLog;
import com.zcbspay.platform.support.task.pojo.PojoTxnsNotifyTask;
import com.zcbspay.platform.support.task.pojo.PojoTxnsOrderinfo;
import com.zcbspay.platform.support.task.service.TradeNotifyService;
import com.zcbspay.platform.support.task.service.TradeQueueService;
import com.zcbspay.platform.support.task.thread.AsynHttpRequestThread;
import com.zcbspay.platform.support.task.thread.AsyncNotifyThreadPool;
import com.zcbspay.platform.support.task.util.DateUtil;
@Service("tradeNotifyService")
public class TradeNotifyServiceImpl implements TradeNotifyService {

	private final static Logger log = LoggerFactory.getLogger(TradeNotifyServiceImpl.class);
	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private TxnsNotifyTaskDAO txnsNotifyTaskDAO;
	@Autowired
	private OrderCollectBatchDAO orderCollectBatchDAO;
	@Autowired
	private OrderPaymentBatchDAO orderPaymentBatchDAO;
	@Autowired
	private OrderCollectSingleDAO orderCollectSingleDAO;
	@Autowired
	private OrderPaymentSingleDAO orderPaymentSingleDAO;
	
	@Override
	public void notify(String txnseqno) {
		// TODO Auto-generated method stub
		NotifyBean bean = null;
		/*try {
			PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
			BusiTypeEnum busiTypeEnum = BusiTypeEnum.fromValue(txnsLog.getBusitype());
			if(busiTypeEnum == BusiTypeEnum.CONSUMPTION){//消费
				PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
				BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(orderinfo.getBiztype());
				bean = beanFactory.getBean(txnseqno,biztypeEnum);
			}else if(busiTypeEnum == BusiTypeEnum.CHARGE){//充值
				PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getTxnsOrderinfoByTxnseqno(txnseqno);
				BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(orderinfo.getBiztype());
				bean = beanFactory.getBean(txnseqno,biztypeEnum);
			}else if(busiTypeEnum == BusiTypeEnum.INSTEADPAY){//代付
				bean = beanFactory.getInsteadPayBean(txnseqno, BusinessEnum.fromValue(txnsLog.getBusicode()));
			}
			if(bean!=null){
				AsynHttpRequestThread notifyThread = new AsynHttpRequestThread(txnsLog.getAccsecmerno(), txnseqno,bean);
				AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLog.getBusicode());
		String tradeType = null;
		if(businessEnum == BusinessEnum.REAL_TIME_COLLECTION){//实时代收
			OrderCollectSingleDO collectSingle = orderCollectSingleDAO.getOrderinfoByTxnseqno(txnseqno);
			BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(collectSingle.getBiztype());
			bean = beanFactory.getBean(txnseqno,BusinessEnum.REAL_TIME_COLLECTION);
			tradeType = "03";
		}else if(businessEnum == BusinessEnum.REAL_TIME_PAYMENT){//实时代付
			OrderPaymentSingleDO paymentSingle = orderPaymentSingleDAO.getOrderinfoByTxnseqno(txnseqno);
			BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(paymentSingle.getBiztype());
			bean = beanFactory.getBean(txnseqno,BusinessEnum.REAL_TIME_PAYMENT);
			tradeType = "04";
		}
		if(bean!=null){
			AsynHttpRequestThread notifyThread = new AsynHttpRequestThread(txnsLog.getAccsecmerno(), txnsLog.getTxnseqno(),bean,tradeType);
			AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
		}
	}
	
	public void notify(String tn,TradeTypeEnum tradeTypeEnum){
		NotifyBean bean = null;
		String merchNo = null;
		String tradeType = null;
		if(tradeTypeEnum==TradeTypeEnum.BATCHCOLLECTION){
			OrderCollectBatchDO collectBatch = orderCollectBatchDAO.getCollectBatchOrderByTn(tn);
			//BiztypeEnum biztypeEnum = BiztypeEnum.fromValue(collectBatch.getBiztype());
			//bean = beanFactory.getBean(tn,biztypeEnum);
			merchNo = collectBatch.getMerid();
			bean = beanFactory.getBatchTradeBean(tn, BusinessEnum.BATCH_COLLECTION);
			tradeType = "01";
		}else if(tradeTypeEnum==TradeTypeEnum.BATCHPAYMENT){
			OrderPaymentBatchDO paymentBatch = orderPaymentBatchDAO.getPaymentBatchOrderByTn(tn);
			merchNo = paymentBatch.getMerid();
			bean = beanFactory.getBatchTradeBean(tn, BusinessEnum.BATCH_PAYMENT);
			tradeType = "02";
		}
		
		if(bean!=null){
			AsynHttpRequestThread notifyThread = new AsynHttpRequestThread(merchNo, tn,bean,tradeType);
			AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
		}
	}
	
	public void queueNotfiy() {
		// TODO Auto-generated method stub
		log.info("【开始异步通知任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.NOTIFYQUEUE);
		log.info("【异步通知队列大小】"+queueSize);
		if(queueSize>0){
			for (int i = 0; i < queueSize; i++) {
				NotifyQueueBean notifyQueueBean = tradeQueueService.queuePop(TradeQueueEnum.NOTIFYQUEUE,NotifyQueueBean.class);
				log.info("【异步通知队列数据】"+JSON.toJSONString(notifyQueueBean));
				if(notifyQueueBean==null){
					continue;
				}
				if(Long.valueOf(DateUtil.getCurrentDateTime())<Long.valueOf(notifyQueueBean.getSendDateTime())){//没有到通知时间，不发送信息，重回队列
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知时间:"+notifyQueueBean.getSendDateTime()+" 未到发送时间】");
					tradeQueueService.addNotifyQueue(notifyQueueBean);
					continue;
				}
				//判断异步通知是否成功
				PojoTxnsNotifyTask asyncNotifyTask = txnsNotifyTaskDAO.getAsyncNotifyTask(notifyQueueBean.getTxnseqno());
				if("00".equals(asyncNotifyTask.getSendStatus())){
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知完成】");
					continue;
				}
				String tradeType = asyncNotifyTask.getTradeType();
				if("01".equals(tradeType)){//批量代收
					notify(notifyQueueBean.getTxnseqno(),TradeTypeEnum.BATCHCOLLECTION);
				}else if("02".equals(tradeType)){//批量代付
					notify(notifyQueueBean.getTxnseqno(),TradeTypeEnum.BATCHPAYMENT);
				}else if("03".equals(tradeType)){//实时代收
					notify(notifyQueueBean.getTxnseqno(),TradeTypeEnum.BATCHCOLLECTION);
				}else if("04".equals(tradeType)){//实时代付
					notify(notifyQueueBean.getTxnseqno(),TradeTypeEnum.BATCHCOLLECTION);
				}
				
			}
			
		}
		
	}

}
