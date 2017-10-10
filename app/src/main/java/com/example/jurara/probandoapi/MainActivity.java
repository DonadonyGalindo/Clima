package com.example.jurara.probandoapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

        TextView txt;
        JSONObject data = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            txt=(TextView)findViewById(R.id.txtsan);
            getJSON();
        }

        public void getJSON() {

            new AsyncTask<Void, Void, Void>() {


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Santiago%20Ixcuintla,mx&APPID=0906362826d2cfea265ed029381a7e31");

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuffer json = new StringBuffer(1024);
                        String tmp = "";

                        while((tmp = reader.readLine()) != null)
                            json.append(tmp).append("\n");
                        reader.close();

                        data = new JSONObject(json.toString());

                        if(data.getInt("cod") != 200) {
                            System.out.println("Cancelled");
                            return null;
                        }



                    } catch (Exception e) {

                        System.out.println("Exception "+ e.getMessage());
                        return null;
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void Void) {
                    if(data!=null){
                        Log.d("my weather received",data.toString());
                        try {
                            Log.d("-------->>>>",data.getString("main")+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            String a;

                            JSONObject ab = new JSONObject(data.getString("main"));
                            //a=a-273.15;
                            //DecimalFormat formateador = new DecimalFormat("####.##");
                            txt.setText((ab.getDouble("temp")-273.15)+" °C\nSantiago\nIxcuintla\n"+(ab.getDouble("temp_max")-273.15)+ " Max\n"+(ab.getDouble("temp_min")-273.15)+" Min");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.execute();

        }
    }

