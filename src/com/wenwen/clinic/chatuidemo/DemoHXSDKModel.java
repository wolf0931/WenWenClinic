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
package com.wenwen.clinic.chatuidemo;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.wenwen.clinic.applib.model.DefaultHXSDKModel;
import com.wenwen.clinic.chatuidemo.db.DbOpenHelper;
import com.wenwen.clinic.chatuidemo.db.SickUserDao;
import com.wenwen.clinic.chatuidemo.db.UserDao;
import com.wenwen.clinic.chatuidemo.domain.MyUser;
import com.wenwen.clinic.chatuidemo.domain.ScikUser;

public class DemoHXSDKModel extends DefaultHXSDKModel{

    public DemoHXSDKModel(Context ctx) {
        super(ctx);
        // TODO Auto-generated constructor stub
    }

    // demo will use HuanXin roster
    public boolean getUseHXRoster() {
        // TODO Auto-generated method stub
        return true;
    }
    
    // demo will switch on debug mode
    public boolean isDebugMode(){
        return true;
    }
    
    public boolean saveContactList(List<MyUser> contactList) {
        // TODO Auto-generated method stub
        UserDao dao = new UserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public boolean saveSickContactList(List<ScikUser> contactList) {
        // TODO Auto-generated method stub
        SickUserDao dao = new SickUserDao(context);
        dao.saveSickContactList(contactList);
        return true;
    }
    public Map<String, MyUser> getContactList() {
        // TODO Auto-generated method stub
        UserDao dao = new UserDao(context);
        return dao.getContactList();
    }
    public Map<String, ScikUser> getSickContactList() {
        // TODO Auto-generated method stub
        SickUserDao dao = new SickUserDao(context);
        return dao.getSickContactList();
    }
    public void closeDB() {
        // TODO Auto-generated method stub
        DbOpenHelper.getInstance(context).closeDB();
    }
}
