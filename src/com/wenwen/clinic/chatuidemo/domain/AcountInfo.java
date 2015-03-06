package com.wenwen.clinic.chatuidemo.domain;

import java.io.Serializable;

public class AcountInfo implements Serializable {
    /**{"ret":"1","account_id":"1","account_username":"13646875594",
     *  "account_name":"\u674e\u56db","account_image":"","account_sex":"1",
     *  "account_wedding":"0","account_occupation_own":"\u533b\u751f",
     *  "account_heredity":"0","account_irritability":"0","account_info":"\u674e\u56db",
     *  "account_report_url":"",
     *  "account_birth_date":"0000-00-00 00:00:00"}
     * 
     */
    private static final long serialVersionUID = 1L;
    private String account_id;
    private String account_username;
    private String account_name;
    private String account_image;
    private String account_wedding;
    private String account_sex;
    private String account_occupation_own;
    private String account_heredity;
    private String account_irritability;
    private String account_report_url;
    private String account_job;
    private String account_info;
    private String account_hospital;
    private String account_department;

    public String getAccount_image() {
        return account_image;
    }

    public void setAccount_image(String account_image) {
        this.account_image = account_image;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_job() {
        return account_job;
    }

    public void setAccount_job(String account_job) {
        this.account_job = account_job;
    }

    public String getAccount_info() {
        return account_info;
    }

    public void setAccount_info(String account_info) {
        this.account_info = account_info;
    }

    public String getAccount_username() {
        return account_username;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;
    }

    public String getAccount_hospital() {
        return account_hospital;
    }

    public void setAccount_hospital(String account_hospital) {
        this.account_hospital = account_hospital;
    }

    public String getAccount_department() {
        return account_department;
    }

    public void setAccount_department(String account_department) {
        this.account_department = account_department;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_wedding() {
        return account_wedding;
    }

    public void setAccount_wedding(String account_wedding) {
        this.account_wedding = account_wedding;
    }

    public String getAccount_sex() {
        return account_sex;
    }

    public void setAccount_sex(String account_sex) {
        this.account_sex = account_sex;
    }

    public String getAccount_occupation_own() {
        return account_occupation_own;
    }

    public void setAccount_occupation_own(String account_occupation_own) {
        this.account_occupation_own = account_occupation_own;
    }

    public String getAccount_heredity() {
        return account_heredity;
    }

    public void setAccount_heredity(String account_heredity) {
        this.account_heredity = account_heredity;
    }

    public String getAccount_irritability() {
        return account_irritability;
    }

    public void setAccount_irritability(String account_irritability) {
        this.account_irritability = account_irritability;
    }

    public String getAccount_report_url() {
        return account_report_url;
    }

    public void setAccount_report_url(String account_report_url) {
        this.account_report_url = account_report_url;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    
}