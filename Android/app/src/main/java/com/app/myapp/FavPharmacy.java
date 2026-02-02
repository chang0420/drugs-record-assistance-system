package com.app.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavPharmacy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavPharmacy extends Fragment {
    String URL="http://120.125.78.206/BrainShaking.asmx";
    private static final String NAMESPACE2 = "http://tempuri.org/";//
    private static final String METHOD_NAME2 ="DeleteFavPh";
    private static final String SOAP_ACTION2 = "http://tempuri.org/DeleteFavPh";//
    private static final String NAMESPACE3 = "http://tempuri.org/";//
    private static final String METHOD_NAME3 ="SearchFavPh_LackMed";
    private static final String SOAP_ACTION3 = "http://tempuri.org/SearchFavPh_LackMed";//

    View view;
    String[][]Fav_LackMed;
    String ph;
    Object resultString;
    String message,uid;
    Context context;
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;//myAdapter
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),MedMessage.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    private void recyclerViewAction(RecyclerView mRecyclerView, final ArrayList<HashMap<String,String>> arrayList, final MyListAdapter myListAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,
                        ItemTouchHelper.LEFT );
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        ph= arrayList.get(position).get("PH");//
                        arrayList.remove(position);
                        myListAdapter.notifyItemRemoved(position);
                        DeleteFavPh();
                        break;
                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_GINSYU))
                        .addActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        });
        helper.attachToRecyclerView(mRecyclerView);
    }
    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtPH,txtCMed,txtDate,txtItem;
            private LinearLayout linePh;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtPH = itemView.findViewById(R.id.txtPH);
                txtCMed = itemView.findViewById(R.id.txtLackCMed2);
                txtDate  = itemView.findViewById(R.id.txtMDate2);
                txtItem=itemView.findViewById(R.id.txtLackItem);
                linePh=itemView.findViewById(R.id.lineFavPh);
            }
        }
        @NonNull
        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favph_item,parent,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
            holder.txtPH.setText(arrayList.get(position).get("PH"));
            holder.txtCMed.setText(arrayList.get(position).get("CMed"));
            holder.txtDate.setText(arrayList.get(position).get("Date"));
            holder.txtItem.setText(arrayList.get(position).get("LackItem"));
            holder.linePh.setOnClickListener((v)->{
                Intent intent = new Intent();
                intent.setClass(getActivity(),MedMessage.class);
                intent .putExtra("PH",holder.txtPH.getText());//跨頁取值
                intent.putExtra("uid",uid);//
                startActivity(intent);
            });
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
    private void DeleteFavPh() {
        //刪除常用藥局
        new WebServiceDeleteFav().execute("");
    }

    private class WebServiceDeleteFav extends AsyncTask<String,Void,String> {


        //刪除常用藥局
        @Override
        protected String doInBackground(String... strings) {
            try{
                uid=getArguments().getString("uid");
                SoapObject request=new SoapObject(NAMESPACE2,METHOD_NAME2);
                request.addProperty("uid",uid);//
                request.addProperty("Phname",ph);//
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION2,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            myListAdapter = new MyListAdapter();
            mRecyclerView.setAdapter(myListAdapter);
            recyclerViewAction(mRecyclerView, arrayList, myListAdapter);
            super.onPostExecute(s);
        }
    }
    private void PostLackMed(){new WebServicePostLackMed().execute("");}
    private class WebServicePostLackMed extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try{
                uid=getArguments().getString("uid");
                SoapObject request=new SoapObject(NAMESPACE3,METHOD_NAME3);
                request.addProperty("uid",uid);//
                SoapSerializationEnvelope soapSerializationEnvelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet=true;
                soapSerializationEnvelope.setOutputSoapObject(request);
                HttpTransportSE transportSE= new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION3,soapSerializationEnvelope);
                resultString =soapSerializationEnvelope.getResponse();
                SoapObject bodyIn = (SoapObject) soapSerializationEnvelope.bodyIn; // KDOM 節點文字編碼
                SoapObject obj2 =(SoapObject) bodyIn.getProperty(0);//
                Fav_LackMed=new String[obj2.getPropertyCount()][3];
                for(int i=0; i<obj2.getPropertyCount(); i++)
                {
                    SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                    Fav_LackMed[i][0] = obj3.getProperty(0).toString();//PH
                    Fav_LackMed[i][2] = obj3.getProperty(2).toString();//Date
                    Fav_LackMed[i][1] = obj3.getProperty(1).toString();//Message
                    HashMap<String,String> hashMap = new HashMap<>();
                    if(Fav_LackMed[i][2].equals("anyType{}") )
                    {
                        hashMap.put("CMed","正常供應");
                        //hashMap.put("Date",Fav_LackMed[i][1]);
                        hashMap.put("PH",Fav_LackMed[i][0]);
                    }
                    else {
                        hashMap.put("CMed",Fav_LackMed[i][1]);
                        hashMap.put("PH",Fav_LackMed[i][0]);
                        hashMap.put("Date",Fav_LackMed[i][2]);
                        hashMap.put("LackItem","缺藥");
                    }
                    arrayList.add(hashMap);
                    message="OK";
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                message="ERROR:"+e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            myListAdapter = new MyListAdapter();
            mRecyclerView.setAdapter(myListAdapter);
            recyclerViewAction(mRecyclerView, arrayList, myListAdapter);
            super.onPostExecute(s);
        }
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavPharmacy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavPharmacy.
     */
    // TODO: Rename and change types and number of parameters
    public static FavPharmacy newInstance(String param1, String param2) {
        FavPharmacy fragment = new FavPharmacy();
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
        PostLackMed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        context=this.getActivity();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fav_pharmacy, container, false);
//        LinearLayoutManager manager=new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);

        //設置RecycleView
        mRecyclerView = view.findViewById(R.id.recycleview2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);
        recyclerViewAction(mRecyclerView, arrayList, myListAdapter);
        //下拉更新
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout2);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
        swipeRefreshLayout.setOnRefreshListener(()-> {
            arrayList.clear();
            PostLackMed();
            if (myListAdapter != null) {
                mRecyclerView.setAdapter(myListAdapter);
            }else {
                myListAdapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }
}