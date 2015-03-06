package com.wenwen.clinic.chatuidemo.activity;

import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.debug.DebugLog;

import android.os.Bundle;
import android.view.View;

public class StateMent extends BaseActivity {
    private final String TAG = "StateMent";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.state_ment);
    }

    public void back(View view) {
        finish();
    }
}
