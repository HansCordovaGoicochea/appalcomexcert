package app.sc.com.appalcomexcert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import app.sc.com.appalcomexcert.Utils.Constantes;

public class ActividadInicio extends AppCompatActivity {

    Button btnConsultas, btnInscripciones, btnIngreso, btnEncuestas, btnSalir;
    ArrayList<Parcelable> entidades;
    ArrayList<Parcelable> empresas;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_inicio);

        Bundle extras = getIntent().getExtras();
        entidades = extras.getParcelableArrayList("entidades");
        empresas = extras.getParcelableArrayList("empresas");

        btnConsultas = findViewById(R.id.btnConsultas);
        btnInscripciones = findViewById(R.id.btnInscripciones);
        btnIngreso = findViewById(R.id.btnIngreso);
        btnEncuestas = findViewById(R.id.btnEncuestas);
        btnSalir = findViewById(R.id.btnSalir);

        btnConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(ActividadInicio.this);
                dialog.setMessage("Cargando...");
                dialog.show();

                Intent intent = new Intent(ActividadInicio.this, ActividadPrincipal.class);
//                    intent.putExtras(args);
                intent.putExtra("entidades", entidades);
                intent.putExtra("empresas", empresas);
                startActivity(intent);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });

        btnInscripciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToURL(Constantes.BASE_URL_INSCRIPCIONES);
            }
        });

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToURL(Constantes.BASE_URL_INGRESO);
            }
        });

        btnEncuestas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToURL(Constantes.BASE_URL_ENCUESTAS);
            }
        });


        // Salir de la aplicacion toobar

        final AlertDialog.Builder builder = new AlertDialog.Builder(ActividadInicio.this);
        builder
                .setIcon(R.drawable.exit)
                .setTitle("¿Seguro de salir?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);



        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.BLACK);
            }
        });



    }

    public void GoToURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Haga clic ATRÁS nuevamente para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
