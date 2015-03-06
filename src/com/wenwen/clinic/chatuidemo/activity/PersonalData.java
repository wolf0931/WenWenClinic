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
    private EditText et_username, et_work, et_female_work, et_readme;
    private RadioGroup rg_sex,rg_marital_status,rg_history,rg_allergy; //allergy过敏 history遗传
    private RadioButton rb_male,rb_female;
    private RadioButton rb_status1,rb_status2;
    private RadioButton rb_history1,rb_history2;
    private RadioButton rb_allergy1,rb_allergy2;
    private int sexint =1;
    private int statusint =1;
    private int historyint =0;
    private int allergyint =0;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.activity_persondata);
        rg_sex = (RadioGroup) findViewById(R.id.sex);
        rb_male = (RadioButton) findViewById(R.id.male);
        rb_female = (RadioButton) findViewById(R.id.female);
     
        rg_marital_status = (RadioGroup) findViewById(R.id.marital_status);
        rb_status1 = (RadioButton) findViewById(R.id.status1);
        rb_status2 = (RadioButton) findViewById(R.id.status2);
        
        rg_history = (RadioGroup) findViewById(R.id.history);
        rb_history1 = (RadioButton) findViewById(R.id.history1);
        rb_history2 = (RadioButton) findViewById(R.id.history2);
        
        rg_allergy = (RadioGroup) findViewById(R.id.allergy);
        rb_allergy1 = (RadioButton) findViewById(R.id.allergy1);
        rb_allergy2 = (RadioButton) findViewById(R.id.allergy2);
        
        et_username = (EditText) findViewById(R.id.username);
        et_work = (EditText) findViewById(R.id.work);
        et_female_work = (EditText) findViewById(R.id.female_work);
        et_readme = (EditText) findViewById(R.id.readme);
        rg_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==rb_male.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        
        rg_marital_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==rb_status1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        
        rg_history.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==rb_history1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
        
        rg_allergy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId ==rb_allergy1.getId()) {
                    sexint = 1;
                }else{
                    sexint = 0;
                }
            }
        });
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
        final String username = et_username.getText().toString().trim();
        final String work = et_work.getText().toString().trim();
        final String female_work = et_female_work.getText().toString().trim();
        final String readme = et_readme.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
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
            params.put("name", username);
            params.put("occupation_own", work);
            params.put("occupation_partner", female_work);
            params.put("info", readme);
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
