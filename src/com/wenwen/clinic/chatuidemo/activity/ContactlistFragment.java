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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.adapter.ContactAdapter;
import com.wenwen.clinic.chatuidemo.domain.MyUser;
import com.wenwen.clinic.chatuidemo.domain.User;
import com.wenwen.clinic.chatuidemo.utils.Constants;
import com.wenwen.clinic.chatuidemo.utils.HttpClientRequest;
import com.wenwen.clinic.chatuidemo.utils.Urls;

/**
 * 联系人列表页
 * 
 */
public class ContactlistFragment extends Fragment {
	private ContactAdapter adapter;
	private List<MyUser> contactList;
	private ListView listView;
	private InputMethodManager inputMethodManager;
	private Gson gson = new Gson();
    private ProgressDialog pd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		listView = (ListView) getView().findViewById(R.id.list);
		//黑名单列表
		contactList = new ArrayList<MyUser>();
		// 获取设置contactlist
		getContactList();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ColleaguesInfo.class).putExtra("uid", contactList.get(position).getAccount_id()));

			}
		});
		listView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏软键盘
				if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getActivity().getCurrentFocus() != null)
						inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});

		ImageView addContactView = (ImageView) getView().findViewById(R.id.iv_new_contact);
		// 进入添加好友页
		addContactView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//startActivity(new Intent(getActivity(), AddContactActivity.class));
			    startActivityForResult(new Intent(getActivity(),
                        AddContactActivity.class),
                        Constants.ActionCode.ACT_FIND);
			}
		});
	}
	  @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	        // 注册成功时返回 处于登录状态
	        case Constants.ActionCode.ACT_FIND:
	            if (data != null) {
	                contactList.add((MyUser) data.getExtras().get("myuser"));
	                adapter.notifyDataSetChanged();
	            }
	            break;
	        }
	    }
	/**
	 * 获取联系人列表，并过滤掉黑名单和排序
	 */
	private void getContactList() {
        contactList.clear();
        try {
            RequestParams params = new RequestParams();
            params.put("uid", DemoApplication.getInstance().getUserUid());
            params.put("flag", "0");
            params.put("pageindex", "1");
            params.put("pagesize", "20");
            HttpClientRequest.post(Urls.FRIENDSGET, params, 3000,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            // TODO Auto-generated method stub
                            super.onStart();
                        }
                        @SuppressWarnings("null")
                        @Override
                        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                            // TODO Auto-generated method stub
                            String res = new String(arg2);
                            System.err.println("返回结果" + res);
                            JsonParser parser = new JsonParser();
                            JsonArray Jarray = parser.parse(res).getAsJsonArray();
                            MyUser myuser =null;
                            User user = null;
                            for (JsonElement obj : Jarray) {
                                myuser = gson.fromJson(obj, MyUser.class);
                                contactList.add(myuser);
                            }
                            // 排序
                            Collections.sort(contactList, new Comparator<MyUser>() {
                                @Override
                                public int compare(MyUser lhs, MyUser rhs) {
                                    return lhs.getAccount_username().compareTo(rhs.getAccount_username());
                                }
                            });
                            
                            adapter = new ContactAdapter(getActivity(), contactList);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            super.onFinish();
                        }

                        @Override
                        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                Throwable arg3) {
                            // TODO Auto-generated method stub

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
