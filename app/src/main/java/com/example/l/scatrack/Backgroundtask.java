//package com.example.l.scatrack;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//
//
//public class Backgroundtask extends AsyncTask<String,Void,String> {
//    AlertDialog alertDialog;
//    Context ctx;
//    public String returndata;
//    Backgroundtask(Context ctx)
//    {
//        this.ctx =ctx;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(ctx).create();
//        alertDialog.setTitle("Login Information....");
//    }
//    @Override
//    protected String doInBackground(String... params) {
//
//        String login_url = "https://prekshaweb.000webhostapp.com/index.php";
//        String method = params[0];
//
//        if(method.equals("post"))
//        {
//            String name = params[1];
//            String pass = params[2];
//            try {
//                URL url = new URL(login_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data =   URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
//                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
//
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                String response = "";
//                String line = "";
//                while((line = bufferedReader.readLine())!=null)
//                {
//                    response+=line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return response;
//            } catch (MalformedURLException e) {
//                e.  printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//    @Override
//    protected void onProgressUpdate(Void... values) {
//        super.onProgressUpdate(values);
//    }
//    @Override
//    protected void onPostExecute(String jsonStr) {
//        String name="",pass="";
//        ArrayList<String> data = new ArrayList<String>();
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(jsonStr);
//                JSONArray contacts = jsonObj.getJSONArray("server_response1");
//
//                for (int i = 0; i < contacts.length(); i++) {
//
//                    JSONObject c = contacts.getJSONObject(i);
//                    name = c.getString("name");
//                    pass = c.getString("pass");
//                    System.out.println("name ->"+name);
//                    System.out.println("pass ->"+pass);
//                    data.add(name);
//                    data.add(pass);
//                }
//                if(name=="" || pass==""){
//                    Toast.makeText(ctx, "Login Unsuccessful", Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(ctx,Login_Bo.class);
//                    ctx.startActivity(i);
//                }
//                else{
//                    Toast.makeText(ctx, "Login Successful", Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(ctx,HomePage.class);
//                    i.putExtra("result","1");
//                    ctx.startActivity(i);
//                }
//            }
//            catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//        else{
//            Toast.makeText(ctx, "Login Unsuccessful", Toast.LENGTH_LONG).show();
//        }
////
////        for(int i1=0;i1<data.size()-1;i1++){
////            returndata += data.get(i1).toString() +" " + data.get(i1+1).toString() + " ";
////            alertDialog.setMessage(returndata);
////            alertDialog.show();
////        }
//    }
//
//}
