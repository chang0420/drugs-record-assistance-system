package com.app.myapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MedicalHistory2 extends AppCompatActivity {

    private ActionBar mActionBar;
    private Toolbar toolbar;
    private Button mbtnNext4;
    String Drug="", Drug2, Pregnant="", Pregnant2, Smoke="", Betlet="", Drunk="", message;
    TextView mtxtDrugAllergy, mtxtPregnant, mtxtSmoke, mtxtBetlet, mtxtDrunk;
    CheckBox mchkbxDrugAllergyN, mchkbxDrugAllergyY, mchkbxPregnantN, mchkbxPregnantY,
            mchkbxSmokeN, mchkbxSmokeY, mchkbxBetletN, mchkbxBetletY;
    EditText medtDrugAllergy, medtPregnant;
    Spinner mSpiDrunk;
    Context context;
    Boolean ans=true;

    Object resultString;
    String URL= "http://120.125.78.200/WebService1.asmx";
    private  static  final  String NAMESPACE="http://tempuri.org/";
    private static final  String SOAP_ACTION="http://tempuri.org/UpdateHealth";
    private static  final  String METHOD_NAME="UpdateHealth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history2);
        context=this;
        mbtnNext4=findViewById(R.id.btnfinish);
        mbtnNext4.setOnClickListener(btnNext4OnClick);
        mSpiDrunk=findViewById(R.id.SpiDrunk);
        mSpiDrunk.setOnItemSelectedListener(spnSelect);

        mchkbxDrugAllergyY=findViewById(R.id.chkbxDrugAllergyY);
        mchkbxDrugAllergyN=findViewById(R.id.chkbxDrugAllergyN);
        mchkbxPregnantY=findViewById(R.id.chkbxPregnantY);
        mchkbxPregnantN=findViewById(R.id.chkbxPregnantN);
        mchkbxSmokeY=findViewById(R.id.chkbxSmokeY);
        mchkbxSmokeN=findViewById(R.id.chkbxSmokeN);
        mchkbxBetletY=findViewById(R.id.chkbxBetletY);
        mchkbxBetletN=findViewById(R.id.chkbxBetletN);

        mtxtDrugAllergy=findViewById(R.id.txtDrugAllergy);
        mtxtPregnant=findViewById(R.id.txtPregnant);
        mtxtSmoke=findViewById(R.id.txtSmoke);
        mtxtBetlet=findViewById(R.id.txtBetlet);
        mtxtDrunk=findViewById(R.id.txtDrunk);

        medtDrugAllergy=findViewById(R.id.edtDrugAllergy);
        medtPregnant=findViewById(R.id.edtPregnant);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        /*mchkbxDrugAllergyN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxDrugAllergyN.isChecked()){
                    mchkbxDrugAllergyY.setChecked(false);
                    mchkbxDrugAllergyY.setEnabled(false);
                    medtDrugAllergy.setText("");
                    medtDrugAllergy.setFocusable(false);
                    medtDrugAllergy.setEnabled(false);
                    medtDrugAllergy.setFocusableInTouchMode(false);
                }
                else {
                    mchkbxDrugAllergyY.setEnabled(true);
                    mchkbxDrugAllergyY.setChecked(true);
                    medtDrugAllergy.setEnabled(true);
                    medtDrugAllergy.setFocusableInTouchMode(true);
                    medtDrugAllergy.setFocusable(true);
                    medtDrugAllergy.requestFocus();
                }
            }
        });*/
        mchkbxDrugAllergyY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxDrugAllergyY.isChecked()){
                    mchkbxDrugAllergyN.setChecked(false);
                    mchkbxDrugAllergyN.setEnabled(false);
                    medtDrugAllergy.setEnabled(true);
                    medtDrugAllergy.setFocusableInTouchMode(true);
                    medtDrugAllergy.setFocusable(true);
                    medtDrugAllergy.requestFocus();
                }
                else {
                    mchkbxDrugAllergyN.setEnabled(true);
                    medtDrugAllergy.setText("");
                    medtDrugAllergy.setFocusable(false);
                    medtDrugAllergy.setEnabled(false);
                    medtDrugAllergy.setFocusableInTouchMode(false);
                }
            }
        });
        /*mchkbxPregnantN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxPregnantN.isChecked()){
                    mchkbxPregnantY.setChecked(false);
                    mchkbxPregnantY.setEnabled(false);
                    medtPregnant.setText("");
                    medtPregnant.setFocusable(false);
                    medtPregnant.setEnabled(false);
                    medtPregnant.setFocusableInTouchMode(false);
                }
                else {
                    mchkbxPregnantY.setEnabled(true);
                    mchkbxPregnantY.setChecked(true);
                    medtPregnant.setEnabled(true);
                    medtPregnant.setFocusableInTouchMode(true);
                    medtPregnant.setFocusable(true);
                    medtPregnant.requestFocus();
                }
            }
        });*/
        mchkbxPregnantY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxPregnantY.isChecked()){
                    mchkbxPregnantN.setChecked(false);
                    mchkbxPregnantN.setEnabled(false);
                    medtPregnant.setEnabled(true);
                    medtPregnant.setFocusableInTouchMode(true);
                    medtPregnant.setFocusable(true);
                    medtPregnant.requestFocus();
                }
                else {
                    mchkbxPregnantN.setEnabled(true);
                    medtPregnant.setText("");
                    medtPregnant.setFocusable(false);
                    medtPregnant.setEnabled(false);
                    medtPregnant.setFocusableInTouchMode(false);
                }
            }
        });
        mchkbxSmokeN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxSmokeN.isChecked()){
                    mchkbxSmokeY.setChecked(false);
                    mchkbxSmokeY.setEnabled(false);
                }
                else{
                    mchkbxSmokeY.setEnabled(true);
                    //mchkbxSmokeY.setChecked(true);
                }
            }
        });
        mchkbxBetletN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mchkbxBetletN.isChecked()){
                    mchkbxBetletY.setChecked(false);
                    mchkbxBetletY.setEnabled(false);
                }
                else{
                    mchkbxBetletY.setEnabled(true);
                    // mchkbxBetletY.setChecked(true);
                }
            }
        });

    }

    private void Check2(){
        ans=true;
        mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.text));
        mtxtPregnant.setTextColor(this.getResources().getColor(R.color.text));
        mtxtSmoke.setTextColor(this.getResources().getColor(R.color.text));
        mtxtBetlet.setTextColor(this.getResources().getColor(R.color.text));
        mtxtDrunk.setTextColor(this.getResources().getColor(R.color.text));

        if(Drug.equals("")){
            mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        else if(mchkbxDrugAllergyY.isChecked() && Drug2.equals("")){
            mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.error_Orange));
            Toast.makeText(context,"請輸入過敏藥物", Toast.LENGTH_SHORT).show();
            ans=false;
        }
        if(Pregnant.equals("")){
            mtxtPregnant.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        else if(mchkbxPregnantY.isChecked() && Pregnant2.equals("")){
            mtxtPregnant.setTextColor(this.getResources().getColor(R.color.error_Orange));
            Toast.makeText(context,"請輸入懷孕週數", Toast.LENGTH_SHORT).show();
            ans=false;
        }
        if(Smoke.equals("")){
            mtxtSmoke.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        if(Betlet.equals("")){
            mtxtBetlet.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        if(Drunk.equals("")){
            mtxtDrunk.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
    }

    private void Check1() {
        Drug2 = medtDrugAllergy.getText().toString();
        Pregnant2 = medtPregnant.getText().toString();

        if (mchkbxDrugAllergyN.isChecked()) {
            Drug = "無";
        } else if (mchkbxDrugAllergyY.isChecked()) {
            Drug = ("有;" + Drug2);
        }
        if (mchkbxPregnantN.isChecked()) {
            Pregnant += "無";
        } else if (mchkbxPregnantY.isChecked()) {
            Pregnant = ("有;" + Pregnant2);
        }
        if (mchkbxSmokeN.isChecked()) {
            Smoke += "無";
        } else if (mchkbxSmokeY.isChecked()) {
            Smoke += "有";
        }
        if (mchkbxBetletN.isChecked()) {
            Betlet += "無";
        } else if (mchkbxBetletY.isChecked()) {
            Betlet += "有";
        }
    }

    private AdapterView.OnItemSelectedListener spnSelect=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            switch (i){
                case 0:
                    Drunk="無";
                    break;
                case 1:
                    Drunk="偶爾";
                    break;
                case 2:
                    Drunk="經常";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    String History, uid;
    private View.OnClickListener btnNext4OnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Check1();
            Check2();
            if(ans==true){
                Intent it = getIntent();
                History = it.getStringExtra("U_History");
                uid=it.getStringExtra("uid");
                postData();
            }
            //Intent it=new Intent();
            //it.setClass(MedicalHistory2.this,MedcalHistory3.class);
            //startActivity(it);

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

        @Override
        protected String doInBackground(String... strings) {

            try {//左邊是webservice方法參數名,右邊是這邊值的名子
                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("uid",uid);
                request.addProperty("DrugAllergy",Drug);
                request.addProperty("MedHistory",History);
                request.addProperty("Smoke",Smoke);
                request.addProperty("Drunk",Drunk);
                request.addProperty("Betlet",Betlet);
                request.addProperty("Pregnant",Pregnant);

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
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
            //progressDialog.dismiss();
            if(resultString.toString() != "false")
            {
                new AlertDialog.Builder(context).setMessage("填寫完成!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent it=new Intent();
                                it.setClass(MedicalHistory2.this,LogIn.class);
                                startActivity(it);//跳到登入頁面
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
            else
            {
                Toast.makeText(context,"填寫有誤", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
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