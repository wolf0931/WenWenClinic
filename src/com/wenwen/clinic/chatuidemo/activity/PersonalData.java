package com.wenwen.clinic.chatuidemo.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.utils.HttpClientRequest;
import com.wenwen.clinic.chatuidemo.utils.Urls;
import com.wenwen.clinic.debug.DebugLog;

public class PersonalData extends BaseActivity implements OnClickListener {
    private final String TAG = "PersonalData";
    private EditText username; //名字
    private RadioGroup sex; //性别
    private RadioButton male,female;
    private RadioGroup marital_status;//婚姻状况
    private RadioButton status1,status2; //status1已婚
    private RadioGroup bady;//怀孕史
    private RadioButton bady1,bady2;//bady1 是
    private EditText work;//本人职业
    private EditText ed_zq;//月经周期
    private EditText ed_jz;//男方精子
    private EditText tv_fsh_txt;//基础FSH值
    private EditText tv_lh_txt;//基础LH值
    private EditText tv1_ed1;//不孕原因
    private EditText tv_ivf_txt;//曾经做过IVF
    private EditText female_work;//配儿职业
    private RadioGroup history;//有无遗传史
    private RadioButton history1,history2;//history1 有
    private RadioGroup allergy;//有无过敏史  
    private RadioButton allergy1,allergy2;//allergy1有
    private EditText readme;//自述
    
    private String uid;
    private ProgressDialog pd;
    private int sexint =1;
    private int statusint =1;
    private int badyint =1;
    private int historyint =0;
    private int allergyint =0;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.activity_sickpersondata);
        init();
        initdata();
        setdate();
    }

    private void initdata() {
        // TODO Auto-generated method stub
        pd = new ProgressDialog(PersonalData.this);
        pd.setMessage("正在获取...");
        RequestParams params = new RequestParams();
        params.put("uid", DemoApplication.getInstance().getUserUid());
        params.put("flag", "2");
        HttpClientRequest.post(Urls.GETUSERDETAILS, params, 3000,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        pd.show();
                    }
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        /*
                         *  * @ret int 0 参数有误 1 成功 -1 用户不存在 -2 密码不正确 -3 未知错误
                         *  ﻿{"ret":"1","account_id":"1","account_username":"13646875594",
                         *  "account_name":"\u674e\u56db","account_image":"","account_sex":"1",
                         *  "account_wedding":"0","account_occupation_own":"\u533b\u751f",
                         *  "account_heredity":"0","account_irritability":"0","account_info":"\u674e\u56db",
                         *  "account_report_url":"",
                         *  "account_birth_date":"0000-00-00 00:00:00"}
                         *  
                         */
                        try {
                            String res = new String(arg2);
                            DebugLog.i("res", res);
                            final JSONObject result = new JSONObject(res);
                            switch (Integer.valueOf(result.getString("ret"))) {
                            case 1:
                                username.setText(result.getString("account_name"));
                                if (result.getString("account_sex").equals("1")) {
                                    male.setChecked(true);
                                }else {
                                    female.setChecked(true);
                                }
                                if (result.getString("account_wedding").equals("1")) {
                                    status1.setChecked(true);
                                }else {
                                    status2.setChecked(true);
                                }
                                
                                if (result.getString("account_heredity").equals("1")) {
                                    history1.setChecked(true);
                                }else {
                                    history2.setChecked(true);
                                }
                                if (result.getString("account_irritability").equals("1")) {
                                    allergy1.setChecked(true);
                                }else {
                                    allergy2.setChecked(true);
                                }
                                readme.setText(result.getString("account_info"));
                                work.setText(result.getString("account_occupation_own"));
                                break;
                            default:
                                break;

                            }
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                            Throwable arg3) {
                        // TODO Auto-generated method stub

                    }
                });
    
    
    
    }

    private void setdate() {
        // TODO Auto-generated method stub
        sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==male.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        bady.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    if (checkedId ==bady1.getId()) {
                        badyint = 1;
                    }else{
                        badyint = 0;
                    }
                }
            });
        marital_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==status1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        
        history.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==history1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        
        allergy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==allergy1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
    }

    private void init() {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        //tag = (TextView) findViewById(R.id.tag);
        username = (EditText) findViewById(R.id.username);
        sex = (RadioGroup) findViewById(R.id.sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        marital_status = (RadioGroup) findViewById(R.id.marital_status);
        status1 = (RadioButton) findViewById(R.id.status1);
        status2 = (RadioButton) findViewById(R.id.status2);
        bady = (RadioGroup) findViewById(R.id.bady);
        bady1 = (RadioButton) findViewById(R.id.bady1);
        bady2 = (RadioButton) findViewById(R.id.bady2);
        work = (EditText) findViewById(R.id.work);
        ed_zq = (EditText) findViewById(R.id.ed_zq);
        ed_jz = (EditText) findViewById(R.id.ed_jz);
        tv_fsh_txt = (EditText) findViewById(R.id.tv_fsh_txt);
        tv_lh_txt = (EditText) findViewById(R.id.tv_lh_txt);
        tv1_ed1 = (EditText) findViewById(R.id.tv1_ed1);
        tv_ivf_txt = (EditText) findViewById(R.id.tv_ivf_txt);
        female_work = (EditText) findViewById(R.id.female_work);
        history = (RadioGroup) findViewById(R.id.history);
        history1 = (RadioButton) findViewById(R.id.history1);
        history2 = (RadioButton) findViewById(R.id.history2);
        allergy = (RadioGroup) findViewById(R.id.allergy);
        allergy1 = (RadioButton) findViewById(R.id.allergy1);
        allergy2 = (RadioButton) findViewById(R.id.allergy2);
        readme = (EditText) findViewById(R.id.readme);
    
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
        // case Constants.ActionCode.ACT_DEPARTMENT_SELECT:
        // laboratory.setText(Constants.department[data.getExtras().getInt("department")]);
        // break;
        // case Constants.ActionCode.ACT_HOSPITAL_SELECT:
        // hospital.setText(Constants.hostpital[data.getExtras().getInt("hospital")]);
        // break;
        default:
            break;
        }
    }

    public void sub(View view) {
        final String accountname = username.getText().toString().trim();
        final String workname = work.getText().toString().trim();
       // final String female_work = et_female_work.getText().toString().trim();
        final String readmetxt = readme.getText().toString().trim();

        if (TextUtils.isEmpty(accountname)) {
            Toast.makeText(this, "名字不能为空！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            /*
             * @param: userid int name string //姓名 sex int //0 女， 1 男 birth
             * datetime//出生日期 wed_date datetime//结婚日期 weding int //婚否
             * occupation_own string //本人职业 occupation_partner string 配偶职业
             * heredity int //有无遗传史 irritability; int //有无过敏史 info string //用户自述
             */
            final ProgressDialog pd = new ProgressDialog(PersonalData.this);
            pd.setMessage("完善中...");
            RequestParams params = new RequestParams();
            params.put("userid", DemoApplication.getInstance().getUserUid());
            params.put("name", accountname);
            params.put("occupation_own", workname);
            params.put("occupation_partner", "");
            params.put("info", readmetxt);
            params.put("sex", sexint);
            params.put("weding", statusint);
            params.put("heredity", historyint);
            params.put("irritability", allergyint);
            HttpClientRequest.post(Urls.UPDATEUSER, params, 3000,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            // TODO Auto-generated method stub
                            super.onStart();
                            pd.show();
                        }

                        @Override
                        public void onSuccess(int arg0, Header[] arg1,
                                byte[] arg2) {
                            // TODO Auto-generated method stub
                            try {
                                String res = new String(arg2);
                                DebugLog.i(TAG, "返回结果" + res);
                                JSONObject result = new JSONObject(res);
                                switch (Integer.valueOf(result.getString("ret"))) {
                                case -1:
                                    Toast.makeText(PersonalData.this, "用户不存在",Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(PersonalData.this, "成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;
                                case 0:
                                case -2:
                                    Toast.makeText(PersonalData.this, "失败",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            super.onFinish();
                            pd.dismiss();
                        }

                        @Override
                        public void onFailure(int arg0, Header[] arg1,
                                byte[] arg2, Throwable arg3) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
    }

    public void back(View view) {
        finish();
    }

}
