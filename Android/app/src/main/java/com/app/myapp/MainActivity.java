package com.app.myapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    TabLayout tabLayout;
    TabItem tabHome,tabMeasurement,tabFavPharmacy,tabLTC;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    AlertDialog dialog;
    private TextView txtUserName;
    int c=0;
    public String uid;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_Account="Account";
    private static final String KEY_Password="Password";
    private static final String KEY_UserName="UserName";
    //permission
    private static  final int STORAGE_REQUEST_CODE =101;
    private String[] storagePermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestStoragePermission();

        NavigationView navigationView=(NavigationView) findViewById(R.id.navigationView);
        View headerView=navigationView.getHeaderView(0);
        txtUserName=(TextView)headerView.findViewById(R.id.txtUserName);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String Account=sharedPreferences.getString(KEY_Account,null);
        String Password=sharedPreferences.getString(KEY_Password,null);
        String UserName=sharedPreferences.getString(KEY_UserName,null);

        //Intent aintent = this.getIntent();
        //取得傳遞過來的資料
        //String name = aintent.getStringExtra("UserName");
        txtUserName.setText(UserName);

        if(Account!=null &&UserName!=null) {

            uid=Account;
            SharedPreferences Preferences = getSharedPreferences("RemindMed", MODE_PRIVATE);
            boolean remMed = Preferences.getBoolean("goRemind", false);
            boolean remEff = Preferences.getBoolean("goEffect", false);
            if (remMed || remEff) {
                //Intent intent = new Intent(MainActivity.this, RemindService.class);
                //intent.setAction("goRemind");

                if(!foregroundServiceRunning())
                {
                    Bundle b = new Bundle();
                    Intent i = this.getIntent();
                    if(i.getAction()=="Reminded")
                    {
                        b.putBoolean("wentRemind", true);
                    }
                    else
                        b.putBoolean("wentRemind", false);
                    b.putBoolean("goRemind", remMed);
                    b.putBoolean("goEffect", remEff);
                    b.putString("uid", Account);
                    Intent serviceIntent=new Intent(MainActivity.this,ServiceReceiver.class);
                    serviceIntent.setAction("goService");
                    serviceIntent.putExtras(b);
                    sendBroadcast(serviceIntent);
                    //startForegroundService(serviceIntent);
                }

//                Toast.makeText(MainActivity.this, "Service跑起!", Toast.LENGTH_LONG).show();
            }



        }


        // 取得DrawerLayout元件，才能呼叫它的closeDrawers()關閉選單
        mDrawerLayout = findViewById(R.id.drawerLayout);

        // 取得NavigationView元件，然後設定Listener處理按下選單項目的操作
        NavigationView navView = findViewById(R.id.navigationView);
        navView.setNavigationItemSelectedListener(navViewOnItemSelected);

//        ActionBar actBar = getSupportActionBar();
//        actBar.setDisplayHomeAsUpEnabled(true);
//        actBar.setHomeButtonEnabled(true);

        // 取得Toolbar元件，把它設為Action Bar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // 建立ActionBarDrawerToggle，傳入Toolbar元件，並且讓它和DrawerLayout元件偕同運作
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //tab
        tabLayout=(TabLayout) findViewById(R.id.tablayout);
        tabHome=(TabItem) findViewById(R.id.Home);
        tabMeasurement=findViewById(R.id.Measurement);
        tabFavPharmacy=(TabItem) findViewById(R.id.FavPharmacy);
        tabLTC=(TabItem) findViewById(R.id.Setting);

        //tabMeasurement.setOnClickListener(tabMeasurementOnclick);
        InnerPagerAdapter pagerAdapter = new InnerPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.house3px);
                        tabLayout.getTabAt(1).setIcon(R.drawable.phy4);
                        tabLayout.getTabAt(2).setIcon(R.drawable.medstore4);
                        tabLayout.getTabAt(3).setIcon(R.drawable.setting4);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.phy3);
                        tabLayout.getTabAt(0).setIcon(R.drawable.house4px);
                        tabLayout.getTabAt(2).setIcon(R.drawable.medstore4);
                        tabLayout.getTabAt(3).setIcon(R.drawable.setting4);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.medstore3);
                        tabLayout.getTabAt(0).setIcon(R.drawable.house4px);
                        tabLayout.getTabAt(1).setIcon(R.drawable.phy4);
                        tabLayout.getTabAt(3).setIcon(R.drawable.setting4);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.setting3);
                        tabLayout.getTabAt(0).setIcon(R.drawable.house4px);
                        tabLayout.getTabAt(1).setIcon(R.drawable.phy4);
                        tabLayout.getTabAt(2).setIcon(R.drawable.medstore4);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean foregroundServiceRunning()
    {
        ActivityManager activityManager= (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service:activityManager.getRunningServices(Integer.MAX_VALUE))
        {
            if(RemindService.class.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.house3px);
        tabLayout.getTabAt(1).setIcon(R.drawable.phy4);
        tabLayout.getTabAt(2).setIcon(R.drawable.medstore4);
        tabLayout.getTabAt(3).setIcon(R.drawable.setting4);
    }

    public class InnerPagerAdapter extends FragmentPagerAdapter{
        public InnerPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position){
            Fragment fragment = null;

            Bundle bundle = new Bundle();
            bundle.putString("uid", uid);

            switch (position){

                case 0:
                    fragment = new Home();
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new PhysiologicalMeasure();
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new FavPharmacy();
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new ltc();
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount(){
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Home";
                case 1:
                    return "生理測量";
                case 2:
                    return "常用藥局";
                case 3:
                    return "細項設定";
                default:
                    return null;
            }
        }

//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 要先把選單的項目傳給 ActionBarDrawerToggle 處理。
        // 如果它回傳 true，表示處理完成，不需要再繼續往下處理。
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //側拉選單
    private NavigationView.OnNavigationItemSelectedListener navViewOnItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent = new Intent();
            switch (menuItem.getItemId()) {
                case R.id.pharmacy_search:
                    intent.setClass(MainActivity.this,SearchPh.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.medcine_search:
                    intent.setClass(MainActivity.this,SeachMed.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.Prescriptions:
                    intent.setClass(MainActivity.this,MainPersonalMedicien.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.MonthlyReport:
                    intent.setClass(MainActivity.this,SideEffectActivity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.AddSideEffect:
                    intent.setClass(MainActivity.this,InsertEffectActivity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.Logout:
                    setDialog();

                    break;
            }
            return false;
        }
    };
    private void setDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("平安~");
        alertDialog.setMessage("確定要登出嗎？");
        alertDialog.setPositiveButton("確定",((dialog, which) -> {}));
        alertDialog.setNeutralButton("取消",((dialog, which) -> {}));
        dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v -> {

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent serviceIntent=new Intent(MainActivity.this,ServiceReceiver.class);
            serviceIntent.setAction("stopService");
            sendBroadcast(serviceIntent);

            Toast.makeText(MainActivity.this,"已登出!",Toast.LENGTH_SHORT).show();

            finish();

            Intent intent = new Intent();
            intent.setClass(MainActivity.this,First.class);
            startActivity(intent);
        }));
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener((v -> {
            setToast("取消");
            dialog.dismiss();
        }));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }
    //不要鳥他他只是打醬油設吐司的
    private void setToast(String input){
        Toast.makeText(getBaseContext(),input,Toast.LENGTH_SHORT).show();
    }

    private void requestStoragePermission()
    {
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResult)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);

        switch (requestCode){

            case STORAGE_REQUEST_CODE:{
                //Check if Storage permission dialog performed or not Allow /Deny
                if(grantResult.length>0){
                    //Check if storage permission granted,contains boolean results either true or false
                    boolean storageAccepted =grantResult[0]== PackageManager.PERMISSION_GRANTED;
                    //Check if storage permission is granted or not
                    if(storageAccepted)
                    {
                        //storage permission granted, we can launch gallery intent
                        //pickImageGallery();
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
    protected void onDestroy() {
        if(dialog!=null)
            dialog.dismiss();
        super.onDestroy();
    }
}
