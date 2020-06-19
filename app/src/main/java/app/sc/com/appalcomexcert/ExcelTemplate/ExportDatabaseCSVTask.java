package app.sc.com.appalcomexcert.ExcelTemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.sc.com.appalcomexcert.Clases.Excel;
import au.com.bytecode.opencsv.CSVWriter;

//new async task for file export to csv
public class ExportDatabaseCSVTask extends AsyncTask<String, String, String> {
    private static final String TAG = "SHOW TEST --->>";
    private final List<Excel> excelList;
    private ProgressDialog dialog;
    private IGenerarExcel _excel;

    public ExportDatabaseCSVTask(IGenerarExcel iGenerarExcel, List<Excel> excelList) {
        this._excel = iGenerarExcel;
        this.excelList = excelList;
    }

    // to show Loading dialog box
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog = new ProgressDialog(this._excel.getContext());
        this.dialog.setMessage("Exportando excel...");
        this.dialog.show();
    }

    // to write process
    protected String doInBackground(final String... args) {

        File exportDir = new File(Environment.getExternalStorageDirectory(), "alcomex");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "cap.csv");
        try {

            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            FileOutputStream os = new FileOutputStream(file);
            os.write(0xef);
            os.write(0xbb);
            os.write(0xbf);
            CSVWriter csvWrite = new CSVWriter(new OutputStreamWriter(os));

            //Headers
            String arrStr1[] = {
                    "CODIGO_INST",
                    "NOMBRE_INST",
                    "COD_CURSO",
                    "NOMBRE_CURSO",
                    "COD_TEMA",
                    "DETALLE_TEMA",
                    "FECHA SESION",
                    "NRO HORAS TEORICAS",
                    "NRO HORAS PRACTICAS",
                    "FOTOCHECK_INST",
                    "DNI_INST",
                    "INSTRUCTOR",
                    "FOTOCHECK",
                    "DNI",
                    "PARTICIPANTE",
                    "NOTA PRE TEST",
                    "NOTA POST TEST",
                    "NOTAL ORAL",
                    "¿APROBO SESION?",
                    "NOTA CASO ESTUDIO",
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
                    "Nro REGISTRO CERTIFICADO"
            };
            csvWrite.writeNext(arrStr1);

            for (int i = 0; i < excelList.size(); i++) {
                Excel excel = excelList.get(i);
                String arrStr[] ={
                        String.valueOf(excel.getId_instructor()).equals("null") ? "" : String.valueOf(excel.getId_instructor()),
                        excel.getInstructor().equals("null") ? "" : excel.getInstructor(),
                        String.valueOf(excel.getId_curso()).equals("null") ? "" : String.valueOf(excel.getId_curso()),
                        excel.getNombre_curso().equals("null") ? "" : excel.getNombre_curso(),
                        String.valueOf(excel.getId_tema()).equals("null") ? "" : String.valueOf(excel.getId_tema()),
                        excel.getDetalle_tema().equals("null") ? "" : excel.getDetalle_tema(),
                        excel.getFecha_hora_sesion().equals("null") ? "" : excel.getFecha_hora_sesion(),
                        String.valueOf(excel.getHoras_teoricas()).equals("null") ? "" : String.valueOf(excel.getHoras_teoricas()),
                        String.valueOf(excel.getHoras_practicas()).equals("null") ? "" : String.valueOf(excel.getHoras_practicas()),
                        excel.getFotocheck_inst().equals("null") ? "" : excel.getFotocheck_inst(),
                        excel.getInstructor_dni().equals("null") ? "" : excel.getInstructor_dni(),
                        excel.getInstructor().equals("null") ? "" : excel.getInstructor(),
                        excel.getFotocheck_part().equals("null") ? "" : excel.getFotocheck_part(),
                        excel.getParticipante_dni().equals("null") ? "" : excel.getParticipante_dni(),
                        excel.getParticipante().equals("null") ? "" : excel.getParticipante(),
                        String.valueOf(excel.getNota_pre_test()).equals("null") ? "" : String.valueOf(excel.getNota_pre_test()),
                        String.valueOf(excel.getNota_pos_test()).equals("null") ? "" : String.valueOf(excel.getNota_pos_test()),
                        String.valueOf(excel.getNota_oral()).equals("null") ? "" : String.valueOf(excel.getNota_oral()),
                        excel.getAprobo_sesion().equals("null") ? "" : excel.getAprobo_sesion(),
                        excel.getNota_caso_estudio().equals("null") ? "" : excel.getNota_caso_estudio(),
                        excel.getAprobo_caso_estudio().equals("null") ? "" : excel.getAprobo_caso_estudio(),
                        String.valueOf(excel.getSituacion_final()).equals("null") ? "" : String.valueOf(excel.getSituacion_final()),
                        excel.getEmpresa().equals("null") ? "" : excel.getEmpresa(),
                        excel.getArea().equals("null") ? "" : excel.getArea(),
                        excel.getCargo().equals("null") ? "" : excel.getCargo(),
                        excel.getVehiculo().equals("null") ? "" : excel.getVehiculo(),
                        excel.getMarca().equals("null") ? "" : excel.getMarca(),
                        excel.getModelo().equals("null") ? "" : excel.getModelo(),
                        excel.getTm().equals("null") ? "" : excel.getTm(),
                        excel.getOportunidad().equals("null") ? "" : excel.getOportunidad(),
                        excel.getObservacion().equals("null") ? "" : excel.getObservacion(),
                        excel.getRegistro_certificado().equals("null") ? "" : excel.getRegistro_certificado(),
                };
                csvWrite.writeNext(arrStr);
            }
//            String arrStr[] ={ listdata.get(0), listdata.get(1), listdata.get(2), listdata.get(3) };
//            csvWrite.writeNext(arrStr);

            csvWrite.close();
            return "";
        }
        catch (IOException e){
            Log.e("MainActivity", e.getMessage(), e);
            return "";
        }
    }

    // close dialog and give msg
    @Override
    protected void onPostExecute(String success) {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this._excel.finishGeneratedExcel(success);
//        if (success.isEmpty()){
//            Toast.makeText(this._excel.getContext(), "Exportado con exito!", Toast.LENGTH_SHORT).show();
//
//        }
//        else {
//            Toast.makeText(this._excel.getContext(), "Export failed!", Toast.LENGTH_SHORT).show();
//        }
    }


}