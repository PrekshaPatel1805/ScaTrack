package com.example.l.scatrack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    LinearLayout l3,l4,l5,l6;
    Backgroundtaskcontact  backgroundtaskcontact;
    Backgroundtasktoday  backgroundtasktoday;
    SessionManagement session;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        l3 = (LinearLayout) findViewById(R.id.l3);
        l4 = (LinearLayout) findViewById(R.id.l4);
        l5 = (LinearLayout) findViewById(R.id.l5);
        l6 = (LinearLayout) findViewById(R.id.l6);
        backgroundtaskcontact = new Backgroundtaskcontact(this);
        backgroundtasktoday = new Backgroundtasktoday(this);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomePage.this,ComingSoon.class);
                startActivity(i1);
            }
        });

        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        id = user.get(SessionManagement.KEY_ID);
        System.out.println("***********************************************"+id);

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundtaskcontact.execute("post",id);

            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               backgroundtasktoday.execute("post",id);
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(HomePage.this,ComingSoon.class);
                startActivity(i4);
            }
        });
    }
    class Backgroundtaskcontact extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        public String returndata;
        ProgressDialog progressDialog;

        Backgroundtaskcontact(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
//            progressDialog.setMessage("Loading, please wait...");
//            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            String login_url = "https://sonali8849.000webhostapp.com/getdata.php";
            String method = params[0];

            if(method.equals("post"))
            {
                String cid = params[1];
                System.out.println("----------------------------------"+cid);
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data =   URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String response = "";
                    String line = "";
                    while((line = bufferedReader.readLine())!=null)
                    {
                        response+=line;
                        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+response);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.  printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String jsonStr) {
            int loop;
            String cid="";
            ArrayList<String> data = new ArrayList<String>();
            if (jsonStr != null) {
                System.out.println("response : " + jsonStr);
                data = new ArrayList<String>(Arrays.asList(jsonStr.split(",")));
                for(loop=1;loop<=data.size();loop++){
                    System.out.println("loop-------------"+loop);
                }
                Intent i2 = new Intent(HomePage.this,sharedcontactlist.class);
                i2.putExtra("list",data);
                startActivity(i2);
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    JSONArray contacts = jsonObj.getJSONArray("server_response1");
//
//                    for (int i = 0; i < contacts.length(); i++) {
//
//                        JSONObject c = contacts.getJSONObject(i);
//                        cid = c.getString("cid");
//                        System.out.println("name ->"+cid);
//                        data.add(cid);
//                    }
//
////                    if (progressDialog.isShowing()) {
////                        progressDialog.dismiss();
////                    }
//                    Intent intent = new Intent(HomePage.this, sharedcontactlist.class);
//                    intent.putExtra("list",data);
//                    startActivity(intent);
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
            else{
                Toast.makeText(ctx, "Login Unsuccessful", Toast.LENGTH_LONG).show();
            }
//
//        for(int i1=0;i1<data.size()-1;i1++){
//            returndata += data.get(i1).toString() +" " + data.get(i1+1).toString() + " ";
//            alertDialog.setMessage(returndata);
//            alertDialog.show();
//        }
        }

    }
    class Backgroundtasktoday extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        public String returndata;
        ProgressDialog progressDialog;

        Backgroundtasktoday(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
//            progressDialog.setMessage("Loading, please wait...");
//            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            String login_url = "https://sonali8849.000webhostapp.com/today's.php";
            String method = params[0];

            if(method.equals("post"))
            {
                String cid = params[1];
                System.out.println("----------------------------------"+cid);
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data =   URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String response = "";
                    String line = "";
                    while((line = bufferedReader.readLine())!=null)
                    {
                        response+=line;
                        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+response);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.  printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String jsonStr) {
            int loop;
            String cid="";
            ArrayList<String> data = new ArrayList<String>();
            if (jsonStr != null) {
                System.out.println("response : " + jsonStr);
                data = new ArrayList<String>(Arrays.asList(jsonStr.split(",")));
                for(loop=1;loop<=data.size();loop++){
                    System.out.println("loop-------------"+loop);
                }
                Intent i2 = new Intent(HomePage.this,sharedcontactlist.class);
                i2.putExtra("list",data);
                startActivity(i2);
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    JSONArray contacts = jsonObj.getJSONArray("server_response1");
//
//                    for (int i = 0; i < contacts.length(); i++) {
//
//                        JSONObject c = contacts.getJSONObject(i);
//                        cid = c.getString("cid");
//                        System.out.println("name ->"+cid);
//                        data.add(cid);
//                    }
//
////                    if (progressDialog.isShowing()) {
////                        progressDialog.dismiss();
////                    }
//                    Intent intent = new Intent(HomePage.this, sharedcontactlist.class);
//                    intent.putExtra("list",data);
//                    startActivity(intent);
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
            else{
                Toast.makeText(ctx, "Login Unsuccessful", Toast.LENGTH_LONG).show();
            }
//
//        for(int i1=0;i1<data.size()-1;i1++){
//            returndata += data.get(i1).toString() +" " + data.get(i1+1).toString() + " ";
//            alertDialog.setMessage(returndata);
//            alertDialog.show();
//        }
        }

    }
}
