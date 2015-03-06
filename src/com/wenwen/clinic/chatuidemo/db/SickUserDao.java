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
package com.wenwen.clinic.chatuidemo.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.wenwen.clinic.chatuidemo.Constant;
import com.wenwen.clinic.chatuidemo.domain.ScikUser;

public class SickUserDao {
	public static final String TABLE_NAME = "sickuers";
	public static final String COLUMN_NAME_ID = "username";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_NAME_IS_STRANGER = "is_stranger";

	private DbOpenHelper dbHelper;

	public SickUserDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveSickContactList(List<ScikUser> sickcontactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
			for (ScikUser user : sickcontactList) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_NAME_ID, user.getUsername());
				if(user.getNick() != null)
					values.put(COLUMN_NAME_NICK, user.getNick());
				db.replace(TABLE_NAME, null, values);
			}
		}
	}
	/**
     * 获取好友list
     * 
     * @return
     */
    public Map<String, ScikUser> getSickContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, ScikUser> sickusers = new HashMap<String, ScikUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK));
                ScikUser user = new ScikUser();
                user.setUsername(username);
                user.setNick(nick);
                String headerName = null;
                if (!TextUtils.isEmpty(user.getNick())) {
                    headerName = user.getNick();
                } else {
                    headerName = user.getUsername();
                }
                
                if (username.equals(Constant.NEW_FRIENDS_USERNAME) || username.equals(Constant.GROUP_USERNAME)) {
                    user.setHeader("");
                } else if (Character.isDigit(headerName.charAt(0))) {
                    user.setHeader("#");
                } else {
                    user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
                            .get(0).target.substring(0, 1).toUpperCase());
                    char header = user.getHeader().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setHeader("#");
                    }
                }
                sickusers.put(username, user);
            }
            cursor.close();
        }
        return sickusers;
    }
    
	
	/**
	 * 删除一个联系人
	 * @param username
	 */
	public void deleteSickContact(String username){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{username});
		}
	}
	
	/**
	 * 保存一个联系人
	 * @param user
	 */
	public void saveSickContact(ScikUser user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_ID, user.getUsername());
		if(user.getNick() != null)
			values.put(COLUMN_NAME_NICK, user.getNick());
		if(db.isOpen()){
			db.replace(TABLE_NAME, null, values);
		}
	}
}
