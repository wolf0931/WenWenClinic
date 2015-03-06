package com.wenwen.clinic.chatuidemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ConsultationFragment extends Fragment {
    private final String TAG = "ConsultationFragment";
    private ListView listView;
    private DoctorsAdapter doctorsAdapter;
    private List<AcountInfo> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DebugLog.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.consultation, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<AcountInfo>();
        init();
        getdata();
    }

    private void getdata() {
        // TODO Auto-generated method stub
        final ProgressDialog pd = new ProgressDialog(getActivity());
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
                            doctorsAdapter = new DoctorsAdapter(getActivity(), list);
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

    private void init() {
        // TODO Auto-generated method stub
        listView = (ListView) getView().findViewById(R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                AcountInfo acountInfo = list.get(position);
                bundle.putSerializable("acountInfo", acountInfo);
                bundle.putString("acountname", list.get(position).getAccount_name());
                bundle.putString("username", list.get(position).getAccount_username());
                bundle.putInt("type", 3);
                Intent intent = new Intent(getActivity(),OrderInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        DebugLog.i(TAG, "onResume");
        super.onResume();
    }
}
