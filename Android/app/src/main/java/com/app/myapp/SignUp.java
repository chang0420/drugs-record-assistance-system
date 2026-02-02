package com.app.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private ActionBar mActionBar;
    Toolbar toolbar;

    String Gender="", Name, Phone, Email, Address, Birth, Identity;
    ImageButton mbtnDatePick;
    Button mbtnNext;
    EditText medtDateBirth, medtName, medtPhone, medtEmail, medtAddress, medtIdentity;
    TextView mtxtName, mtxtGender, mtxtBirth, mtxtPhone, mtxtEmail, mtxtAddress, mtxtIdentity;
    RadioGroup mrdRadioGroup;
    RadioButton mRdbtnFemale, mRdbtnMale;
    DatePickerDialog.OnDateSetListener datePicker;//日曆監聽
    Calendar calendar = Calendar.getInstance();//日期的格式

    boolean ans=true, phoneOK=true, emailOk=true, nameOK=true, IDOK=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mrdRadioGroup = findViewById(R.id.radioGroup);
        mRdbtnFemale=findViewById(R.id.rdbtnFemale);
        mRdbtnMale=findViewById(R.id.rdbtnMale);
        medtName=findViewById(R.id.edtName);
        medtPhone=findViewById(R.id.edtPhone);
        medtEmail=findViewById(R.id.edtEmail);
        medtAddress=findViewById(R.id.edtAddress);
        medtDateBirth=findViewById(R.id.edtBirth);
        medtIdentity=findViewById(R.id.edtIdentity);

        mtxtName=findViewById(R.id.txtName);
        mtxtGender=findViewById(R.id.txtGender);
        mtxtBirth=findViewById(R.id.txtBirth);
        mtxtPhone=findViewById(R.id.txtPhone);
        mtxtEmail=findViewById(R.id.txtEmail);
        mtxtAddress=findViewById(R.id.txtAddress);
        mtxtIdentity=findViewById(R.id.txtIdentity);

        mbtnNext=findViewById(R.id.btnNext);
        mbtnDatePick=findViewById(R.id.btnDatePick);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(" ");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        datePicker=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                String myBirth="yyyy/MM/dd";
                SimpleDateFormat sdf=new SimpleDateFormat(myBirth, Locale.TAIWAN);
                medtDateBirth.setText(sdf.format(calendar.getTime()));
            }
        };

        mbtnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(SignUp.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        mrdRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radgender = (RadioButton) findViewById(checkedId);
                Gender=radgender.getText().toString();
            }
        });
        mbtnNext.setOnClickListener(btnNextOnClick);
    }
    private void isnull(){

        ans=true;
        Name = medtName.getText().toString();
        Phone = medtPhone.getText().toString();
        Email = medtEmail.getText().toString();
        Address = medtAddress.getText().toString();
        Birth = medtDateBirth.getText().toString();
        Identity = medtIdentity.getText().toString();

        // ColorStateList a=mtxtBirth.getTextColors();

        mtxtName.setTextColor(this.getResources().getColor(R.color.original));
        mtxtGender.setTextColor(this.getResources().getColor(R.color.original));
        mtxtBirth.setTextColor(this.getResources().getColor(R.color.original));
        mtxtPhone.setTextColor(this.getResources().getColor(R.color.original));
        mtxtEmail.setTextColor(this.getResources().getColor(R.color.original));
        mtxtAddress.setTextColor(this.getResources().getColor(R.color.original));
        mtxtIdentity.setTextColor(this.getResources().getColor(R.color.original));

        if(Name.equals("")){
            mtxtName.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
       /* else {
            checkIsChinese();
        }*/
        if(Identity.equals("")){
            mtxtIdentity.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        else {
            checkID();
            if(IDOK == false){
                ans=false;
                mtxtIdentity.setTextColor(this.getResources().getColor(R.color.error_Orange));
            }
        }
        if(Gender==""){
            mtxtGender.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
        if(Birth.equals("")){
            mtxtBirth.setTextColor(this.getResources().getColor(R.color.error_Orange));
            ans=false;
        }
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
    //姓名防呆
   /* private void checkIsChinese() {
        String Name= medtName.getText().toString();
        Matcher m1 = Pattern.compile("^[\\u4E00-\\uFA29]*$").matcher(Name);
        Matcher m2 = Pattern.compile("^[\\uE7C7-\\uE7F3]*$").matcher(Name);
        if(!m1.find() && !m2.find()) {
            nameOK=false;
            mtxtName.setTextColor(this.getResources().getColor(R.color.error_Orange));
        }else{
            nameOK=true;
        }
    }*/
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
    //身分證驗證
    private void checkID(){
        String ID = medtIdentity.getText().toString();
        ID = ID.trim(); //把空白濾掉
        Pattern pattern = Pattern.compile("^[A-Z]{1}[1-2]{1}[0-9]{8}$");
        Matcher matcher = pattern.matcher(ID);
        if(matcher.matches()) {
            IDOK=true;
        }
        else {
            IDOK=false;
        }
    }

    private  View.OnClickListener btnNextOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent it=new Intent();
            //it.setClass(SignUp.this,MedicalHistory2.class);
            //startActivity(it);
            isnull();
            if(ans!=false)
            {
                Intent it=new Intent();
                it.setClass(SignUp.this,SignAccount.class);
                it.putExtra("U_Name",Name);
                it.putExtra("U_Gender",Gender);
                it.putExtra("U_BD",Birth);
                it.putExtra("U_Phone",Phone);
                it.putExtra("U_Email",Email);
                it.putExtra("U_Address",Address);
                it.putExtra("U_Identity",Identity);
                startActivity(it);
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