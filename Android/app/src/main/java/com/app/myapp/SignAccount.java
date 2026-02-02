package com.app.myapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SignAccount extends AppCompatActivity {
    private ActionBar mActionBar;
    private Toolbar toolbar;
    private Button mbtnNext2;
    TextView mtxtPassword, mtxtPasswordCheck, mtxtID;
    EditText medtPassword, medtPasswordCheck, medtID;
    boolean ans=true;
    private ImageButton mimgbtnEye1, mimgbtnEye2;
    private boolean isOpenEye1 = false, isOpenEye2 = false;

    Context context;
    ProgressBar progressDialog;
    String message, password, passwordCheck, ID;

    Object resultString;
    String Ans;
    String URL= "http://120.125.78.200/WebService1.asmx";
    private  static  final  String NAMESPACE="http://tempuri.org/";
    private static final  String SOAP_ACTION="http://tempuri.org/UserInsert";
    private static  final  String METHOD_NAME="UserInsert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_account);

        mbtnNext2=findViewById(R.id.btnNext2);
        mbtnNext2.setOnClickListener(btnNext2Click);

        mtxtID=findViewById(R.id.txtID);
        mtxtPassword=findViewById(R.id.txtPassword2);
        mtxtPasswordCheck=findViewById(R.id.txtPasswordCheck);

        medtPassword=findViewById(R.id.edtPass);
        medtPasswordCheck=findViewById(R.id.edtPassCheck);
        medtID=findViewById(R.id.edtAccount);

        mimgbtnEye1 = findViewById(R.id.imgbtnEye1);
        mimgbtnEye1.setOnClickListener(imgbtnEye1OnClick);
        mimgbtnEye2 = findViewById(R.id.imgbtnEye2);
        mimgbtnEye2.setOnClickListener(imgbtnEye2OnClick);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        context=this;
    }
    private void Check1(){
        ans=true;
        ID=medtID.getText().toString();
        password=medtPassword.getText().toString();
        passwordCheck=medtPasswordCheck.getText().toString();

        mtxtID.setTextColor(this.getResources().getColor(R.color.original));
        mtxtPassword.setTextColor(this.getResources().getColor(R.color.original));
        mtxtPasswordCheck.setTextColor(this.getResources().getColor(R.color.original));

        if(ID.equals("") && password.equals("") && passwordCheck.equals("")){
            mtxtID.setTextColor(this.getResources().getColor(R.color.error_Orange));
            mtxtPassword.setTextColor(this.getResources().getColor(R.color.error_Orange));
            mtxtPasswordCheck.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
            Toast.makeText(context,"請輸入帳號密碼!", Toast.LENGTH_SHORT).show();
        }
        else if(ID.equals("")){
            mtxtID.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
            Toast.makeText(context,"請輸入帳號!", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")||passwordCheck.equals("")){
            mtxtPassword.setTextColor(this.getResources().getColor(R.color.error_Orange));
            mtxtPasswordCheck.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
            ans=false;
            Toast.makeText(context,"請輸入密碼!", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordCheck.equals(password)) {
            mtxtPassword.setTextColor(this.getResources().getColor(R.color.error_Orange));
            mtxtPasswordCheck.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
            ans=false;
            Toast.makeText(context,"確認密碼不相符", Toast.LENGTH_SHORT).show();
        }
    }

    String U_Address, U_Email, U_Phone, U_BD, U_Gender, U_Name, U_Identity;
    private View.OnClickListener btnNext2Click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Intent it=new Intent();
            // it.setClass(SignAccount.this,MedicalHistory.class);
            // startActivity(it);
            Check1();
            if(ans==true){
                Intent it = getIntent();//取得傳遞過來的資料
                U_Name = it.getStringExtra("U_Name");
                U_Gender = it.getStringExtra("U_Gender");
                U_BD = it.getStringExtra("U_BD");
                U_Phone = it.getStringExtra("U_Phone");
                U_Email = it.getStringExtra("U_Email");
                U_Address = it.getStringExtra("U_Address");
                U_Identity=it.getStringExtra("U_Identity");
                postData();
            }
            /*else {
                Toast.makeText(context,"請確認帳號密碼!", Toast.LENGTH_SHORT).show();
            }*/
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
                request.addProperty("Uname",U_Name);
                request.addProperty("Ugender",U_Gender);
                request.addProperty("Ubirth",U_BD);
                request.addProperty("Uphone",U_Phone);
                request.addProperty("Uemail",U_Email);
                request.addProperty("Uaddress",U_Address);
                request.addProperty("uid",ID);
                request.addProperty("Upassword",password);
                request.addProperty("Identity",U_Identity);

                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                Ans=resultString.toString();
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
            if(Ans== "1")
            {
                new AlertDialog.Builder(context).setMessage(U_Name+"註冊成功!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent it=new Intent();
                                it.setClass(SignAccount.this,MedicalHistory.class);
                                it.putExtra("uid",ID);
                                startActivity(it);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
            else if(Ans=="0")
            {
                medtID.setText("");
                medtPassword.setText("");
                medtPasswordCheck.setText("");
                Toast.makeText(context,"請重新輸入帳號密碼!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                medtID.setText("");
                medtPassword.setText("");
                medtPasswordCheck.setText("");
                Toast.makeText(context,"您已建立過帳號!", Toast.LENGTH_LONG).show();

                Intent it = new Intent();
                it.setClass(SignAccount.this, First.class);
                it.putExtra("uid", ID);
                startActivity(it);

            }
            super.onPostExecute(s);
        }
    }

    private View.OnClickListener imgbtnEye1OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOpenEye1) {
                mimgbtnEye1.setSelected(true);
                isOpenEye1 = true;
                mimgbtnEye1.setImageResource(R.drawable.eyes_open);
                //密碼可見
                medtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mimgbtnEye1.setSelected(false);
                isOpenEye1 = false;
                mimgbtnEye1.setImageResource(R.drawable.eyes_off);
                //密碼不可見
                medtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    };

    private View.OnClickListener imgbtnEye2OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOpenEye2) {
                mimgbtnEye2.setSelected(true);
                isOpenEye2 = true;
                mimgbtnEye2.setImageResource(R.drawable.eyes_open);
                //密碼可見
                medtPasswordCheck.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mimgbtnEye2.setSelected(false);
                isOpenEye2 = false;
                mimgbtnEye2.setImageResource(R.drawable.eyes_off);
                //密碼不可見
                medtPasswordCheck.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
}