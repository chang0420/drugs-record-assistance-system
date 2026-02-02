package com.app.myapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.util.Log;
import static android.content.Context.NOTIFICATION_SERVICE;

import static com.app.myapp.RemindService.mediaPlayer;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {

    //接收資料

    Bundle bundle;



//    private final MyBroadcastListener listener;
//
//    public AlarmReceiver(MyBroadcastListener listener){
//        this.listener = listener;
//    }



    @Override
    public void onReceive(Context context, Intent intent) {

//        listener.doSomething(intent.getStringExtra("uid"));
//        listener.doData(intent.getStringArrayExtra("remindData"));
        if (Objects.equals(intent.getAction(), "Med")){

            bundle=new Bundle();

            bundle = intent.getExtras();
            String[] remindData =bundle.getStringArray("remindData");
            String uid=bundle.getString("uid");

//            String[] remindData=intent.getStringArrayExtra("remindData");
////            remindData=intent.getStringArrayExtra("remindData");
//            String uid=intent.getStringExtra("uid");
            //傳送

            Intent i = new Intent(context,NavigationActivity.class);

            bundle.putString("uid",uid);
            bundle.putStringArray("remindData",remindData);
            i.putExtras(bundle);
            i.setAction("goNavigation");

//            i.putExtra("remindData",remindData);
//            i.putExtra("uid",uid);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

            /*建立要嵌入在通知裡的介面*/
            RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.custom_notification);


            /*設置通知內的控件要做的事*/
            /*設置標題*/
            view.setTextViewText(R.id.textView_Title,"您好，服用藥物時間到囉！");


            /*設置圖片*/
            view.setImageViewResource(
                    R.id.imageView_Icon,R.drawable.drugs);

            /*設置"Close"按鈕點擊事件(綁close)*/
            view.setOnClickPendingIntent(R.id.button_Noti_Close,pendingIntent);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "foxandroid")
                    .setSmallIcon(R.drawable.drugs)
//                    .setLargeIcon(BitmapFactory. decodeResource( context.getResources(),
//                            R.drawable.btnclose2)) // 通知清單裡面的圖示（大圖示）
                    .setContent(view)
                    .setOngoing(true)
                    .setColor(Color.rgb(125,209,129))
                    .setColorized(true)
                    .setAutoCancel(true)    // 點擊後讓Notification消失
                    .setDefaults(NotificationCompat.PRIORITY_HIGH)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123,builder.build());

            mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();



        }
        else if(intent.getAction().equals("Effect"))
        {
            NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(123);
            Intent i = new Intent(context,InsertEffectActivity.class);
            i.setAction("RemindEffect");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "effect")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("您好，吃完藥有不舒服的地方嗎?")
                    .setContentText("請點擊此紀錄您或家屬服用藥物後的情況!")
                    .setAutoCancel(true)    // 點擊後讓Notification消失
                    .setDefaults(NotificationCompat.PRIORITY_HIGH)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(456,builder.build());
        }

        if(intent.getAction().equals("notification_cancelled"))
        {
            NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(123);
            mediaPlayer.pause();
            bundle = intent.getExtras();
            String[] remindData =bundle.getStringArray("remindData");
            String uid=bundle.getString("uid");


            Intent i=new Intent().setClass(context,NavigationActivity.class);
            i.setAction("AlarmReceiver");
            i.putExtra("remindData",remindData);
            i.putExtra("uid",uid);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
////                data = i.getIntExtra("count", 0);
//                //remindData = i.getStringArrayExtra("remindData");
            context.startActivity(i);
        }


    }
}
