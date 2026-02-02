package com.app.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;


public class ServiceReceiver extends BroadcastReceiver {

    Bundle bundle;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("goService"))
        {

            bundle=new Bundle();
            bundle=intent.getExtras();
            Intent serviceIntent=new Intent(context,RemindService.class);
            Bundle Servicebundle = new Bundle();
            Servicebundle.putBoolean("goRemind",bundle.getBoolean("goRemind"));
            Servicebundle.putBoolean("goEffect",bundle.getBoolean("goEffect"));
            Servicebundle.putString("uid",bundle.getString("uid"));
            Servicebundle.putBoolean("wentRemind", bundle.getBoolean("wentRemind"));
            serviceIntent.putExtras(Servicebundle);
            serviceIntent.setAction("goRemind");
            context.startForegroundService(serviceIntent);
        }
        if(intent.getAction().equals("stopService"))
        {
            Intent i=new Intent(context, RemindService.class);
            i.setAction("stopService");
            bundle=new Bundle();
            bundle.putBoolean("wentRemind",false);
            i.putExtras(bundle);
            context.startForegroundService(i);
            //context.stopService(i);
        }
        if(intent.getAction().equals("updateService"))
        {

            Intent i=new Intent(context,RemindService.class);
            context.stopService(i);
            Bundle Servicebundle = new Bundle();
            bundle=intent.getExtras();
            bundle=new Bundle();
            Servicebundle.putBoolean("goRemind",bundle.getBoolean("goRemind"));
            Servicebundle.putBoolean("goEffect",bundle.getBoolean("goEffect"));
            Servicebundle.putString("uid",bundle.getString("uid"));
            Servicebundle.putBoolean("wentRemind", bundle.getBoolean("wentRemind"));
            i.putExtras(Servicebundle);
            context.startForegroundService(i);
        }


    }



}
