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

public class Otpverification extends AppCompatActivity {
    Button sub;
    String otp;
    EditText e1, e2, e3, e4;
    String method;
    String f_email;
    Intent intent;
    String com_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        sub = (Button) findViewById(R.id.submit);
        e1 = (EditText) findViewById(R.id.pin_first_edittext);
        e2 = (EditText) findViewById(R.id.pin_second_edittext);
        e3 = (EditText) findViewById(R.id.pin_third_edittext);
        e4 = (EditText) findViewById(R.id.pin_forth_edittext);
        intent = getIntent();
//        Toast.makeText(this, intent.getStringExtra("finalemail"), Toast.LENGTH_SHORT).show();
        f_email = intent.getStringExtra("finalemail");
        Toast.makeText(this, f_email, Toast.LENGTH_SHORT).show();
//        com_email = f_email;
//        Intent intent1 = new Intent(Otpverification.this,NewPassword.class);
//        intent1.putExtra("updateemail",com_email);
//        startActivity(intent1);
//        Toast.makeText(this, "///////////"+com_email, Toast.LENGTH_SHORT).show();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num1 = e1.getText().toString();
                String num2 = e2.getText().toString();
                String num3 = e3.getText().toString();
                String num4 = e4.getText().toString();
                otp = num1 + num2 + num3 + num4;
                f_email = intent.getStringExtra("finalemail");

//                Intent intent =getIntent();
////        Toast.makeText(this, intent.getStringExtra("finalemail"), Toast.LENGTH_SHORT).show();
//                f_email = intent.getStringExtra("finalemail");
//                Toast.makeText(Otpverification.this, "==================="+f_email, Toast.LENGTH_SHORT).show();
////                Toast.makeText(this,f_email, Toast.LENGTH_SHORT).show();

//                Toast.makeText(Otpverification.this, "******************" + otp, Toast.LENGTH_SHORT).show();
//                Intent intent =getIntent();
//                f_email = intent.getStringExtra("finalemail");
//                Toast.makeText(Otpverification.this, "--------->"+f_email, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, intent.getStringExtra("finalemail"), Toast.LENGTH_SHORT).show();
//                Toast.makeText(Otpverification.this,otp, Toast.LENGTH_SHORT).show();
                method = "verifyotp";
                BackGroundTaskOtpVerify backGroundTaskOtpVerify = new BackGroundTaskOtpVerify(Otpverification.this);
                backGroundTaskOtpVerify.execute(method, otp, f_email);
//                Intent intent1 = new Intent(Otpverification.this, NewPassword.class);
//                startActivity(intent1);
            }
        });
    }

    public class BackGroundTaskOtpVerify extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;

        BackGroundTaskOtpVerify(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("checking otp....");
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "http://scatr.000webhostapp.com/otp.php";
            String method = params[0];
            if (method.equalsIgnoreCase("verifyotp")) {
                String otp = params[1];
                String f_email = params[2];
//                String f_email=params[2];
//                String u_pass=params[2];

//                MessageDigest md = null;
//                try {
//                    md = MessageDigest.getInstance("MD5");
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//                md.upda```te(u_pass.getBytes());
//                byte byteData[] = md.digest();
//
//                StringBuffer sb = new StringBuffer();
//                for (int i = 0; i < byteData.length; i++) {
//                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//                }
                // u_pass = sb.toString();
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("otp", "UTF-8") + "=" + URLEncoder.encode(otp, "UTF-8") + "&" +
                            URLEncoder.encode("f_email", "UTF-8") + "=" + URLEncoder.encode(f_email, "UTF-8");
                    Log.e("---->", otp.toString());
//                    Log.e("---->",u_pass.toString());
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
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "execute -> "+result, Toast.LENGTH_SHORT).show();
            String j_otp = "";
            if (result != null) {
                try {
                    Toast.makeText(ctx, "Inside post execute...try", Toast.LENGTH_SHORT).show();
//                    JSONObject jsonobj = new JSONObject(result);
//                    JSONArray contacts = jsonobj.getJSONArray("server_response");
//                    JSONObject c = contacts.getJSONObject(0);
//                    j_otp = c.getString("v_otp");
                    String newotp = "";
                    JSONObject json_data = new JSONObject(result);
                    JSONArray jArray = json_data.getJSONArray("server_response");
                    for(int i=0;i<jArray.length();i++){
                        json_data = jArray.getJSONObject(i);
                        newotp = json_data.getString("v_otp");
                        Log.d("msg --> ",newotp);
                        Toast.makeText(ctx, "otp in java"+newotp, Toast.LENGTH_LONG).show();

                    }

                    if(newotp.equals("1")){
                        Intent intent = new Intent(getBaseContext(), NewPassword.class);
                        intent.putExtra("mail_id", f_email);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ctx, "not found ... ", Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(ctx, "contacts array "+String.valueOf(contacts.length()), Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);
////                        Login lg = new Login();
//                        j_otp = c.getString("verifiedotp");
//                        System.out.println("otp is----->" + j_otp);
//                        Toast.makeText(Otpverification.this, "your encoded otp" + j_otp, Toast.LENGTH_SHORT).show();
//
//                    }
                } catch (final JSONException e) {
                    Toast.makeText(ctx, "json parsing error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


//            Toast.makeText(Otpverification.this, result, Toast.LENGTH_LONG).show();

//            alertDialog.setMessage(result);
//            alertDialog.show();
            if (result.trim().equalsIgnoreCase("Successfully checked")) {
                Toast.makeText(Otpverification.this, result, Toast.LENGTH_LONG).show();
                alertDialog.setTitle(result);
//                sessionManage.createLoginStatus(uemail.getText().toString());
//                editor.putBoolean(Constants.TAG_IS_LOGIN,true);
//                editor.commit();
                startActivity(new Intent(Otpverification.this, NewPassword.class));
                finish();
            } else {

                Toast.makeText(Otpverification.this, result, Toast.LENGTH_LONG).show();
            }


        }


    }
}