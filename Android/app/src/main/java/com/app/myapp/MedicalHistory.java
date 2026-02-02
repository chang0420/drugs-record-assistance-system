package com.app.myapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MedicalHistory extends AppCompatActivity {
    private ActionBar mActionBar;
    private Toolbar toolbar;
    private Button mbtnNext3;
    private Button mbtnSkip;
    private Dialog mDlgSkip;
    CheckBox mchkbxNull,mchkbxBrain, mchkbxCancer, mchkbxlung, mchkbxHeart, mchkbxkidney, mchkbxHighBlood,
            mchkbxBig, mchkbxLiver, mchkbxMental, mchkbxImmunity, mchkbxSugar, mchkbxEpilepsy, mchkbxOther;
    TextView medtOther;
    String Other, History="";
    boolean ans=true;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        mbtnNext3=findViewById(R.id.btnNext3);
        mbtnNext3.setOnClickListener(btnNext3OnClick);
        mbtnSkip.setOnClickListener(btnSkipOnClick);

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

        medtOther=findViewById(R.id.edtOther);
        Other=medtOther.getText().toString();
        context=this;

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

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
                Intent it1 = getIntent();//取得傳遞過來的資料
                uid = it1.getStringExtra("uid");

                Intent it=new Intent();

                it.setClass(MedicalHistory.this,MedicalHistory2.class);
                it.putExtra("U_History",History);
                it.putExtra("uid",uid);

                startActivity(it);
            }
        }
    };

    private View.OnClickListener btnSkipOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDlgSkip=new Dialog(MedicalHistory.this);//建立對話合
            mDlgSkip.setCancelable(false);//無法利用銀幕下方回上一頁按鈕
            mDlgSkip.setContentView(R.layout.pass_dlg);
            Button mbtnOK=mDlgSkip.findViewById(R.id.btnOK);
            mbtnOK.setOnClickListener(btnOKOnClick);//
            mDlgSkip.show();

        }
    };

    String uid, U_History;
    private View.OnClickListener btnOKOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {//?
            mDlgSkip.cancel();
            Intent it=new Intent();
            uid=it.getStringExtra("uid");
            it.putExtra("uid",uid);
            it.setClass(MedicalHistory.this,LogIn.class);
            startActivity(it);

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