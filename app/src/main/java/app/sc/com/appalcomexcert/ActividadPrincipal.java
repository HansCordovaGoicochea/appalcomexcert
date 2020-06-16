package app.sc.com.appalcomexcert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.sc.com.appalcomexcert.Actividades.ActividadDatos;
import app.sc.com.appalcomexcert.Clases.Certificado;
import app.sc.com.appalcomexcert.Clases.Empresas;
import app.sc.com.appalcomexcert.Clases.Entidades;
import app.sc.com.appalcomexcert.Clases.Persona;
import app.sc.com.appalcomexcert.ExcelTemplate.ExportDatabaseCSVTask;
import app.sc.com.appalcomexcert.Utils.Constantes;

public class ActividadPrincipal extends AppCompatActivity {
    private static final int WRITE_EXTERNAL_STORAGE = 1;
    private static final int READ_EXTERNAL_STORAGE = 2;
    private static boolean isRationale = true;
    private static boolean isFirst = true;

    EditText tvNombres, tvApellidos, tvDNI, tvDesde, tvHasta, tvRuc;
    Button btBuscarP, btBuscarEmp, btOcultarBuscarEmp, btnBuscarPbyE, exportarExcel;
    CardView cvEmpresa;
    TextView txtJson;
    Spinner spEntidades, spEmpresas;
    ProgressDialog pd;
    RelativeLayout rlBtnMostrar, rlBtnOcultar;
    private List<Persona> personasList = new ArrayList<Persona>();

    ArrayList<Parcelable> entidades;
    ArrayList<Parcelable> empresas;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    String ruc_entidad = "";
    String ruc_empresa = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askPermissions(true);
        }

        exportarExcel = findViewById(R.id.exportExcel);
        tvRuc = findViewById(R.id.tvRuc);
        btnBuscarPbyE = findViewById(R.id.btnBuscarPbyE);
        spEntidades = findViewById(R.id.spEntidades);
        spEmpresas = findViewById(R.id.spEmpresas);
        tvNombres = findViewById(R.id.tvNombres);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvDNI = findViewById(R.id.tvDNI);
        btBuscarP = findViewById(R.id.btBuscarP);
        btBuscarEmp = findViewById(R.id.btBuscarEmp);
        btOcultarBuscarEmp = findViewById(R.id.btOcultarBuscarEmp);
        rlBtnMostrar = findViewById(R.id.rlBtnMostrar);
        rlBtnOcultar = findViewById(R.id.rlBtnOcultar);
        rlBtnOcultar.setVisibility(View.GONE);
        cvEmpresa= findViewById(R.id.buscar_empresa);
        cvEmpresa.setVisibility(View.INVISIBLE);


        btBuscarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isEmptyString(String.valueOf(tvNombres.getText()))
                    || !isEmptyString(String.valueOf(tvApellidos.getText()))
                    || !isEmptyString(String.valueOf(tvDNI.getText()))){
                    new JsonTask().execute(Constantes.BASE_URL_P+"&nombres="+tvNombres.getText()+"&apellidos="+tvApellidos.getText()+"&dni="+tvDNI.getText());
                }

            }
        });

        btBuscarEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvEmpresa.setVisibility(View.VISIBLE);
                rlBtnOcultar.setVisibility(View.VISIBLE);
                rlBtnMostrar.setVisibility(View.GONE);
            }
        });

        btOcultarBuscarEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvEmpresa.setVisibility(View.INVISIBLE);
                rlBtnMostrar.setVisibility(View.VISIBLE);
                rlBtnOcultar.setVisibility(View.GONE);
            }
        });

        //getting the datbundel from other activity incoming
//        Bundle extras = getIntent().getExtras();
//        entidades = extras.getParcelableArrayList("entidades");
//        empresas = extras.getParcelableArrayList("empresas");

        final ArrayList<Entidades> entidades = (ArrayList<Entidades>) getIntent().getSerializableExtra("entidades");
        entidades.add(new Entidades(0, "0", "Seleccione entidad"));
        final ArrayList<Empresas> empresas = (ArrayList<Empresas>) getIntent().getSerializableExtra("empresas");
        empresas.add(new Empresas(0, "0", "Seleccione empresa"));

        //fill data in spinner
        ArrayAdapter<Entidades> adapter = new ArrayAdapter<Entidades>(ActividadPrincipal.this, android.R.layout.simple_spinner_dropdown_item, entidades){
            @Override
            public boolean isEnabled(int position){
                if(position == (entidades.size()-1))
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");

                View view = super.getDropDownView(position, convertView, parent);
                CheckedTextView tv = (CheckedTextView) view;
                tv.setTypeface(font);

                if(position == (entidades.size()-1)){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }

                return view;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
                CheckedTextView v = (CheckedTextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
//                v.setTextColor(Color.RED);
                v.setTextSize(13);
                return v;
            }
        };
        spEntidades.setAdapter(adapter);
        spEntidades.setSelection(entidades.size()-1);//Optional to set the selected item.
        spEntidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Entidades selectedItemText = (Entidades) parent.getItemAtPosition(position);
//                Toast.makeText(context, "Country ID: "+country.getId()+",  Country Name : "+country.getName(), Toast.LENGTH_SHORT).show();

                // If user change the default selection
                // First item is disable and it is used for hint
                if(position != (entidades.size()-1)){
                    // Notify the selected item text
                    ruc_entidad = selectedItemText.getRuc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //fill data in spinner
        ArrayAdapter<Empresas> adapter2 = new ArrayAdapter<Empresas>(ActividadPrincipal.this, android.R.layout.simple_spinner_dropdown_item, empresas){
            @Override
            public boolean isEnabled(int position){
                if(position == (empresas.size()-1))
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");

                View view = super.getDropDownView(position, convertView, parent);
                CheckedTextView tv = (CheckedTextView) view;
                tv.setTypeface(font);

                if(position == (empresas.size()-1)){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }

                return view;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
                CheckedTextView v = (CheckedTextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
//                v.setTextColor(Color.RED);
                v.setTextSize(13);
                return v;
            }
        };
        spEmpresas.setAdapter(adapter2);
        spEmpresas.setSelection(empresas.size()-1);//Optional to set the selected item.
        spEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Empresas selectedItemText = (Empresas) parent.getItemAtPosition(position);

                // If user change the default selection
                // First item is disable and it is used for hint
                if(position != (empresas.size()-1)){
                    // Notify the selected item text

                    ruc_empresa = selectedItemText.getRuc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvDesde= findViewById(R.id.tvDesde);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tvDesde.setText(sdf.format(myCalendar.getTime()));
            }

        };
        tvDesde.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ActividadPrincipal.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvHasta= findViewById(R.id.tvHasta);
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tvHasta.setText(sdf.format(myCalendar2.getTime()));
            }

        };
        tvHasta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ActividadPrincipal.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        btnBuscarPbyE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                Date date_desde = null;
                Date date_hasta = null;
                try {
                    date_desde = fmt.parse(String.valueOf(tvDesde.getText()));
                    date_hasta = fmt.parse(String.valueOf(tvHasta.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String s_desde = "";
                String s_hasta = "";
                if (date_desde != null && date_hasta != null){
                    SimpleDateFormat fmtOut = new SimpleDateFormat(pattern);
                    s_desde = fmtOut.format(date_desde);
                    s_hasta = fmtOut.format(date_hasta);
                }



                new JsonTask().execute(Constantes.BASE_URL_E+"&nombres="+tvNombres.getText()+"&apellidos="+tvApellidos.getText()+"&dni="+tvDNI.getText()+"&entidad="+ruc_entidad+"&ruc_entidad="+tvRuc.getText()+"&ruc_empresa="+ruc_empresa+"&desde="+s_desde+"&hasta="+s_hasta);


            }
        });


        exportarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportDatabaseCSVTask task=new ExportDatabaseCSVTask(ActividadPrincipal.this);
                task.execute();
            }
        });

    }


    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ActividadPrincipal.this);
            pd.setMessage("Espere porfavor...!");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                Log.e("Response URL" , String.valueOf(params[0]));
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                //set input and output descript
                connection.setDoInput(true);
                connection.setDoOutput(false); // in needed?
                connection.connect();


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
                    Persona persona = null;
                    personasList.clear();
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jObject = jArray.getJSONObject(i);
                        int id_capacitacion = jObject.getInt("id_capacitacion");
                        String participante = jObject.getString("participante");
                        String participante_dni = jObject.getString("participante_dni");
                        String fotocheck = jObject.getString("fotocheck");

                        persona = new Persona(id_capacitacion, participante, participante_dni, fotocheck);
                        personasList.add(persona);

                    } // End Loop

                    Bundle args = new Bundle();
                    args.putSerializable("personas", (Serializable) personasList);
                    Intent intent = new Intent(ActividadPrincipal.this, ActividadDatos.class);
                    intent.putExtras(args);
                    startActivity(intent);
                    limpiar();
                } catch (JSONException e) {
                    Log.e("JSONException", "Error: " + e.toString());
                } // catch (JSONException e)
            }else{
                Toast.makeText(ActividadPrincipal.this, "No hay datos", Toast.LENGTH_SHORT).show();
            }

            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }

    public void limpiar(){
        tvNombres.setText("");
        tvApellidos.setText("");
        tvDNI.setText("");
    }

    private void askPermissions(boolean isForOpen) {
        isRationale = false;
        List permissionsRequired = new ArrayList();

        final List<String> permissionsList = new ArrayList<String>();
        if (!checkPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsRequired.add("Write External Storage");

        if (!checkPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsRequired.add("Read External Storage");


        if (permissionsList.size() > 0 && !isRationale) {
            if (permissionsRequired.size() > 0) {

            }
            if (isForOpen) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                            11);
                }
            }

        } else if (isRationale) {
            if (isForOpen) {

                new AlertDialog.Builder(this, R.style.AppTheme)
                        .setTitle("Permission Alert")
                        .setMessage("You need to grant permissions manually. Go to permission and grant all permissions.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 123);
                            }
                        })
                        .show();
            }
        }
//        else {
//            startActivity(new Intent(PermissionsActivity.this, SplashActivity.class));
//            finish();
//        }
    }


    private boolean checkPermission(List permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!isFirst) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        isRationale = true;
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
