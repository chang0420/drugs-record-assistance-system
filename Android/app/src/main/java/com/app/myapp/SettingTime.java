package com.app.myapp;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;

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

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.myapp.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class SettingTime extends AppCompatActivity {

    private ActionBar mActionBar;
    String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    Object PersonalMed;

    ProgressDialog progressDialog;
    String message;

    //存檔時間
    boolean goUpdate =false;
    boolean success=false;

    String [] Timing;
    int oldcunt=0;
    int update=0;

    private static String SOAP_ACTION = "http://tempuri.org/SelectMedTime";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "SelectMedTime";
    private static String URL = "http://120.125.78.200/WebService1.asmx";

    //alarm
    Context context;

    Toolbar toolbar;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_time);

        //取得傳遞過來的資料
        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");
        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("設定用藥時間");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        context =this;

        //製造資料
        postData();

        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
        swipeRefreshLayout.setOnRefreshListener(()->{
            goUpdate=false;
            arrayList.clear();
            postData();
            myListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });

    }


    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(context,"","請稍後....");
//            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                if(goUpdate!=true)
                {

                    METHOD_NAME="SelectMedTime";
                    SOAP_ACTION="http://tempuri.org/SelectMedTime";
                    SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("uid",uid);
                    //request.addProperty("password",mCaseId.getText().toString());

                    SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet=true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE= new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION,soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                    PersonalMed = soapSerializationEnvelope.getResponse();

                    SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                    Timing=new String[obj2.getPropertyCount()];
                    for(int i=0; i<obj2.getPropertyCount(); i=i+2)
                    {
                        //SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("Period",obj2.getProperty(i).toString());
                        if(!obj2.getProperty(i+1).toString().equals("anyType{}") )
                            hashMap.put("Timing",obj2.getProperty(i+1).toString());
                        else
                        {
                            hashMap.put("Timing","尚未設定");
                            oldcunt++;
                        }



                        hashMap.put("btnSet","設定時間");
                        Timing[i]=obj2.getProperty(i+1).toString();
                        arrayList.add(hashMap);
                    }


                }
                else
                {
                    if(B_B==null&&A_B==null&&B_L==null&&A_L==null&&B_D==null&&A_D==null&&B_S==null&&ON_B==null&&ON_L==null&&ON_D==null)
                    {
                        success=false;
                    }
                    else
                    {
                        METHOD_NAME="InsertRemindTime";
                        SOAP_ACTION="http://tempuri.org/InsertRemindTime";
                        SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                        request.addProperty("B_B", B_B);
                        request.addProperty("A_B", A_B);
                        request.addProperty("B_L", B_L);
                        request.addProperty("A_L", A_L);
                        request.addProperty("B_D", B_D);
                        request.addProperty("A_D", A_D);
                        request.addProperty("B_S", B_S);
                        request.addProperty("ON_B", ON_B);
                        request.addProperty("ON_L", ON_L);
                        request.addProperty("ON_D", ON_D);
                        request.addProperty("uid",uid);


                        SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        soapSerializationEnvelope.dotNet=true;
                        soapSerializationEnvelope.setOutputSoapObject(request);

                        HttpTransportSE transportSE= new HttpTransportSE(URL);
                        transportSE.call(SOAP_ACTION,soapSerializationEnvelope);


                        //若是單純的值，直接用SoapPrimitive接

                        SoapPrimitive result = (SoapPrimitive) soapSerializationEnvelope.getResponse();

                        success = Boolean.parseBoolean(result.toString());
                    }





                }



                message="OK";

            }catch(Exception e)
            {
                e.printStackTrace();

                message="ERROR:"+e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            progressDialog.dismiss();
            if(PersonalMed != null && !goUpdate)
            {
                //製造資料

                myListAdapter = new MyListAdapter();
                mRecyclerView.setAdapter(myListAdapter);
                //mRecyclerView.smoothScrollToPosition(arrayList.size()-1);

            }
            else if(goUpdate)
            {
                if (success) {

                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    update=1;
                    success = false;
                }
                else
                {
                    Toast.makeText(context, "用藥時間設定不完全!\n請先選取提醒時間，再點擊右上角儲存", Toast.LENGTH_LONG).show();
                    update=0;
                }
                goUpdate = false;

            }
            else
            {
                Toast.makeText(context,"目前尚未建立藥單哦!",Toast.LENGTH_SHORT).show();
                Intent it=new Intent();
                it.putExtra("uid",uid);
                it.setClass(SettingTime.this,MainActivity.class);
                startActivity(it);//跳到登入頁面
            }


            super.onPostExecute(s);
        }


    }



    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtTiming,txtPeriod;
            private View mView;
            private  Context context;
            private Button btnInsert;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                //tvSub1 = itemView.findViewById(R.id.textView_sub1);
                btnInsert=itemView.findViewById(R.id.btnInsert);
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

            holder.txtTiming.setText(arrayList.get(position).get("Timing"));
            holder.txtPeriod.setText(arrayList.get(position).get("Period"));


            holder.mView.setOnClickListener(v -> {
                int p=holder.getAdapterPosition();

                //createNotificationChannel();

                showTimePicker(p);


            });


        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    final Calendar c = Calendar.getInstance();
    private void showTimePicker(int position) {


        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(SettingTime.this, new TimePickerDialog.OnTimeSetListener(){

            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //timeText.setText("現在時間是" + hourOfDay + ":" + minute);

                HashMap<String,String> hashMap = new HashMap<>();

                String value = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                hashMap.put("Timing", value);
                hashMap.put("Period",arrayList.get(position).get("Period"));
                hashMap.put("btnSet","設定時間");
                arrayList.set(position,hashMap);
                myListAdapter.notifyDataSetChanged();

//                calendar = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                c.set(Calendar.MINUTE,minute);
                c.set(Calendar.SECOND,0);
                c.set(Calendar.MILLISECOND,0);
                //myListAdapter.notifyDataSetChanged();
                //update--;
                //setAlarm();
            }
        }, hour, minute, false).show();



    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //toolBar上的新增
        inflater.inflate(R.menu.med_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    String B_B, A_B,  B_L, A_L,  B_D,  A_D,  B_S,selectItem,ON_B,ON_L,ON_D;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int count=0;
        if (item.getItemId() == android.R.id.home) {


            for(int i =0;i<=arrayList.size()-1;i++)
            {
                if(arrayList.get(i).get("Timing").equals("尚未設定"))
                {
                    count++;
                }
            }
            if(oldcunt==0)
            {
                Intent intent=new Intent();
                intent.setClass(SettingTime.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                if(count>0 )
                {
                    Toast.makeText(context,"請確實填寫提醒時間",Toast.LENGTH_SHORT).show();
                }
                else if(count==0 && update==0)
                {
                    Toast.makeText(context,"您尚未儲存修改的時間，請點擊右上角儲存",Toast.LENGTH_SHORT).show();
                    count++;
                }
                else if(count==0 && update>0)
                {
                    Intent intent=new Intent();
                    intent.setClass(SettingTime.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    update=0;
                }
            }

        }

        if (item.toString().equals("儲存")) {


            for(int i =0;i<=arrayList.size()-1;i++)
            {

                switch (arrayList.get(i).get("Period"))
                {
                    case "早上飯前":
                        B_B=arrayList.get(i).get("Timing");
                        break;
                    case "早上隨餐服用":
                        ON_B=arrayList.get(i).get("Timing");
                        break;
                    case "早上飯後":
                        A_B=arrayList.get(i).get("Timing");
                        break;
                    case "中午飯前":
                        B_L=arrayList.get(i).get("Timing");
                        break;
                    case "中午隨餐服用":
                        ON_L=arrayList.get(i).get("Timing");
                        break;
                    case "中午飯後":
                        A_L=arrayList.get(i).get("Timing");
                        break;
                    case "晚上飯前":
                        B_D=arrayList.get(i).get("Timing");
                        break;
                    case "晚上隨餐服用":
                        ON_D=arrayList.get(i).get("Timing");
                        break;
                    case "晚上飯後":
                        A_D=arrayList.get(i).get("Timing");
                        break;
                    case "睡前":
                        B_S=arrayList.get(i).get("Timing");
                        break;

                }


            }

            goUpdate=true;
            postData();
            count=0;


        }
        return super.onOptionsItemSelected(item);
    }

}