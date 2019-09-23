package com.example.l.scatrack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
public class BusinessInfo extends AppCompatActivity {
    TextView Contactno,Email,Address;
    Button rate;
    String bid="";
    SessionManagement session;
    BusinessInfoBackgroundTask businessInfoBackgroundTask;
    SendRating sendRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);
        Contactno = (TextView)findViewById(R.id.Contactno);
        Email = (TextView)findViewById(R.id.Email);
        Address = (TextView)findViewById(R.id.Address);
        rate = (Button) findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                callRate();
            }
        });
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        String id = user.get(SessionManagement.KEY_ID);
        bid = getIntent().getExtras().getString("bid");
        setupBottomNavigationView();
        businessInfoBackgroundTask = new BusinessInfoBackgroundTask(this);
        sendRating = new SendRating(this);
        businessInfoBackgroundTask.execute("post",id,bid);

    }
    public void callRate()
    {
        MyCustomDialog myDialog = new MyCustomDialog(this, "",
                new BusinessInfo.OnReadyListener());
        myDialog.show();
    }
    private class OnReadyListener implements MyCustomDialog.ReadyListener {

        public void ready(String name) {
            sendRating.execute(name,bid);
            Toast.makeText(BusinessInfo.this, name, Toast.LENGTH_LONG).show();
        }
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(BusinessInfo.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
    class BusinessInfoBackgroundTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context ctx;
        ProgressDialog progressDialog;

        BusinessInfoBackgroundTask(Context ctx) {
            this.ctx = ctx;
            progressDialog = new ProgressDialog(BusinessInfo.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading, please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "https://prekshaweb.000webhostapp.com/visitedCard.php";
            String method = params[0];

            if (method.equals("post")) {
                String cid = params[1],bid=params[2];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("cid", "UTF-8") + "=" + URLEncoder.encode(cid, "UTF-8") + "&" +
                            URLEncoder.encode("bid", "UTF-8") + "=" + URLEncoder.encode(bid, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
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
            String email = "",contact = "",address="";
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr.toString());
                    JSONArray contacts = jsonObj.getJSONArray("server_response");

                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);
                        email = c.getString("Email");
                        contact = c.getString("Contactno");
                        address = c.getString("Address");
                        Contactno.setText(contact);
                        Email.setText(email);
                        Address.setText(address);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
  }
    class SendRating extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context ctx;
        ProgressDialog progressDialog;

        SendRating(Context ctx) {
            this.ctx = ctx;
            progressDialog = new ProgressDialog(BusinessInfo.this);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading, please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "https://prekshaweb.000webhostapp.com/rate.php";
            String rate = params[0],bid = params[1];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("ratings", "UTF-8") + "=" + URLEncoder.encode(rate, "UTF-8") + "&" +
                            URLEncoder.encode("bid", "UTF-8") + "=" + URLEncoder.encode(bid, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            //Toast.makeText(ctx, jsonStr, Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing()) {
                Toast.makeText(ctx, "Your response has been recorded...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }
    }

}
