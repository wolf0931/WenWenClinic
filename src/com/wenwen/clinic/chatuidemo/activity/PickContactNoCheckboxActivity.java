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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wenwen.clinic.chatuidemo.Constant;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.adapter.ContactAdapter;
import com.wenwen.clinic.chatuidemo.db.UserDao;
import com.wenwen.clinic.chatuidemo.domain.MyUser;

public class PickContactNoCheckboxActivity extends BaseActivity {

    private ListView listView;
    protected ContactAdapter contactAdapter;
    protected List<MyUser> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact_no_checkbox);
        listView = (ListView) findViewById(R.id.list);
        contactList = new ArrayList<MyUser>();
        // 获取设置contactlist
        getContactList();
        // 设置adapter
        contactAdapter = new ContactAdapter(this, contactList);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(position);
            }
        });

    }

    protected void onListItemClick(int position) {
        if (position != 0) {
            setResult(RESULT_OK, new Intent().putExtra("username", contactList.get(position).getAccount_name()));
            finish();
        }
    }

    public void back(View view) {
        finish();
    }

    private void getContactList() {
        contactList.clear();
        UserDao userDao = new UserDao(this);
        userDao.getContactList();
//      Map<String, User> users = DemoApplication.getInstance().getContactList();
        Map<String, MyUser> users = userDao.getContactList();
        Iterator<Entry<String, MyUser>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, MyUser> entry = iterator.next();
            if (!entry.getKey().equals(Constant.NEW_FRIENDS_USERNAME) && !entry.getKey().equals(Constant.GROUP_USERNAME))
                contactList.add(entry.getValue());
        }
        // 排序
        Collections.sort(contactList, new Comparator<MyUser>() {

            @Override
            public int compare(MyUser lhs, MyUser rhs) {
                return (lhs.getAccount_name()).compareTo(rhs.getAccount_name());
            }
        });
    }

}
