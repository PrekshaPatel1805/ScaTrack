package com.example.l.scatrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.List;

public class Take_email extends AppCompatActivity {
    List<String> contactList;
    EditText em;
    Button sotp;
    String t_email;
    String method;
    ArrayList<Login> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_email);
        em= (EditText) findViewById(R.id.emailsend);
        sotp= (Button) findViewById(R.id.otpsend);
        contactList = new ArrayList<String>();
        sotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t_email=em.getText().toString();
                method="takeemail";
                BackgroundTaskTakeEmail backgroundTaskTakeEmail=new BackgroundTaskTakeEmail(Take_email.this);
                backgroundTaskTakeEmail.execute(method,t_email);
                Intent intent = new Intent(Take_email.this,Otpverification.class);
                startActivity(intent);
            }
        });
    }
    public class BackgroundTaskTakeEmail extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        BackgroundTaskTakeEmail(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Taking email....");
        }

        @Override
        protected String doInBackground(String... params) {
//            HttpHandler sh = new HttpHandler();
            String login_url="http://scatr.000webhostapp.com/checkemail.php";
//            String jsonStr = sh.makeServiceCall(login_url);
////            Log.e( ,"response from url"+jsonStr);
////            Toast.makeText(ctx, jsonStr, Toast.LENGTH_SHORT).show();
//            if(jsonStr != null){
//                try{
//                    JSONObject jsonobj = new JSONObject(jsonStr);
//                    JSONArray contacts = jsonobj.getJSONArray("server_response");
//
//                    for(int i=0; i<contacts.length();i++){
//                        JSONObject c = contacts.getJSONObject(i);
//                        String final_email =c.getString("reg_email");
//                        contactList.add(final_email);
//                    }
//                }catch(final JSONException e){
//                    Toast.makeText(ctx, "json parsing error"+e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            String method=params[0];
            if(method.equalsIgnoreCase("takeemail"))
            {
                String t_email=params[1];
//                String u_pass=params[2];

//                MessageDigest md = null;
//                try {
//                    md = MessageDigest.getInstance("MD5");
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//                md.update(u_pass.getBytes());
//                byte byteData[] = md.digest();
//
//                StringBuffer sb = new StringBuffer();
//                for (int i = 0; i < byteData.length; i++) {
//                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//                }
                // u_pass = sb.toString();
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("t_email", "UTF-8") + "=" + URLEncoder.encode(t_email, "UTF-8");
                    Log.e("---->",t_email.toString());
//                    Log.e("---->",u_pass.toString());
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine())!=null)
                    {
                        response+= line;
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
        protected void onPostExecute(String result)
        {
            String ses_email="";
            ArrayList<String> data = new ArrayList<String>();
//            HttpHandler sh = new HttpHandler();
//            String jsonStr = sh.makeServiceCall(login_url);
//            Log.e( ,"response from url"+jsonStr);
//            Toast.makeText(ctx, jsonStr, Toast.LENGTH_SHORT).show();
            if(result != null){
                try{
                    JSONObject jsonobj = new JSONObject(result);
                    JSONArray contacts = jsonobj.getJSONArray("server_response");

                    for(int i=0; i<contacts.length();i++){
                        JSONObject c = contacts.getJSONObject(i);
                        Login lg = new Login();
                        ses_email=c.getString("reg_email");
                        System.out.println("email is----->"+ses_email);
//                        Toast.makeText(Take_email.this, "your email"+ses_email, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Take_email.this,Otpverification.class);
                        intent.putExtra("finalemail",ses_email);
                        startActivity(intent);
                        lg.setP_email(c.getString("reg_email"));
//                        listdata.add(lg);
//                        String final_email =c.getString("reg_email");
//                        contactList.add(final_email);
                    }
                }catch(final JSONException e){
                    Toast.makeText(ctx, "json parsing error"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

//            Toast.makeText(Take_email.this, result, Toast.LENGTH_LONG).show();

//            alertDialog.setMessage(result);
//            alertDialog.show();
            if(result.trim().equalsIgnoreCase("Successfully registered")) {
                Toast.makeText(Take_email.this," ------>"+ result, Toast.LENGTH_LONG).show();
//                sessionManage.createLoginStatus(uemail.getText().toString());
//                editor.putBoolean(Constants.TAG_IS_LOGIN,true);
//                editor.commit();
                startActivity(new Intent(Take_email.this,Otpverification.class));
                finish();
            }

            else {

                Toast.makeText(Take_email.this, result, Toast.LENGTH_LONG).show();
            }



        }

    }
}
