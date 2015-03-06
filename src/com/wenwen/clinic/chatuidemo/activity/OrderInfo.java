package com.wenwen.clinic.chatuidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.domain.AcountInfo;
import com.wenwen.clinic.debug.DebugLog;

public class OrderInfo extends BaseActivity implements OnClickListener {
    private final String TAG = "OrderInfo";
    private TextView title, name, hospital, information;
    private ImageView avatar;
    private Button btn_clinic, btn_phone, btn_line;
    private AcountInfo acountInfo;
    private int type = 2;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.order_info);
        Intent intent = this.getIntent();
        acountInfo = (AcountInfo) intent.getSerializableExtra("acountInfo");
        init();
        type = getIntent().getExtras().getInt("type");
        if (type == 2) {
            btn_clinic.setVisibility(View.GONE);
            btn_phone.setVisibility(View.GONE);
            btn_line.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            btn_clinic.setVisibility(View.VISIBLE);
            btn_phone.setVisibility(View.VISIBLE);
            btn_line.setVisibility(View.VISIBLE);
        } else {
            btn_clinic.setVisibility(View.VISIBLE);
            btn_phone.setVisibility(View.VISIBLE);
            btn_line.setVisibility(View.GONE);
        }
        setText();
    }

    private void setText() {
        // TODO Auto-generated method stub
        title.setText(acountInfo.getAccount_name());
        name.setText(acountInfo.getAccount_name() + " "
                + acountInfo.getAccount_job());
        hospital.setText(acountInfo.getAccount_hospital());
        information.setText(acountInfo.getAccount_info());
    }

    private void init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        hospital = (TextView) findViewById(R.id.hospital);
        information = (TextView) findViewById(R.id.information);
        avatar = (ImageView) findViewById(R.id.avatar);
        btn_clinic = (Button) findViewById(R.id.btn_clinic);
        btn_phone = (Button) findViewById(R.id.btn_phone);
        btn_line = (Button) findViewById(R.id.btn_line);
        btn_line.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_clinic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btn_clinic) {
            startActivity(new Intent(OrderInfo.this, ClinicOrderInfo.class).putExtra("uid", acountInfo.getAccount_id()));
            finish();
        } else if (v == btn_phone) {
            startActivity(new Intent(OrderInfo.this, PhoneOrderInfo.class).putExtra("uid", acountInfo.getAccount_id()));
            finish();
        } else if (v == btn_line) {
            startActivity(new Intent(OrderInfo.this, PhotoText.class)
                    .putExtra("uid", acountInfo.getAccount_id())
                    .putExtra("accountname", acountInfo.getAccount_name())
                    .putExtra("username", acountInfo.getAccount_username()));
            finish();
        }
    }

    public void back(View view) {
        finish();
    }
}
