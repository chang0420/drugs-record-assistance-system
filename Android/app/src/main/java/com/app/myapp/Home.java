package com.app.myapp;


import static androidx.core.app.NotificationCompat.getColor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    View view;
    Context context;
    ImageButton btnMorning,btnLunch,btnNight,btnSleep;
    ProgressDialog progressDialog;
    String message,setPeriod=null;


    Object PersonalMed;
    //SOAP
    private static String URL = "http://120.125.78.206/BrainShaking.asmx";
    private static String NAMESPACE = "http://tempuri.org/";
    String TAG1 = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    NoneListAdapter noneListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    String uid;

    boolean goSelect=false;
    boolean goImage=false;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view ==null)
        {

            uid = getArguments().getString("uid");


            view=inflater.inflate(R.layout.fragment_home, container, false);
            context=getContext();
            btnMorning=view.findViewById(R.id.btnMorning);
            btnLunch=view.findViewById(R.id.btnLunch);
            btnNight=view.findViewById(R.id.btnNight);
            btnSleep=view.findViewById(R.id.btnSleep);


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            Date morning, noon, night,now,sleep;
            try {
                morning = new SimpleDateFormat("HH:mm").parse("12:00");
                noon=new SimpleDateFormat("HH:mm").parse("15:00");
                night=new SimpleDateFormat("HH:mm").parse("20:00");
                sleep=new SimpleDateFormat("HH:mm").parse("03:00");
                now=new SimpleDateFormat("HH:mm").parse(dtf.format(LocalDateTime.now()));

                if(now.after(sleep) && now.before(morning) )
                {
                    setPeriod="早";
                    btnMorning.setImageResource(R.drawable.halfmoon2);
                    btnSleep.setImageResource(R.drawable.bed);
                    btnLunch.setImageResource(R.drawable.sun);
                    btnNight.setImageResource(R.drawable.moon);
                }
                else if(now.after(morning) && now.before(noon)) {
                    setPeriod = "午";
                    btnLunch.setImageResource(R.drawable.sun2);
                    btnSleep.setImageResource(R.drawable.bed);
                    btnMorning.setImageResource(R.drawable.halfmoon);
                    btnNight.setImageResource(R.drawable.moon);
                }
                else if(now.after(noon) && now.before(night))
                {
                    setPeriod="晚";
                    btnNight.setImageResource(R.drawable.moon2);
                    btnSleep.setImageResource(R.drawable.bed);
                    btnLunch.setImageResource(R.drawable.sun);
                    btnMorning.setImageResource(R.drawable.halfmoon);
                }
                else if(now.after(night) || now.before(sleep))
                {
                    setPeriod="睡前";
                    btnSleep.setImageResource(R.drawable.bed2);
                    btnMorning.setImageResource(R.drawable.halfmoon);
                    btnLunch.setImageResource(R.drawable.sun);
                    btnNight.setImageResource(R.drawable.moon);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            postData();

            //設置RecycleView
            mRecyclerView= view.findViewById(R.id.recycleview);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            myListAdapter = new MyListAdapter();
            mRecyclerView.setAdapter(myListAdapter);
            RecyclerView.ItemDecoration decoration=new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.right = 10;
                    outRect.left = 10;
                    outRect.top = 10;
                    outRect.bottom = 10;
                }
            };
            mRecyclerView.addItemDecoration(decoration);

            //下拉刷新
            swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
            swipeRefreshLayout.setOnRefreshListener(()->{
                arrayList.clear();
                postData();
                myListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            });

            btnMorning.setOnClickListener(v -> {
                btnMorning.setImageResource(R.drawable.halfmoon2);
                btnSleep.setImageResource(R.drawable.bed);
                btnLunch.setImageResource(R.drawable.sun);
                btnNight.setImageResource(R.drawable.moon);
                setPeriod="早";
                arrayList.clear();
                postData();
            });
            btnLunch.setOnClickListener(v -> {
                btnLunch.setImageResource(R.drawable.sun2);
                btnSleep.setImageResource(R.drawable.bed);
                btnMorning.setImageResource(R.drawable.halfmoon);
                btnNight.setImageResource(R.drawable.moon);
                setPeriod="午";
                arrayList.clear();
                postData();
                //btnLunch.setBackgroundColor(Color.CYAN);

            });
            btnNight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnNight.setImageResource(R.drawable.moon2);
                    btnSleep.setImageResource(R.drawable.bed);
                    btnLunch.setImageResource(R.drawable.sun);
                    btnMorning.setImageResource(R.drawable.halfmoon);
                    setPeriod="晚";
                    arrayList.clear();
                    postData();

                }
            });

            btnSleep.setOnClickListener(v -> {
                btnSleep.setImageResource(R.drawable.bed2);
                btnMorning.setImageResource(R.drawable.halfmoon);
                btnLunch.setImageResource(R.drawable.sun);
                btnNight.setImageResource(R.drawable.moon);
                setPeriod="睡前";
                arrayList.clear();
                postData();
//                    if(arrayList==null)
//                        Toast.makeText(context,"目前尚未建立睡前的用藥哦!",Toast.LENGTH_SHORT).show();

            });
        }
        return view;
    }

    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context,"","資料載入中....");
            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                String SOAP_ACTION = "http://tempuri.org/SelectHomePageMed";

                String METHOD_NAME = "SelectHomePageMed";


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("uid", uid);
                request.addProperty("period", setPeriod);

                SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet = true;
                soapSerializationEnvelope.setOutputSoapObject(request);

                HttpTransportSE tSE = new HttpTransportSE(URL);
                tSE.call(SOAP_ACTION, soapSerializationEnvelope);


                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼

                PersonalMed = soapSerializationEnvelope.getResponse();

                SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                for (int i = 0; i < obj2.getPropertyCount(); i = i + 9) {
//                        SoapObject obj3 =(SoapObject) obj2.getProperty(i);

                    HashMap<String, String> hashMap = new HashMap<>();
                    if (!obj2.getProperty(i + 5).toString().equals("anyType{}")) {
                        hashMap.put("sortPeriod", obj2.getProperty(i + 5).toString());
                        hashMap.put("period", obj2.getProperty(i + 6).toString() + obj2.getProperty(i + 4).toString() + obj2.getProperty(i + 5).toString() + obj2.getProperty(i + 3).toString());
                        hashMap.put("Med_C", obj2.getProperty(i + 1).toString());
                        hashMap.put("Med_E", obj2.getProperty(i + 2).toString());
                        hashMap.put("MedPicture", obj2.getProperty(i).toString());
                    } else {
                        hashMap.put("sortPeriod", "");
                        hashMap.put("period", obj2.getProperty(i + 6).toString() + obj2.getProperty(i + 4).toString() + obj2.getProperty(i + 3).toString());
                        hashMap.put("Med_C", obj2.getProperty(i + 1).toString());
                        hashMap.put("Med_E", obj2.getProperty(i + 2).toString());
                        hashMap.put("MedPicture", obj2.getProperty(i).toString());
                    }


                    arrayList.add(hashMap);
                }
                //String perMed=PersonalMed[0].toString();
                //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                message = "OK";


                for (int i = 0; i < arrayList.size(); i++) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    String namespace = "http://tempuri.org/";

                    String url = "http://120.125.78.206/BrainShaking.asmx";

                    String SOAP_Image = "http://tempuri.org/imagePost";
                    String methodName = "imagePost";
                    SoapObject soapObject = new SoapObject(namespace, methodName);
                    soapObject.addProperty("path", arrayList.get(i).get("MedPicture"));
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);
                    HttpTransportSE transportSE = new HttpTransportSE(url);
                    try {
                        transportSE.call(SOAP_Image, envelope);
                        Object result = envelope.getResponse();
                        if (!obj2.getProperty(i + 5).toString().equals("anyType{}")) {
                            hashMap.put("sortPeriod", arrayList.get(i).get("sortPeriod"));
                            hashMap.put("period", arrayList.get(i).get("period"));
                            hashMap.put("Med_C", arrayList.get(i).get("Med_C"));
                            hashMap.put("Med_E", arrayList.get(i).get("Med_E"));
                            hashMap.put("MedPicture", result.toString());
                        } else {
                            hashMap.put("sortPeriod", arrayList.get(i).get("sortPeriod"));
                            hashMap.put("period", arrayList.get(i).get("period"));
                            hashMap.put("Med_C", arrayList.get(i).get("Med_C"));
                            hashMap.put("Med_E", arrayList.get(i).get("Med_E"));
                            hashMap.put("MedPicture", result.toString());
                        }
                        Log.i("connectWebService", result.toString());
                        arrayList.set(i,hashMap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }





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
            if( arrayList.size()>0 )
            {
                //製造資料
                myListAdapter = new MyListAdapter();
                mRecyclerView.setAdapter(myListAdapter);
                PersonalMed=null;


            }
            else
            {
                noneListAdapter = new NoneListAdapter();
                mRecyclerView.setAdapter(noneListAdapter);
                if(setPeriod.equals("晚") )
                    Toast.makeText(context,"目前尚未建立晚上的用藥哦!",Toast.LENGTH_SHORT).show();
                else if(setPeriod.equals("睡前"))
                    Toast.makeText(context,"目前尚未建立睡前的用藥哦!",Toast.LENGTH_SHORT).show();
                else if(setPeriod.equals("午"))
                    Toast.makeText(context,"目前尚未建立中午的用藥哦!",Toast.LENGTH_SHORT).show();
                else if(setPeriod.equals("早"))
                    Toast.makeText(context,"目前尚未建立早上的用藥哦!",Toast.LENGTH_SHORT).show();
            }
            goImage=false;
            goSelect=false;

            super.onPostExecute(s);
        }
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView imgMed;
            private TextView txtMed,txtPeriod,txtDesc;
            private View mView;
            private ShapeableImageView imageIv;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txtDesc=itemView.findViewById(R.id.txtDesc);
                txtMed=itemView.findViewById(R.id.txtMed);
                txtPeriod=itemView.findViewById(R.id.txtPeriod);
                imgMed =itemView.findViewById(R.id.imgMed);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_home,parent,false);  //利用LayoutInflater方法載入介面
            return new ViewHolder(view);
        }


        @Override //onBindViewHolder方法是用來管控內部元件的操作的
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.txtMed.setText(arrayList.get(position).get("Med_C")+" "+arrayList.get(position).get("Med_E"));

            holder.txtPeriod.setText(arrayList.get(position).get("period"));


            // decode base64 string
            byte[] bytes = Base64.decode(arrayList.get(position).get("MedPicture"), Base64.DEFAULT);
            // Initialize bitmap

            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            // set bitmap on imageView

            holder.imgMed.setImageBitmap(bitmap);

//                holder.imgMed.setImageURI(Uri.parse(arrayList.get(position).get("MedPicture")));
            holder.txtDesc.setText("請勿配茶、葡萄柚服用");



        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        } //getItemCount方法是決定顯示數量
    }

    private class NoneListAdapter extends RecyclerView.Adapter<NoneListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtNone;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);


                txtNone=itemView.findViewById(R.id.txtNone);
                mView  = itemView;

            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_home_none,parent,false);  //利用LayoutInflater方法載入介面
            return new ViewHolder(view);
        }


        @Override //onBindViewHolder方法是用來管控內部元件的操作的
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(setPeriod.equals("晚") )
                holder.txtNone.setText("目前尚未建立晚上的用藥哦!");
            else if(setPeriod.equals("睡前"))
                holder.txtNone.setText("目前尚未建立睡前的用藥哦!");
            else if(setPeriod.equals("午"))
                holder.txtNone.setText("目前尚未建立中午的用藥哦!");
            else if(setPeriod.equals("早"))
                holder.txtNone.setText("目前尚未建立早上的用藥哦!");

        }

        @Override
        public int getItemCount() {
            return 1;
        } //getItemCount方法是決定顯示數量
    }

//    private void makeData() {
//        ArrayList<HashMap<String, String>> data = new ArrayList<>();
//
//        for(int i=0;i<arrayList.size();i++)  //飯前
//        {
//            HashMap<String,String> hashMap = new HashMap<>();
//            if(Objects.equals(arrayList.get(i).get("sortPeriod"), "飯前"))
//            {
//
//                hashMap.put("period",arrayList.get(i).get("period"));
//                hashMap.put("Med_C",arrayList.get(i).get("Med_C"));
//                hashMap.put("Med_E",arrayList.get(i).get("Med_E"));
//                hashMap.put("MedPicture",arrayList.get(i).get("MedPicture"));
//                data.add(hashMap);
//            }
//
//        }
//
//        for(int i=0;i<arrayList.size();i++)  //飯前
//        {
//            HashMap<String,String> hashMap = new HashMap<>();
//            if(arrayList.get(i).get("sortPeriod").equals("飯後"))
//            {
//
//                hashMap.put("period",arrayList.get(i).get("period"));
//                hashMap.put("Med_C",arrayList.get(i).get("Med_C"));
//                hashMap.put("Med_E",arrayList.get(i).get("Med_E"));
//                hashMap.put("MedPicture",arrayList.get(i).get("MedPicture"));
//                data.add(hashMap);
//            }
//
//        }
//        for(int i=0;i<arrayList.size();i++)  //飯前
//        {
//            HashMap<String,String> hashMap = new HashMap<>();
//            if(Objects.equals(arrayList.get(i).get("sortPeriod"), "隨餐服用"))
//            {
//
//                hashMap.put("period",arrayList.get(i).get("period"));
//                hashMap.put("Med_C",arrayList.get(i).get("Med_C"));
//                hashMap.put("Med_E",arrayList.get(i).get("Med_E"));
//                hashMap.put("MedPicture",arrayList.get(i).get("MedPicture"));
//                data.add(hashMap);
//            }
//
//        }
//        for(int i=0;i<arrayList.size();i++)  //飯前
//        {
//            HashMap<String,String> hashMap = new HashMap<>();
//            if(Objects.equals(arrayList.get(i).get("sortPeriod"), ""))
//            {
//
//                hashMap.put("period",arrayList.get(i).get("period"));
//                hashMap.put("Med_C",arrayList.get(i).get("Med_C"));
//                hashMap.put("Med_E",arrayList.get(i).get("Med_E"));
//                hashMap.put("MedPicture",arrayList.get(i).get("MedPicture"));
//                data.add(hashMap);
//            }
//
//        }
//        arrayList=data;
//        myListAdapter = new MyListAdapter();
//        mRecyclerView.setAdapter(myListAdapter);
//    }
}
