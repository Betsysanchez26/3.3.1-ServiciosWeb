package com.example.betsysanchez.a331_serviciosweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    String datos="";
    Button consultarid;
    TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consultarid = findViewById(R.id.consultarid);
        resultado = findViewById(R.id.tresultado);
        datos="";

/*
        consultarid.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        Hola al=new Hola();
        al.execute();

    }

    private class Hola extends AsyncTask<Void,Void,Void>{

        String data="";
        @Override
        protected Void doInBackground(Void... voids) {
                                    try{
                                URL url = new URL("http://192.168.0.17/datos1/obtener_alumnos.php");
                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new
                                                InputStreamReader(inputStream));
                                String line ="";
                                while (line!=null){
                                        line = bufferedReader.readLine();
                                        data = data+line;
                                    }
                                JSONObject jsonObject = new JSONObject(data);
                                JSONArray alumnos = new JSONArray(jsonObject.getString("alumnos"));
                                JSONObject alumnos2;
                                for(int i=0;i<alumnos.length();i++){
                                        alumnos2=(JSONObject)alumnos.get(i);
                                        datos+="id : "+alumnos2.getString("idAlumno")+" nombre: "+alumnos2.getString("nombre")+";";
                                    }
                            }catch (Exception e){
                                e.printStackTrace();
                        }
                       return null;
                    }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            resultado.setText(datos);
        }
/*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }*/
    }
}
