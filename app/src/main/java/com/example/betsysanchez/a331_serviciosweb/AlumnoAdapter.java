package com.example.betsysanchez.a331_serviciosweb;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BetsySanchez on 10/04/2018.
 */

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.RecyclerViewHolder> {
    String [][]data;
    Context context;
    public AlumnoAdapter(String[][] data) {
        this.data = data;
    }

    @Override
    public AlumnoAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_alumnos,parent,false);
        return new AlumnoAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumnoAdapter.RecyclerViewHolder holder, final int position) {
        final String id = data[position][0];
        holder.nombre.setText("nombre: "+data[position][1]);
        holder.direccion.setText("direccion: "+data[position][2]);
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Eliminar delete = new Eliminar();
                delete.did=id;
                delete.dname=data[position][1];
                delete.daddress=data[position][2];
                delete.execute();
                            }
        });
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            InsertActivity insert = new InsertActivity();
                insert.mid=id;
                insert.mname=data[position][1];
                insert.maddress=data[position][2];
                Intent i = new Intent(context,InsertActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,direccion;
        Button editar,eliminar;
        public RecyclerViewHolder(View itemView) {
             super(itemView);
             nombre=itemView.findViewById(R.id.txtNombre);
             direccion=itemView.findViewById(R.id.txtDireccion);
             editar=itemView.findViewById(R.id.btnEditar);
             eliminar=itemView.findViewById(R.id.btnEliminar);
        }
    }

    private class Eliminar extends AsyncTask<Void,Void,Void> {
        String did="";
        String dname="";
        String daddress="";
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://172.20.8.25/datos1/borrar_alumno.php");
                Log.d("url",url.toString());
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idalumno",did);
                jsonObject.put("nombre",dname);
                jsonObject.put("direccion",daddress);
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
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }
}
