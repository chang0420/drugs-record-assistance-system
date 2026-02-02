package com.app.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class monthlyReportdetail extends AppCompatActivity {

    String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    private ActionBar mActionBar;

    Context context;

    Toolbar toolbar;

    String effect;
    TextView title;

    ArrayList<String>effectDetail= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_reportdetail);

        Intent intent = this.getIntent();
        effect=intent.getStringExtra("effect");
        effectDetail=intent.getStringArrayListExtra("effectDetail");

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("用藥反應細項紀錄");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        context = this;
        //製造資料
        postData();


        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);

//        this.setTitle("設定用藥時間");
        title=findViewById(R.id.txtEffect);
        title.setText(effect);

    }


    private void postData() {

        for (int i = 0; i < effectDetail.size(); i = i + 4) {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("account", effectDetail.get(i));
                hashMap.put("date", effectDetail.get(i+1));
                hashMap.put("time", effectDetail.get(i+2));
                hashMap.put("memo", effectDetail.get(i+3));
                arrayList.add(hashMap);
        }
    }


    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtDate, txtTime, txtAccount,txtMemo;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                //tvSub1 = itemView.findViewById(R.id.textView_sub1);
                txtMemo = itemView.findViewById(R.id.txtMemo);
                txtTime = itemView.findViewById(R.id.txtTime);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtAccount=itemView.findViewById(R.id.txtAccount);
                mView = itemView;
            }
        }

        @NonNull
        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_message, parent, false);
            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            if ((position % 2) == 0) {
                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg);
            } else {
                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg2);
            }

            holder.txtTime.setText(arrayList.get(position).get("time"));
            holder.txtDate.setText(arrayList.get(position).get("date"));
            holder.txtAccount.setText(arrayList.get(position).get("account"));
            holder.txtMemo.setText(arrayList.get(position).get("memo"));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}