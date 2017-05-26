package com.zcbspay.platform.support.task.enums;

/**
 * 
  * @ClassName: BiztypeEnum
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:05:54
  *
 */
public enum BiztypeEnum {
	BE000001("000001"),
	BE000002("000002"),
	BE000003("000003"),
	UNKNOW("");
	private String code;

	private BiztypeEnum(String code){
	    this.code = code;
	}

	public static BiztypeEnum fromValue(String value) {
	    for(BiztypeEnum status:values()){
	        if(status.code.equals(value)){
	            return status;
	        }
	    }
	    return UNKNOW;
	}

	public String getCode(){
	    return code;
	}
}
