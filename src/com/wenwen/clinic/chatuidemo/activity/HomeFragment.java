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

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.wenwen.clinic.chatui.adv.Adv;
import com.wenwen.clinic.chatui.adv.AdvClient;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.utils.StringUtil;
import com.wenwen.clinic.debug.DebugLog;

public class HomeFragment<HomeLine> extends Fragment implements OnClickListener {
    private final String TAG = "HomeFragment";
    private RelativeLayout latyout_home_line;
    private RelativeLayout latyout_home_phone;
    private RelativeLayout latyout_home_mz;
    private TextView unread_line_number;
    private TextView unread_phone_number;
    private TextView unread_mz_number;
    private AdvClient client;
    private RelativeLayout latyout_mz,latyout_line;
    private ImageView account_img,account_img2;
    private NewMessageBroadcastReceiver msgReceiver;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DebugLog.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_tab_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        client = new AdvClient(getActivity());
        client.initClientById(R.id.scroll_layout, R.id.page_control,R.id.page_title);
        List<Adv> data = new ArrayList<Adv>();
        for (int i = 0; i < 4; i++) {
            Adv adv = new Adv();
            //adv.setMessage("adv Num is:" + (i + 1));
            if (i == 0) {
                adv.setMessage(StringUtil.getSubString("贝克汉姆空降北京,开始了他“中超形象大使”之旅的第一站", 14));
                adv.setDefaultDrawable(R.drawable.qwee1);
            }
            if (i == 1) {
                adv.setMessage(StringUtil.getSubString("青岛农贸市场摊主围堵城管 双方冲突", 14));
                adv.setDefaultDrawable(R.drawable.qwee2);
            }
            if (i == 2) {
                adv.setMessage(StringUtil.getSubString("世界睡眠日，中国小姐的睡姿优美", 14));
                adv.setDefaultDrawable(R.drawable.qwee3);
            }
            if (i == 3) {
                adv.setMessage(StringUtil.getSubString("亚洲杯预选赛,中国1：0胜伊拉克", 14));
                adv.setDefaultDrawable(R.drawable.qwee4);
            }
            data.add(adv);
        }
        client.setData(data);
        client.start();
        setText();

    }
    private void setText() {
        // TODO Auto-generated method stub
        unread_line_number.setText("");
        unread_phone_number.setText("");
        unread_mz_number.setText("");
        unread_line_number.setVisibility(View.GONE);
        unread_phone_number.setVisibility(View.GONE);
        unread_mz_number.setVisibility(View.GONE);
    }

    private void init() {
        // TODO Auto-generated method stub
        latyout_home_line = (RelativeLayout) getView().findViewById(
                R.id.latyout_home_line);
        latyout_home_line.setOnClickListener(this);
        latyout_home_phone = (RelativeLayout) getView().findViewById(
                R.id.latyout_home_phone);
        latyout_home_phone.setOnClickListener(this);
        latyout_home_mz = (RelativeLayout) getView().findViewById(
                R.id.latyout_home_mz);
        latyout_home_mz.setOnClickListener(this);
        
        account_img2 = (ImageView) getView().findViewById(R.id.account_img2);
        account_img2.setOnClickListener(this);
        account_img = (ImageView) getView().findViewById(R.id.account_img);
        account_img.setOnClickListener(this);
        unread_line_number = (TextView) getView().findViewById(
                R.id.unread_line_number);

        unread_phone_number = (TextView) getView().findViewById(
                R.id.unread_phone_number);

        unread_mz_number = (TextView) getView().findViewById(
                R.id.unread_mz_number);
        
        latyout_mz = (RelativeLayout) getView().findViewById(R.id.latyout_mz);
        latyout_mz.setOnClickListener(this);
        
        latyout_line = (RelativeLayout) getView().findViewById(R.id.latyout_line);
        latyout_line.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // 注册一个接收消息的BroadcastReceiver
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager
                .getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        getActivity().registerReceiver(msgReceiver, intentFilter);
        
    }
    /**
     * 新消息广播接收者
     * 
     * 
     */
    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
            String from = intent.getStringExtra("from");
            // 消息id
            String msgId = intent.getStringExtra("msgid");
            DebugLog.i(TAG, "新消息广播接收者");
            DebugLog.i(TAG, from);
            DebugLog.i(TAG, msgId);
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            // 2014-10-22 修复在某些机器上，在聊天页面对方发消息过来时不立即显示内容的bug
            if (ChatActivity.activityInstance != null) {
                if (message.getChatType() == ChatType.GroupChat) {
                    if (message.getTo().equals(
                            ChatActivity.activityInstance.getToChatUsername()))
                        return;
                } else {
                    if (from.equals(ChatActivity.activityInstance
                            .getToChatUsername()))
                        return;
                }
            }

            // 注销广播接收者，否则在ChatActivity中会收到这个广播
            abortBroadcast();
            
            ((BaseActivity) getActivity()).notifyNewMessage(message);
            
            if (DemoApplication.getActivityByName("MyConsultation") !=null) {
                ((MyConsultation) DemoApplication.getActivityByName("MyConsultation")).refresh();
             }

        }
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onResume");
        super.onResume();
    }
    @Override
    public void onClick(View v) {
        if (v == account_img) {
            Intent intent = new Intent(getActivity(), MyConsultation.class); 
            startActivity(intent);
        } else if (v == latyout_home_phone) {

        } else if (v == account_img2) {
            Intent intent = new Intent(getActivity(), ClinicOrder.class); 
            startActivity(intent);
        }else if(v == latyout_mz){
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            Intent intent = new Intent(getActivity(), DoctorsAppointment.class); 
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(v == latyout_line){
            Bundle bundle = new Bundle();
            bundle.putInt("type", 2);
            Intent intent = new Intent(getActivity(), DoctorsAppointment.class); 
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
     // 注销广播接收者
        try {
            ((BaseActivity) getActivity()).unregisterReceiver(msgReceiver);
        } catch (Exception e) {
        }
    }
}
