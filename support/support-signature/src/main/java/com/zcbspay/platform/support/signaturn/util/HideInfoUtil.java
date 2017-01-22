/* 
 * MaskInfoUtil.java  
 * 
 * version TODO
 *
 * 2016年9月6日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.signaturn.util;

import java.util.Random;

/**
 * 隐藏部分信息
 *
 * @author houyong
 * @version
 * @date 2016年9月6日 上午9:13:02
 * @since 
 */
public class HideInfoUtil {
    /**
     * 隐藏账户  前6后4
     * @param accNo
     * @return
     */
    public static String hideAccNo(String accNo){
        if (StringUtil.isEmpty(accNo)) {
            return "";
        }
        String start=accNo.substring(0, 6);
        String end=accNo.substring(accNo.length()-4);
        StringBuffer middle=new StringBuffer();
        for (int i = 0; i < accNo.length()-10; i++) {
            middle.append("*");
        }
        return start+middle.toString()+end;
    }
    /**
     *隐藏账户名 取长度除4四舍五入的值作为前后的保留位置
     * @param accName
     * @return
     */
    public static String hideAccName(String accName){
        if (StringUtil.isEmpty(accName)) {
            return "";
        }
        int hideNum=(int) Math.round(accName.length()*1.0/4);
        String start=accName.substring(0,hideNum);
        String end=accName.substring(accName.length()-hideNum,accName.length());
        StringBuffer middle=new StringBuffer();
        for (int i = 0; i < accName.length()-hideNum*2; i++) {
            middle.append("*");
        }
        return start+middle.toString()+end;
    } 
    /**
     * 隐藏手机号 中间4位
     * @param phoneNo
     * @return
     */
    public static String hidePhoneNo(String phoneNo){
        if (StringUtil.isEmpty(phoneNo)) {
            return "";
        }
        String start=phoneNo.substring(0,3);
        String end=phoneNo.substring(phoneNo.length()-4);
        StringBuffer middle=new StringBuffer();
        for (int i = 0; i <4; i++) {
            middle.append("*");
        }
        return start+middle.toString()+end;
    } 
    /**
     * 隐藏姓名称 最后一位
     * @param name
     * @return
     */
    public static String hideName(String name){
        if (StringUtil.isEmpty(name)) {
            return "";
        }
        String end=name.substring(name.length()-1);
        StringBuffer middle=new StringBuffer();
        for (int i = 0; i <name.length()-1; i++) {
            middle.append("*");
        }
        return middle.toString()+end;
    } 
    
    /**
     * 隐藏身份证号码  前6后4
     * @param accNo
     * @return
     */
    public static String hideCertifNo(String certNo){
        if (StringUtil.isEmpty(certNo)) {
            return "";
        }
        String start=certNo.substring(0, 6);
        String end=certNo.substring(certNo.length()-4);
        StringBuffer middle=new StringBuffer();
        for (int i = 0; i < certNo.length()-10; i++) {
            middle.append("*");
        }
        return start+middle.toString()+end;
    }
    
    /**
     * 随机数
     * @param max
     * @return 
     */
    public static int randomIndex(int max){
        Random random=new Random();
        int result=random.nextInt(max);
        return result;
    }
}
