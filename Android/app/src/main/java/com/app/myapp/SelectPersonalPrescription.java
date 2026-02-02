package com.app.myapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SelectPersonalPrescription extends AppCompatActivity {

    private Button btnNext;
    private EditText editHospital, editThisTime, editclinc, editNextTime, editDeadline, editDeadlineCount, editDeadlineThree,
            editDeadlineThreeCount, editReturnAppointmentDate;
    private ImageView imgCalender, imgCalender2, imgReturnCalender,imgCalender4;
    DatePickerDialog.OnDateSetListener datePicker;
    DatePickerDialog.OnDateSetListener datePicker2;
    DatePickerDialog.OnDateSetListener datePicker4;
    DatePickerDialog.OnDateSetListener datePickerReturn;
    Calendar calendar = Calendar.getInstance();
    String Hospital, Department, ReturnAppointmentDate, ThisDate, goPage,uid,Deadline,DeadlineThree;
    boolean success=false;
    boolean frist=false;

    ArrayList<String> dataMed=new ArrayList<>();

    Context context;

    Toolbar toolbar;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personal_prescription);

        context = this;

        editHospital = findViewById(R.id.edit_hospital);
        editThisTime = findViewById(R.id.edit_thisTime);
        editclinc = findViewById(R.id.edit_clinic);
        editNextTime = findViewById(R.id.edit_returnDate);
        editDeadline = findViewById(R.id.edit_deadline);
        imgCalender = findViewById(R.id.imgVCalender);
        imgCalender2 = findViewById(R.id.imgVCalender2);
        imgCalender4=findViewById(R.id.imgVCalender4);
        imgReturnCalender = findViewById(R.id.imgVCalenderReturn);
        editDeadlineCount = findViewById(R.id.edit_deadlineCount);
        editDeadlineThree = findViewById(R.id.edit_deadlineThree);
        editDeadlineThreeCount = findViewById(R.id.edit_deadlineThreeCount);
        editReturnAppointmentDate = findViewById(R.id.edit_returnDate);

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle =  toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        dataMed= intent.getStringArrayListExtra("PersonalMed");

        uid = intent.getStringExtra("uid");
        Hospital = intent.getStringExtra("Hospital");
        Department = intent.getStringExtra("Department");
        ThisDate = intent.getStringExtra("ThisDate");
        goPage = intent.getStringExtra("goPage");




        if (Hospital != null  && ThisDate != null) {
            editHospital.setText(Hospital);
            editThisTime.setText(ThisDate);
            editclinc.setText(Department);
            if(!intent.getStringExtra("ReturnAppointmentDate").equals("無"))
                ReturnAppointmentDate = intent.getStringExtra("ReturnAppointmentDate");
            else
                ReturnAppointmentDate="";
            editNextTime.setText(ReturnAppointmentDate);
            mTitle.setText(Hospital + " " + Department);
            if(!intent.getStringExtra("DeadlineEnd_2nd").equals("無"))
                editDeadline.setText(intent.getStringExtra("DeadlineStart_2nd"));
            if(!intent.getStringExtra("DeadlineStart_2nd").equals("無"))
                Deadline=intent.getStringExtra("DeadlineStart_2nd");
            else
                Deadline="";
            editDeadlineCount.setText(Deadline);

            if(!intent.getStringExtra("DeadlineEnd_3nd").equals("無"))
                editDeadlineThreeCount.setText(intent.getStringExtra("DeadlineEnd_3nd"));
            if(!intent.getStringExtra("DeadlineStart_3nd").equals("無"))
                DeadlineThree=intent.getStringExtra("DeadlineStart_3nd");
            else
                DeadlineThree="";
            editDeadlineThree.setText(DeadlineThree);


        } else {
            mTitle.setText("新增藥單");
            frist=true;
        }
        btnNext = findViewById(R.id.btnNextMedicien);
        btnNext.setOnClickListener(btnNextOnclick);
        imgCalender.setOnClickListener(imgCalenderOnclick);
        imgCalender2.setOnClickListener(imgCalender2Onclick);
        imgCalender4.setOnClickListener(imgCalender4Onclick);
        imgReturnCalender.setOnClickListener(imgReturnCalenderOnclick);

        datePicker = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
            editThisTime.setText(sdf.format(calendar.getTime()));

        };

        datePickerReturn = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
            editReturnAppointmentDate.setText(sdf.format(calendar.getTime()));

        };


        datePicker2 = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
            editDeadline.setText(sdf.format(calendar.getTime()));  //第二次領藥開始

            Calendar cal = Calendar.getInstance();  //第二次領藥結束
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.add(Calendar.DATE, +10);
            editDeadlineCount.setText(sdf.format(cal.getTime()));

            //第三次領藥開始
            calendar.add(Calendar.DATE, +28);
            editDeadlineThree.setText(sdf.format(calendar.getTime()));

            calendar.add(calendar.DATE,+10);
            editDeadlineThreeCount.setText(sdf.format(calendar.getTime()));

        };

        datePicker4 = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
            editDeadlineThree.setText(sdf.format(calendar.getTime()));  //第二次領藥開始

            Calendar cal = Calendar.getInstance();  //第二次領藥結束
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.add(Calendar.DATE, +10);
            editDeadlineThreeCount.setText(sdf.format(cal.getTime()));

        };
    }

    private View.OnClickListener imgReturnCalenderOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog dialog = new DatePickerDialog(SelectPersonalPrescription.this,
                    datePickerReturn,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    };

    private void postData() {
        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }


        protected  String doInBackground(String...strings )
        {
            try{
                //SOAP
                String SOAP_ACTION = "http://tempuri.org/UpdatePersonalPage1";
                String NAMESPACE = "http://tempuri.org/";
                String METHOD_NAME = "UpdatePersonalPage1";
                String URL = "http://120.125.78.200/WebService1.asmx";

                //string uid,string mcode,string ThisDate, string Hospital, string Department, string returnAppointmentDate
                //         , string DeadlineStart_2nd, string DeadlineEnd_2nd, string DeadlineStart_3nd, string DeadlineEnd_3nd
                for(int i=0;i<dataMed.size();i=i+11)
                {
                    SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("uid",uid);
                    request.addProperty("mcode",dataMed.get(i));
                    request.addProperty("ThisDate",editThisTime.getText().toString());
                    request.addProperty("Hospital",editHospital.getText().toString());
                    request.addProperty("Department",editclinc.getText().toString());
                    if(editReturnAppointmentDate.getText().toString()!="" && editReturnAppointmentDate.getText().toString()!="無")
                        request.addProperty("returnAppointmentDate",editReturnAppointmentDate.getText().toString());
                    else
                        request.addProperty("returnAppointmentDate","");
                    if(editDeadline.getText().toString()!="" && !editDeadline.getText().toString().equals("無"))
                    {
                        request.addProperty("DeadlineStart_2nd", editDeadline.getText().toString());
                        request.addProperty("DeadlineEnd_2nd", editDeadlineCount.getText().toString());
                    }
                    else
                    {
                        request.addProperty("DeadlineStart_2nd", "");
                        request.addProperty("DeadlineEnd_2nd", "");
                    }
                    if(editDeadlineThree.getText().toString()!="" && !editDeadlineThree.getText().toString().equals("無"))
                    {
                        request.addProperty("DeadlineStart_3nd", editDeadlineThree.getText().toString());
                        request.addProperty("DeadlineEnd_3nd", editDeadlineThreeCount.getText().toString());
                    }
                    else
                    {
                        request.addProperty("DeadlineStart_3nd", "");
                        request.addProperty("DeadlineEnd_3nd", "");
                    }

                    SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet=true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE= new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION,soapSerializationEnvelope);

                    SoapPrimitive result = (SoapPrimitive) soapSerializationEnvelope.getResponse();

                    success = Boolean.parseBoolean(result.toString());
                }



            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String s)
        {
            if(success)
            {
                Toast.makeText(context,"已修改成功",Toast.LENGTH_SHORT).show();
                success=false;
            }

            super.onPostExecute(s);
        }
    }

    private View.OnClickListener imgCalenderOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog dialog = new DatePickerDialog(SelectPersonalPrescription.this,
                    datePicker,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    };
    private View.OnClickListener imgCalender2Onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog dialog = new DatePickerDialog(SelectPersonalPrescription.this,
                    datePicker2,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    };
    private View.OnClickListener imgCalender4Onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog dialog = new DatePickerDialog(SelectPersonalPrescription.this,
                    datePicker4,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public  void goNextPage()
    {

        Intent intent = new Intent();
        intent.putStringArrayListExtra("PersonalMed",dataMed);
        intent.putExtra("uid", uid);
        if (goPage.equals("true")) {
            intent.setClass(SelectPersonalPrescription.this, InsertPersonalPrescription.class);
        } else
        {

            intent.setClass(SelectPersonalPrescription.this, PersonalMedicien.class);
        }

        if(!editReturnAppointmentDate.getText().toString().equals("無"))  //回診日期
            intent.putExtra("ReturnAppointmentDate", editReturnAppointmentDate.getText().toString());
        else
        {
            //editReturnAppointmentDate.setText("");
            intent.putExtra("ReturnAppointmentDate", "");
        }


        if(!editDeadline.getText().toString().equals("無"))  //第二次領藥
        {
            intent.putExtra("DeadlineStart_2nd", editDeadline.getText().toString());
            intent.putExtra("DeadlineEnd_2nd", editDeadlineCount.getText().toString());
        }
        else
        {
            intent.putExtra("DeadlineStart_2nd", "");
            intent.putExtra("DeadlineEnd_2nd", "");
//                editDeadlineCount.setText("");
//                editDeadline.setText("");
        }

        if(!editDeadlineThree.getText().toString().equals("無"))  //第三次領藥
        {
            intent.putExtra("DeadlineStart_3nd", editDeadlineThree.getText().toString());
            intent.putExtra("DeadlineEnd_3nd", editDeadlineThreeCount.getText().toString());
        }
        else
        {
            intent.putExtra("DeadlineStart_3nd", "");
            intent.putExtra("DeadlineEnd_3nd", "");
//                editDeadlineThree.setText("");
//                editDeadlineThreeCount.setText("");

        }

        intent.putExtra("ThisDate", editThisTime.getText().toString());
        intent.putExtra("Hospital", editHospital.getText().toString());
        intent.putExtra("Department", editclinc.getText().toString());

        if(update)
        {
            update=false;
            postData();

        }


        startActivity(intent);
        finish();



    }


    boolean update=false;

    private View.OnClickListener btnNextOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if (frist) {
                if (editHospital.getText().toString().trim().length()==0 || editThisTime.getText().toString().trim().length()==0 || editclinc.getText().toString().trim().length()==0) {
                    Toast.makeText(context, "請確實輸入欄位!", Toast.LENGTH_SHORT).show();
                    return;
                }
                frist = false;
                goNextPage();
            }
            else {
                if (!ThisDate.equals(editThisTime.getText().toString()) || !Hospital.equals(editHospital.getText().toString())|| !Department.equals(editclinc.getText().toString())
                        || !ReturnAppointmentDate.equals(editReturnAppointmentDate.getText().toString()) || !Deadline.equals(editDeadline.getText().toString()) || !DeadlineThree.equals(editDeadlineThree.getText().toString())) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectPersonalPrescription.this);
                    alertDialog.setTitle("您確定要修改此頁資料嗎");
                    alertDialog.setMessage("請點擊下列選項");
                    alertDialog.setPositiveButton("確定", ((dialog, which) -> {
                    }));
                    alertDialog.setNegativeButton("取消", ((dialog, which) -> {
                    }));
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v -> {

                        update = true;
                        goNextPage();

                        dialog.dismiss();
                    }));

                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener((v -> {

                        dialog.dismiss();
                    }));

                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                } else {
                    update = false;
                    goNextPage();
                }
            }

        }




    };
}