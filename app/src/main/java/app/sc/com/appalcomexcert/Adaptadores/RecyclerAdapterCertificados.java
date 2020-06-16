package app.sc.com.appalcomexcert.Adaptadores;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.sc.com.appalcomexcert.Clases.Certificado;
import app.sc.com.appalcomexcert.PdfTemplates.IGenerarPDF;
import app.sc.com.appalcomexcert.PdfTemplates.TemplatePDF;
import app.sc.com.appalcomexcert.R;


public class RecyclerAdapterCertificados extends RecyclerView.Adapter<RecyclerAdapterCertificados.ViewHolder> implements IGenerarPDF {

    private final Context context;
    private final List<Certificado> items;

    public RecyclerAdapterCertificados(Context mContext, List<Certificado> items) {
        this.context = mContext;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerAdapterCertificados.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_certificado, parent, false);



        return new ViewHolder(v);
    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterCertificados.ViewHolder holder, int position) {
        final Certificado certificado = (Certificado) items.get(position);

        holder.txtCurso.setText(certificado.getCurso());
        if (isEmptyString(certificado.getEmpresa())){
            holder.txtEmpresa.setText("NO REGISTRO EMPRESA");
        }else {
            holder.txtEmpresa.setText(certificado.getEmpresa());
        }


        String pattern = "dd 'de' MMMM yyyy";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = fmt.parse(certificado.getFecha_hora_sesion());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat(pattern);
        String fecha_mov = fmtOut.format(date);
        holder.txtFecha.setText(fecha_mov);

        holder.imgCertificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarPDF(TemplatePDF.Action.ViewPDF, certificado);
            }
        });
    }

    private ProgressDialog dialog;


    private void generarPDF(TemplatePDF.Action action, Certificado certificado) {
        if (Build.VERSION.SDK_INT >= 23) {
            checkExternalStoragePermission();
        }
        this.dialog = ProgressDialog.show(context, "Generando PDF", "Espere un momento...", true);
        new TemplatePDF(this, action, certificado).execute();
    }


    private void checkExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 225);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void finishGeneratedPdf(boolean z, TemplatePDF.Action action) {
        this.dialog.dismiss();
        if (!z) {
            Toast.makeText(context, "Error al Generar el PDF", Toast.LENGTH_SHORT).show();
        } else if (z && action == TemplatePDF.Action.ViewPDF) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder();
            sb.append("alcomex/");
            sb.append("cert"); // esta parte se puede hacer dinamica
            sb.append(".pdf");
            File file = new File(externalStorageDirectory, sb.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
                context.startActivity(Intent.createChooser(intent, "Abriendo PDF"));
            } catch (ActivityNotFoundException unused) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
            }
        }
        else if (z && action == TemplatePDF.Action.SendPDF) {
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.setType("application/pdf");
            File file2 = new File(Environment.getExternalStorageDirectory(), "alcomex");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("certificado"); // esta parte se puede hacer dinamica
            sb2.append(".pdf");
            File file3 = new File(file2, sb2.toString());
            if (!file3.exists() || !file3.canRead()) {
                Toast.makeText(context, "No se encontro el PDF", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("file://");
            sb3.append(file3);
            intent2.putExtra(Intent.EXTRA_STREAM, Uri.parse(sb3.toString()));
            context.startActivity(Intent.createChooser(intent2, "Enviando correo..."));
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    // declarar las vistas del XML
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtCurso, txtFecha, txtEmpresa;
        ImageView imgCertificado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txtCurso = itemView.findViewById(R.id.txtCurso);
            txtEmpresa = itemView.findViewById(R.id.txtEmpresa);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgCertificado = itemView.findViewById(R.id.imgCertificado);

        }

        @Override
        public void onClick(View v) {

        }
    }


}