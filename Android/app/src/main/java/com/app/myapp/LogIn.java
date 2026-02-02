package com.app.myapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LogIn extends AppCompatActivity {

    private ActionBar mActionBar;
    private Toolbar toolbar;
    Context context;
    ProgressDialog progressDialog;
    String message;

    private ImageButton mimgbtnEye;
    private EditText medtLogInPass,mCaseId;
    private boolean isOpenEye = false;
    private Button btnLogIn;
    AlertDialog alertDialog;
    String ans;

    Object resultString;
    String URL= "http://120.125.78.200/WebService1.asmx";

    private  static  final  String NAMESPACE="http://tempuri.org/";
    private static final  String SOAP_ACTION="http://tempuri.org/UserSelect";
    private static  final  String METHOD_NAME="UserSelect";

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_Account="Account";
    private static final String KEY_Password="Password";
    private static final String KEY_UserName="UserName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String Account=sharedPreferences.getString(KEY_Account,null);

        medtLogInPass = findViewById(R.id.edtLogInPass); //密碼
        mCaseId =findViewById(R.id.edtLogInAccount); //帳號

        mimgbtnEye = findViewById(R.id.imgbtnEye);
        mimgbtnEye.setOnClickListener(imgbtnEyeOnClick);
        btnLogIn=findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(btnLogInOnClick);


        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        context =this;
    }

    private View.OnClickListener btnLogInOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            postData();
        }
    };

    private void postData() {

        new WebServicePostData().execute("");
    }

    private void SharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_Account,mCaseId.getText().toString());
        editor.putString(KEY_Password,medtLogInPass.getText().toString());
        editor.putString(KEY_UserName,resultString.toString());
        editor.commit();
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context,"","登入中‧‧‧");
            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("account",mCaseId.getText().toString());
                request.addProperty("password",medtLogInPass.getText().toString());

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);

                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);

                resultString =soapSerializationEnvelope.getResponse();

                ans=resultString.toString();

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
            if(!ans.equals("0"))
            {
                alertDialog= new AlertDialog.Builder(context).setMessage(resultString+"，歡迎光臨!")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();

                SharedPreferences();

                Intent it=new Intent();
                it.putExtra("uid",mCaseId.getText().toString());
                it.setClass(LogIn.this,MainActivity.class);
                startActivity(it);//跳到登入頁面
                finish();
            }
            else
            {
                medtLogInPass.setText("");
                mCaseId.setText("");
                Toast.makeText(context,"請重新輸入帳號密碼!",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }

    private View.OnClickListener imgbtnEyeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOpenEye) {
                mimgbtnEye.setSelected(true);
                isOpenEye = true;
                mimgbtnEye.setImageResource(R.drawable.eyes_open);
                //密碼可見
                medtLogInPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mimgbtnEye.setSelected(false);
                isOpenEye = false;
                mimgbtnEye.setImageResource(R.drawable.eyes_off);
                //密碼不可見
                medtLogInPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(alertDialog!=null)
            alertDialog.dismiss();
        super.onDestroy();
    }
}