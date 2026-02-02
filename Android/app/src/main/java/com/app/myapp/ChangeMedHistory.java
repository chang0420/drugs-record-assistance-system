package com.app.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ChangeMedHistory extends AppCompatActivity {

    private ActionBar mActionBar;
    private Button mbtnNext3;
    Toolbar toolbar;
    CheckBox mchkbxNull,mchkbxBrain, mchkbxCancer, mchkbxlung, mchkbxHeart, mchkbxkidney, mchkbxHighBlood,
            mchkbxBig, mchkbxLiver, mchkbxMental, mchkbxImmunity, mchkbxSugar, mchkbxEpilepsy, mchkbxOther;
    TextView medtOther;
    String Other, Other2, History="", uid, message, Med;
    boolean ans=true, goUpdate=false;
    Context context;
    String[] PersonalMed;

    Object resultString;
    String URL= "http://120.125.78.200/WebService1.asmx";
    private static String NAMESPACE="http://tempuri.org/";
    private static String SOAP_ACTION="http://tempuri.org/HealthShow";
    private static String METHOD_NAME="HealthShow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_med_history);
        mbtnNext3=findViewById(R.id.btnNext3);
        mbtnNext3.setOnClickListener(btnNext3OnClick);

        mchkbxNull=findViewById(R.id.chkbxNull);
        mchkbxBrain=findViewById(R.id.chkbxBrain);
        mchkbxCancer=findViewById(R.id.chkbxCancer);
        mchkbxlung=findViewById(R.id.chkbxlung);
        mchkbxHeart=findViewById(R.id.chkbxHeart);
        mchkbxkidney=findViewById(R.id.chkbxkidney);
        mchkbxHighBlood=findViewById(R.id.chkbxHighBlood);
        mchkbxBig=findViewById(R.id.chkbxBig);
        mchkbxLiver=findViewById(R.id.chkbxLiver);
        mchkbxMental=findViewById(R.id.chkbxMental);
        mchkbxImmunity=findViewById(R.id.chkbxImmunity);
        mchkbxSugar=findViewById(R.id.chkbxSugar);
        mchkbxEpilepsy=findViewById(R.id.chkbxEpilepsy);
        mchkbxOther=findViewById(R.id.chkbxOther);

        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");
        medtOther=findViewById(R.id.edtOther);
        Other=medtOther.getText().toString();
        context=this;

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("健康狀況修改");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        postData();

        mchkbxNull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxNull.isChecked()){
                    mchkbxBrain.setChecked(false);
                    mchkbxBrain.setEnabled(false);
                    mchkbxCancer.setChecked(false);
                    mchkbxCancer.setEnabled(false);
                    mchkbxlung.setChecked(false);
                    mchkbxlung.setEnabled(false);
                    mchkbxHeart.setChecked(false);
                    mchkbxHeart.setEnabled(false);
                    mchkbxkidney.setChecked(false);
                    mchkbxkidney.setEnabled(false);
                    mchkbxHighBlood.setChecked(false);
                    mchkbxHighBlood.setEnabled(false);
                    mchkbxBig.setChecked(false);
                    mchkbxBig.setEnabled(false);
                    mchkbxLiver.setChecked(false);
                    mchkbxLiver.setEnabled(false);
                    mchkbxMental.setChecked(false);
                    mchkbxMental.setEnabled(false);
                    mchkbxImmunity.setChecked(false);
                    mchkbxImmunity.setEnabled(false);
                    mchkbxSugar.setChecked(false);
                    mchkbxSugar.setEnabled(false);
                    mchkbxEpilepsy.setChecked(false);
                    mchkbxEpilepsy.setEnabled(false);
                    mchkbxOther.setChecked(false);
                    mchkbxOther.setEnabled(false);
                }
                else {
                    mchkbxBrain.setEnabled(true);
                    mchkbxCancer.setEnabled(true);
                    mchkbxlung.setEnabled(true);
                    mchkbxHeart.setEnabled(true);
                    mchkbxkidney.setEnabled(true);
                    mchkbxHighBlood.setEnabled(true);
                    mchkbxBig.setEnabled(true);
                    mchkbxLiver.setEnabled(true);
                    mchkbxMental.setEnabled(true);
                    mchkbxImmunity.setEnabled(true);
                    mchkbxSugar.setEnabled(true);
                    mchkbxEpilepsy.setEnabled(true);
                    mchkbxOther.setEnabled(true);
                }
            }

        });

        mchkbxOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxOther.isChecked()){
                    // medtOther.setFocusable(true);

                    medtOther.setEnabled(true);
                    medtOther.setFocusableInTouchMode(true);
                    medtOther.setFocusable(true);
                    medtOther.requestFocus();
                }
                else {
                    //medtOther.setFocusable(false);

                    medtOther.setText("");
                    medtOther.setFocusable(false);
                    medtOther.setEnabled(false);
                    medtOther.setFocusableInTouchMode(false);
                }
            }
        });
    }

    private void Check(){
        Other=medtOther.getText().toString();
        if(mchkbxNull.isChecked()){
            History+="無";
        }
        else {
            if(mchkbxBrain.isChecked()){
                History+="腦血管疾病;";
            }
            if(mchkbxCancer.isChecked()){
                History+="惡性腫瘤;";
            }
            if(mchkbxlung.isChecked()){
                History+="肺部疾病;";
            }
            if(mchkbxHeart.isChecked()){
                History+="心臟疾病;";
            }
            if(mchkbxkidney.isChecked()){
                History+="腎臟疾病;";
            }
            if(mchkbxHighBlood.isChecked()){
                History+="高血壓疾病;";
            }
            if(mchkbxLiver.isChecked()){
                History+="慢性肝病及肝硬化;";
            }
            if(mchkbxMental.isChecked()){
                History+="精神病;";
            }
            if(mchkbxImmunity.isChecked()){
                History+="免疫性疾病;";
            }
            if(mchkbxSugar.isChecked()){
                History+="糖尿病;";
            }
            if(mchkbxEpilepsy.isChecked()){
                History+="癲癇;";
            }
            if(mchkbxOther.isChecked()){
                //medtOther.setEnabled(true);
                if(!Other.equals("")){
                    History+=("其他:"+Other);
                }
                else{
                    ans=false;
                    Toast.makeText(context,"請輸入其他疾病", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //MedHistory=String.join(";",History.toArray());
    }

    private View.OnClickListener btnNext3OnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Check();
            if(!History.equals("") && ans!=false){
                Intent it=new Intent();
                it.setClass(ChangeMedHistory.this,ChangeMedHistory2.class);
                it.putExtra("U_History",History);
                it.putExtra("uid",uid);

                startActivity(it);
                goUpdate=true;
                postData();
            }
        }
    };

    private void postData() {
        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            /*progressDialog = ProgressDialog.show(context,"","waiting....");
            progressDialog.setCancelable(false);*/
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {
          try {
                if(goUpdate != true)
                {
                    SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("uid",uid);

                    SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet=true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE= new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION,soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                    SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                    PersonalMed=new String[obj2.getPropertyCount()];

                    //拆解他obj2
                    Med= obj2.getProperty(1).toString();
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                    message="OK";
                }
                else
                {

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }

        //Postdata最後的結果作判斷
        @Override
        protected void onPostExecute(String s) {
            //progressDialog.dismiss();
            if(Med != null && goUpdate!=true){
                makeData();
            }
            /*
            if(resultString.toString() != "false")
            {
                new AlertDialog.Builder(context).setMessage(Name+"修改成功!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent it=new Intent();
                                it.setClass(ChangePersonalInfo.this,MainActivity.class);
                                startActivity(it);//跳到健康1頁面
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
            else
            {
                medtName.setText("");
                medtPhone.setText("");
                medtEmail.setText("");
                medtAddress.setText("");
                Toast.makeText(context,"請重新輸個人資料!", Toast.LENGTH_SHORT).show();
            }*/
            super.onPostExecute(s);
        }
        //U_Name, U_Gender, U_BD, U_Phone, U_Email, U_Address, U_Identity
        private void makeData() {
            if(Med.equals("無"))
            {
                mchkbxNull.setChecked(true);
            }
            else
            {
                String[] cmds = Med.split(";");
                for(int i = 0; i<cmds.length;i++){
                    if (cmds[i].equals("惡性腫瘤"))
                        mchkbxCancer.setChecked(true);
                    if (cmds[i].equals("腦血管疾病"))
                        mchkbxBrain.setChecked(true);
                    if (cmds[i].equals("心臟疾病"))
                        mchkbxHeart.setChecked(true);
                    if (cmds[i].equals("肺部疾病"))
                        mchkbxlung.setChecked(true);
                    if (cmds[i].equals("腎臟病"))
                        mchkbxkidney.setChecked(true);
                    if (cmds[i].equals("高血壓疾病"))
                        mchkbxHighBlood.setChecked(true);
                    if (cmds[i].equals("慢性肝病及肝硬化"))
                        mchkbxLiver.setChecked(true);
                    if (cmds[i].equals("免疫性疾病"))
                        mchkbxImmunity.setChecked(true);
                    if (cmds[i].equals("前列腺肥大"))
                        mchkbxBig.setChecked(true);
                    if (cmds[i].equals("精神病"))
                        mchkbxMental.setChecked(true);
                    if (cmds[i].equals("癲癇"))
                        mchkbxEpilepsy.setChecked(true);
                    if (cmds[i].equals("糖尿病"))
                        mchkbxSugar.setChecked(true);
                    if (cmds[i].indexOf("其他:")>-1)
                    {
                        mchkbxOther.setChecked(true);
                        int a = Med.indexOf("其他:");
                        Other2 = Med.substring(a+3);
                        medtOther.setText(Other2);
                    }
                }
                }
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