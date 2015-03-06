package com.wenwen.clinic.chatuidemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.adapter.DoctorsAdapter;
import com.wenwen.clinic.chatuidemo.domain.AcountInfo;
import com.wenwen.clinic.chatuidemo.utils.HttpClientRequest;
import com.wenwen.clinic.chatuidemo.utils.Urls;
import com.wenwen.clinic.debug.DebugLog;

public class DoctorsAppointment extends BaseActivity {
    private final String TAG = "DoctorsAppointment";
    private ListView listView;
    private DoctorsAdapter doctorsAdapter;
    private List<AcountInfo> list;
    private int type =2;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.doctors_row_contact);
        type = getIntent().getExtras().getInt("type");
        list = new ArrayList<AcountInfo>();
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                if (type == 1) {
                    Bundle bundle = new Bundle();
                    AcountInfo acountInfo = list.get(position);
                    bundle.putSerializable("acountInfo", acountInfo);
                    bundle.putString("accountname", list.get(position).getAccount_name());
                    bundle.putString("username", list.get(position).getAccount_username());
                    bundle.putInt("type", type);
                    Intent intent = new Intent(DoctorsAppointment.this,OrderInfo.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", type);
                    AcountInfo acountInfo = list.get(position);
                    bundle.putSerializable("acountInfo", acountInfo);
                    Intent intent = new Intent(DoctorsAppointment.this, OrderInfo.class);
                    bundle.putString("accountname", list.get(position).getAccount_name());
                    bundle.putString("username", list.get(position).getAccount_username());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
        final ProgressDialog pd = new ProgressDialog(DoctorsAppointment.this);
        pd.setMessage("正在加载中...");
        HttpClientRequest.post(Urls.GETDOCTORS, null, 3000,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        pd.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        try {
                            String res = new String(arg2);
                            DebugLog.i(TAG, "res" + res);
                            JsonParser parser = new JsonParser();
                            JsonArray Jarray = parser.parse(res).getAsJsonArray();
                            AcountInfo acountInfo = null;
                            Gson gson = new Gson();
                            for (JsonElement obj : Jarray) {
                                acountInfo = gson.fromJson(obj, AcountInfo.class);
                                list.add(acountInfo);
                            }
                            doctorsAdapter = new DoctorsAdapter(DoctorsAppointment.this, list);
                            listView.setAdapter(doctorsAdapter);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                            Throwable arg3) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    public void back(View view) {
        finish();
    }
}
