package com.app.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SearchPh extends AppCompatActivity {
    //Soap
    String URL="http://120.125.78.206/BrainShaking.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_NAME ="SearchPh";
    private static final String SOAP_ACTION = "http://tempuri.org/SearchPh";
    private static final String NAMESPACE1 = "http://tempuri.org/";
    private static final String METHOD_NAME1 ="InserFavPh";
    private static final String SOAP_ACTION1 = "http://tempuri.org/InserFavPh";
    private static final String NAMESPACE3 = "http://tempuri.org/";
    private static final String METHOD_NAME3 ="DeleteFavPh";
    private static final String SOAP_ACTION3 = "http://tempuri.org/DeleteFavPh";
    private static final String NAMESPACE4 = "http://tempuri.org/";
    private static final String METHOD_NAME4 ="SearchFavPh";
    private static final String SOAP_ACTION4 = "http://tempuri.org/SearchFavPh";
    private ActionBar mActionBar;
    EditText edtSearchPh;
    Object resultString;
    String message,address,addTime,Sph,uid;
    String[][] Search_Pharmacy,Search_Fav;
    Context context;
    Button imgLove;
    Toolbar toolbar;
    //TextView txtSPH,txtSPHphone,txtSPHaddress;
    RecyclerView PhRecyclerView;
    MyListAdapter myPhListAdapter;
    private boolean fav = false;
    ArrayList<HashMap<String,String>> PharrayList = new ArrayList<>();
    ArrayList<String> Favlist = new ArrayList<>();
    private Button btnSearchPh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ph);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("藥局搜尋");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        PhRecyclerView = findViewById(R.id.recyclerviewSearchPh);
        edtSearchPh=findViewById(R.id.edtSearchPH);
        btnSearchPh=findViewById(R.id.btnSearchPh);
        imgLove=findViewById(R.id.imgLOVE);
        btnSearchPh.setOnClickListener(btnSearchPhOnClick);
        context =this;
        uid=getIntent().getStringExtra("uid");
        LoadFavPh();
    }
    private void LoadFavPh(){
        new WebServiceLoadFavPh().execute("");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private class WebServiceLoadFavPh extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE4,METHOD_NAME4);
                request.addProperty("uid",uid);//
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION4,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);//
                Search_Fav=new String[obj2.getPropertyCount()][3];
                for(int i=0; i<obj2.getPropertyCount(); i++) {
                    SoapObject obj3 = (SoapObject) obj2.getProperty(i);
                    Search_Fav[i][0] = obj3.getProperty(0).toString();//PH
                    Favlist.add(Search_Fav[i][0]);
                }
                message="OK";

            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
    }
    private void DeleteFavPH(){
        new WebServiceDeleteFavPH().execute("");
    }
    private class WebServiceDeleteFavPH extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE3,METHOD_NAME3);
                request.addProperty("Phname",Sph);//
                request.addProperty("uid",uid);//
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION3,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
    }
    private void AddFavPh(){
        new WebServiceAddFavPh().execute("");
    }
    private class WebServiceAddFavPh extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE1,METHOD_NAME1);
                request.addProperty("uid",uid);//
                request.addProperty("Address",address);
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION1,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
            }catch (Exception e){
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }
    }
    private View.OnClickListener btnSearchPhOnClick=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(edtSearchPh.getText().toString().matches(""))
            {
                Toast.makeText(context, "請輸入藥局", Toast.LENGTH_SHORT).show();
            }
            PharrayList.clear();
            SearchPh();
            edtSearchPh.setText("");

        }
    };
    private void SearchPh(){
        new WebServiceSearchPh().execute("");
    }
    private class WebServiceSearchPh extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("Phname",edtSearchPh.getText().toString());
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);//
                Search_Pharmacy=new String[obj2.getPropertyCount()][3];
                SoapObject obj3 =(SoapObject) obj2.getProperty(0);
                Search_Pharmacy[0][0]= obj3.getProperty(0).toString();//NAME
                Search_Pharmacy[0][1] = obj3.getProperty(1).toString();//ADDRESS
                Search_Pharmacy[0][2]= obj3.getProperty(2).toString();//PHONE
                HashMap<String,String> ShashMap = new HashMap<>();
                ShashMap.put("PH", Search_Pharmacy[0][0]);
                ShashMap.put("SPHaddress", Search_Pharmacy[0][1]);
                ShashMap.put("SPHphone", Search_Pharmacy[0][2]);
                PharrayList.add(ShashMap);
            }catch (Exception e){
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
        protected void onPostExecute(String s){
            PhRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            myPhListAdapter = new MyListAdapter();
            PhRecyclerView.setAdapter(myPhListAdapter);
            super.onPostExecute(s);
        }
    }
    public  class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtSPH,txtSPHphone,txtSPHaddress;
            private Button imgLove;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSPH=itemView.findViewById(R.id.txtSPH);
                txtSPHphone=itemView.findViewById(R.id.txtSPHphone);
                txtSPHaddress=itemView.findViewById(R.id.txtSPHaddress);
                imgLove= (Button) itemView.findViewById(R.id.imgLOVE);
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy2_item,parent,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtSPH.setText(PharrayList.get(position).get("PH"));
            holder.txtSPHphone.setText(PharrayList.get(position).get("SPHphone"));
            holder.txtSPHaddress.setText(PharrayList.get(position).get("SPHaddress"));
            address= PharrayList.get(position).get("SPHaddress");//
            Sph= PharrayList.get(position).get("PH");//
            holder.txtSPH.setOnClickListener((v)->{
                Intent SearchPhintent = new Intent();
                SearchPhintent.setClass(SearchPh.this,MedMessage.class);
                SearchPhintent .putExtra("PH",holder.txtSPH.getText());//跨頁取值
                SearchPhintent .putExtra("uid",uid);//跨頁取值
                startActivity(SearchPhintent);
            });
            if(Favlist.contains(Sph))
            {
                holder.imgLove.setBackgroundResource(R.drawable.full_heart);
            }
            holder.imgLove.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fav) {
                        addTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        holder.imgLove.setBackgroundResource(R.drawable.full_heart);
                        holder.imgLove.setSelected(true);
                        fav = true;
                        Favlist.add(Sph);
                        Toast.makeText(context, "加入常用藥局成功", Toast.LENGTH_SHORT).show();
                        AddFavPh();//加入常用藥局
                    }
                    else {
                        holder.imgLove.setBackgroundResource(R.drawable.empty_heart);
                        holder.imgLove.setSelected(false);
                        fav=false;
                        Favlist.remove(Sph);
                        Toast.makeText(context, "刪除常用藥局", Toast.LENGTH_SHORT).show();
                        DeleteFavPH();
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return PharrayList.size();
        }
    }
}