package com.wenwen.clinic.chatuidemo.domain;

public class ClinicOrderInfo {
    /*
     * order_id 预约id。后续操作有用。 
     * order_type 预约类型【1 图文，2 电话，3 门诊】
     * account_id 用户id【大众版看到的是医生信息， 医生版看到的是大众信息】 
     * account_name 用户名称 【大众版看到的是医生信息， 医生版看到的是大众信息】
     * account_image 头像 【大众版看到的是医生信息， 医生版看到的是大众信息】
     */
    private String order_id;
    private String order_type;
    private String account_id;
    private String account_name;
    private String account_image;
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getOrder_type() {
        return order_type;
    }
    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
    public String getAccount_id() {
        return account_id;
    }
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
    public String getAccount_name() {
        return account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    public String getAccount_image() {
        return account_image;
    }
    public void setAccount_image(String account_image) {
        this.account_image = account_image;
    }
    @Override
    public String toString() {
        return "ClinicOrder [order_id=" + order_id + ", order_type="
                + order_type + ", account_id=" + account_id + ", account_name="
                + account_name + ", account_image=" + account_image + "]";
    }
    
    
}
