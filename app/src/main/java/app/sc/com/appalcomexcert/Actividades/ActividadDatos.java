package app.sc.com.appalcomexcert.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import app.sc.com.appalcomexcert.ActividadPrincipal;
import app.sc.com.appalcomexcert.Adaptadores.RecyclerAdapterCertificados;
import app.sc.com.appalcomexcert.Adaptadores.RecyclerAdapterPersonas;
import app.sc.com.appalcomexcert.Clases.Certificado;
import app.sc.com.appalcomexcert.R;

public class ActividadDatos extends AppCompatActivity {

    RecyclerView rviewPersona;
    RecyclerAdapterPersonas recyclerAdapterPersona;
    ArrayList<Parcelable> personas;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_datos);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }


        LinearLayoutManager llm = new LinearLayoutManager(ActividadDatos.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rviewPersona = findViewById(R.id.rviewPersona);
        rviewPersona.setLayoutManager(llm);

        //getting the datbundel from other activity incoming
        Bundle extras = getIntent().getExtras();
        personas = extras.getParcelableArrayList("personas");

//        Certificado certificado = (Certificado) certificados.get(0);
//        for (int i = 0; i < certificados.size(); i++) {
//            Certificado cert = (Certificado) certificados.get(i);
//        }
        setAdapter();
    }


    private void setAdapter() {
//        Log.e("AdapterMovimientos->", "setAdapter: " + MovimientosList.size());
        recyclerAdapterPersona = new RecyclerAdapterPersonas(ActividadDatos.this, personas);
        rviewPersona.setAdapter(recyclerAdapterPersona);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
