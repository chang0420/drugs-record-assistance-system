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

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertEffectActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    ArrayList<String> dataE=new ArrayList<>();
    ArrayList<Item> myDataset = new ArrayList<>();
    private ActionBar mActionBar;

    //progress dialog
    private ProgressDialog progressDialog;

    //soap insert參數

    String SOAP_ACTION = "http://tempuri.org/SelectEffect";
    String SOAP_ACTION2 = "http://tempuri.org/InsertEffectRecord";
    String NAMESPACE = "http://tempuri.org/";
    String METHOD_NAME;
    String METHOD_NAME2 = "InsertEffectRecord";
    String URL = "http://120.125.78.200/WebService1.asmx";
    String[] Personal_rem;
    String message;

    Toolbar toolbar;
    Button btnInsert;
    EditText txtMemo,txtEffect;
    Context context;
    boolean goInsert = false;
    boolean goSelect = true;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_effect);

        Intent intent = this.getIntent();


//        if(intent.getAction()!=null&&intent.getAction().equals("RemindEffect"))
//        {
//            NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
//            manager.cancel(456);
//        }
        uid = intent.getStringExtra("uid");

        context = this;

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("用藥反應紀錄");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        btnInsert = findViewById(R.id.btnInsert);
        txtMemo = findViewById(R.id.txtMemo);
        txtEffect=findViewById(R.id.txtEff);

        context = this;

        //製造資料
        postData();


        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        btnInsert.setOnClickListener(v -> {

            goInsert=true;
            postData();

        });
    }


    //soap
    int count=0;
    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,"","資料傳送中，請稍後....");
            progressDialog.setCancelable(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                if(goInsert)
                {


                    for(int j=0;j< dataE.size();j++)
                    {

                        Thread.sleep(1000);

                        //it=item;
                        //new WebServiceInsert().execute("");
                        //Initialize soap request + add parameters
                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
                        //Use this to add parameters
                        request.addProperty("uid", uid);
                        request.addProperty("effect", dataE.get(j));
                        request.addProperty("memo", txtMemo.getText().toString());
                        //Declare the version of the SOAP request
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.setOutputSoapObject(request);
                        envelope.dotNet = true;

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                        //this is the actual part that will call the webservice
                        androidHttpTransport.call(SOAP_ACTION2, envelope);
                        /* Get the SoapResult from the envelope body. */
//                        SoapObject result = (SoapObject) envelope.bodyIn;
//                        success = Boolean.parseBoolean(result.toString());

                    }

                    if(!txtEffect.getText().toString().equals(""))
                    {
                        Thread.sleep(1000);
                        SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                        //Use this to add parameters
                        r.addProperty("uid", uid);
                        r.addProperty("effect", txtEffect.getText().toString());
                        r.addProperty("memo", txtMemo.getText().toString());
                        //Declare the version of the SOAP request
                        SoapSerializationEnvelope e = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        e.setOutputSoapObject(r);
                        e.dotNet = true;

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                        //this is the actual part that will call the webservice
                        androidHttpTransport.call(SOAP_ACTION2, e);
                        /* Get the SoapResult from the envelope body. */
//                        SoapObject bodyIn = (SoapObject) e.bodyIn;
//                        success = Boolean.parseBoolean(bodyIn.toString());
                    }


                }


                if(goSelect)
                {
                    METHOD_NAME = "SelectEffect";
                    SOAP_ACTION = "http://tempuri.org/SelectEffect";
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("uid", "123");


                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet = true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn;

                    //PersonalMed = soapSerializationEnvelope.getResponse();

                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);
                    Personal_rem = new String[obj2.getPropertyCount()];


                    for (int i = 0; i < obj2.getPropertyCount(); i++) {
//                    SoapObject obj3 = (SoapObject) obj2.getProperty(i);
//                        Personal_rem[i] = obj2.getProperty(i).toString();
//                        HashMap<String,String> hashMap = new HashMap<>();
//                        hashMap.put("Effect", obj2.getProperty(i).toString());
                        Item item = new Item();
                        item.setText(obj2.getProperty(i).toString());
                        myDataset.add(item);
//                        arrayList.add(hashMap);

                    }

                    message = "OK";
                }

            } catch (Exception e) {
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }
            return null;
        }



        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (goInsert) {
                count++;
                Toast.makeText(context, "成功新增:"+count, Toast.LENGTH_LONG).show();
                goInsert = false;
                Intent it = new Intent();
                it.putExtra("uid",uid);
                it.setClass(InsertEffectActivity.this, SideEffectActivity.class);
                startActivity(it);//跳到登入頁面
                finish();


            }

            if (goSelect) {

//                myListAdapter = new MyAdapter(myDataset);
//                mRecyclerView.setAdapter(myListAdapter);

                myListAdapter = new MyListAdapter(myDataset);
                mRecyclerView.setAdapter(myListAdapter);
                goSelect=false;

            }
            if(myDataset == null  ) {
                goSelect=false;
                Toast.makeText(context, "目前尚未建立藥單哦!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent();
                it.setClass(InsertEffectActivity.this, MainActivity.class);
                startActivity(it);//跳到登入頁面
            }

            super.onPostExecute(s);
        }

    }

    //recyclerview

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private final List<Item> mData;

        public MyListAdapter(List<Item> data)
        {
            mData = data;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private final View mView;
            public TextView mTextView;
            public CheckBox mCheckBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                //tvSub1 = itemView.findViewById(R.id.textView_sub1);
                mTextView = itemView.findViewById(R.id.info_text);
                mCheckBox = itemView.findViewById(R.id.info_chcekbox);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_effect,parent,false);
            return new MyListAdapter.ViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {


            Item item = mData.get(position);
            holder.mTextView.setText(item.getText());
            holder.mCheckBox.setChecked(item.isCheck());





            holder.mView.setOnClickListener(v -> {
//                int p=holder.getAdapterPosition();

                //createNotificationChannel();

                int pos= holder.getAdapterPosition();
                if (!holder.mCheckBox.isChecked()) {

                    holder.mCheckBox.setChecked(true);
                    mData.get(pos).setCheck(true);
                    dataE.add(holder.mTextView.getText().toString());
                } else {
                    holder.mCheckBox.setChecked(false);
                    mData.get(pos).setCheck(false);
                    dataE.remove(holder.mTextView.getText().toString());

                }



            });
            holder.mCheckBox.setOnClickListener(v -> {
                int p= holder.getAdapterPosition();
                if (((CheckBox) v).isChecked()) {
                    holder.mCheckBox.setChecked(true);
                    mData.get(p).setCheck(true);
                    dataE.add(holder.mTextView.getText().toString());
                } else {
                    holder.mCheckBox.setChecked(false);
                    mData.get(p).setCheck(false);
                    dataE.remove(holder.mTextView.getText().toString());
                }

            });

        }



        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
    private void dismissProgressDialog() {
        if ( progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}