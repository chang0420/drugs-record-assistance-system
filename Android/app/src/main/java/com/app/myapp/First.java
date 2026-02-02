package com.app.myapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class First extends AppCompatActivity {
    private Button mbtnSignUp;
    private Button mbtnLogIn;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_Account="Account";
    private static final String KEY_Password="Password";
    private static final String KEY_UserName="UserName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //判斷有無登入
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String Account=sharedPreferences.getString(KEY_Account,null);
        String UserName=sharedPreferences.getString(KEY_UserName,null);
        if(Account!=null)
        {
            Intent intent=new Intent(First.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        Button mbtnSignUP=findViewById(R.id.btnSignUp);
        mbtnSignUP.setOnClickListener(btnSignUpOnClick);
        Button mbtnLogIn=findViewById(R.id.btnLogIn);
        mbtnLogIn.setOnClickListener(btnLogInOnClick);

    }
    private View.OnClickListener btnSignUpOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it=new Intent();
            it.setClass(First.this,SignUp.class);
            startActivity(it);
        }
    };
    private View.OnClickListener btnLogInOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it=new Intent();
            it.setClass(First.this,LogIn.class);
            startActivity(it);
        }
    };
}