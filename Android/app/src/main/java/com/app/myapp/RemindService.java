package com.app.myapp;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.myapp.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class RemindService extends Service {

    //SOAP
    private static String SOAP_ACTION = "http://tempuri.org/GoRemind";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "GoRemind";
    private static final String URL = "http://120.125.78.200/WebService1.asmx";
    String[] Personal_rem;
    String message;
    SoapObject bodyIn;
    Context context;


    Date date = null; //取得鬧鐘時間
    Calendar calendar;
    String min, hr;

    //alarm
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int count = 1,updateDelete=0;

    //接收資料

    public static MediaPlayer mediaPlayer;
    boolean goMed = false, goEffect = false;
    Bundle bundle;
    String uid;
    boolean wait = false;

    NotificationManager notificationManager;


    @Override
    public void onCreate() {
        createServiceChannel();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"BackgroundService");
        Notification notification=
                builder.setOngoing(true)
                        .setSmallIcon(R.drawable.drugs)
                        .setContentTitle("居家用藥反應紀錄系統")
                        .setContentText("以啟用背景程式").build();
        startForeground(1001, notification);

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread(() -> {

            if (intent.getAction()=="stopService") {

                stopForeground(true);
                stopSelf();

            }
            context = this;
            bundle = intent.getExtras();
            wait = bundle.getBoolean("wentRemind");
            //在這裡執行
            if (!wait) {
                if (intent.getAction() == "goRemind") {

                    goMed = bundle.getBoolean("goRemind");
                    goEffect = bundle.getBoolean("goEffect");
                    uid = bundle.getString("uid");
                    if(updateDelete==0)
                    {
                        updateDelete=1;
                        goDelete();
                    }
                }

//                else if(intent.getAction()=="closeEffect")
//                {
//                    //stopSelf();  // 停止Service
//                }

//                if(goMed)
//                {
//                if (count != 0) {
////                    Calendar cal_obj1 = Calendar.getInstance();
////                    if(remindEffect!=false && calendar.equals(cal_obj1.getTime()))
////                    {
////
////                    createNotificationChannel();
////                    if(goEffect)
////                        getEffect();
//
//
//
//                    count = 0;
////                    }
////                    remindEffect=false;
////                    calendar=null;
//                }
                if(count==1)
                {
                    postData();
                    Log.d("RemindService","Service is going to do postData!");
                }
                else
                {
                    try {
                        sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    count=1;
                }

//                }

            }
            else {
                wait = false;


            }


        }).start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int anHour = 6 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime();
        Intent i = new Intent(this, RemindService.class);
//        Bundle b = new Bundle();
//        b.putString("uid",uid);
//        b.putStringArray("remindData",Personal_rem);
//        i.putExtras(b);
//
//        i.setAction("notification_cancelled");


        pendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime + anHour,
                pendingIntent);

        //Toast.makeText(this, "正在執行Service", Toast.LENGTH_SHORT).show();




        return super.onStartCommand(intent, flags, startId);
    }

    private void goDelete() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long systemTime=System.currentTimeMillis();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        calendar.set(Calendar.HOUR_OF_DAY,21);
//        calendar.set(Calendar.MINUTE,36);
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND,0);


        //calendar.set(Calendar.HOUR, 24);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 00);
        long selectTime=calendar.getTimeInMillis();
        //如果目前時間超過12:00就隔天同一時間提醒
        if(systemTime>selectTime){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }



        Intent intent=new Intent(this,DeleteReceiver.class);
        intent.putExtra("uid",uid);
        intent.setAction("goDelete");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // 取消以前同类型的提醒
        alarmManager.cancel(pendingIntent);


        // 设定每天在指定的时间运行alert
        alarmManager.setRepeating(AlarmManager.RTC,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void getEffect() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(this, AlarmReceiver.class);
        i.setAction("Effect");

        pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);

//        date = new SimpleDateFormat("HH:mm").parse(Personal_rem[8]);
        calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        //calendar.setTime(date);   // assigns calendar to given date
        calendar.set(calendar.MINUTE, Integer.parseInt(min));
        calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(hr));
        calendar.add(calendar.MINUTE, 2);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private void getMed()
    {
        count = 0;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(this, AlarmReceiver.class);

        Bundle bundle=new Bundle();
        bundle.putString("uid",uid);
        bundle.putStringArray("remindData",Personal_rem);
        i.putExtras(bundle);
        i.setAction("Med");
//        i.putExtra("uid", uid);
//        i.putExtra("remindData", Personal_rem);

        pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        calendar = Calendar.getInstance();  //取得現在時間


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }




    private void createServiceChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("BackgroundService", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }


    private void createNotificationChannelEffect() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("effect", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }


    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {
//                if(goUpdate != true)
//                {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("uid", uid);


                SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet = true;
                soapSerializationEnvelope.setOutputSoapObject(request);


                HttpTransportSE transportSE = new HttpTransportSE(URL, 60000);
                transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                if (soapSerializationEnvelope.bodyIn instanceof SoapFault) {
                    final SoapFault sf = (SoapFault) soapSerializationEnvelope.bodyIn;

                } else {
                    bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn;
                    //PersonalMed = soapSerializationEnvelope.getResponse();


                    if(!bodyIn.toString().equals("GoRemindResponse{}"))
                    {
                        SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);
                        Personal_rem = new String[obj2.getPropertyCount()];

                        for (int i = 0; i < obj2.getPropertyCount(); i = i + 9) {
//                    SoapObject obj3 = (SoapObject) obj2.getProperty(i);
                            Personal_rem[i] = obj2.getProperty(i).toString();
                            Personal_rem[i + 1] = obj2.getProperty(1 + i).toString();
                            Personal_rem[i + 2] = obj2.getProperty(2 + i).toString();
                            Personal_rem[i + 3] = obj2.getProperty(3 + i).toString();
                            Personal_rem[i + 4] = obj2.getProperty(4 + i).toString();
                            Personal_rem[i + 5] = obj2.getProperty(5 + i).toString();
                            Personal_rem[i + 6] = obj2.getProperty(6 + i).toString();
                            Personal_rem[i + 7] = obj2.getProperty(7 + i).toString();
                            Personal_rem[i + 8] = obj2.getProperty(8 + i).toString();

                        }
                    }

                    //String perMed=PersonalMed[0].toString();
                    //PersonalMed =bodyIn.getPrimitiveProperty("M_Code","");

                }


                if (Personal_rem != null && count==1) {


                    if (goMed) {
                        count=0;
                        createNotificationChannel();
                        getMed();


                    }


                    //副作用提醒
                    if (goEffect) {
                        date = new SimpleDateFormat("HH:mm").parse(Personal_rem[8]);
                        String time = Personal_rem[8];
                        min = time.substring(3);
                        hr = time.substring(0, 2);

                        createNotificationChannelEffect();
                        getEffect();
                    }




                }


                message = "OK";
//            }
//
            } catch (Exception e) {
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        protected void onPostExecute(String s) {

            if (Personal_rem != null)
                Personal_rem = null;

            //interrupted();

            super.onPostExecute(s);
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        if(pendingIntent!=null)
            alarmManager.cancel(pendingIntent);

        Log.d("Myservice", "onDestory");
    }

}