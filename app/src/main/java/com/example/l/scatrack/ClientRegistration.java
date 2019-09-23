package com.example.l.scatrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ClientRegistration extends AppCompatActivity {
    //Button register;
    EditText C_fname, C_lname, C_email, C_password, C_contact_number, C_birthdate, C_profession,C_city,C_business_desc,C_reg_date;
    BackgroundTaskRegister backgroundTaskRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);

        Button personal = (Button) findViewById(R.id.personal);
        Button reg = (Button) findViewById(R.id.reg);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.fadein);

        backgroundTaskRegister  =new BackgroundTaskRegister(this);
        C_fname = (EditText) findViewById(R.id.fname);
        C_lname = (EditText) findViewById(R.id.lname);
        C_email = (EditText) findViewById(R.id.email);
        C_password = (EditText) findViewById(R.id.password);
        C_contact_number = (EditText) findViewById(R.id.contact);
        C_birthdate = (EditText) findViewById(R.id.birthday);
        C_profession = (EditText) findViewById(R.id.profession);
        C_city = (EditText) findViewById(R.id.city);
        C_business_desc = (EditText) findViewById(R.id.business_desc);
        C_reg_date = (EditText) findViewById(R.id.reg_date);
        //http://angel198.000webhostapp.com/insert.php?f_name=shrui&l_name=shah&o_mailid=shrui@gmail.com&password=123&contact=123456&profession=programmer&birthday=02/04/1991


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());
        C_reg_date.setText(strDate);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animationfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                layout.startAnimation(animationfadein);


            }
        });
        C_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });


//        C_reg_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment dialogFragment = new DatePickerFragment();
//                dialogFragment.show(getFragmentManager(), "Date Picker");
//            }
//        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"daa ->"+f_name.getText().toString()+"->"+l_name.getText().toString()+"->"+o_mailid.getText().toString()+"->"+password.getText().toString()+"->"+contact.getText().toString()+"->"+profession.getText().toString()+"->"+birthday.getText().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "done.....", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, f_name.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, l_name.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, o_mailid.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, password.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, contact.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, profession.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, birthday.getText().toString(), Toast.LENGTH_SHORT).show();


                System.out.printf("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                backgroundTaskRegister.execute("post",C_fname.getText().toString(),C_lname.getText().toString(),C_email.getText().toString(),C_password.getText().toString(),C_contact_number.getText().toString(),C_birthdate.getText().toString(),C_profession.getText().toString(),C_city.getText().toString(),C_business_desc.getText().toString(),C_reg_date.getText().toString());

            }
        });
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                Initialize a new DatePickerDialog

                DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack,
                    int year, int monthOfYear, int dayOfMonth)
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            // TextView tv = (TextView) getActivity().findViewById(R.id.tv);

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            DateFormat df = DateFormat.getDateInstance();
            String formattedDate = df.format(chosenDate);

            // Display the chosen date to app interface
            C_birthdate.setText(formattedDate);
            //C_reg_date.setText(formattedDate);
        }


    }

    class BackgroundTaskRegister extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        BackgroundTaskRegister(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
        }
        @Override
        protected String doInBackground(String... params) {
            String login_url = "http://angel198.000webhostapp.com/insert.php";
            String method = params[0];

            if(method.equals("post"))
            {
                String C_fname=params[1], C_lname=params[2], C_email=params[3], C_password=params[4],C_contact_number =params[5], C_birthdate=params[6], C_profession=params[7], C_city=params[8],C_business_desc=params[9],C_reg_date=params[10];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data =   URLEncoder.encode("C_fname","UTF-8")+"="+URLEncoder.encode(C_fname,"UTF-8")+"&"+
                            URLEncoder.encode("C_lname","UTF-8")+"="+URLEncoder.encode(C_lname,"UTF-8")+"&"+
                            URLEncoder.encode("C_email","UTF-8")+"="+URLEncoder.encode(C_email,"UTF-8")+"&"+
                            URLEncoder.encode("C_password","UTF-8")+"="+URLEncoder.encode(C_password,"UTF-8")+"&"+
                            URLEncoder.encode("C_contact_number","UTF-8")+"="+URLEncoder.encode(C_contact_number,"UTF-8")+"&"+
                            URLEncoder.encode("C_birthdate","UTF-8")+"="+URLEncoder.encode(C_birthdate,"UTF-8")+"&"+
                            URLEncoder.encode("C_profession","UTF-8")+"="+URLEncoder.encode(C_profession,"UTF-8")+"&"+
                            URLEncoder.encode("C_city","UTF-8")+"="+URLEncoder.encode(C_city,"UTF-8")+"&"+
                            URLEncoder.encode("C_business_desc","UTF-8")+"="+URLEncoder.encode(C_business_desc,"UTF-8")+"&"+
                            URLEncoder.encode("C_reg_date","UTF-8")+"="+URLEncoder.encode(C_reg_date,"UTF-8");

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
            alertDialog.setMessage(jsonStr);
            alertDialog.show();
        }
    }


}

