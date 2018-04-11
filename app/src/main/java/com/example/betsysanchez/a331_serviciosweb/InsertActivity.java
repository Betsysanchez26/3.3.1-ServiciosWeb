package com.example.betsysanchez.a331_serviciosweb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BetsySanchez on 10/04/2018.
 */

public class InsertActivity extends AppCompatActivity {
    public static String mid,mname,maddress;
    EditText name,address;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_activity);
        init();
        clean();
        if(!mname.equals("") && !maddress.equals(""))save.setText("Editar");
        else save.setText("Guardar");
        read();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mname.equals("") && maddress.equals("")) {
                    Inserting insert = new Inserting();
                    insert.execute();
                }
                else {
                    Modify modify = new Modify();
                    modify.execute();
                }
            }
        });

    }

    private void init(){
        name=findViewById(R.id.nuevoNombre);
        address=findViewById(R.id.NuevoDireccion);
        save=findViewById(R.id.btnGuardar);
    }
    private void clean(){
        name.setText("");
        address.setText("");
    }
    private void read(){
        if(!mname.equals(""))name.setText(mname);
        if(!maddress.equals(""))address.setText(maddress);
    }

    private class Inserting extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://192.168.0.17/datos1/insertar_alumno.php");
                Log.d("url",url.toString());
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nombre",name.getText().toString());
                jsonObject.put("direccion",address.getText().toString());
                Log.d("JSON",jsonObject.toString());

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonObject.toString());

                dataOutputStream.flush();
                dataOutputStream.close();

                Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
                Log.i("MSG" , httpURLConnection.getResponseMessage());

                httpURLConnection.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();

        }
    }
    private class Modify extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://192.168.0.17/datos1/actualizar_alumno.php");
                Log.d("url",url.toString());
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idalumno",mid);
                jsonObject.put("nombre",name.getText().toString());
                jsonObject.put("direccion",address.getText().toString());
                Log.d("JSON",jsonObject.toString());

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonObject.toString());

                dataOutputStream.flush();
                dataOutputStream.close();

                Log.i("STATUS", String.valueOf(httpURLConnection.getResponseCode()));
                Log.i("MSG" , httpURLConnection.getResponseMessage());

                httpURLConnection.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }
}

