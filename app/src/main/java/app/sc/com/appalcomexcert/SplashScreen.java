package app.sc.com.appalcomexcert;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.sc.com.appalcomexcert.Clases.Empresas;
import app.sc.com.appalcomexcert.Clases.Entidades;
import app.sc.com.appalcomexcert.Utils.Constantes;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    private ArrayList<Entidades> entidadesList = new ArrayList<Entidades>();
    private ArrayList<Empresas> empresasList = new ArrayList<Empresas>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        new Timer().schedule(new TimerTask() {
//
//            public void run() {
//                if(isOnline()){
//
//                    Intent intent = new Intent(SplashScreen.this, ActividadPrincipal.class);
//                    startActivity(intent);
//                    finish();
//
//                }else{
//
////                    Intent intent = new Intent(Splash.this,ErrorActivity.class);
////                    startActivity(intent);
////                    finish();
//
//                    SplashScreen.this.runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(SplashScreen.this, "Error no hay internet", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        }, 2000);

        new JsonTaskEntidadesEmpresas().execute(Constantes.BASE_URL_S);

    }

    public boolean isOnline() {
        System.out.println("executeCommand");
        Runtime localRuntime = Runtime.getRuntime();
        try {
            int i = localRuntime.exec("/system/bin/ping -c 1 8.8.8.8")
                    .waitFor();
            System.out.println(" mExitValue " + i);
            boolean bool = false;
            if (i == 0) {
                bool = true;
            }
            return bool;
        } catch (InterruptedException localInterruptedException) {
            localInterruptedException.printStackTrace();
            System.out.println(" Exception:" + localInterruptedException);
            return false;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            System.out.println(" Exception:" + localIOException);
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    private class JsonTaskEntidadesEmpresas extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
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
                Log.e("Response Code" , String.valueOf(connection.getResponseCode()));

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
//            Log.e("Response Cosssde" , String.valueOf(result));

            if (result != null){
                //parse JSON data
                try {
                    Entidades entidades = null;
                    entidadesList.clear();
                    Empresas empresas = null;
                    empresasList.clear();
                    JSONObject response = new JSONObject(result);
                    JSONArray jArray = response.getJSONArray("entidades");
                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jObject = jArray.getJSONObject(i);
                        int id_entidad = jObject.getInt("id_entidad");
                        String ruc = jObject.getString("ruc");
                        String razon_social = jObject.getString("razon_social");

                        entidades = new Entidades(id_entidad, ruc, razon_social);
                        entidadesList.add(entidades);

                    } // End Loop

                    JSONObject response2 = new JSONObject(result);
                    JSONArray jArray2 = response2.getJSONArray("empresas");
                    for(int j=0; j < jArray2.length(); j++) {

                        JSONObject jObject = jArray2.getJSONObject(j);
                        int id_empresas = jObject.getInt("id_empresas");
                        String ruc = jObject.getString("ruc");
                        String razon_social = jObject.getString("razon_social");

                        empresas = new Empresas(id_empresas, ruc, razon_social);
                        empresasList.add(empresas);

                    } // End Loop

//                    Bundle args = new Bundle();
//                    args.putSerializable("entidades", entidadesList);
//                    args.putSerializable("empresas", empresasList);
                    Intent intent = new Intent(SplashScreen.this, ActividadPrincipal.class);
//                    intent.putExtras(args);
                    intent.putExtra("entidades", entidadesList);
                    intent.putExtra("empresas", empresasList);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    Log.e("JSONException", "Error: " + e.toString());
                } // catch (JSONException e)
            }else{
                Toast.makeText(SplashScreen.this, "No dispone de internet", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
