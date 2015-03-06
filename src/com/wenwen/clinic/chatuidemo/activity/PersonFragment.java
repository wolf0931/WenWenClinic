/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wenwen.clinic.chatuidemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.debug.DebugLog;

/**
 * 个人界面
 * 
 * @author Administrator
 * 
 */
public class PersonFragment extends Fragment implements OnClickListener {
    private final String TAG = "PersonFragment";
    private RelativeLayout latyout_personal;
    private Button btn_open_vip;
    private RelativeLayout latyout_accout_pay;
    private RelativeLayout layout_health_data;
    private RelativeLayout layout_change_pwd;
    private RelativeLayout layout_check_updates;
    private RelativeLayout layout_advice;
    private RelativeLayout layout_statement;
    private Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DebugLog.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_tab_person, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onClick(View v) {
        if (v == latyout_personal) {

        } else if (v == btn_open_vip) {

        } else if (v == latyout_accout_pay) {

        } else if (v == layout_health_data) {
            Intent intent = new Intent(getActivity(), PersonalData.class);
            startActivity(intent);
        } else if (v == layout_change_pwd) {
            Intent intent = new Intent(getActivity(), ChangPwd.class);
            startActivity(intent);
        } else if (v == layout_check_updates) {
         // 更新
            UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
            UmengUpdateAgent.setUpdateAutoPopup(false);
            UmengUpdateAgent.setUpdateListener(updateListener);
            UmengUpdateAgent.forceUpdate(getActivity());
        } else if (v == layout_advice) {
            Intent intent = new Intent(getActivity(), Advice.class);
            startActivity(intent);
        } else if (v == layout_statement) {
            Intent intent = new Intent(getActivity(), StateMent.class);
            startActivity(intent);
        } else if (v == btn_logout) {
            logout();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    private void init() {
        // TODO Auto-generated method stub
        latyout_personal = (RelativeLayout) getView().findViewById(
                R.id.latyout_personal);
        latyout_personal.setOnClickListener(this);

        btn_open_vip = (Button) getView().findViewById(R.id.btn_open_vip);
        btn_open_vip.setOnClickListener(this);

        latyout_accout_pay = (RelativeLayout) getView().findViewById(
                R.id.latyout_accout_pay);
        latyout_accout_pay.setOnClickListener(this);

        layout_health_data = (RelativeLayout) getView().findViewById(
                R.id.layout_health_data);
        layout_health_data.setOnClickListener(this);

        layout_change_pwd = (RelativeLayout) getView().findViewById(
                R.id.layout_change_pwd);
        layout_change_pwd.setOnClickListener(this);

        layout_check_updates = (RelativeLayout) getView().findViewById(
                R.id.layout_check_updates);
        layout_check_updates.setOnClickListener(this);

        layout_advice = (RelativeLayout) getView().findViewById(
                R.id.layout_advice);
        layout_advice.setOnClickListener(this);

        layout_statement = (RelativeLayout) getView().findViewById(
                R.id.layout_statement);
        layout_statement.setOnClickListener(this);

        btn_logout = (Button) getView().findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onResume");
        super.onResume();
    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("正在退出登陆..");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        ((MainActivity) getActivity()).finish();
                        startActivity(new Intent(getActivity(),
                                LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
    
    UmengUpdateListener updateListener = new UmengUpdateListener() {
        @Override
        public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
            switch (updateStatus) {
            case 0: // has update
                UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
                break;
            case 1: // has no update
              Toast.makeText(getActivity(), "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case 3: // time out
              Toast.makeText(getActivity(), "网络连接超时", Toast.LENGTH_SHORT).show();
                break;
            case 4: // is updating
                break;
            }

        }
    };
}
