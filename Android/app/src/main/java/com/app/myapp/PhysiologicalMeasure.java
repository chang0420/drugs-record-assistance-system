package com.app.myapp;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhysiologicalMeasure#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhysiologicalMeasure extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhysiologicalMeasure() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhysiologicalMeasure.
     */
    // TODO: Rename and change types and number of parameters
    public static PhysiologicalMeasure newInstance(String param1, String param2) {
        PhysiologicalMeasure fragment = new PhysiologicalMeasure();
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
    String uid;
    LineChart lineChart;
    TextView lblBp,lblHR,lblItem_BP,lblItem_HR,lbl7day,lbl28day;
    LinearLayout l_HR,l_BP,btn7,btn28;
    Context context;
    String Item; //測量項目

    ArrayList<String> S_BP,D_BP,HR;
    ArrayList<String> firstAns;

    boolean go7Day=false;
    boolean go28Day=false;
    boolean first=false;

    //SOAP
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://120.125.78.206/BrainShaking.asmx";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view!=null) {
            uid = getArguments().getString("uid");
            lblBp=view.findViewById(R.id.lblBP);
            lblHR=view.findViewById(R.id.lblHR);
            btn7=view.findViewById(R.id.mbtn7);
            btn28=view.findViewById(R.id.mbtn28);
            l_BP=view.findViewById(R.id.lineBP);
            l_HR=view.findViewById(R.id.lineHR);
            lblItem_BP=view.findViewById(R.id.lblItemBp);
            lblItem_HR=view.findViewById(R.id.lblItemHr);
            lbl7day=view.findViewById(R.id.lbl7);
            lbl28day=view.findViewById(R.id.lbl28);
            lineChart = view.findViewById(R.id.lineChart);
            context=view.getContext();
//            first=true;
//            go7Day=true;
//            Item="血壓";
//            postData();

        }
        else
        {
            view= inflater.inflate(R.layout.fragment_physiological_measure, container, false);

            uid = getArguments().getString("uid");
            context= view.getContext();
            lblBp=view.findViewById(R.id.lblBP);
            lblHR=view.findViewById(R.id.lblHR);
            lblItem_BP=view.findViewById(R.id.lblItemBp);
            lblItem_HR=view.findViewById(R.id.lblItemHr);
            lbl7day=view.findViewById(R.id.lbl7);
            lbl28day=view.findViewById(R.id.lbl28);
            btn7=view.findViewById(R.id.mbtn7);
            btn28=view.findViewById(R.id.mbtn28);
            l_BP=view.findViewById(R.id.lineBP);
            l_HR=view.findViewById(R.id.lineHR);
            lineChart = view.findViewById(R.id.lineChart);
            context=view.getContext();
            first=true;
            go7Day=true;
            Item="血壓";
            BP_click();
            postData();

        }
        btn28.setOnClickListener(v -> {
            btn28.setBackgroundResource(R.drawable.chart_btn_clicked);
            btn7.setBackgroundResource(R.drawable.chart_btn_bg);
            lbl28day.setTextColor(context.getResources().getColor(R.color.white));
            lbl7day.setTextColor(context.getResources().getColor(R.color.phtYText));

            go28Day=true;
            postData();
        });
        btn7.setOnClickListener(v -> {
            btn7.setBackgroundResource(R.drawable.chart_btn_clicked);
            btn28.setBackgroundResource(R.drawable.chart_btn_bg);
            lbl7day.setTextColor(context.getResources().getColor(R.color.white));
            lbl28day.setTextColor(context.getResources().getColor(R.color.phtYText));
            go7Day=true;
            postData();
        });
        l_HR.setOnClickListener(v -> {

            HR_click();
            go7Day=true;
            Item="心跳";
            postData();
        });
        l_BP.setOnClickListener(v -> {

            BP_click();
            go7Day=true;
            Item="血壓";
            postData();
        });


        return  view;

    }

    private void HR_click()
    {
        l_HR.setBackgroundResource(R.drawable.chart_item_clicked);
        l_BP.setBackgroundResource(R.drawable.chart_item_bg);
        btn7.setBackgroundResource(R.drawable.chart_btn_clicked);
        btn28.setBackgroundResource(R.drawable.chart_btn_bg);
        lblItem_HR.setTextColor(context.getResources().getColor(R.color.phtYText));
        lblItem_BP.setTextColor(context.getResources().getColor(R.color.phtNText));
        lblHR.setTextColor(context.getResources().getColor(R.color.phtYText));
        lblBp.setTextColor(context.getResources().getColor(R.color.phtNText));
        lbl7day.setTextColor(context.getResources().getColor(R.color.white));
        lbl28day.setTextColor(context.getResources().getColor(R.color.phtYText));
    }

    private void BP_click()
    {
        l_BP.setBackgroundResource(R.drawable.chart_item_clicked);
        l_HR.setBackgroundResource(R.drawable.chart_item_bg);
        btn7.setBackgroundResource(R.drawable.chart_btn_clicked);
        btn28.setBackgroundResource(R.drawable.chart_btn_bg);
        lblItem_BP.setTextColor(context.getResources().getColor(R.color.phtYText));
        lblItem_HR.setTextColor(context.getResources().getColor(R.color.phtNText));
        lblHR.setTextColor(context.getResources().getColor(R.color.phtNText));
        lblBp.setTextColor(context.getResources().getColor(R.color.phtYText));
        lbl7day.setTextColor(context.getResources().getColor(R.color.white));
        lbl28day.setTextColor(context.getResources().getColor(R.color.phtYText));
    }


    private ArrayList<Entry> dateValue1() //收縮壓
    {
        ArrayList<Entry> dataValues=new ArrayList<>();

        int j=0;
        for(int i=0;i<S_BP.size();i=i+6)
        {
            dataValues.add(new Entry(j,Integer.parseInt(S_BP.get(i+5))));
            j++;
        }


        return dataValues;

    }

    private ArrayList<Entry> dateValue2() //舒張壓
    {

        ArrayList<Entry> dataValues=new ArrayList<>();

        int j=0;
        for(int i=0;i<D_BP.size();i=i+6)
        {
            dataValues.add(new Entry(j,Integer.parseInt(D_BP.get(i+5))));
            j++;
        }
        return dataValues;

    }

    private ArrayList<String> dateValueDate() {
//ArrayList<String> dataValues = new ArrayList<>();
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        for (int i = 0; i < D_BP.size(); i=i+6) {
            hashSet.add( D_BP.get(i) +" " +D_BP.get(i+1));
        }


        return new ArrayList<>(hashSet);

    }


    private ArrayList<Entry> dateValueHR() {
        ArrayList<Entry> dataValues=new ArrayList<>();

        int j=0;
        for(int i=0;i<HR.size();i=i+6)
        {
            dataValues.add(new Entry(j,Integer.parseInt(HR.get(i+5))));
            j++;
        }
        return dataValues;
    }

    private ArrayList<String> HR_Date() {
//ArrayList<String> dataValues = new ArrayList<>();
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        for (int i = 0; i < HR.size(); i=i+6) {
            hashSet.add( HR.get(i) +" " +HR.get(i+1));
        }


        return new ArrayList<>(hashSet);

    }


    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(context,"","waiting....");
            //progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            try {

                if(first)
                {
                    String METHOD_NAME2 = "NewestPhyData";
                    String SOAP_ACTION2 = "http://tempuri.org/NewestPhyData";
                    SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                    r.addProperty("U_ID", uid);


                    SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    Envelope.dotNet = true;
                    Envelope.setOutputSoapObject(r);

                    HttpTransportSE SE = new HttpTransportSE(URL);
                    SE.call(SOAP_ACTION2, Envelope);


                    SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼


                    SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                    firstAns = new ArrayList<>();

                    if(!obj2.toString().equals("anyType{}"))
                    {
                        firstAns.add(obj2.getProperty(3).toString() + "/" + obj2.getProperty(7).toString() + "\n mmHg");
                        firstAns.add(obj2.getProperty(11)+"bmp");
                    }
                    else
                    {
                        firstAns.add("\n尚無資料");
                        firstAns.add("\n尚無資料");
                    }



                }

                if (go7Day) {

                    if(Item.equals("血壓"))
                    {
                        String METHOD_NAME2 = "PhyData7Days";
                        String SOAP_ACTION2 = "http://tempuri.org/PhyData7Days";
                        SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                        r.addProperty("U_ID", uid);
                        r.addProperty("E_Project", "舒張壓");


                        SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        Envelope.dotNet = true;
                        Envelope.setOutputSoapObject(r);

                        HttpTransportSE SE = new HttpTransportSE(URL);
                        SE.call(SOAP_ACTION2, Envelope);


                        SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼


                        SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                        //D_BP=new String[obj2.getPropertyCount()][6];
                        D_BP = new ArrayList<>();

                        for (int i = 0; i < obj2.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            D_BP.add(obj2.getProperty(i).toString());
                            D_BP.add(obj2.getProperty(i + 1).toString());
                            D_BP.add(obj2.getProperty(i + 2).toString());
                            D_BP.add(obj2.getProperty(i + 3).toString());
                            D_BP.add(obj2.getProperty(i + 4).toString());
                            D_BP.add(obj2.getProperty(i + 5).toString());

                        }


                        SoapObject rSBP = new SoapObject(NAMESPACE, METHOD_NAME2);
                        rSBP.addProperty("U_ID", uid);
                        rSBP.addProperty("E_Project", "收縮壓");


                        SoapSerializationEnvelope EnvelopeSBP = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        EnvelopeSBP.dotNet = true;
                        EnvelopeSBP.setOutputSoapObject(rSBP);

                        HttpTransportSE SE_SBP = new HttpTransportSE(URL);
                        SE_SBP.call(SOAP_ACTION2, EnvelopeSBP);


                        SoapObject bodyIn2 = (SoapObject) EnvelopeSBP.bodyIn; // KDOM 節點文字編碼


                        SoapObject objSBP = (SoapObject) bodyIn2.getProperty(0);

                        //S_BP=new String[objSBP.getPropertyCount()][6];
                        S_BP = new ArrayList<>();

                        for (int i = 0; i < objSBP.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            S_BP.add(objSBP.getProperty(i).toString());
                            S_BP.add(objSBP.getProperty(i + 1).toString());
                            S_BP.add(objSBP.getProperty(i + 2).toString());
                            S_BP.add(objSBP.getProperty(i + 3).toString());
                            S_BP.add(objSBP.getProperty(i + 4).toString());
                            S_BP.add(objSBP.getProperty(i + 5).toString());

                        }
                    }
                    else if(Item.equals("心跳"))
                    {
                        String METHOD_NAME2 = "PhyData7Days";
                        String SOAP_ACTION2 = "http://tempuri.org/PhyData7Days";
                        SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                        r.addProperty("U_ID", uid);
                        r.addProperty("E_Project", Item);


                        SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        Envelope.dotNet = true;
                        Envelope.setOutputSoapObject(r);

                        HttpTransportSE SE = new HttpTransportSE(URL);
                        SE.call(SOAP_ACTION2, Envelope);


                        SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼


                        SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                        //D_BP=new String[obj2.getPropertyCount()][6];
                        HR = new ArrayList<>();

                        for (int i = 0; i < obj2.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            HR.add(obj2.getProperty(i).toString());
                            HR.add(obj2.getProperty(i + 1).toString());
                            HR.add(obj2.getProperty(i + 2).toString());
                            HR.add(obj2.getProperty(i + 3).toString());
                            HR.add(obj2.getProperty(i + 4).toString());
                            HR.add(obj2.getProperty(i + 5).toString());

                        }
                    }


                }


                if (go28Day) {

                    if (Item.equals("血壓")) {
                        String METHOD_NAME2 = "PhyData28Days";
                        String SOAP_ACTION2 = "http://tempuri.org/PhyData28Days";
                        SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                        r.addProperty("U_ID", uid);
                        r.addProperty("E_Project", "舒張壓");

                        SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        Envelope.dotNet = true;
                        Envelope.setOutputSoapObject(r);

                        HttpTransportSE SE = new HttpTransportSE(URL);
                        SE.call(SOAP_ACTION2, Envelope);


                        SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼


                        SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                        D_BP = new ArrayList<>();

                        for (int i = 0; i < obj2.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            D_BP.add(obj2.getProperty(i).toString());
                            D_BP.add(obj2.getProperty(i + 1).toString());
                            D_BP.add(obj2.getProperty(i + 2).toString());
                            D_BP.add(obj2.getProperty(i + 3).toString());
                            D_BP.add(obj2.getProperty(i + 4).toString());
                            D_BP.add(obj2.getProperty(i + 5).toString());

                        }
                        SoapObject rSBP = new SoapObject(NAMESPACE, METHOD_NAME2);
                        rSBP.addProperty("U_ID", uid);
                        rSBP.addProperty("E_Project", "收縮壓");


                        SoapSerializationEnvelope EnvelopeSBP = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        EnvelopeSBP.dotNet = true;
                        EnvelopeSBP.setOutputSoapObject(rSBP);

                        HttpTransportSE SE_SBP = new HttpTransportSE(URL);
                        SE_SBP.call(SOAP_ACTION2, EnvelopeSBP);


                        SoapObject bodyIn2 = (SoapObject) EnvelopeSBP.bodyIn; // KDOM 節點文字編碼


                        SoapObject objSBP = (SoapObject) bodyIn2.getProperty(0);

                        S_BP = new ArrayList<>();

                        for (int i = 0; i < objSBP.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            S_BP.add(objSBP.getProperty(i).toString());
                            S_BP.add(objSBP.getProperty(i + 1).toString());
                            S_BP.add(objSBP.getProperty(i + 2).toString());
                            S_BP.add(objSBP.getProperty(i + 3).toString());
                            S_BP.add(objSBP.getProperty(i + 4).toString());
                            S_BP.add(objSBP.getProperty(i + 5).toString());

                        }
                    }
                    else if (Item.equals("心跳")) {
                        String METHOD_NAME2 = "PhyData28Days";
                        String SOAP_ACTION2 = "http://tempuri.org/PhyData28Days";
                        SoapObject r = new SoapObject(NAMESPACE, METHOD_NAME2);
                        r.addProperty("U_ID", uid);
                        r.addProperty("E_Project", Item);


                        SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        Envelope.dotNet = true;
                        Envelope.setOutputSoapObject(r);

                        HttpTransportSE SE = new HttpTransportSE(URL);
                        SE.call(SOAP_ACTION2, Envelope);


                        SoapObject bodyIn = (SoapObject) Envelope.bodyIn; // KDOM 節點文字編碼


                        SoapObject obj2 = (SoapObject) bodyIn.getProperty(0);

                        //D_BP=new String[obj2.getPropertyCount()][6];
                        HR = new ArrayList<>();

                        for (int i = 0; i < obj2.getPropertyCount(); i = i + 6) {
                            //SoapObject obj3 =(SoapObject) objSBP.getProperty(i);
                            HR.add(obj2.getProperty(i).toString());
                            HR.add(obj2.getProperty(i + 1).toString());
                            HR.add(obj2.getProperty(i + 2).toString());
                            HR.add(obj2.getProperty(i + 3).toString());
                            HR.add(obj2.getProperty(i + 4).toString());
                            HR.add(obj2.getProperty(i + 5).toString());

                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //progressDialog.dismiss();



            if (Item.equals("血壓")) {

                lineChart.clear();
                if (D_BP.size()>0 && S_BP.size()>0) {
                    lblBp.setText(D_BP.get(5) + "/" + S_BP.get(5) + "\n mmHg");
                    SetChart();   //69/105 \n mmHg
                } else {
                    lineChart.setNoDataText("目前無測量記錄");
                    lineChart.setNoDataTextColor(context.getResources().getColor(R.color.blue_limit));
                }
            }

            if (Item.equals("心跳"))
            {

                lineChart.clear();
                if(HR.size()>0)
                {
                    lblHR.setText(HR.get(5)+"bmp");
                    SetChartHR();   //69/105 \n mmHg
                }
                else
                {
                    lineChart.setNoDataText("目前無測量記錄");
                }
            }


            if(first)
            {
                first=false;
                go7Day=false;
                lblBp.setText(firstAns.get(0));
                lblHR.setText(firstAns.get(1));
                firstAns.clear();
            }
            else
            {
                if(go28Day)
                {
                    go28Day=false;
                    Toast.makeText(context, "已選取28天資料", Toast.LENGTH_SHORT).show();
                }

                if(go7Day)
                {
                    go7Day=false;
                    Toast.makeText(context, "已選取7天資料", Toast.LENGTH_SHORT).show();
                }
            }

            //postChart();
            super.onPostExecute(s);
        }
    }

    private void SetChartHR() {
        LineDataSet lineDataSet = new LineDataSet(dateValueHR(),"脈搏(單位:bpm)");


        ArrayList<ILineDataSet>dataSets=new ArrayList<>();
        dataSets.add(lineDataSet);

        //lineChart.setBackgroundColor(Color.GREEN);  //圖表背景顏色
        //lineChart.setNoDataText("目前無測量記錄");

        lineChart.setDrawGridBackground(true);  //圖表背景會灰灰
        //lineChart.setDrawBorders(true);  //圖表外框加粗


        lineDataSet.setLineWidth(3);
        lineDataSet.setColor(context.getResources().getColor(R.color.blue_line));
        lineDataSet.setCircleColor(context.getResources().getColor(R.color.blue_line));
        lineDataSet.setDrawCircleHole(true); //點點設成實心
        lineDataSet.setCircleColorHole(Color.WHITE);  //點點內圈顏色
        lineDataSet.setCircleRadius(4);
        lineDataSet.setDrawValues(false);


        YAxis rightYAxis=lineChart.getAxisRight();
        rightYAxis.removeAllLimitLines();
        rightYAxis.setEnabled(false);//不顯示右側Y軸


        LimitLine limitLine =new LimitLine(101,"心跳過快");
        limitLine.setLineWidth(2f);
        limitLine.setTextSize(12f);
        limitLine.setTextColor(Color.BLACK);
        limitLine.setLineColor(context.getResources().getColor(R.color.blue_limit));
        limitLine.enableDashedLine(10f, 10f, 0f);  //設定虛線
        rightYAxis.addLimitLine(limitLine);

        LimitLine limitLineH =new LimitLine(49,"心跳過慢");
        limitLineH.setLineWidth(2f);
        limitLineH.setTextSize(12f);
        limitLineH.setTextColor(Color.BLACK);
        limitLineH.setLineColor(context.getResources().getColor(R.color.green));
        limitLineH.enableDashedLine(10f, 10f, 0f);  //設定虛線
        rightYAxis.addLimitLine(limitLineH);


        Legend legend= lineChart.getLegend();   //線條下方標題
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);


        /*Description description =new Description();
        description.setText("Zoo");
        description.setTextColor(Color.BLUE);
        description.setTextSize(20);
        lineChart.setDescription(description);*/

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelRotationAngle(50f);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        //String setter in x-Axis
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(HR_Date()));


        LineData data =new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

    }

    private void SetChart()  //血壓
    {

        LineDataSet lineDataSet = new LineDataSet(dateValue1(),"收縮壓(單位:mmHg)");
        LineDataSet lineDataSet2 = new LineDataSet(dateValue2(),"舒張壓(單位:mmHg)");

        ArrayList<ILineDataSet>dataSets=new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet2);



        //lineChart.setBackgroundColor(Color.GREEN);  //圖表背景顏色
        lineChart.setNoDataText("目前無測量記錄");

        lineChart.setDrawGridBackground(true);  //圖表背景會灰灰
        //lineChart.setDrawBorders(true);  //圖表外框加粗



        lineDataSet.setLineWidth(3);
//        lineDataSet.setColor(R.color.teal_200);
//        lineDataSet.setCircleColor(R.color.teal_200);
        lineDataSet.setColor(context.getResources().getColor(R.color.blue_line));
        lineDataSet.setCircleColor(context.getResources().getColor(R.color.blue_line));
        lineDataSet.setDrawCircleHole(true); //點點設成實心
        lineDataSet.setCircleColorHole(Color.WHITE);  //點點內圈顏色

        lineDataSet.setCircleRadius(4);
        lineDataSet.setDrawValues(false);

        lineDataSet2.setLineWidth(3);
        lineDataSet2.setColor(context.getResources().getColor(R.color.teal_700));
        lineDataSet2.setCircleColor(context.getResources().getColor(R.color.teal_700));
        lineDataSet2.setDrawCircleHole(true); //點點設成實心

        lineDataSet2.setCircleColorHole(Color.WHITE);  //點點內圈顏色
        lineDataSet2.setCircleRadius(4);
        lineDataSet2.setDrawValues(false);

        YAxis rightYAxis=lineChart.getAxisRight();
        YAxis leftYAxis=lineChart.getAxisLeft();

        rightYAxis.removeAllLimitLines();
        rightYAxis.setEnabled(false);//不顯示右側Y軸
        rightYAxis.setAxisMinimum(20f);//Y軸標籤最小值
        rightYAxis.setAxisMaximum(160f);//Y軸標籤最大值
        leftYAxis.setAxisMinimum(20f);//Y軸標籤最小值
        leftYAxis.setAxisMaximum(160f);//Y軸標籤最大值


        LimitLine limitLine =new LimitLine(140f,"收縮壓上限");
        limitLine.setLineWidth(2f);
        limitLine.setTextSize(12f);
        limitLine.setTextColor(Color.BLACK);
        //limitLine.setLineColor(Color.BLUE);
        limitLine.setLineColor(context.getResources().getColor(R.color.blue_limit));
        limitLine.enableDashedLine(10f, 10f, 0f);  //設定虛線
        rightYAxis.addLimitLine(limitLine);


        LimitLine limitLineH =new LimitLine(95f,"舒張壓上限");
        limitLineH.setLineWidth(2f);
        limitLineH.setTextSize(12f);
        limitLineH.setTextColor(Color.BLACK);

        limitLineH.setLineColor(context.getResources().getColor(R.color.green));
        limitLineH.enableDashedLine(10f, 10f, 0f);  //設定虛線
        rightYAxis.addLimitLine(limitLineH);



        Legend legend= lineChart.getLegend();   //線條下方標題
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);

        /*Description description =new Description();
        description.setText("Zoo");
        description.setTextColor(Color.BLUE);
        description.setTextSize(20);
        lineChart.setDescription(description);*/

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelRotationAngle(50f);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        //String setter in x-Axis
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dateValueDate()));


        LineData data =new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }
}