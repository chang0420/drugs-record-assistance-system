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

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RelationActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    Button btnInsert,btnConfirm,btnSend,btnSelect;
    TextView tvAccount,tvPassword;
    EditText txtAccount , txtPassword;

    MyListAdapter myListAdapter;
    ConfirmListAdapter confirmListAdapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    Object PersonalMed;

    //SOAP
    private static String SOAP_ACTION = "http://tempuri.org/UserSelect";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "UserSelect";
    private static String URL = "http://120.125.78.206/BrainShaking.asmx";
    Object resultString;


    RecyclerView recyclerView;

    //判斷事件
    boolean goInsert=false;  //新增關係
    boolean goSelect=false;   //目前有的關係
    boolean goConfirm=false; //待核准

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        this.setTitle("家屬連結");
        toolbar=findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        btnConfirm=findViewById(R.id.btn_confirm_relation);
        btnInsert=findViewById(R.id.btn_Insert_relation);
        btnSelect=findViewById(R.id.btn_Now_relation);
        btnSend=findViewById(R.id.btnSend);
        txtAccount=findViewById(R.id.txtAccount);
        txtPassword=findViewById(R.id.txtPassword);
        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        tvAccount=findViewById(R.id.tvAccount);
        tvPassword=findViewById(R.id.tvPassword);
        recyclerView.setVisibility(View.GONE);

        postData();

        btnSelect.setBackgroundColor(getResources().getColor(R.color.white));
        btnConfirm.setBackgroundColor(getResources().getColor(R.color.white));
        btnInsert.setTextColor(getResources().getColor(R.color.white));
        btnInsert.setBackgroundColor(getResources().getColor(R.color.brown2));

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect.setTextColor(getResources().getColor(R.color.white));
                btnInsert.setTextColor(getResources().getColor(R.color.gray));
                btnConfirm.setTextColor(getResources().getColor(R.color.gray));
                //
                btnSelect.setBackgroundColor(getResources().getColor(R.color.brown2));
                btnInsert.setBackgroundColor(getResources().getColor(R.color.white));
                btnConfirm.setBackgroundColor(getResources().getColor(R.color.white));

                tvAccount.setVisibility(View.GONE);
                tvPassword.setVisibility(View.GONE);
                txtAccount.setVisibility(View.GONE);
                txtPassword.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);
                goSelect=true;
                postData();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnInsert.setTextColor(getResources().getColor(R.color.white));
                btnSelect.setTextColor(getResources().getColor(R.color.gray));
                btnConfirm.setTextColor(getResources().getColor(R.color.gray));
                btnSelect.setBackgroundColor(getResources().getColor(R.color.white));
                btnInsert.setBackgroundColor(getResources().getColor(R.color.brown2));
                btnConfirm.setBackgroundColor(getResources().getColor(R.color.white));

                tvAccount.setVisibility(View.VISIBLE);
                tvPassword.setVisibility(View.VISIBLE);
                txtAccount.setVisibility(View.VISIBLE);
                txtPassword.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goInsert=true;
                postData();
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelect.setTextColor(getResources().getColor(R.color.gray));
                btnInsert.setTextColor(getResources().getColor(R.color.gray));
                btnConfirm.setTextColor(getResources().getColor(R.color.white));
                btnSelect.setBackgroundColor(getResources().getColor(R.color.white));
                btnInsert.setBackgroundColor(getResources().getColor(R.color.white));
                btnConfirm.setBackgroundColor(getResources().getColor(R.color.brown2));

                tvAccount.setVisibility(View.GONE);
                tvPassword.setVisibility(View.GONE);
                txtAccount.setVisibility(View.GONE);
                txtPassword.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);
                goConfirm=true;
                postData();
            }
        });
    }

    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(context,"","waiting....");
            //progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("account","123");
                request.addProperty("password", "123");

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);

                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);

                resultString =soapSerializationEnvelope.getResponse();

                if(goSelect)
                {
                    String METHOD_NAME2="SearchFamCon";
                    String SOAP_ACTION2="http://tempuri.org/SearchFamCon";
                    SoapObject r=new SoapObject(NAMESPACE,METHOD_NAME2);


                    SoapSerializationEnvelope Envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    Envelope.dotNet=true;
                    Envelope.setOutputSoapObject(r);

                    HttpTransportSE SE= new HttpTransportSE(URL);
                    SE.call(SOAP_ACTION2,Envelope);


                    SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼



                    SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                    arrayList.clear();


                    for(int i=0; i<obj2.getPropertyCount(); i++)
                    {
                        SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("Account", obj3.getProperty(0).toString());
                        hashMap.put("Relation", obj3.getProperty(1).toString());
                        arrayList.add(hashMap);

                    }
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");


                }

                if(goInsert)
                {
                    String METHOD_NAME2="AddFamCon";
                    String SOAP_ACTION2="http://tempuri.org/AddFamCon";
                    Date currentTime = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
                    String date = sdf.format(currentTime);
                    String time=sd.format(currentTime);
                    SoapObject re=new SoapObject(NAMESPACE,METHOD_NAME2);
                    re.addProperty("AddID",txtAccount.getText().toString());
                    re.addProperty("Rel",txtPassword.getText().toString()); //關係
                    re.addProperty("Date",date);
                    re.addProperty("Time", time);


                    SoapSerializationEnvelope E =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    E.dotNet=true;
                    E.setOutputSoapObject(re);

                    HttpTransportSE S= new HttpTransportSE(URL);
                    S.call(SOAP_ACTION2,E);


                    SoapObject bodyIn = (SoapObject) E.bodyIn; // KDOM 節點文字編碼

                    SoapPrimitive result = (SoapPrimitive) soapSerializationEnvelope.getResponse();

                    boolean success = Boolean.parseBoolean(result.toString());



                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");
                }


                if(goConfirm)
                {
                    String METHOD_NAME3="SearchFamConReq";
                    String SOAP_ACTION3="http://tempuri.org/SearchFamConReq";
                    SoapObject req=new SoapObject(NAMESPACE,METHOD_NAME3);


                    SoapSerializationEnvelope Envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    Envelope.dotNet=true;
                    Envelope.setOutputSoapObject(req);

                    HttpTransportSE TSE= new HttpTransportSE(URL);
                    TSE.call(SOAP_ACTION3,Envelope);


                    SoapObject data = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼



                    SoapObject obj2 =(SoapObject) data.getProperty(0);

                    arrayList.clear();

                    for(int i=0; i<obj2.getPropertyCount(); i++)
                    {
                        SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("Account", obj3.getProperty(0).toString());
                        hashMap.put("Relation", obj3.getProperty(1).toString());
                        hashMap.put("Ok", "核准");
                        hashMap.put("No", "拒絕");
                        arrayList.add(hashMap);

                    }
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");
                }

            }catch(Exception e)
            {
                e.printStackTrace();


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
                recyclerView.setAdapter(myListAdapter);
                PersonalMed=null;
            }

            if(goInsert)
            {
                goInsert=false;
                Toast.makeText(RelationActivity.this, "申請成功", Toast.LENGTH_LONG).show();
            }
            if(goConfirm)
            {
                goConfirm=false;
                confirmListAdapter = new ConfirmListAdapter();
                recyclerView.setAdapter(confirmListAdapter);
            }



            super.onPostExecute(s);
        }
    }


    private class ConfirmListAdapter extends RecyclerView.Adapter<ConfirmListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtAccount,txtRelation;
            private View mView;
            private Button btnOk,btnNo;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                //tvSub1 = itemView.findViewById(R.id.textView_sub1);

                txtAccount=itemView.findViewById(R.id.tv_Account);
                txtRelation=itemView.findViewById(R.id.tv_Relation);
                btnNo=itemView.findViewById(R.id.btnNo);
                btnOk=itemView.findViewById(R.id.btnOk);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.relation_request,parent,false);
            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            //換背景顏色
//            if ((position %2) ==0){
//                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg);
//            }
//            else {
//                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg2);
//            }
//
            holder.txtAccount.setText(arrayList.get(position).get("Account"));
            holder.txtRelation.setText(arrayList.get(position).get("Relation"));
            holder.btnOk.setText(arrayList.get(position).get("Ok"));
            holder.btnNo.setText(arrayList.get(position).get("No"));



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    p=holder.getAdapterPosition();
//                    //effect=arrayList.get(p).get("Effect");
//                    viewClick=true;
//                    postData();

                }
            });
        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtAccount,txtRelation;
            private View mView;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                //tvSub1 = itemView.findViewById(R.id.textView_sub1);

                txtAccount=itemView.findViewById(R.id.tv_Account);
                txtRelation=itemView.findViewById(R.id.tv_Relation);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_relation,parent,false);
            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            //換背景顏色
//            if ((position %2) ==0){
//                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg);
//            }
//            else {
//                holder.mView.setBackgroundResource(R.drawable.recycle_time_bg2);
//            }
//
            holder.txtAccount.setText(arrayList.get(position).get("Account"));
            holder.txtRelation.setText(arrayList.get(position).get("Relation"));



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    p=holder.getAdapterPosition();
//                    //effect=arrayList.get(p).get("Effect");
//                    viewClick=true;
//                    postData();

                }
            });
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