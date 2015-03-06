package com.wenwen.clinic.chatuidemo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.exceptions.EaseMobException;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.adapter.ChatAllHistoryAdapter;
import com.wenwen.clinic.debug.DebugLog;

public class MyConsultation extends BaseActivity {
    private final String TAG = "ClinicOrder";
    private InputMethodManager inputMethodManager;
    private ListView listView;
    private ChatAllHistoryAdapter adapter;
    public RelativeLayout errorItem;
    public TextView errorText;
    private boolean hidden;
    private List<EMConversation> conversationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.myconsultation);
        DemoApplication.register(this);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        errorItem = (RelativeLayout)findViewById(R.id.rl_error_item);
        errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);
        conversationList = loadConversationsWithRecentChat();
        listView = (ListView)findViewById(R.id.list);
        adapter = new ChatAllHistoryAdapter(MyConsultation.this, 1, conversationList);
        // 设置adapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                EMConversation conversation = adapter.getItem(position);
                String username = conversation.getUserName();
                DebugLog.i(TAG, "username==="+username);
                String acountname =null;
                String toacountname =null;
                try {
                    acountname = conversation.getLastMessage().getStringAttribute("accountname");
                    toacountname = conversation.getLastMessage().getStringAttribute("toaccountname");
                if (username.equals(DemoApplication.getInstance().getUserName()))
                    Toast.makeText(MyConsultation.this, "不能和自己聊天", 0).show();
                else {
                    Intent intent = new Intent(MyConsultation.this,ChatActivity.class);
                    intent.putExtra("username", username);
                    if (conversation.getLastMessage().getStringAttribute("type").equals("2")) {
                        intent.putExtra("accountname", toacountname);
                    }else{
                        intent.putExtra("accountname", acountname);
                    }
                    intent.putExtra("type", "1");
                    startActivity(intent);
                    }
                } catch (EaseMobException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        listView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 隐藏软键盘
                hideSoftKeyboard();
                return false;
            }

        });
    }
    void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 获取所有会话
     * 
     * @param context
     * @return
     */
    private List<EMConversation> loadConversationsWithRecentChat() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
        List<EMConversation> list = new ArrayList<EMConversation>();
        // 过滤掉messages seize为0的conversation
        for (EMConversation conversation : conversations.values()) {
            if (conversation.getAllMessages().size() != 0)
                list.add(conversation);
        }
        // 排序
        sortConversationByLastChatTime(list);
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     * 
     * @param usernames
     */
    private void sortConversationByLastChatTime(List<EMConversation> conversationList) {
        Collections.sort(conversationList, new Comparator<EMConversation>() {
            @Override
            public int compare(final EMConversation con1, final EMConversation con2) {

                EMMessage con2LastMessage = con2.getLastMessage();
                EMMessage con1LastMessage = con1.getLastMessage();
                if (con2LastMessage.getMsgTime() == con1LastMessage.getMsgTime()) {
                    return 0;
                } else if (con2LastMessage.getMsgTime() > con1LastMessage.getMsgTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
    /**
     * 刷新页面
     */
    public void refresh() {
        conversationList.clear();
        conversationList.addAll(loadConversationsWithRecentChat());
        adapter.notifyDataSetChanged();
    }
    public void back(View view) {
        finish();
    }
}
