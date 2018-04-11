package com.example.betsysanchez.a331_serviciosweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    String result="";
    Button buscar, nuevo;
    //TextView resultado;
    EditText idB;
   String numId;
    String datos [][];
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numId="";
        buscar = findViewById(R.id.btnBuscar);
        nuevo=findViewById(R.id.btnNuevo);
        idB=findViewById(R.id.etId);
        //resultado = findViewById(R.id.tresultado);
        recyclerView = findViewById(R.id.recycler);
        result="";
        buscar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                numId=idB.getText().toString();
                getAlumnos al=new getAlumnos();
                al.execute();
            }
        });

        nuevo.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
            InsertActivity insert = new InsertActivity();
            insert.mid="";
            insert.mname="";
            insert.maddress="";
            Intent i = new Intent(MainActivity.this,InsertActivity.class);
            startActivity(i);
        }
        });
    }

    private class getAlumnos extends AsyncTask<Void,Void,Void>{

        String data="";
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url;
                if(numId.equals("")){
                    url=new URL("http://172.20.8.25/datos1/obtener_alumnos.php");

                }else{
                    url=new URL("http://172.20.8.25/datos1/obtener_alumno_por_id.php?idalumno="+numId);
                }

                HttpURLConnection httpURLConnection = (HttpURLConnection) (url).openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line ="";
                while (line!=null){
                    line = bufferedReader.readLine();
                    data = data+line;
                    }
                JSONObject jsonObject = new JSONObject(data);

                if(numId.equals("")){
                    JSONArray alumnos = new JSONArray(jsonObject.getString("alumnos"));
                    JSONObject alumnosC,alumnosC2;
                    datos= new String[alumnos.length()][3];
                    for(int i=0;i<alumnos.length();i++){
                        alumnosC=(JSONObject)alumnos.get(i);
                        datos[i][0]=alumnosC.getString("idalumno");
                        datos[i][1]=alumnosC.getString("nombre");
                        datos[i][2]=alumnosC.getString("direccion");}
                }else{
                    datos= new String[1][3];
                        JSONObject alumnosC2=(JSONObject)jsonObject.get("alumno");
                        datos[0][0]=alumnosC2.getString("idAlumno");
                        datos[0][1]=alumnosC2.getString("nombre");
                        datos[0][2]=alumnosC2.getString("direccion");}

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            adapter = new AlumnoAdapter(datos);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }
    }
}
