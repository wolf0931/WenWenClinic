package com.wenwen.clinic.chatuidemo.domain;

import java.io.Serializable;

public class AcountInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String account_image;
    private String account_id;
    private String account_job;
    private String account_info;
    private String account_username;
    private String account_hospital;
    private String account_department;
    private String account_name;

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

}