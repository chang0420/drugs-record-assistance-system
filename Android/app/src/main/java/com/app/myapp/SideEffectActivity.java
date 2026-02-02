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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SideEffectActivity extends AppCompatActivity {

    String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    NoneListAdapter noneListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    Object PersonalMed;
    Button btnSet;
    TextView txtTime;

    private static String SOAP_ACTION = "http://tempuri.org/SelectEffRecord";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "SelectEffRecord";
    private static String URL = "http://120.125.78.200/WebService1.asmx";

    String message;

    Toolbar toolbar;
    Context context;

    boolean viewClick =false;
    int p;

    boolean goSelect=true;
    String month=null;String year=null;String uid;
    ArrayList<String> Effect;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_effect);
        context=this;

        //取得傳遞過來的資料
        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");

        Calendar today=Calendar.getInstance();
        year= String.valueOf(today.get(Calendar.YEAR));
        month= String.valueOf(today.get(Calendar.MONTH)+1);


        btnSet=findViewById(R.id.btnSet);
        txtTime=findViewById(R.id.txtTime);

        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        String startTime = sdf.format(currentTime);

        txtTime.setText(startTime);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("用藥反應紀錄");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
        swipeRefreshLayout.setOnRefreshListener(()->{
            //arrayList.clear();
            postData();
            myListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });

        postData();

        btnSet.setOnClickListener(v -> {
            Calendar today1 =Calendar.getInstance();
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(SideEffectActivity.this,
                    (selectedMonth, selectedYear) -> {
                        txtTime .setText(selectedYear+"年"+(selectedMonth+1)+"月");
                        month= String.valueOf(selectedMonth+1);
                        year=String.valueOf(selectedYear);

                        arrayList.clear();
                        goSelect=true;

                        postData();
                    }, today1.get(Calendar.YEAR), today1.get(Calendar.MONTH));

            builder.setActivatedMonth(Calendar.getInstance().get(Calendar.MONTH))
                    .setActivatedYear(Calendar.getInstance().get(Calendar.YEAR))
                    .setMinYear(2022)
                    .setMaxYear(Calendar.getInstance().get(Calendar.YEAR))
                    .setTitle("選取時間")
                    .build().show();


        });
    }

    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {
                if (viewClick) {
                    METHOD_NAME = "SelectMonthlyDetail";
                    SOAP_ACTION = "http://tempuri.org/SelectMonthlyDetail";
                    Effect = new ArrayList<>();
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("uid", uid);
                    request.addProperty("year", year);
                    request.addProperty("month", month);
                    request.addProperty("effect", arrayList.get(p).get("Effect"));

                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet = true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                    PersonalMed = soapSerializationEnvelope.getResponse();

                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                    for (int i = 0; i < obj2.getPropertyCount(); i = i + 4) {

                        if (!obj2.getProperty(i + 3).toString().equals("anyType{}"))
                        {

                            Effect.add(obj2.getProperty(i).toString());
                            Effect.add(obj2.getProperty(i+1).toString());
                            Effect.add(obj2.getProperty(i+2).toString());
                            Effect.add(obj2.getProperty(i+3).toString());
                        }



                    }


                    message = "OK";
                }

                if(goSelect)
                {
                    arrayList.clear();
                    SOAP_ACTION = "http://tempuri.org/SelectMonthEffect";
                    METHOD_NAME = "SelectMonthEffect";
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("uid", uid);
                    request.addProperty("month", month );
                    request.addProperty("year", year);

                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet = true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                    //PersonalMed = soapSerializationEnvelope.getResponse();

                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);
//                    PersonalMed = obj2;

                    for (int i = 0; i < obj2.getPropertyCount(); i = i + 2) {
//                    SoapObject obj3 = (SoapObject) obj2.getProperty(i);
//                    Personal_rem[i] = obj2.getProperty(0).toString();
//                    Personal_rem[i+1] = obj2.getProperty(1).toString();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("Count", obj2.getProperty(i).toString());
                        hashMap.put("Effect", obj2.getProperty(1 + i).toString());
                        arrayList.add(hashMap);
                    }
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                    message = "OK";
                }
//
            }
            catch(Exception e)
            {
                e.printStackTrace();

                message="ERROR:"+e.getMessage();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        protected void onPostExecute(String s) {

            if(arrayList.size()>0 && goSelect)
            {
                goSelect=false;
                myListAdapter = new MyListAdapter();
                mRecyclerView.setAdapter(myListAdapter);
                PersonalMed=null;

            }
            else if(!goSelect)
            {
                if(viewClick && Effect.size()!=0 )
                {
                    viewClick=false;
                    Intent i =new Intent();
                    i.putExtra("effectDetail",Effect);
                    i.putExtra("effect",arrayList.get(p).get("Effect"));
                    Effect=null;
                    i.setClass(SideEffectActivity.this,monthlyReportdetail.class);
                    startActivity(i);

                }
                else
                {
                    viewClick=false;
                    Toast.makeText(context, "尚未有備註紀錄!", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                goSelect=false;
                noneListAdapter = new NoneListAdapter();
                mRecyclerView.setAdapter(noneListAdapter);
            }



            super.onPostExecute(s);
        }


    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtTiming,txtPeriod;
            private Button btnSet;
            private View mView;
            private Context context;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                //tvSub1 = itemView.findViewById(R.id.textView_sub1);

                txtTiming=itemView.findViewById(R.id.txtTiming);
                txtPeriod=itemView.findViewById(R.id.txtPeriod);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_time,parent,false);
            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            if ((position %2) ==0){
                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg);
            }
            else {
                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg2);
            }
//
//            else if ((position %3) ==0){
//                holder.mView.setBackgroundColor(getColor(R.color.blue_RURI));
//            }else if((position %5) ==0){
//                holder.mView.setBackgroundColor(getColor(R.color.yellow_YAMABUKI));
//            }else {
//                holder.mView.setBackgroundColor(getColor(R.color.red_GINSYU));
//            }

            holder.txtTiming.setText(arrayList.get(position).get("Count"));
            holder.txtPeriod.setText(arrayList.get(position).get("Effect"));



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    p=holder.getAdapterPosition();
                    //effect=arrayList.get(p).get("Effect");
                    viewClick=true;
                   postData();

                }
            });
        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    private class NoneListAdapter extends RecyclerView.Adapter<NoneListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtNone;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                txtNone=itemView.findViewById(R.id.txtNone);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public NoneListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_home_none,parent,false);  //利用LayoutInflater方法載入介面
            return new NoneListAdapter.ViewHolder(view);
        }


        @Override //onBindViewHolder方法是用來管控內部元件的操作的
        public void onBindViewHolder(@NonNull NoneListAdapter.ViewHolder holder, int position) {
            holder.txtNone.setText("此月份目前尚未紀錄用藥反應哦!");
        }

        @Override
        public int getItemCount() {
            return 1;
        } //getItemCount方法是決定顯示數量
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}