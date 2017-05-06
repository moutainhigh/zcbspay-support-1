package com.zcbspay.platform.support.cipher;



import junit.framework.TestCase;

import org.junit.Test;

import com.westone.pboc.mina.client.ClientThreadPool;
import com.westone.pboc.service.imp.HSMZCBSApiServiceImp;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	static HSMZCBSApiServiceImp imp = null;
	static {
		imp =  new HSMZCBSApiServiceImp();
	}
	private static ClientThreadPool connectPool =  ClientThreadPool.getInstance();
	@Test
	public void test(){
		try{
			//初始化连接
			connectPool.initClient();
			testHsmGenerateMAC9_9();
		}catch(Exception e){
			
			System.out.println("??????????Exception :"+ e);
		}
	}
	/**
	 * 测试计算MAC
	 */ 
	public static void testHsmGenerateMAC9_9() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 测试计算MAC_ANXI9.9");
		int MKindex = 1;//used to encrypt mac plaindata
		
		String cipherMAKData = "E408C01308B5DFD1";
		int datalen = 16;
		String data = "1234567890ABCDEF";
		//String data = "ABCDEF0123456789";
		
		try {
			
			String mac = imp.hsmGenerateMAC9_9(MKindex, cipherMAKData, datalen, data);
			System.out.println("计算MAC成功,MAC值："+mac.toUpperCase().substring(0,8));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
