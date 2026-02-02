package com.app.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ChangeMedHistory2 extends AppCompatActivity {

    private ActionBar mActionBar;
    private Button mbtnNext4;
    String Drug = "", Drug2, Pregnant = "", Pregnant2, Smoke = "", Betlet = "", Drunk = "", message;
    String Drug3, Drug4, Pregnant3, Pregnant4, Smoke3, Betlet3, Drunk3;
    TextView mtxtDrugAllergy, mtxtPregnant, mtxtSmoke, mtxtBetlet, mtxtDrunk;
    CheckBox mchkbxDrugAllergyN, mchkbxDrugAllergyY, mchkbxPregnantN, mchkbxPregnantY,
            mchkbxSmokeN, mchkbxSmokeY, mchkbxBetletN, mchkbxBetletY;
    EditText medtDrugAllergy, medtPregnant;
    Spinner mSpiDrunk;
    Toolbar toolbar;
    String uid;
    Context context;
    Boolean ans = true, goUpdate=false, success=false;;
    String[] PersonalMed;

    Object resultString;
    String URL = "http://120.125.78.200/WebService1.asmx";
    private static  String NAMESPACE = "http://tempuri.org/";
    private static  String SOAP_ACTION = "http://tempuri.org/HealthShow";
    private static  String METHOD_NAME = "HealthShow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_med_history2);
        context = this;
        mbtnNext4 = findViewById(R.id.btnfinish);
        mbtnNext4.setOnClickListener(btnNext4OnClick);
        mSpiDrunk = findViewById(R.id.SpiDrunk);
        mSpiDrunk.setOnItemSelectedListener(spnSelect);

        mchkbxDrugAllergyY = findViewById(R.id.chkbxDrugAllergyY);
        mchkbxDrugAllergyN = findViewById(R.id.chkbxDrugAllergyN);
        mchkbxPregnantY = findViewById(R.id.chkbxPregnantY);
        mchkbxPregnantN = findViewById(R.id.chkbxPregnantN);
        mchkbxSmokeY = findViewById(R.id.chkbxSmokeY);
        mchkbxSmokeN = findViewById(R.id.chkbxSmokeN);
        mchkbxBetletY = findViewById(R.id.chkbxBetletY);
        mchkbxBetletN = findViewById(R.id.chkbxBetletN);

        mtxtDrugAllergy = findViewById(R.id.txtDrugAllergy);
        mtxtPregnant = findViewById(R.id.txtPregnant);
        mtxtSmoke = findViewById(R.id.txtSmoke);
        mtxtBetlet = findViewById(R.id.txtBetlet);
        mtxtDrunk = findViewById(R.id.txtDrunk);

        medtDrugAllergy = findViewById(R.id.edtDrugAllergy);
        medtPregnant = findViewById(R.id.edtPregnant);

        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("健康狀況修改");
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
                if (mchkbxDrugAllergyY.isChecked()) {
                    mchkbxDrugAllergyN.setChecked(false);
                    mchkbxDrugAllergyN.setEnabled(false);
                    medtDrugAllergy.setEnabled(true);
                    medtDrugAllergy.setFocusableInTouchMode(true);
                    medtDrugAllergy.setFocusable(true);
                    medtDrugAllergy.requestFocus();
                } else {
                    mchkbxDrugAllergyN.setEnabled(true);
                    mchkbxDrugAllergyN.setChecked(true);
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
                if (mchkbxPregnantY.isChecked()) {
                    mchkbxPregnantN.setChecked(false);
                    mchkbxPregnantN.setEnabled(false);
                    medtPregnant.setEnabled(true);
                    medtPregnant.setFocusableInTouchMode(true);
                    medtPregnant.setFocusable(true);
                    medtPregnant.requestFocus();
                } else {
                    mchkbxPregnantN.setEnabled(true);
                    mchkbxPregnantN.setChecked(true);
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
                if (mchkbxSmokeN.isChecked()) {
                    mchkbxSmokeY.setChecked(false);
                    mchkbxSmokeY.setEnabled(false);
                } else {
                    mchkbxSmokeY.setEnabled(true);
                    mchkbxSmokeY.setChecked(true);
                }
            }
        });
        mchkbxBetletN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mchkbxBetletN.isChecked()) {
                    mchkbxBetletY.setChecked(false);
                    mchkbxBetletY.setEnabled(false);
                } else {
                    mchkbxBetletY.setEnabled(true);
                    mchkbxBetletY.setChecked(true);
                }
            }
        });
        postData();
    }

    private void Check2() {
        ans = true;
        mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.text));
        mtxtPregnant.setTextColor(this.getResources().getColor(R.color.text));
        mtxtSmoke.setTextColor(this.getResources().getColor(R.color.text));
        mtxtBetlet.setTextColor(this.getResources().getColor(R.color.text));
        mtxtDrunk.setTextColor(this.getResources().getColor(R.color.text));
        if (Drug.equals("")) {
            mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans = false;
        } else if (mchkbxDrugAllergyY.isChecked() && Drug2.equals("")) {
            mtxtDrugAllergy.setTextColor(this.getResources().getColor(R.color.error_Orange));
            Toast.makeText(context, "請輸入過敏藥物", Toast.LENGTH_SHORT).show();
            ans = false;
        }
        if (Pregnant.equals("")) {
            mtxtPregnant.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans = false;
        } else if (mchkbxPregnantY.isChecked() && Pregnant2.equals("")) {
            mtxtPregnant.setTextColor(this.getResources().getColor(R.color.error_Orange));
            Toast.makeText(context, "請輸入懷孕週數", Toast.LENGTH_SHORT).show();
            ans = false;
        }
        if (Smoke.equals("")) {
            mtxtSmoke.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans = false;
        }
        if (Betlet.equals("")) {
            mtxtBetlet.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans = false;
        }
        if (Drunk.equals("")) {
            mtxtDrunk.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans = false;
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

    private AdapterView.OnItemSelectedListener spnSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            switch (i) {
                case 0:
                    Drunk = "無";
                    break;
                case 1:
                    Drunk = "偶爾";
                    break;
                case 2:
                    Drunk = "經常";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    String History, ID;
    private View.OnClickListener btnNext4OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Check1();
            Check2();
            if (ans == true) {
                goUpdate=true;
                Intent it = getIntent();
                History = it.getStringExtra("U_History");
                postData();
            }
        }
    };

    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String, Void, String> {

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
                if (goUpdate != true) {
                    SOAP_ACTION = "http://tempuri.org/HealthShow";
                    METHOD_NAME = "HealthShow";
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("uid",uid);

                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet = true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);
                    PersonalMed = new String[obj2.getPropertyCount()];

                    //拆解他obj2
                    Drug3 = obj2.getProperty(0).toString();
                    Pregnant3 = obj2.getProperty(5).toString();
                    Smoke3 = obj2.getProperty(2).toString();
                    Betlet3 = obj2.getProperty(4).toString();
                    Drunk3 = obj2.getProperty(3).toString();
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                    message = "OK";
                }
                else if(goUpdate){
                    SOAP_ACTION = "http://tempuri.org/UpdateHealth";
                    METHOD_NAME = "UpdateHealth";

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

                    SoapPrimitive result = (SoapPrimitive) soapSerializationEnvelope.getResponse();
                    success = Boolean.parseBoolean(result.toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                message = "ERROR:" + e.getMessage();
            }
            return null;
        }

        //Postdata最後的結果作判斷
        @Override
        protected void onPostExecute(String s) {
            //progressDialog.dismiss();

            if (goUpdate != true) {
                makeData();
            }
            else if(goUpdate)
            {
                if (success)
                {
                    Toast.makeText(ChangeMedHistory2.this, "修改成功", Toast.LENGTH_LONG).show();
                    Intent it=new Intent();
                    it.setClass(ChangeMedHistory2.this,MainActivity.class);
                    startActivity(it);//跳到主頁面
                }
                else {
                    Toast.makeText(ChangeMedHistory2.this, "有欄位沒填到哦", Toast.LENGTH_LONG).show();
                }
            }

            super.onPostExecute(s);
        }

        //U_Name, U_Gender, U_BD, U_Phone, U_Email, U_Address, U_Identity
        private void makeData() {
            if (Drug3.equals("無"))
            {
                mchkbxDrugAllergyN.setChecked(true);
            }
            else if (Drug3.indexOf("有")>-1)
            {
                mchkbxDrugAllergyY.setChecked(true);
                int a = Drug3.indexOf(";");
                Drug4 = Drug3.substring(a+1);
                medtDrugAllergy.setText(Drug4);
            }

            if (Drunk3.equals("無"))
            {
                mSpiDrunk.setSelection(0);
            }
            else if (Drunk3.equals("偶爾"))
            {
                mSpiDrunk.setSelection(1);
            }
            else if (Drunk3.equals("經常"))
            {
                mSpiDrunk.setSelection(2);
            }

            if (Betlet3.equals("無"))
            {
                mchkbxBetletN.setChecked(true);
            }
            if (Betlet3.equals("有"))
            {
                mchkbxBetletY.setChecked(true);
            }

            if (Smoke3.equals("無"))
            {
                mchkbxSmokeN.setChecked(true);
            }
            if (Smoke3.equals("有"))
            {
                mchkbxSmokeY.setChecked(true);
            }

            if (Pregnant3.equals("無"))
            {
                mchkbxPregnantN.setChecked(true);
            }
            else if (Pregnant3.indexOf("有")>-1)
            {
                mchkbxPregnantY.setChecked(true);
                int a = Pregnant3.indexOf(";");
                Pregnant4 = Pregnant3.substring(a+1);
                medtPregnant.setText(Pregnant4);
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