package com.app.myapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MainPersonalMedicien extends AppCompatActivity {

    boolean goToPage=false;
    String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    NoneListAdapter noneListAdapter;
    Context context;
    ProgressDialog progressDialog;
    String message;
    String[][] Personal_med;
    private ActionBar mActionBar;


    private static String SOAP_ACTION = "http://tempuri.org/SelectPersonalMed";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "SelectPersonalMed";
    private static String URL = "http://120.125.78.200/WebService1.asmx";

    String uid;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal_medicien);

        //取得傳遞過來的資料
        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");

        toolbar=findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("個人藥單");
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        context =this;
        //SOAP
        postData();

        //製造資料
        //makeData();

        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
        swipeRefreshLayout.setOnRefreshListener(()->{
            arrayList.clear();
            makeData();
            myListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });
    }


    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "載入中....");
            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("uid",uid);


                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);

                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);


                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                //PersonalMed = soapSerializationEnvelope.getResponse();

                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                Personal_med=new String[obj2.getPropertyCount()][19];

                for(int i=0; i<obj2.getPropertyCount(); i++)
                {
                    SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                    Personal_med[i][0]= obj3.getProperty(0).toString();
                    Personal_med[i][1] = obj3.getProperty(1).toString();
                    Personal_med[i][2]= obj3.getProperty(2).toString();
                    Personal_med[i][3]= obj3.getProperty(3).toString();
                    Personal_med[i][4] = obj3.getProperty(4).toString();
                    Personal_med[i][5]= obj3.getProperty(5).toString();
                    Personal_med[i][6]= obj3.getProperty(6).toString();
                    Personal_med[i][7] = obj3.getProperty(7).toString();
                    Personal_med[i][8]= obj3.getProperty(8).toString();
                    Personal_med[i][9] = obj3.getProperty(9).toString();
                    Personal_med[i][10]= obj3.getProperty(10).toString();
                    Personal_med[i][11]= obj3.getProperty(11).toString();
                    Personal_med[i][12] = obj3.getProperty(12).toString();
                    Personal_med[i][13]= obj3.getProperty(13).toString();
                    Personal_med[i][14] = obj3.getProperty(14).toString();
                    Personal_med[i][15]= obj3.getProperty(15).toString();
                    Personal_med[i][16]= obj3.getProperty(16).toString();
                    Personal_med[i][17] = obj3.getProperty(17).toString();
                    Personal_med[i][18] = obj3.getProperty(18).toString();

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
            progressDialog.dismiss();
            if(Personal_med.length>0)
            {
                //製造資料
                makeData();
                myListAdapter = new MyListAdapter();
                mRecyclerView.setAdapter(myListAdapter);
            }
            else
            {
                Toast.makeText(context,"點擊右上角來新增藥單吧!",Toast.LENGTH_SHORT).show();
                noneListAdapter = new NoneListAdapter();
                mRecyclerView.setAdapter(noneListAdapter);
            }
            super.onPostExecute(s);
        }
    }

    private void makeData() {

        ArrayList <String> selectList=new ArrayList<>();

        for (int i = 0;i< Personal_med.length;i++){

            if(selectList.indexOf(Personal_med[i][4])==-1 ||selectList.indexOf(Personal_med[i][5])==-1 || selectList.indexOf(Personal_med[i][6])==-1)
            {
                selectList.add(Personal_med[i][4]);
                selectList.add(Personal_med[i][5]);
                selectList.add(Personal_med[i][6]);
                selectList.add(Personal_med[i][7]);
                selectList.add(Personal_med[i][8]);
                selectList.add(Personal_med[i][9]);
                selectList.add(Personal_med[i][10]);
                selectList.add(Personal_med[i][11]);
            }


        }


        for(int j=0;j<selectList.size();j=j+8)
        {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("ThisDate",selectList.get(j));
            hashMap.put("Hospital",selectList.get(j+1));
            hashMap.put("Department",selectList.get(j+2));
            if(!selectList.get(j+3).equals("anyType{}"))
                hashMap.put("ReturnAppointmentDate",selectList.get(j+3));
            else
                hashMap.put("ReturnAppointmentDate","無");

            if(!selectList.get(j+4).equals("anyType{}"))
            {
                hashMap.put("DeadlineStart_2nd",selectList.get(j+4));
                hashMap.put("DeadlineEnd_2nd",selectList.get(j+5));
            }
            else
            {
                hashMap.put("DeadlineStart_2nd","無");
                hashMap.put("DeadlineEnd_2nd","");
            }


            if(!selectList.get(j+6).equals("anyType{}"))
            {
                hashMap.put("DeadlineStart_3nd",selectList.get(j+6));
                hashMap.put("DeadlineEnd_3nd",selectList.get(j+7));
            }
            else
            {
                hashMap.put("DeadlineStart_3nd","無");
                hashMap.put("DeadlineEnd_3nd","");
            }


            arrayList.add(hashMap);
        }
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tvHospital,tvDepartment,tvThisDate,tvReturnAppointmentDate,tv_DeadlineStart_2nd,tv_DeadlineEnd_2nd,tv_DeadlineStart_3nd,tv_DeadlineEnd_3nd;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvHospital = itemView.findViewById(R.id.textView_Hospital);
                tvDepartment = itemView.findViewById(R.id.textView_Department);
                tvThisDate = itemView.findViewById(R.id.textView_ThisDate);
                tvReturnAppointmentDate = itemView.findViewById(R.id.textView_ReturnAppointmentDate);
                tv_DeadlineStart_2nd = itemView.findViewById(R.id.textView_DeadlineStart_2nd);
                tv_DeadlineEnd_2nd = itemView.findViewById(R.id.textView_DeadlineEnd_2nd);
                tv_DeadlineStart_3nd = itemView.findViewById(R.id.textView_DeadlineStart_3nd);
                tv_DeadlineEnd_3nd = itemView.findViewById(R.id.textView_DeadlineEnd_3nd);
                //tvAvg  = itemView.findViewById(R.id.textView_avg);
                mView  = itemView;
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item,parent,false);  //利用LayoutInflater方法載入介面
            return new ViewHolder(view);
        }


        @Override //onBindViewHolder方法是用來管控內部元件的操作的
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.tvHospital.setText(arrayList.get(position).get("Hospital")); //設定Id欄位使用holder.tvId去取得他的內容
            holder.tvDepartment.setText(arrayList.get(position).get("Department"));
            holder.tvThisDate.setText(arrayList.get(position).get("ThisDate"));
            holder.tvReturnAppointmentDate.setText(arrayList.get(position).get("ReturnAppointmentDate"));
            holder.tv_DeadlineEnd_2nd.setText(arrayList.get(position).get("DeadlineEnd_2nd"));
            holder.tv_DeadlineStart_2nd.setText(arrayList.get(position).get("DeadlineStart_2nd"));
            holder.tv_DeadlineEnd_3nd.setText(arrayList.get(position).get("DeadlineEnd_3nd"));
            holder.tv_DeadlineStart_3nd.setText(arrayList.get(position).get("DeadlineStart_3nd"));
            //holder.tvAvg.setText(arrayList.get(position).get("Avg"));

            holder.mView.setOnClickListener((v)->{
                //Toast.makeText(getBaseContext(),holder.tvAvg.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainPersonalMedicien.this,SelectPersonalPrescription.class);
                ArrayList<String>data=setData(holder.tvHospital.getText().toString(),holder.tvDepartment.getText().toString(),holder.tvThisDate.getText().toString());
                intent.putStringArrayListExtra("PersonalMed",data);
                intent.putExtra("uid",uid);
                intent.putExtra("Hospital",holder.tvHospital.getText());//可放所有基本類別
                intent.putExtra("Department",holder.tvDepartment.getText());
                intent.putExtra("ThisDate",holder.tvThisDate.getText());
                intent.putExtra("ReturnAppointmentDate",holder.tvReturnAppointmentDate.getText());//可放所有基本類別
                intent.putExtra("DeadlineStart_2nd",holder.tv_DeadlineStart_2nd.getText());
                intent.putExtra("DeadlineStart_3nd",holder.tv_DeadlineStart_3nd.getText());

                if(holder.tv_DeadlineEnd_2nd.getText().equals(""))
                    intent.putExtra("DeadlineEnd_2nd","無");
                else
                    intent.putExtra("DeadlineEnd_2nd",holder.tv_DeadlineEnd_2nd.getText());


                if(holder.tv_DeadlineEnd_3nd.getText().equals(""))
                    intent.putExtra("DeadlineEnd_3nd","無");
                else
                    intent.putExtra("DeadlineEnd_3nd",holder.tv_DeadlineEnd_3nd.getText());
                intent.putExtra("goPage","false");//可放所有基本類別
                startActivity(intent);

            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        } //getItemCount方法是決定顯示數量
    }

    public ArrayList<String> setData(String Hospital,String Department,String ThisDate)
    {
        ArrayList<String>data=new ArrayList<>();
        for (int i=0;i<Personal_med.length;i++)
        {
            if(Personal_med[i][4].equals(ThisDate)&& Personal_med[i][5].equals(Hospital) && Personal_med[i][6].equals(Department))
            {
                data.add(Personal_med[i][0]);//M_Code
                data.add(Personal_med[i][1]);//Med_G
                data.add(Personal_med[i][2]);//Med_C
                data.add(Personal_med[i][3]);//Med_E
                data.add(Personal_med[i][12]);//MedPicture
                data.add(Personal_med[i][13]);//Dose
                data.add(Personal_med[i][14]);//MedPeriod
                data.add(Personal_med[i][15]);//MedTiming
                data.add(Personal_med[i][16]);//MedFrequency
                data.add(Personal_med[i][17]);//MedStart
                data.add(Personal_med[i][18]);//MedDay
            }
        }
        return data;

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //toolBar上的新增
        inflater.inflate(R.menu.ab_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                onBackPressed();
                return true;
            case R.id.search:
                Intent intent = new Intent();
                intent.setClass(MainPersonalMedicien.this, SelectPersonalPrescription.class);
                intent.putExtra("goPage", "true");//可放所有基本類別
                intent.putExtra("uid", uid);
                startActivity(intent);
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class NoneListAdapter extends RecyclerView.Adapter<NoneListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtNone;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNone=itemView.findViewById(R.id.txtNone);

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
                holder.txtNone.setText("目前尚未建立個人藥單哦!");

        }

        @Override
        public int getItemCount() {
            return 1;
        } //getItemCount方法是決定顯示數量
    }

}