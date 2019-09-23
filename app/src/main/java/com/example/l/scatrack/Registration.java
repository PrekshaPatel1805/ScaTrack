package com.example.l.scatrack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Registration extends AppCompatActivity {
    EditText B_O_fname, B_O_lname, B_O_email, B_O_password, B_O_contact_no, B_O_profession, B_O_birthdate,B_link,B_O_address,B_description,B_registration_date;
    BackgroundTaskRegister backgroundTaskRegister;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final Button submit = (Button) findViewById(R.id.submit);
        final Button personal = (Button) findViewById(R.id.personal);
        //  DatePicker picker = (DatePicker) findViewById(R.id.datepicker);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.fadein);

        B_O_fname = (EditText) findViewById(R.id.fname);
        B_O_lname = (EditText) findViewById(R.id.lname);
        B_O_email = (EditText) findViewById(R.id.email);
        B_O_password = (EditText) findViewById(R.id.password);
        B_O_contact_no = (EditText) findViewById(R.id.contact);
        B_O_profession = (EditText) findViewById(R.id.birthday);
        B_O_birthdate = (EditText) findViewById(R.id.profession);
        B_link = (EditText) findViewById(R.id.link);
        B_O_address = (EditText) findViewById(R.id.address);
        B_description  = (EditText) findViewById(R.id.desc);
        B_registration_date = (EditText)findViewById(R.id.reg_date);

        backgroundTaskRegister = new BackgroundTaskRegister(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());
        B_registration_date.setText(strDate);


        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animationfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                layout.startAnimation(animationfadein);


            }
        });


        B_O_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backgroundTaskRegister.execute("post",B_O_fname.getText().toString(),B_O_lname.getText().toString(),B_O_email.getText().toString(),B_O_password.getText().toString(),B_O_contact_no.getText().toString(),B_O_profession.getText().toString(),B_O_birthdate.getText().toString(),B_link.getText().toString(),B_O_address.getText().toString(),B_description.getText().toString(),B_registration_date.getText().toString());

//                String f_name = fname.getText().toString();
//                String l_name = lname.getText().toString();
//                String o_mailid = mailid.getText().toString();
//                String password = pass.getText().toString();
//                String contact = contact_l.getText().toString();
//                String profession = profession_l.getText().toString();
//                String birthday  = birthday_l.getText().toString();



            }
        });


    }


//    public String getdate()
//    {
//        StringBuilder builder = new StringBuilder();
//        builder.append((picker.getMonth()+1)+"/");
//        builder.append(picker.getDayOfMonth()+"/");
//        builder.append(picker.getYear());
//        return builder.toString();
//    }

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
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String formattedDate = df.format(chosenDate);

            // Display the chosen date to app interface
            B_O_birthdate.setText(formattedDate);
        }
    }
    public class BackgroundTaskRegister extends AsyncTask<String,Void,String> {
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
            String login_url = "http://angel198.000webhostapp.com/business.php";
            String method = params[0];
            int cnt=0;

            if(method.equals("post"))
            {
                String B_O_fname =params[1], B_O_lname=params[2], B_O_email=params[3], B_O_password=params[4], B_O_contact_no=params[5], B_O_profession=params[6], B_O_birthdate=params[7], B_link=params[8],B_O_address=params[9],B_description=params[10],B_registration_date=params[11];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data =   URLEncoder.encode("B_O_fname","UTF-8")+"="+URLEncoder.encode(B_O_fname,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_lname","UTF-8")+"="+URLEncoder.encode(B_O_lname,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_email","UTF-8")+"="+URLEncoder.encode(B_O_email,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_password","UTF-8")+"="+URLEncoder.encode(B_O_password,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_contact_no","UTF-8")+"="+URLEncoder.encode(B_O_contact_no,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_profession","UTF-8")+"="+URLEncoder.encode(B_O_profession,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_birthdate","UTF-8")+"="+URLEncoder.encode(B_O_birthdate,"UTF-8")+"&"+
                            URLEncoder.encode("B_link","UTF-8")+"="+URLEncoder.encode(B_link,"UTF-8")+"&"+
                            URLEncoder.encode("B_O_address","UTF-8")+"="+URLEncoder.encode(B_O_address,"UTF-8")+"&"+
                            URLEncoder.encode("B_description","UTF-8")+"="+URLEncoder.encode(B_description,"UTF-8")+"&"+
                            URLEncoder.encode("B_registration_date","UTF-8")+"="+URLEncoder.encode(B_registration_date,"UTF-8");

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
            if(jsonStr.compareTo("New record created successfully") == 0){
                Intent i= new Intent(Registration.this,Login_Bo.class);
                startActivity(i);
            }
            else{
                Intent i= new Intent(Registration.this,Registration.class);
                startActivity(i);
            }
            alertDialog.setMessage(jsonStr);
            alertDialog.show();
        }
    }


}