package com.app.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SeachMed extends AppCompatActivity {
    Toolbar toolbar;
    private ActionBar mActionBar;
    String URL = "http://120.125.78.206/BrainShaking.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";//
    private static final String METHOD_NAME = "Data";
    private static final String SOAP_ACTION = "http://tempuri.org/Data";//
    String message;
    Object resultString;
    TextView child1;
    Context context;
    private EditText edtSearchMed;
    Button btnSearch;
    private boolean isCurrentItems;
    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView expandableListView;
    private String[] groups = {"藥代:", "成分名:", "英文商品名:", "中文商品名:", "用法用量:", "適應症:", "副作用:", "交互做用:", "禁忌:", "醫療須知:"};
    private String[][] childs = {{""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_med);
        expandableListView = findViewById(R.id.expandAbleListView);
        myExpandableListAdapter = new MyExpandableListAdapter();
        expandableListView.setAdapter(myExpandableListAdapter);
        expandableListView.setChildDivider(getResources().getDrawable(R.color.bgWhite));//這是去子層底線用的
        edtSearchMed = findViewById(R.id.edtSearchMed);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnSearchOnClick);//
        child1 = findViewById(R.id.textView_child1);
        context = this;

        toolbar = findViewById(R.id.myToolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("搜尋藥品");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private View.OnClickListener btnSearchOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edtSearchMed.getText().toString().matches("")) {
                Toast.makeText(context, "請輸入藥名", Toast.LENGTH_SHORT).show();
            } else {
                postData();
//                edtSearchMed.setText("");
            }
        }
    };

    private class MyExpandableListAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {//父陣列長度
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childs[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) SeachMed.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_group, null);
            }
            convertView.setTag(R.layout.item_group, groupPosition);
            convertView.setTag(R.layout.item_group, -1);
            TextView textView = convertView.findViewById(R.id.textView_ItemTitle);
            textView.setText(groups[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) SeachMed.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_child, null);
            }
            convertView.setTag(R.layout.item_child, groupPosition);
            convertView.setTag(R.layout.item_child, -1);
            child1 = convertView.findViewById(R.id.textView_child1);
            child1.setText(childs[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    private void postData() {
        new WebServicePostData().execute("");
    }
    private class WebServicePostData extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("中商", edtSearchMed.getText().toString());
                SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet = true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION, soapSerializationEnvelope);
                resultString = soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);//
                SoapObject obj3 = (SoapObject) obj2.getProperty(0);
                childs[0][0] = obj3.getProperty(0).toString();
                childs[1][0] = obj3.getProperty(1).toString();
                childs[2][0] = obj3.getProperty(2).toString();
                childs[3][0] = obj3.getProperty(3).toString();
                childs[4][0] = obj3.getProperty(4).toString();
                childs[5][0] = obj3.getProperty(5).toString();
                childs[6][0] = obj3.getProperty(6).toString();
                childs[7][0] = obj3.getProperty(7).toString();
                childs[8][0] = obj3.getProperty(8).toString();
                childs[9][0] = obj3.getProperty(9).toString();
                message = "OK";
            } catch (Exception e) {
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }
            return null;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}