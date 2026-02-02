package com.app.myapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ltc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ltc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ltc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ltc.
     */
    // TODO: Rename and change types and number of parameters
    public static ltc newInstance(String param1, String param2) {
        ltc fragment = new ltc();
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
    boolean goMed=false,goEff=false;
    SharedPreferences pref;
    String uid;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(view!=null)
        {
            uid = getArguments().getString("uid");

            Switch swEffect = view.findViewById(R.id.switchEff);
            Switch swMed = view.findViewById(R.id.switchMed);
            context= view.getContext();
            read();
            if(goMed)
                swMed.setChecked(true);
            else
                swMed.setChecked(false);
            if (goEff)
                swEffect.setChecked(true);
            else
                swEffect.setChecked(false);

            swMed.setChecked(goMed);
            swEffect.setChecked(goEff);
            swMed.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Intent stopIntent = new Intent(context, RemindService.class);
                context.stopService(stopIntent);
                if (isChecked) {
                    goMed = true;
                    Toast.makeText(context, "用藥提醒已開起! ", Toast.LENGTH_SHORT).show();
                } else {
                    goMed = false;
                    Toast.makeText(context, "用藥提醒已關閉! ", Toast.LENGTH_SHORT).show();
                }
                save();
                Intent intent = new Intent(getActivity(), RemindService.class);
                intent.setAction("goRemind");
                Bundle b = new Bundle();
                b.putBoolean("goRemind", goMed);
                b.putBoolean("goEffect", goEff);
                b.putString("uid",uid);
                intent.putExtras(b);
//                context.sendBroadcast(intent);
                context.startForegroundService(intent);


            });

            swEffect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Intent stopIntent = new Intent(context, RemindService.class);
                context.stopService(stopIntent);
                if (isChecked) {
                    goEff = true;
                    Toast.makeText(context, "副作用提醒已開啟! ", Toast.LENGTH_SHORT).show();
                } else {
                    goEff = false;
                    Toast.makeText(context, "副作用提醒已關閉! ", Toast.LENGTH_SHORT).show();
                }
                save();
                Intent intent = new Intent(getActivity(), RemindService.class);
                intent.setAction("goRemind");
                Bundle b = new Bundle();
                b.putBoolean("goRemind", goMed);
                b.putBoolean("goEffect", goEff);
                b.putString("uid",uid);
                intent.putExtras(b);
//                context.sendBroadcast(intent);
                context.startForegroundService(intent);
            });

        }
        else
        {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.fragment_ltc, container, false);

            uid = getArguments().getString("uid");
            context= view.getContext();

            Switch swEffect = view.findViewById(R.id.switchEff);
            Switch swMed = view.findViewById(R.id.switchMed);

            read();
            if(goMed)
                swMed.setChecked(true);
            else
                swMed.setChecked(false);
            if (goEff)
                swEffect.setChecked(true);
            else
                swEffect.setChecked(false);
            swMed.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Intent stopIntent = new Intent(context, RemindService.class);
                context.stopService(stopIntent);
                if (isChecked) {
                    goMed = true;
                    Toast.makeText(context, "用藥提醒已開起! ", Toast.LENGTH_SHORT).show();
                } else {
                    goMed = false;
                    Toast.makeText(context, "用藥提醒已關閉! ", Toast.LENGTH_SHORT).show();
                }
                save();
                Intent intent = new Intent(getActivity(), RemindService.class);
                intent.setAction("goRemind");
                Bundle b = new Bundle();
                b.putBoolean("goRemind", goMed);
                b.putBoolean("goEffect", goEff);
                b.putString("uid",uid);
                intent.putExtras(b);
//                context.sendBroadcast(intent);
                context.startForegroundService(intent);



            });

            swEffect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Intent stopIntent = new Intent(context, RemindService.class);
                context.stopService(stopIntent);
                if (isChecked) {
                    goEff = true;
                    Toast.makeText(context, "副作用提醒已開啟! ", Toast.LENGTH_SHORT).show();
                } else {
                    goEff = false;
                    Toast.makeText(context, "副作用提醒已關閉! ", Toast.LENGTH_SHORT).show();
                }
                save();
                Intent intent = new Intent(getActivity(), RemindService.class);
                intent.setAction("goRemind");
                Bundle b = new Bundle();
                b.putBoolean("goRemind", goMed);
                b.putBoolean("goEffect", goEff);
                b.putString("uid",uid);
                intent.putExtras(b);
                //context.sendBroadcast(intent);
                context.startForegroundService(intent);
            });

//            swMed.setChecked(true);
//            swEffect.setChecked(true);
//
//            goMed=true;
//            goEff=true;
            save();

            if(!this.isWorked("com.app.myapp.RemindService"))
            {
                Intent intent = new Intent(getActivity(), RemindService.class);
                intent.setAction("goRemind");
                Bundle b = new Bundle();
                b.putBoolean("goRemind", goMed);
                b.putBoolean("goEffect", goEff);
                b.putString("uid",uid);
                b.putBoolean("wentRemind", false);
                intent.putExtras(b);
//                context.sendBroadcast(intent);
                context.startForegroundService(intent);
            }
            else
            {
                Toast.makeText(context, "服務已經啟動了", Toast.LENGTH_SHORT).show();
            }



        }

        Button btnSet= view.findViewById(R.id.btn_MedTime);

        btnSet.setOnClickListener(v -> {
            Intent i=new Intent(context,SettingTime.class);
            i.putExtra("uid",uid);
            startActivity(i);
        });

        Button btn_Person= view.findViewById(R.id.btn_Person);

        btn_Person.setOnClickListener(v -> {
            Intent i=new Intent(context,ChangePersonalInfo.class);
            i.putExtra("uid",uid);
            startActivity(i);
        });

        Button btn_Health= view.findViewById(R.id.btn_Health);

        btn_Health.setOnClickListener(v -> {
            Intent i=new Intent(context,ChangeMedHistory.class);
            i.putExtra("uid",uid);
            startActivity(i);
        });

//

        return view;

    }

    //讀取資料
    public void read(){
        pref = context.getSharedPreferences("RemindMed",MODE_PRIVATE);
        goMed=pref.getBoolean("goRemind",goMed);
        goEff=pref.getBoolean("goEffect",goEff);
    }




    public void save(){


        pref = context.getSharedPreferences("RemindMed",MODE_PRIVATE);
        pref.edit()
                .putBoolean("goRemind",goMed)
                .putBoolean("goEffect",goEff)
                .apply();
    }

    private boolean isWorked(String className)
    {
        ActivityManager myManger=(ActivityManager) context.getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE
        );

        ArrayList<ActivityManager.RunningServiceInfo> runningService=(ArrayList<ActivityManager.RunningServiceInfo>) myManger.getRunningServices(30);

        for (int i=0;i<runningService.size();i++)
        {
            String a=runningService.get(i).service.getClassName();
            if(runningService.get(i).service.getClassName().equals(className))
                return true;
        }
        return  false;
    }



}