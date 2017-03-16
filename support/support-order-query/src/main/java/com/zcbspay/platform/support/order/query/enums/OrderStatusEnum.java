package com.zcbspay.platform.support.order.query.enums;

public enum OrderStatusEnum {
	INITIAL("01"),
	PAYING("02"),
	FAILED("03"),
	OVERTIME("04"),
	SUCCESS("00"),
	UNKNOW("");
	private String status;
    
    private OrderStatusEnum(String status){
        this.status = status;
    }
    
    public static OrderStatusEnum fromValue(String value) {
        for(OrderStatusEnum enums:values()){
            if(enums.status.equals(value)){
                return enums;
            }
        }
        return UNKNOW;
    }
    
    public String value(){
        return status;
    }
}
