package com.app.myapp;

import static com.app.myapp.RemindService.mediaPlayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    Button backButton, nextButton, skipButton;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    Context context;

    ArrayList<String>ImageData = new ArrayList<>();
    ArrayList<Bitmap> bitmapArray = new ArrayList<>();

    //progress dialog
    private ProgressDialog progressDialog;

    //接收資料
    String[] PassData;
    ArrayList<String> Picture;
    ArrayList<String> Title;
    ArrayList<String> Desc;

    String message;
    String uid;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position > 0) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.INVISIBLE);
            }
            if (position == Picture.size()-1){
                nextButton.setText("完成");
            } else {
                nextButton.setText("下一顆藥");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        context = this;
//        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Picture = new ArrayList<>();
        Title = new ArrayList<>();
        Desc = new ArrayList<>();

        Bundle bundle=this.getIntent().getExtras();
        PassData= bundle.getStringArray("remindData");
        postData();

        //        receiver = new AlarmReceiver(this);
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(123);
        mediaPlayer.pause();



        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);

        if (Title.size() == 1)
            nextButton.setText("完成");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) > 0) {
                    slideViewPager.setCurrentItem(getItem(-1), true); //指定跳到某頁，一定得設置在setAdapter後面
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < Title.size() - 1 && getItem(0) >= 0)
                    slideViewPager.setCurrentItem(getItem(1), true);
                else {
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(123);
                    Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                    i.setAction("Reminded");
                    //i.putExtra("goEffect", true);
                    startActivity(i);
                    finish();
                }
            }
        });
        skipButton.setOnClickListener(v -> {
            NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager1.cancel(123);
            Intent i = new Intent(NavigationActivity.this, MainActivity.class);
            i.setAction("Reminded");
            startActivity(i);
            finish();
        });
        slideViewPager =  findViewById(R.id.slideViewPager);
        dotIndicator =  findViewById(R.id.dotIndicator);
    }




    private void postData() {
        for (int i = 0; i <= PassData.length - 1; i = i + 9) {
            Picture.add(PassData[i + 4]);
            Title.add(PassData[i + 5] + "\n" + PassData[i + 6] + "\n" + PassData[i + 7]);
            if (!PassData[i + 2].equals("anyType{}"))
                Desc.add(PassData[i] + PassData[i + 1] + PassData[i + 2] + PassData[i + 3]);
            else
                Desc.add(PassData[i] + PassData[i + 1] + PassData[i + 3]);
        }
        postImage();

    }

    private void postImage() {

        new WebServiceImage().execute("");
    }

    private class WebServiceImage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "資料載入中，請稍後....");
            progressDialog.setCancelable(false);
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {


            try {
                for (int i = 0; i < Picture.size(); i++) {
                    String namespace = "http://tempuri.org/";

                    String url = "http://120.125.78.206/BrainShaking.asmx";

                    String SOAP_ACTION = "http://tempuri.org/imagePost";
                    String methodName = "imagePost";
                    SoapObject soapObject = new SoapObject(namespace, methodName);
                    soapObject.addProperty("path", Picture.get(i));
                    SoapSerializationEnvelope e = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    e.dotNet = true;
                    e.setOutputSoapObject(soapObject);
                    HttpTransportSE tSE = new HttpTransportSE(url);
                    tSE.call(SOAP_ACTION, e);
                    Object result = e.getResponse();
                    ImageData.add(result.toString());


                }

                for(int i=0;i<ImageData.size();i++)
                {
                    byte[] bytes= Base64.decode(ImageData.get(i),Base64.DEFAULT);
                    // Initialize bitmap
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    // set bitmap on imageView

                    bitmapArray.add(bitmap);
                }
            }
            catch(Exception e){
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();


            viewPagerAdapter = new ViewPagerAdapter(context, bitmapArray, Title, Desc);
            slideViewPager.setAdapter(viewPagerAdapter);  //綁定適配器
            setDotIndicator(0);
            slideViewPager.addOnPageChangeListener(viewPagerListener);
            super.onPostExecute(s);
        }
    }

    //下方換頁圓點
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDotIndicator(int position) {
        dots = new TextView[Picture.size()];
        dotIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.lavender, getApplicationContext().getTheme()));
    }
    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }


}