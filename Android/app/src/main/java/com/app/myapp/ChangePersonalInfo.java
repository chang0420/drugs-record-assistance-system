package com.app.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePersonalInfo extends AppCompatActivity {

    private ActionBar mActionBar;
    ProgressDialog progressDialog;
    Context context;
    String message;
    boolean ans=true, phoneOK=true, emailOk=true, nameOK=true;
    String Name, Phone, Email, Address;
    EditText medtName, medtPhone, medtEmail, medtAddress;
    TextView mtxtName, mtxtPhone, mtxtEmail, mtxtAddress, mtxtIdentity2, mtxtGender2, mtxtBD2;
    Button mbtnChange;
    Toolbar toolbar;
    String uid;

    String[] PersonalInfo;
    boolean goUpdate =false;
    boolean success=false;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_UserName="UserName";

    Object resultString;
    String URL= "http://120.125.78.200/WebService1.asmx";
    private static String NAMESPACE="http://tempuri.org/";
    private static String SOAP_ACTION="http://tempuri.org/UserShow";
    private static String METHOD_NAME="UserShow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);

        medtName=findViewById(R.id.edtName);
        medtPhone=findViewById(R.id.edtPhone);
        medtEmail=findViewById(R.id.edtEmail);
        medtAddress=findViewById(R.id.edtAddress);

        mtxtAddress=findViewById(R.id.txtAddress);
        mtxtEmail=findViewById(R.id.txtEmail);
        mtxtName=findViewById(R.id.txtName);
        mtxtPhone=findViewById(R.id.txtBetlet);
        mtxtBD2=findViewById(R.id.txtBD2);
        mtxtIdentity2=findViewById(R.id.txtIdentity2);
        mtxtGender2=findViewById(R.id.txtGender2);

        mbtnChange=findViewById(R.id.btnChange);
        mbtnChange.setOnClickListener(btnChangeClick);

        Intent intent = this.getIntent();
        uid= intent.getStringExtra("uid");

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("基本資料修改");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        context =this;
        postData();
    }
    private void isnull(){
        ans=true;
        Name = medtName.getText().toString();
        Phone = medtPhone.getText().toString();
        Email = medtEmail.getText().toString();
        Address = medtAddress.getText().toString();

        mtxtName.setTextColor(this.getResources().getColor(R.color.original));
        mtxtPhone.setTextColor(this.getResources().getColor(R.color.original));
        mtxtEmail.setTextColor(this.getResources().getColor(R.color.original));
        mtxtAddress.setTextColor(this.getResources().getColor(R.color.original));

        if(Name.equals("")){
            mtxtName.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
       /* else {
            checkIsChinese();
        }*/
        if(Phone.equals("")){
            mtxtPhone.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        else {
            checkPhone();
            if(phoneOK == false){
                ans=false;
                mtxtPhone.setTextColor(this.getResources().getColor(R.color.error_Orange));
            }
        }
        if(Email.equals("")){
            mtxtEmail.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        else {
            RegexMatches();
            if(emailOk==false){
                mtxtEmail.setTextColor(this.getResources().getColor(R.color.error_Orange));
                ans=false;
            }
        }
        if(Address.equals("")){
            mtxtAddress.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
    };
    //手機格式驗證
    private void checkPhone(){
        String cellphone = medtPhone.getText().toString();
        cellphone = cellphone.trim(); //把空白濾掉
        Pattern pattern = Pattern.compile("(09)+[\\d]{8}");
        Matcher matcher = pattern.matcher(cellphone);
        if(matcher.find()) {
            phoneOK=true;
        }
        else {
            phoneOK=false;
        }
    }
    //信箱格式驗證
    private void RegexMatches(){
        String email=medtEmail.getText().toString();
        Pattern regex = Pattern.compile("^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$");
        Matcher matcher = regex.matcher(email);
        if(matcher.find()) {
            emailOk=true;
        }
        else {
            emailOk=false;
            mtxtEmail.setTextColor(this.getResources().getColor(R.color.error_Orange));
        }
        /*//Pattern p = Pattern.compile(regex);
        boolean isMatch = Pattern.matches(regex, email);
        if(isMatch=false){
            ans=false;
            emailOk=false;}*/
    };

    private View.OnClickListener btnChangeClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isnull();
            if(ans==true)
            {
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

            /*
            try {//左邊是webservice方法參數名,右邊是這邊值的名子
                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("Uname",Name);
                request.addProperty("Uphone",Phone);
                request.addProperty("Uemail",Email);
                request.addProperty("Uaddress",Address);

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                message="OK";

            }catch(Exception e)*/
            try {
                if(goUpdate != true)
                {
                    SOAP_ACTION = "http://tempuri.org/UserShow";
                    METHOD_NAME = "UserShow";
                    SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("uid",uid);

                    SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet=true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE= new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION,soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                    SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);
                    PersonalInfo=new String[obj2.getPropertyCount()];

                    //拆解他obj2
                    PersonalInfo[0]= obj2.getProperty(0).toString();
                    PersonalInfo[1]= obj2.getProperty(1).toString();
                    PersonalInfo[2]= obj2.getProperty(2).toString();
                    PersonalInfo[3]= obj2.getProperty(3).toString();
                    PersonalInfo[4]= obj2.getProperty(4).toString();
                    PersonalInfo[5]= obj2.getProperty(5).toString();
                    PersonalInfo[6]= obj2.getProperty(6).toString();
                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                    message="OK";
                }
                else
                {
                    METHOD_NAME="UserUpdateUser";
                    SOAP_ACTION="http://tempuri.org/UserUpdateUser";

                        SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                        request.addProperty("uid",uid);
                        request.addProperty("Uname",Name);
                        request.addProperty("Uphone",Phone);
                        request.addProperty("Uemail",Email);
                        request.addProperty("Uaddress",Address);

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
            if(goUpdate!=true){
                makeData();
            }
            else if(goUpdate)
            {
                if (success)
                {
                    SharedPreferences();
                    Toast.makeText(ChangePersonalInfo.this, "修改成功", Toast.LENGTH_LONG).show();
                    Intent it=new Intent();
                    it.setClass(ChangePersonalInfo.this,MainActivity.class);
                    startActivity(it);//跳到主頁面
                }
                else {
                    Toast.makeText(ChangePersonalInfo.this, "修改失敗", Toast.LENGTH_LONG).show();
                }
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
            medtName.setText(PersonalInfo[0]);
            mtxtIdentity2.setText(PersonalInfo[6]);
            mtxtGender2.setText(PersonalInfo[1]);
            mtxtBD2.setText(PersonalInfo[2]);
            medtPhone.setText(PersonalInfo[3]);
            medtEmail.setText(PersonalInfo[4]);
            medtAddress.setText(PersonalInfo[5]);
        }
    }
    private void SharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_UserName,Name);
        editor.commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}