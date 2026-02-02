package com.app.myapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;

import com.yalantis.ucrop.UCrop;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Locale;
import java.util.UUID;

import java.util.Calendar;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

public class InsertPersonalPrescription extends AppCompatActivity {

    String sourceUri, destinationUri;
    Uri uri;
    //TAG
    private static final String TAG = "MAIN_TAG";

    // Uri of image that we will take from Camera/Gallery
    private Uri imageUri = null;
    private Uri imgUri = null;  //刪除

    //to handle the result of camera/gallery permission
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    //arrays if permission required to pick image from Camera ,Gallery
    private String[] cameraPermissions;
    private String[] storagePermissions;

    //progress dialog
    private ProgressDialog progressDialog;

    //TextRecognizerChines
    private TextRecognizer textRecognizerChinese;

    private DatePickerDialog.OnDateSetListener datePicker;


    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
//    ArrayList<Uri> updateImg=new ArrayList<>();

    private static final String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "InsertPersonalMed";
    private static final String URL = "http://120.125.78.206/BrainShaking.asmx";

    //OCR
    String C_BrandName;
    String E_BrandName;
    String GenericName;

    String Ans;
    String message;
    String Add_img;//照片路徑
    Context context;

    int pImage;
    boolean cameraMed = false;
    boolean selectMed = false;
    boolean goInsert = false;

    private ActionBar mActionBar;
    private Toolbar toolbar;
    String Hospital, Department, ReturnAppointmentDate, ThisDate, DeadlineStart_2nd, DeadlineEnd_2nd, DeadlineStart_3nd, DeadlineEnd_3nd, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_personal_prescription);

        context = this;

        Intent intent = this.getIntent();
        //取得傳遞過來的資料

        uid = intent.getStringExtra("uid");
        Hospital = intent.getStringExtra("Hospital");
        Department = intent.getStringExtra("Department");
        ReturnAppointmentDate = intent.getStringExtra("ReturnAppointmentDate");
        ThisDate = intent.getStringExtra("ThisDate");
        DeadlineStart_2nd = intent.getStringExtra("DeadlineStart_2nd");
        DeadlineEnd_2nd = intent.getStringExtra("DeadlineEnd_2nd");
        DeadlineEnd_3nd = intent.getStringExtra("DeadlineEnd_3nd");
        DeadlineStart_3nd = intent.getStringExtra("DeadlineStart_3nd");


        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle =  toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mTitle.setText(Hospital + " " + Department);


        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        setData();

        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);

        progressDialog = new ProgressDialog(this);
    }

    //初始空值
    private void setData() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Title","新增藥物");
        hashMap.put("M_GenericName","");
        hashMap.put("M_C_BrandName","" );
        hashMap.put("M_E_BrandName","");
        hashMap.put("MedPicture","");
        hashMap.put("Dose"," ");
        hashMap.put("MedPeriod"," ");
        hashMap.put("MedTiming"," ");
        hashMap.put("MedFrequency"," ");
        hashMap.put("MedStart", ThisDate);
        hashMap.put("MedDays", "");
        arrayList.add(hashMap);
        //arrMed.add(hashMap);
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

        class ViewHolder extends RecyclerView.ViewHolder{
            public View inputImageBtn, btnDelete;
            private final TextView ed_M_GenericName;
            private final TextView ed_M_C_BrandName;
            private final TextView ed_M_E_BrandName;
            private final TextView edt_MedStart;
            private final TextView tv_title;
            private final Spinner spn_Timing;
            private final Spinner spn_Period;
            private final Spinner spn_Frequency;
            private final Spinner spn_Dose;
            private Spinner spn_MedDay;
            private final ShapeableImageView imageIv;
            private ImageView MedStartCalender;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                //init UI Views
                inputImageBtn = itemView.findViewById(R.id.inputImageBtn);

                imageIv = itemView.findViewById(R.id.imageIv);
//                imageIv.setX(30);
//                imageIv.setY(30);
                //init arrays of permission required for camera, gallery
                cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //init TextRecognizer
                textRecognizerChinese = TextRecognition.getClient(new ChineseTextRecognizerOptions.Builder().build());

                ed_M_C_BrandName = itemView.findViewById(R.id.recognizedCBEt);
                ed_M_E_BrandName = itemView.findViewById(R.id.recognizedEBEt);
                ed_M_GenericName = itemView.findViewById(R.id.recognizedGenericEt);
                spn_Period = itemView.findViewById(R.id.spnPeriod);
                spn_Timing = itemView.findViewById(R.id.spnTiming);
                spn_MedDay = itemView.findViewById(R.id.spnMedDay);
                spn_Frequency = itemView.findViewById(R.id.spnFrequency);
                tv_title = itemView.findViewById(R.id.tv_title);
                btnDelete = itemView.findViewById(R.id.delete);
                spn_Dose = itemView.findViewById(R.id.spnDose);
                edt_MedStart = itemView.findViewById(R.id.editMedStart);
                MedStartCalender=itemView.findViewById(R.id.ivCalender);


            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_insert_med,parent,false);  //利用LayoutInflater方法載入介面
            return new ViewHolder(view);
        }


        @Override //onBindViewHolder方法是用來管控內部元件的操作的
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.tv_title.setText(arrayList.get(position).get("Title"));


            if (arrayList.get(position).get("MedPicture") != "") {

                // decode base64 string
                byte[] bytes = Base64.decode(arrayList.get(position).get("MedPicture"), Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // set bitmap on imageView
                holder.imageIv.setImageBitmap(bitmap);

//                Glide.with(InsertPersonalPrescription.this)
//                        .load(updateImg.get(position))
//                        .centerCrop()
//                        .override(300, 300)
//                        .error(R.drawable.drugs)
//                        .into(holder.imageIv);

            }


            holder.ed_M_C_BrandName.setText(arrayList.get(position).get("M_C_BrandName")); //設定Id欄位使用holder.tvId去取得他的內容
            holder.ed_M_E_BrandName.setText(arrayList.get(position).get("M_E_BrandName"));
            holder.ed_M_GenericName.setText(arrayList.get(position).get("M_GenericName"));
            holder.edt_MedStart.setText(arrayList.get(position).get("MedStart"));

            //spinner設定 medDay
            String[] medDay = getResources().getStringArray(R.array.spnMedDay_list);
            ArrayAdapter adapterMedDay = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, medDay);
            holder.spn_MedDay.setAdapter(adapterMedDay);
            holder.spn_MedDay.setSelection(adapterMedDay.getPosition(arrayList.get(position).get("MedDays")));

            //spinner設定 dose
            String[] dose = getResources().getStringArray(R.array.spnDose_list);
            ArrayAdapter adapterDose = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, dose);
            holder.spn_Dose.setAdapter(adapterDose);
            holder.spn_Dose.setSelection(adapterDose.getPosition(arrayList.get(position).get("Dose")));

            //spinner設定 period
            String[] period = getResources().getStringArray(R.array.spnPeriod_list);
            ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, period);
            holder.spn_Period.setAdapter(adapter);
            holder.spn_Period.setSelection(adapter.getPosition(arrayList.get(position).get("MedPeriod")));

            //Timing
            String[] timing = getResources().getStringArray(R.array.spnTiming_list);
            ArrayAdapter adapter2 = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, timing);
            holder.spn_Timing.setAdapter(adapter2);
            holder.spn_Timing.setSelection(adapter2.getPosition(arrayList.get(position).get("MedTiming")));

            //frequency
            String[] frequency = getResources().getStringArray(R.array.spnFrequency_list);
            ArrayAdapter adapter3 = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, frequency);
            holder.spn_Frequency.setAdapter(adapter3);
            holder.spn_Frequency.setSelection(adapter3.getPosition(arrayList.get(position).get("MedFrequency")));


            holder.btnDelete.setOnClickListener(v -> {

                if(arrayList.size()==1 && holder.getAdapterPosition()==0)
                {
                    Toast.makeText(InsertPersonalPrescription.this, "請確實填寫用藥資訊!", Toast.LENGTH_SHORT).show();
                    return;
                }

                arrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();
                Toast.makeText(InsertPersonalPrescription.this, "已刪除!", Toast.LENGTH_SHORT).show();
            });

            Calendar calendar = Calendar.getInstance();
            datePicker = (view, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                holder.edt_MedStart.setText(sdf.format(calendar.getTime()));

            };

            holder.MedStartCalender.setOnClickListener(v -> {
                DatePickerDialog dialog = new DatePickerDialog(InsertPersonalPrescription.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            });

            findViewById(R.id.add).setOnClickListener(view ->
            {

                int pos = arrayList.size() - 1;
                if (arrayList.get(pos).get("MedPicture").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請點擊拍攝藥品外觀照片!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_C_BrandName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入中文藥品名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_E_BrandName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入英文藥品名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_GenericName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入藥學名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("Dose").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入單次劑量!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (arrayList.get(pos).get("MedPeriod").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥時間!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!arrayList.get(pos).get("MedPeriod").equals("睡前") && arrayList.get(pos).get("MedTiming").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥時間!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedFrequency").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥頻率!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedStart").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入開始服用藥物日期!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedDays").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥天份!", Toast.LENGTH_SHORT).show();
                    return;
                }


                setData();
                myListAdapter.notifyItemRemoved(arrayList.size() - 1);
                Toast.makeText(InsertPersonalPrescription.this, "以新增空白藥單在下方，請確實填寫!", Toast.LENGTH_SHORT).show();


            });

            findViewById(R.id.store).setOnClickListener(view ->
            {
                int pos = arrayList.size() - 1;
                if (arrayList.get(pos).get("MedPicture").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請點擊拍攝藥品外觀照片!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_C_BrandName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入中文藥品名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_E_BrandName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入英文藥品名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("M_GenericName").equals("")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入藥學名!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("Dose").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入單次劑量!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (arrayList.get(pos).get("MedPeriod").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥時間!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!arrayList.get(pos).get("MedPeriod").equals("睡前") && arrayList.get(pos).get("MedTiming").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥時間!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedFrequency").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥頻率!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedStart").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入開始服用藥物日期!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (arrayList.get(pos).get("MedDays").equals("請選擇")) {
                    Toast.makeText(InsertPersonalPrescription.this, "請輸入用藥天份!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //SOAP

                goInsert = true;
                postData();


            });


            holder.spn_Dose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pImage = holder.getAdapterPosition();
                    HashMap<String, String> addMed = new HashMap<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    //arrMed.add(addMed);
                    arrayList.set(pImage, addMed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.spn_Timing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pImage = holder.getAdapterPosition();
                    HashMap<String, String> addMed = new HashMap<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    //arrMed.add(addMed);
                    arrayList.set(pImage, addMed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.spn_Period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pImage = holder.getAdapterPosition();
                    HashMap<String, String> addMed = new HashMap<>();
                    ArrayList<HashMap<String, String>> arrMed = new ArrayList<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    if (!holder.spn_Period.getSelectedItem().toString().equals("睡前"))
                        holder.spn_Timing.setEnabled(true);
                    else {
                        holder.spn_Timing.setEnabled(false);
                        holder.spn_Timing.setSelection(0);
                    }
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    //arrMed.add(addMed);
                    arrayList.set(pImage, addMed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.spn_Frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pImage = holder.getAdapterPosition();
                    HashMap<String, String> addMed = new HashMap<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    //arrMed.add(addMed);
                    arrayList.set(pImage, addMed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.spn_MedDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pImage = holder.getAdapterPosition();
                    HashMap<String, String> addMed = new HashMap<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    //arrMed.add(addMed);
                    arrayList.set(pImage, addMed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.edt_MedStart.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    pImage = holder.getAdapterPosition();
                    ArrayList<HashMap<String, String>> arrMed = new ArrayList<>();
                    HashMap<String, String> addMed = new HashMap<>();
                    addMed.put("Title", holder.ed_M_C_BrandName.getText().toString() + "" + holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_C_BrandName", holder.ed_M_C_BrandName.getText().toString());//可放所有基本類別
                    addMed.put("M_E_BrandName", holder.ed_M_E_BrandName.getText().toString());
                    addMed.put("M_GenericName", holder.ed_M_GenericName.getText().toString());
                    addMed.put("MedDays", holder.spn_MedDay.getSelectedItem().toString());  //要再加一欄
                    addMed.put("Dose", holder.spn_Dose.getSelectedItem().toString());//可放所有基本類別
                    addMed.put("MedPeriod", holder.spn_Period.getSelectedItem().toString());
                    addMed.put("MedTiming", holder.spn_Timing.getSelectedItem().toString());
                    addMed.put("MedFrequency", holder.spn_Frequency.getSelectedItem().toString());
                    addMed.put("MedStart", holder.edt_MedStart.getText().toString());
                    addMed.put("MedPicture", arrayList.get(pImage).get("MedPicture"));
                    arrMed.add(addMed);
                    arrayList.set(pImage, addMed);


                }
            });


            //藥品照片
            holder.imageIv.setOnClickListener(v -> {
                pImage = holder.getAdapterPosition();

                PopupMenu popupMenu = new PopupMenu(context, holder.imageIv);

                popupMenu.getMenu().add(Menu.NONE, 1, 1, "相機");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "相簿");

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    int id = menuItem.getItemId();
                    if (id == 1) {
                        Log.d(TAG, "onMenuItemClick: Camera Clicked...");
                        cameraMed = true;
                        if (checkCameraPermission()) {

                            pickImagesCamera();

                        } else {

                            requestCameraPermission();
                        }
                    } else if (id == 2) {
                        cameraMed = true;
                        //Gallery is clicked ,check if storage permission is granted or not
                        Log.d(TAG, "onMenuItemClick: Gallery Clicked");
                        if (checkStoragePermission())
                            //Storage permission granted , ew can launch gallery intent
                            pickImageGallery();
                        else {
                            //storage permission not granted, request the storage permission
                            requestStoragePermission();
                        }

                    }
                    return true;
                });


            });


            //handle click, show input image dialog
            //holder.imageIv.setImageURI();
            holder.inputImageBtn.setOnClickListener(view -> {
                pImage = holder.getAdapterPosition();
                PopupMenu popupMenu = new PopupMenu(context, holder.inputImageBtn);

                popupMenu.getMenu().add(Menu.NONE, 1, 1, "相機");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "相簿");

                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    int id = menuItem.getItemId();
                    if (id == 1) {
                        Log.d(TAG, "onMenuItemClick: Camera Clicked...");
                        if (checkCameraPermission()) {

                            pickImagesCamera();

                        } else {

                            requestCameraPermission();
                        }
                    } else if (id == 2) {
                        //Gallery is clicked ,check if storage permission is granted or not
                        Log.d(TAG, "onMenuItemClick: Gallery Clicked");
                        if (checkStoragePermission())
                            //Storage permission granted , ew can launch gallery intent
                            pickImageGallery();
                        else {
                            //storage permission not granted, request the storage permission
                            requestStoragePermission();
                        }

                    }
                    return true;
                });

            });
        }


        @Override
        public int getItemCount() {

            return arrayList.size();
        } //getItemCount方法是決定顯示數量
    }


    private void addImage(int position)  //新增藥品外觀照片
    {
        ArrayList<HashMap<String, String>> image = new ArrayList<>();
        HashMap<String, String> renew = new HashMap<>();
        renew.put("Title", "新增藥物");
        renew.put("M_GenericName", arrayList.get(position).get("M_GenericName"));
        renew.put("M_C_BrandName", arrayList.get(position).get("M_C_BrandName"));
        renew.put("M_E_BrandName", arrayList.get(position).get("M_E_BrandName"));
        renew.put("MedPicture", BaseImage(imageUri));
        renew.put("Dose", " ");
        renew.put("MedPeriod", arrayList.get(position).get("MedPeriod"));
        renew.put("MedTiming", arrayList.get(position).get("MedTiming"));
        renew.put("MedFrequency", arrayList.get(position).get("MedFrequency"));
        if (renew.put("MedStart", arrayList.get(position).get("MedStart")) != ThisDate)
            renew.put("MedStart", arrayList.get(position).get("MedStart"));
        else
            renew.put("MedStart", ThisDate);
        renew.put("MedDays", arrayList.get(position).get("MedDays"));
        image.add(renew);

//        arrayList.remove(position);
//        arrayList.add(image.get(0));
        arrayList.set(position, image.get(0));

//        if(updateImg.size()<=position)
//            updateImg.add(imageUri);
//        else
//            updateImg.set(position,imageUri);


        myListAdapter.notifyDataSetChanged();

    }

    private void updateText(int position)
    {

        ArrayList<HashMap<String, String>> image = new ArrayList<>();
        HashMap<String,String> renew = new HashMap<>();
        renew.clear();
        renew.put("Title","新增藥物");
        renew.put("M_GenericName",GenericName);
        renew.put("M_C_BrandName", C_BrandName);
        renew.put("M_E_BrandName",E_BrandName);
        renew.put("MedPicture",arrayList.get(position).get("MedPicture"));
        renew.put("Dose",arrayList.get(position).get("Dose"));
        renew.put("MedPeriod",arrayList.get(position).get("MedPeriod"));
        renew.put("MedTiming",arrayList.get(position).get("MedTiming"));
        renew.put("MedFrequency",arrayList.get(position).get("MedFrequency"));
        renew.put("MedStart", ThisDate);
        renew.put("MedDays", arrayList.get(position).get("MedDays"));
        image.add(renew);

        arrayList.set(position, image.get(0));
        myListAdapter.notifyDataSetChanged();
    }


    //ocr
    private void recognizeTextFromImage() {

        Log.d(TAG,"recognizeTextFromImage: ");

        progressDialog.setMessage("辨識中...");
        progressDialog.show();

        try{
            //Prepare InputImage from image uri
            InputImage inputImage=InputImage.fromFilePath(this,imageUri);

            //image prepared, we are about to start text recognition process, change progress message
            progressDialog.setMessage("辨識中 ...");

            //start text recognition process from image
            Task<Text> textTaskResult = textRecognizerChinese.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {

                            //process completed, dismiss dialog
                            progressDialog.dismiss();

                            //get the recognized text
                            String recognizedText =text.getText();
                            //String C_BRandName=String.join("商品名:", recognizedText.split("\n"));


                            C_BrandName = recognizedText;
                            E_BrandName = recognizedText;
                            GenericName = recognizedText;

                            selectMed = true;
                            postData();

                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.delete(imgUri, null, null);


                            Log.d(TAG, "onSuccess: recognizedText: " + C_BrandName);
                            Log.d(TAG, "onSuccess: recognizedText: " + recognizedText);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.d(TAG, "onFailure: ",e);
                            Toast.makeText(InsertPersonalPrescription.this, "Failed recognizing text due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch(Exception e){
            //Exception occurred while preparing InputImage, dismiss dialog, show reason in Toast
            progressDialog.dismiss();
            Log.e(TAG, "recognizeTextFromImage: ", e);
            Toast.makeText(this, "Failed preparing image due to", Toast.LENGTH_SHORT).show();
        }


    }



    private void pickImageGallery()
    {
        Log.d(TAG, "pickImageGallery: ");
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");


        galleryActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //here we will receive the image,if picked
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //image picked
                        Intent data = result.getData();
                        imageUri = data.getData();


                        Log.d(TAG, "onActivityResult: imageUri" + imageUri);
                        if (imageUri == null) {
                            //check if picked or not ,picked image yet,can't recognize text
                            Toast.makeText(InsertPersonalPrescription.this, "Pick image first...", Toast.LENGTH_SHORT).show();
                        } else if (!cameraMed) {
                            //imageUri is not null , which means we picked image,we can recognize text
                            Cropper();
                            //recognizeTextFromImage();
                        } else {
                            cameraMed = false;
                            Add_img = imageUri.toString();
                            addImage(pImage);
                        }
                        //set to imageview
                        //imageIv.setImageURI(imageUri);
                    } else {
                        Log.d(TAG, "onActivityResult: cancelled");
                        //cancelled
                        Toast.makeText(InsertPersonalPrescription.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    private  void  pickImagesCamera()
    {
        Log.d(TAG, "pickImagesCamera: ");
        ContentValues values= new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Sample Description");

        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        imgUri=imageUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        cameraActivityResultLauncher.launch(intent);



    }

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() ==Activity .RESULT_OK)
                    {
                        Log.d(TAG, "onActivityResult: imageUri"+imageUri);
                        if(imageUri == null)
                        {
                            //check if picked or not ,picked image yet,can't recognize text
                            Toast.makeText(InsertPersonalPrescription.this, "Pick image first...", Toast.LENGTH_SHORT).show();
                        }
                        else if(!cameraMed)
                        {
                            Cropper();
                        }
                        else
                        {
                            cameraMed = false;
                            Add_img = imageUri.toString();
                            addImage(pImage);
                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.delete(imgUri, null, null);

                        }

                    }
                    else
                    {
                        Log.d(TAG, "onActivityResult: cancelled");
                        Toast.makeText(InsertPersonalPrescription.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private String BaseImage(Uri imageUri) {

        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

//            //set rotate
//            int w = bitmap.getWidth();
//            int h = bitmap.getHeight();
//
//            // Setting post rotate to 90
//            Matrix mtx = new Matrix();
//            mtx.postRotate(90);
//            // Rotating Bitmap
//            Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);

            // initialize byte stream
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // compress Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            // Initialize byte array
            byte[] bytes = stream.toByteArray();
            // get base64 encoded string
            String data = Base64.encodeToString(bytes, Base64.DEFAULT);
            //ImageStore.add(data);

            return data;

            //Log.d("imageData",sImage);
            //postData();


        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private void Cropper() {

        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            sourceUri=imageUri.toString();
            uri= Uri.parse(sourceUri);
        }
        destinationUri =new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.Options options=new UCrop.Options();

        UCrop.of(uri,Uri.fromFile(new File(getCacheDir(), destinationUri)))
                .withOptions(options)
                .withAspectRatio(16,9)
                .withMaxResultSize(2000,2000)
                .start(InsertPersonalPrescription.this);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);

            imageUri=resultUri;

            recognizeTextFromImage();

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }



    private boolean checkStoragePermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result;
    }
    private boolean checkCameraPermission()
    {
        boolean cameraResult =ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult =ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return cameraResult && storageResult;
    }

    private void requestStoragePermission()
    {
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE);
    }
    private void requestCameraPermission()
    {

        //request Camera permission (for camera intent)
        ActivityCompat.requestPermissions(this,cameraPermissions, CAMERA_REQUEST_CODE);
    }

    //handle permission results

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int [] grantResult)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResult.length>0){

                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResult[1] == PackageManager.PERMISSION_GRANTED;

                    if(grantResult.length>0)
                    {
                        pickImagesCamera();
                    }
                    else
                    {
                        //one or both are denied can't camera intent
                        Toast.makeText(this, "Camera & Storage permission are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Nether allowed not rather cancelled
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                //Check if Storage permission dialog performed or not Allow /Deny
                if(grantResult.length>0){
                    //Check if storage permission granted,contains boolean results either true or false
                    boolean storageAccepted =grantResult[0]==PackageManager.PERMISSION_GRANTED;
                    //Check if storage permission is granted or not
                    if(storageAccepted)
                    {
                        //storage permission granted, we can launch gallery intent
                        pickImageGallery();

                    }
                    else
                    {
                        //storage permission denied , can't launch gallery intent
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //新增藥單
    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "資料上傳中，請稍後....");
            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {
                //ArrayList<String> Image = new ArrayList<>();
                if (selectMed) {
                    String METHOD_Med = "SelectMed";
                    String SOAP_MED = "http://tempuri.org/SelectMed";
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_Med);
                    request.addProperty("M_E_BrandName", E_BrandName);
                    request.addProperty("M_C_BrandName", C_BrandName);
                    request.addProperty("M_GenericName", GenericName);


                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapSerializationEnvelope.dotNet = true;
                    soapSerializationEnvelope.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_MED, soapSerializationEnvelope);


                    SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn;

                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);


                    C_BrandName = obj2.getProperty(0).toString();
                    E_BrandName = obj2.getProperty(1).toString();
                    GenericName = obj2.getProperty(2).toString();


                }


                //儲存藥單
                if (goInsert) {
                    for (int i = 0; i < arrayList.size(); i++) {

                        Thread.sleep(1000);
                        HashMap<String, String> hashMap = new HashMap<>();
                        String namespace = "http://tempuri.org/";

                        String url = "http://120.125.78.206/BrainShaking.asmx";

                        String SOAP_ACTION = "http://tempuri.org/imageUpload";
                        String methodName = "imageUpload";
                        SoapObject soapObject = new SoapObject(namespace, methodName);
                        soapObject.addProperty("fileName", uid);
                        soapObject.addProperty("image", arrayList.get(i).get("MedPicture"));
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(soapObject);
                        HttpTransportSE transportSE = new HttpTransportSE(url);

                        transportSE.call(SOAP_ACTION, envelope);
                        Object result = envelope.getResponse();
                        // Image.add(result.toString());
                        Log.i("connectWebService", result.toString());


                        hashMap.put("M_GenericName", arrayList.get(i).get("M_GenericName"));
                        hashMap.put("M_C_BrandName", arrayList.get(i).get("M_C_BrandName"));
                        hashMap.put("M_E_BrandName", arrayList.get(i).get("M_E_BrandName"));

                        hashMap.put("Dose", arrayList.get(i).get("Dose"));
                        hashMap.put("MedPeriod", arrayList.get(i).get("MedPeriod"));
                        hashMap.put("MedTiming", arrayList.get(i).get("MedTiming"));
                        hashMap.put("MedFrequency", arrayList.get(i).get("MedFrequency"));
                        hashMap.put("MedStart", arrayList.get(i).get("MedStart"));
                        hashMap.put("MedDays", arrayList.get(i).get("MedDays"));
                        hashMap.put("MedPicture", result.toString());

                        arrayList.set(i, hashMap);
                    }


                    for (int i = 0; i < arrayList.size(); i++) {
                        Thread.sleep(1000);
                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                        request.addProperty("uid", uid);
                        request.addProperty("M_E_BrandName", arrayList.get(i).get("M_E_BrandName"));
                        request.addProperty("M_C_BrandName", arrayList.get(i).get("M_C_BrandName"));
                        request.addProperty("M_GenericName", arrayList.get(i).get("M_GenericName"));
                        request.addProperty("ThisDate", ThisDate);
                        request.addProperty("Hospital", Hospital);
                        request.addProperty("Department", Department);
                        request.addProperty("returnAppointmentDate", ReturnAppointmentDate);
                        request.addProperty("DeadlineStart_2nd", DeadlineStart_2nd); //要改還沒有傳值
                        request.addProperty("DeadlineEnd_2nd", DeadlineEnd_2nd);
                        request.addProperty("DeadlineStart_3nd", DeadlineStart_3nd);
                        request.addProperty("DeadlineEnd_3nd", DeadlineEnd_3nd);
                        request.addProperty("MedPicture", arrayList.get(i).get("MedPicture"));
                        request.addProperty("Doses", arrayList.get(i).get("Dose"));
                        if(!arrayList.get(i).get("MedPeriod").equals("請選擇"))
                            request.addProperty("MedPeriod", arrayList.get(i).get("MedPeriod"));
                        else
                            request.addProperty("MedPeriod", "");
                        request.addProperty("MedTiming", arrayList.get(i).get("MedTiming"));
                        request.addProperty("MedFrequency", arrayList.get(i).get("MedFrequency"));
                        request.addProperty("MedStart", arrayList.get(i).get("MedStart"));
                        request.addProperty("MedDays", arrayList.get(i).get("MedDays"));


                        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        soapSerializationEnvelope.dotNet = true;
                        soapSerializationEnvelope.setOutputSoapObject(request);

                        HttpTransportSE transportSE = new HttpTransportSE(URL);
                        //SOAP
                        String SOAP_ACTION = "http://tempuri.org/InsertPersonalMed";
                        transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                        SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn;
                        Ans = bodyIn.toString();
                        //PersonalMed = soapSerializationEnvelope.getResponse();
                        //SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);


                    }
                }


                message = "OK";


            } catch (Exception e) {
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (selectMed) {
                selectMed = false;
                updateText(pImage);

            }

            if (goInsert) {
                goInsert = false;
                if (!Ans.equals("false")) {

                    Toast.makeText(context, "藥單儲存成功!", Toast.LENGTH_SHORT).show();
                    //跳頁到用要設定
                    Intent it = new Intent();
                    it.putExtra("uid", uid);
                    it.setClass(InsertPersonalPrescription.this, SettingTime.class);
                    startActivity(it);//跳到登入頁面
                } else {
                    Toast.makeText(context, "藥單儲存失敗!", Toast.LENGTH_SHORT).show();

                }
            }


            super.onPostExecute(s);
        }


    }


    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

}