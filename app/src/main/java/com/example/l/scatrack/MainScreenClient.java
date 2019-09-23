package com.example.l.scatrack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import java.util.ArrayList;
import java.util.HashMap;

public class MainScreenClient extends AppCompatActivity {
    Button SearchButton;
    EditText SearchText;
    String SearchData;
    BackgroundTask1 backgroundTask1;
    SessionManagement session;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_client);
        SearchButton = (Button) findViewById(R.id.searchButton);
        SearchText = (EditText) findViewById(R.id.searchText);
        image = (ImageView) findViewById(R.id.image);
        backgroundTask1 = new BackgroundTask1(this);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        Bundle extras = getIntent().getExtras();
        String pathToFile = "";
        if (extras != null) {
            pathToFile = extras.getString("image").toString();
        }
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(image);
        downloadTask.execute(pathToFile);
        HashMap<String, String> user = session.getUserDetails();
        String id = user.get(SessionManagement.KEY_ID);
        String name = user.get(SessionManagement.KEY_EMAIL);
        String email = user.get(SessionManagement.KEY_PASS);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SearchText.getText().toString() != ""){
                    SearchData = SearchText.getText().toString();
                }
                else{
                    SearchData = "*";
                }
                backgroundTask1.execute("post", SearchData);
            }
        });
    }
    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    class BackgroundTask1 extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context ctx;
        ProgressDialog progressDialog;

        BackgroundTask1(Context ctx) {
            this.ctx = ctx;
            progressDialog = new ProgressDialog(MainScreenClient.this);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading, please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "https://prekshaweb.000webhostapp.com/xyz.php";
            String ans_url = "https://prekshaweb.000webhostapp.com/xyz.php";
            String method = params[0];

            if (method.equals("post")) {
                String search = params[1];

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(search, "UTF-8");

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
            } else {
                String search = params[1];


                try {
                    URL url = new URL(ans_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("search", "UTF-8") + "=" + URLEncoder.encode(search, "UTF-8");


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

            String bid="",search = "";
            ArrayList<String> bid_array = new ArrayList<String>();
            ArrayList<String> search_array = new ArrayList<String>();

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("search");

                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);
                        bid = c.getString("id");
                        bid_array.add(bid);
                        search = c.getString("search");
                        search_array.add(search);
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Intent intent = new Intent(MainScreenClient.this, BusinessOwnerListview.class);
                    intent.putExtra("bid",bid_array);
                    intent.putExtra("list", search_array);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
