package app.sc.com.appalcomexcert.Adaptadores;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.sc.com.appalcomexcert.ActividadPrincipal;
import app.sc.com.appalcomexcert.Actividades.ActividadDatos;
import app.sc.com.appalcomexcert.Clases.Certificado;
import app.sc.com.appalcomexcert.Clases.Persona;
import app.sc.com.appalcomexcert.PdfTemplates.IGenerarPDF;
import app.sc.com.appalcomexcert.PdfTemplates.TemplatePDF;
import app.sc.com.appalcomexcert.R;
import app.sc.com.appalcomexcert.Utils.Constantes;


public class RecyclerAdapterPersonas extends RecyclerView.Adapter<RecyclerAdapterPersonas.ViewHolder> {

    private final Context context;
    private final ArrayList<Parcelable> items;
    private ArrayList<Integer> counter = new ArrayList<Integer>();
    private List<Certificado> certificadosList = new ArrayList<Certificado>();


    private RecyclerAdapterCertificados recyclerAdapterCertificados;
    private ArrayList<Parcelable> certificado;


    public RecyclerAdapterPersonas(Context mContext, ArrayList<Parcelable> items) {
        this.context = mContext;
        this.items = items;

        for (int i = 0; i < items.size(); i++) {
            counter.add(0);
        }
    }


    @NonNull
    @Override
    public RecyclerAdapterPersonas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_persona, parent, false);



        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterPersonas.ViewHolder holder, final int position) {
        final Persona persona = (Persona) items.get(position);

        holder.tvNombres.setText(persona.getParticipante());
        holder.tvDNI.setText("DNI: "+persona.getParticipante_dni());
        holder.tvFotocheck.setText("Fotocheck: "+persona.getFotocheck());
        holder.rviewCertificados.setLayoutManager(new LinearLayoutManager(context));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter.get(position) % 2 == 0) {
                    new JsonTask(holder).execute(Constantes.BASE_URL_P+"&certificados=1&dni="+persona.getParticipante_dni());
                    holder.rviewCertificados.setVisibility(View.VISIBLE);
                    holder.imgArrow.setImageResource(R.drawable.up);
                    holder.imgArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));

                } else {
                    holder.rviewCertificados.setVisibility(View.GONE);
                    holder.imgArrow.setImageResource(R.drawable.down);
                    holder.imgArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
                }
//
                counter.set(position, counter.get(position) + 1);


            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    // declarar las vistas del XML
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvNombres, tvDNI, tvFotocheck;
        ImageView imgArrow;
        CardView cardView;
        RecyclerView rviewCertificados;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvNombres = itemView.findViewById(R.id.tvNombres);
            tvDNI = itemView.findViewById(R.id.tvDNI);
            tvFotocheck = itemView.findViewById(R.id.tvFotocheck);
            imgArrow = itemView.findViewById(R.id.imgExpandle);
            cardView = itemView.findViewById(R.id.cardView);
            rviewCertificados = itemView.findViewById(R.id.rviewCertificados);

        }

        @Override
        public void onClick(View v) {

        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        ViewHolder holder;

        public JsonTask(ViewHolder holder) {
            this.holder = holder;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(context);
            pd.setMessage("Espere porfavor...!");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {


                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                //set input and output descript
                connection.setDoInput(true);
                connection.setDoOutput(false); // in needed?
                connection.connect();
//                Log.e("Response Code" , String.valueOf(connection.getResponseCode()));

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

//                Log.e("Response Code" , buffer.toString());

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                //parse JSON data
                try {
                    Certificado certificado = null;
                    certificadosList.clear();
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jObject = jArray.getJSONObject(i);
                        int id_capacitacion = jObject.getInt("id_capacitacion");
                        String instructor = jObject.getString("instructor");
                        String instructor_dni = jObject.getString("instructor_dni");
                        String participante = jObject.getString("participante");
                        String participante_dni = jObject.getString("participante_dni");
                        String fecha_hora_sesion = jObject.getString("fecha_hora_sesion");
                        double horas_teorias = jObject.getDouble("horas_teoricas");
                        double horas_practicas = jObject.getDouble("horas_practicas");
                        String fotocheck = jObject.getString("fotocheck");
                        String registro_certificado = jObject.getString("registro_certificado");
                        String area = jObject.getString("area");
                        String situacion_final = jObject.getString("situacion_final");
                        String curso = jObject.getString("curso");
                        String tema = jObject.getString("tema");
                        String empresa = jObject.getString("empresa");

                        certificado = new Certificado(id_capacitacion, instructor, instructor_dni, participante, participante_dni, fecha_hora_sesion, horas_teorias, horas_practicas, fotocheck, registro_certificado, area, situacion_final, curso, tema, empresa);
                        certificadosList.add(certificado);

                    } // End Loop

                    setAdapter(this.holder);

                } catch (JSONException e) {
                    Log.e("JSONException", "Error: " + e.toString());
                } // catch (JSONException e)
            }else{
                Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
            }

            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }

    private void setAdapter(final ViewHolder holder) {
//        Log.e("AdapterMovimientos->", "setAdapter: " + MovimientosList.size());
        recyclerAdapterCertificados = new RecyclerAdapterCertificados(context, certificadosList);
        holder.rviewCertificados.setAdapter(recyclerAdapterCertificados);
    }
}