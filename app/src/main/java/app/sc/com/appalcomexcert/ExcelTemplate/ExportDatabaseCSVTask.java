package app.sc.com.appalcomexcert.ExcelTemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;

//new async task for file export to csv
public class ExportDatabaseCSVTask extends AsyncTask<String, String, String> {
    private static final String TAG = "SHOW TEST --->>";
    private ProgressDialog dialog;
    private boolean memoryErr = false;
    private Context context;

    public ExportDatabaseCSVTask(Context context) {
        this.context = context;
    }

    // to show Loading dialog box
    @Override
    protected void onPreExecute() {
        this.dialog = new ProgressDialog(context);
        this.dialog.setMessage("Exportando excel...");
        this.dialog.show();
    }

    // to write process
    protected String doInBackground(final String... args) {

        File exportDir = new File(Environment.getExternalStorageDirectory(), "alcomex");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "ExcelFile.csv");
        try {

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            //data
            ArrayList<String> listdata= new ArrayList<String>();
            listdata.add("Aniket");
            listdata.add("Shinde");
            listdata.add("pune");
            listdata.add("anything@anything");

            //Headers
            String arrStr1[] = {
                    "CODIGO_INST",
                    "NOMBRE_INST",
                    "COD_CURSO",
                    "NOMBRE_CURSO",
                    "COD_TEMA",
                    "DETALLE_TEMA",
                    "FECHA SESION",
                    "NRO HORAS",
                    "TEORICAS",
                    "NRO HORAS",
                    "PRACTICAS",
                    "FOTOCHECK_INST",
                    "DNI_INST",
                    "NOMBRES",
                    "APELLIDOS",
                    "FOTOCHECK",
                    "DNI",
                    "APELLIDOS",
                    "NOMBRES",
                    "NOTA PRE TEST",
                    "NOTA POST TEST",
                    "NOTAL ORAL",
                    "¿APROBO SESION?",
                    "NOTA",
                    "CASO ESTUDIO",
                    "¿APROBO CASO EST.?",
                    "SITUACION FINAL",
                    "EMPRESA",
                    "AREA",
                    "PUESTO",
                    "EQUIPO",
                    "MARCA",
                    "MODELO",
                    "TM",
                    "OPORTUNIDAD",
                    "OBSERVACIONES",
                    "N_ REGISTRO CERTIFICADO"
            };
            csvWrite.writeNext(arrStr1);

            String arrStr[] ={ listdata.get(0), listdata.get(1), listdata.get(2), listdata.get(3) };
            csvWrite.writeNext(arrStr);

            csvWrite.close();
            return "";
        }
        catch (IOException e){
            Log.e("MainActivity", e.getMessage(), e);
            return "";
        }
    }

    // close dialog and give msg
    protected void onPostExecute(String success) {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        if (success.isEmpty()){
            Toast.makeText(context, "Exportado con exito!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Export failed!", Toast.LENGTH_SHORT).show();
        }
    }
}