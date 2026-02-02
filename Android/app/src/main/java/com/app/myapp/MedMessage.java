package com.app.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MedMessage extends AppCompatActivity {
    private Button btn_add;
    //Soap
    String URL="http://120.125.78.206/BrainShaking.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_NAME ="InserLackMed";
    private static final String SOAP_ACTION = "http://tempuri.org/InserLackMed";
    private static final String METHOD_NAME2 ="DeleteLackMed";
    private static final String NAMESPACE2 = "http://tempuri.org/";
    private static final String SOAP_ACTION2 = "http://tempuri.org/DeleteLackMed";
    private static final String METHOD_NAMEL ="SearchLackMed";
    private static final String NAMESPACEL = "http://tempuri.org/";
    private static final String SOAP_ACTIONL = "http://tempuri.org/SearchLackMed";
    private static final String METHOD_NAME4 ="SearchPh";
    private static final String NAMESPACE4 = "http://tempuri.org/";
    private static final String SOAP_ACTION4 = "http://tempuri.org/SearchPh";
    int deletePosition;
    EditText edtLackMed;
    TextView txtPhone,txtAddress,txtMph,txtNormal,txtMph1;
    Object resultString;
    String message,uid;
    String str,nowDate,LackMCMed,Time;
    String[][] Search_Ph;
    String[][] Lack_Med;
    Context context;
    View view;
    Toolbar toolbar;
    private ActionBar mActionBar;
    //
    RecyclerView mLackRecyclerView;
    MyListAdapter myLackListAdapter;
    ArrayList<HashMap<String,String>> LackarrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_message);
        toolbar = findViewById(R.id.myToolbar2);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        context=this;
        txtPhone=(TextView)findViewById(R.id.txtphone);
        txtAddress=(TextView)findViewById(R.id.txtAddress);
        txtMph=findViewById(R.id.txtMph) ;//跨頁取值
        txtMph.setText(getIntent().getStringExtra("PH"));//跨頁取值

        uid=getIntent().getStringExtra("uid");

        mLackRecyclerView = findViewById(R.id.recycleviewtestLackmessage);
        mLackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LoadMed();
        //update();
        inited();


        txtPhone.setOnClickListener(new View.OnClickListener() {//點擊號碼到撥號頁面
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txtPhone.getText()));
                startActivity(intent);
            }
        });

    }
    private void recyclerViewAction(RecyclerView mLackRecyclerView, final ArrayList<HashMap<String,String>> LackarrayList, final MyListAdapter myLackListAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                //這裡是告訴RecyclerView你想開啟哪些操作
                return makeMovementFlags(0,ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;//管理上下拖曳
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                deleteMessage();//
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        LackMCMed= LackarrayList.get(position).get("LackMCMed");//
                        Time=LackarrayList.get(position).get("LackMDate");
                        LackarrayList.remove(position);
                        myLackListAdapter.notifyItemRemoved(position);
                        deleteMessage();
//                        LackarrayList.clear();
//                        LoadMed();
                        break;
                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MedMessage.this, R.color.red_GINSYU))
                        .addActionIcon(R.drawable.deletetxt1)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(mLackRecyclerView);
    }
    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtLackMDate,txtLackMCMed,txtLackItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtLackMCMed=itemView.findViewById(R.id.txtLackMCMed);
                txtLackMDate=itemView.findViewById(R.id.txtLackMDate);
                txtLackItem=itemView.findViewById(R.id.txtLackItem);
            }
        }
        @NonNull
        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
            holder.txtLackMCMed.setText(LackarrayList.get(position).get("LackMCMed"));
            holder.txtLackMDate.setText(LackarrayList.get(position).get("LackMDate"));
            holder.txtLackItem.setText(LackarrayList.get(position).get("LackItem"));
        }

        @Override
        public int getItemCount() {
            return LackarrayList.size();
        }
    }
    private void update(){
        new WebServiceUpdate().execute("");
    }
    private class WebServiceUpdate extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE4,METHOD_NAME4);
                request.addProperty("Phname", txtMph.getText().toString());

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION4,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                Search_Ph=new String[obj2.getPropertyCount()][3];
                SoapObject obj3 =(SoapObject) obj2.getProperty(0);
                Search_Ph[0][0]= obj3.getProperty(0).toString();
                Search_Ph[0][1] = obj3.getProperty(1).toString();
                Search_Ph[0][2]= obj3.getProperty(2).toString();
                txtAddress.setText(Search_Ph[0][1]);
                txtPhone.setText(Search_Ph[0][2]);
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
    }
    private void inited() {
        edtLackMed = (EditText) findViewById(R.id.edtLackMed);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtLackMed.getText().toString().matches(""))
                {
                    Toast.makeText(context, "請輸入留言", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Data&Time
                    nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    sendMed();//送出缺藥
                    setInsertData(edtLackMed.getText().toString(),nowDate,"缺藥");
                    //myLackListAdapter.notifyItemRemoved(LackarrayList.size()-1);

                }
            }
        });
    }

    private void setInsertData(String Med,String Date,String Item)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("LackMCMed",Med);
        hashMap.put("LackMDate",Date);
        hashMap.put("LackItem",Item);

        LackarrayList.add(hashMap);
        Collections.reverse(LackarrayList);

        myLackListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMed(){
        new WebServiceLoadData().execute("");
    }

    private class WebServiceLoadData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {



            try{
                SoapObject requestDetail=new SoapObject(NAMESPACE4,METHOD_NAME4);
                requestDetail.addProperty("Phname", txtMph.getText().toString());

                SoapSerializationEnvelope Envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                Envelope.dotNet=true;
                Envelope.setOutputSoapObject(requestDetail);
                HttpTransportSE tSE= new HttpTransportSE(URL);
                tSE.call(SOAP_ACTION4,Envelope);
                resultString =Envelope.getResponse();
                SoapObject body = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj =(SoapObject) body.getProperty(0);
                Search_Ph=new String[obj.getPropertyCount()][3];
                SoapObject getObj =(SoapObject) obj.getProperty(0);
                Search_Ph[0][0]= getObj.getProperty(0).toString();
                Search_Ph[0][1] = getObj.getProperty(1).toString();
                Search_Ph[0][2]= getObj.getProperty(2).toString();




                SoapObject request=new SoapObject(NAMESPACEL,METHOD_NAMEL);
                request.addProperty("Phname",txtMph.getText().toString());
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTIONL,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);//
                Lack_Med=new String[obj2.getPropertyCount()][2];
                for(int i=0; i<obj2.getPropertyCount(); i++)
                {
                    SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                    Lack_Med[i][0] = obj3.getProperty(0).toString();//Date
                    Lack_Med[i][1]= obj3.getProperty(1).toString();//Message
                    HashMap<String,String> LhashMap = new HashMap<>();
                    LhashMap.put("LackItem", "缺藥");
                    LhashMap.put("LackMDate", Lack_Med[i][0]);
                    LhashMap.put("LackMCMed", Lack_Med[i][1]);
                    LackarrayList.add(LhashMap);
                    message="OK";
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            txtAddress.setText(Search_Ph[0][1]);
            txtPhone.setText(Search_Ph[0][2]);

            myLackListAdapter = new MyListAdapter();
            mLackRecyclerView.setAdapter(myLackListAdapter);
            recyclerViewAction(mLackRecyclerView, LackarrayList, myLackListAdapter);



            super.onPostExecute(s);

        }
    }
    private void sendMed(){//送出缺藥
        new WebServicePostData().execute("");//
    }
    private class WebServicePostData extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try{

                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("uid",uid);
                request.addProperty("PhAddress",txtAddress.getText().toString());
                request.addProperty("DateandTime",nowDate);//取發送日期
                request.addProperty("Message",edtLackMed.getText().toString());
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();





            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            edtLackMed.setText("");
        }
    }
    private void deleteMessage(){//刪除缺藥
        new WebServiceDeleteData().execute("");//
    }
    private class WebServiceDeleteData extends AsyncTask<String,Void,String>{////刪除缺藥

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                SoapObject request=new SoapObject(NAMESPACE2,METHOD_NAME2);
                request.addProperty("PhAddress",txtAddress.getText().toString());
                request.addProperty("Message",LackMCMed);
                request.addProperty("Time",Time);
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION2,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {




            super.onPostExecute(s);

        }
    }
}
