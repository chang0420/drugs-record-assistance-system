package com.app.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class DeleteReceiver extends BroadcastReceiver {

    private static String SOAP_ACTION = "http://tempuri.org/DeletePersonalMed";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "DeletePersonalMed";
    private static String URL = "http://120.125.78.200/WebService1.asmx";

    boolean success=false;

    String uid;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("goDelete"))
        {
            uid=intent.getStringExtra("uid");
            postData();
        }

    }

    private void postData() {

        new WebServicePostData().execute("");
    }

    private class WebServicePostData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(context,"","更新中，請稍後....");
//            progressDialog.setCancelable(false);

            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        protected String doInBackground(String... strings) {

            String message;
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("uid", uid);



                SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapSerializationEnvelope.dotNet = true;
                soapSerializationEnvelope.setOutputSoapObject(request);

                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION, soapSerializationEnvelope);


                //若是單純的值，直接用SoapPrimitive接

                SoapPrimitive result = (SoapPrimitive) soapSerializationEnvelope.getResponse();

                success = Boolean.parseBoolean(result.toString());


                message = "OK";

            } catch (Exception e) {
                e.printStackTrace();

                message = "ERROR:" + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (success) {

//                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

                success = false;


            } else {

//                Toast.makeText(context, "修改失敗", Toast.LENGTH_SHORT).show();

            }

            super.onPostExecute(s);
        }


    }
}
