package com.wenwen.clinic.chatuidemo.activity;

import android.os.Bundle;
import android.view.View;

import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.debug.DebugLog;

public class ChangPwd extends BaseActivity {
    private final String TAG = "ChangPwd";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.chang_pwd);
    }
    
    public void back(View view) {
        finish();
    }
}
