package app.sc.com.appalcomexcert.PdfTemplates;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.sc.com.appalcomexcert.Clases.Certificado;
import app.sc.com.appalcomexcert.R;


public class TemplatePDF extends AsyncTask<Void, Void, Boolean> {

    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.HELVETICA, 18.0f, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    private Font fDatos = new Font(Font.FontFamily.TIMES_ROMAN, 21);
    private Font fDatos2 = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.ITALIC);


    private Action _action;
    private IGenerarPDF _ipdf;
    private Certificado _certificado;

    public enum Action {
        ViewPDF,
        SendPDF
    }

    public TemplatePDF(IGenerarPDF iGenerarPDF, Action action, Certificado certificado) {
        this._ipdf = iGenerarPDF;
        this._action = action;
        this._certificado = certificado;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            generatePDF();
            return Boolean.TRUE;
        } catch (Exception unused) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void onPostExecute(Boolean bool) {
        this._ipdf.finishGeneratedPdf(bool, this._action);
    }


    private void generatePDF() {
        openDocument();
        addMetaData("Certificado", "alcomex", "alcomex");

        try{
            //x es > ancho
            //y es > alto
            PdfContentByte cb1 = pdfWriter.getDirectContent();
            ColumnText ct1 = new ColumnText(cb1);
            ct1.setSimpleColumn(new Phrase(new Chunk(_certificado.getParticipante(), fDatos)),
                    270, 340, 750, 36, 25, Element.ALIGN_LEFT);
            ct1.go();

            setPara(pdfWriter.getDirectContent(), new Phrase("Por haber aprobado el:", fDatos2), 280, 250);
//            setPara(pdfWriter.getDirectContent(), new Phrase("Curso: "+_certificado.getCurso(), fDatos), 300, 220);
//            setPara(pdfWriter.getDirectContent(), new Phrase("Curso: PRIMEROS AUXILIOS, RESPUESTA DE EMERGENCIA Y USO DE EXTINTORES", fDatos), 300, 220);


            PdfContentByte cb = pdfWriter.getDirectContent();
            ColumnText ct = new ColumnText(cb);
            ct.setSimpleColumn(new Phrase(new Chunk("Curso: "+_certificado.getCurso()+"\n"+(int)(_certificado.getHoras_teorias()+_certificado.getHoras_practicas())+" Horas", fDatos)),
                    300, 240, 750, 36, 25, Element.ALIGN_CENTER);
            ct.go();


            closeDocument();

        }catch(Exception e){
            Log.e("generatePDF", e.toString());
        }

    }

    public void setPara(PdfContentByte canvas, Phrase p, float x, float y) {

        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, p, x, y, 0);
    }

    private void addEmptyLine(Paragraph paragraph, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            paragraph.add((Element) new Paragraph(" "));
        }
    }

    //ABRIR EL DOCUMENTO
    public void openDocument(){
        createFile();
        try{
            document  = new Document(PageSize.A4.rotate());
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
//            pdfWriter.setPageEvent(new PDFBackground());
            document.open();

            Bitmap bitmap = ((BitmapDrawable) this._ipdf.getContext().getResources().getDrawable(R.drawable.certificado)).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            Image background  = Image.getInstance(byteArrayOutputStream.toByteArray());
            // This scales the image to the page,
            // use the image's width & height if you don't want to scale.
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            pdfWriter.getDirectContentUnder()
                    .addImage(background, width, 0, 0, height, 0, 0);

        }catch(Exception e){
            Log.e("openDocument", e.toString());
        }
    }

//    creamos el archivo PDF
    private void createFile(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "alcomex");
        if (!folder.exists()) {
            folder.mkdir();
        }


        pdfFile = new File(folder, "cert.pdf");

    }

    //cerrar el documento
    public void closeDocument(){
        document.close();
    }

    //metadatos
    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    //titulos, subtitulos y fecha que se genero
    public void addTitles(String title, String subTitle, String date){
        try{
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubTitle));
            addChildP(new Paragraph("Generado: "+date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);

        }catch(Exception e){
            Log.e("addTitles", e.toString());
        }
    }

    public void addParagraph(String text){
        try{
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch(Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    //crear tabla
    public void createTable(String[]header, ArrayList<String[]>clients){

        try{
            paragraph = new Paragraph(); // creamos parrafo donde va a ir la tabla
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length); //cuantas columnas tendra la tabla
            pdfPTable.setWidthPercentage(100); //el ancho de la tabla
            paragraph.setSpacingAfter(20);//espacio antes de la tabla
            PdfPCell pdfPCell; // celdas de la tabla

            //columnas
            int indexC=0;
            while(indexC < header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < clients.size(); indexR++) {
                String[] row = clients.get(indexR); //obtenemos la fila
                //recorrer las columnas de la fila
                for (indexC = 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable); // agregar la tabla al parrafo
            document.add(paragraph); // agregamos el parrafo de la tabla al documento

        }catch(Exception e){
            Log.e("createTable", e.toString());
        }

    }

    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }


    public void appViewPDF(Activity activity){

//        dialog = ProgressDialog.show(activity, "Generando PDF", "Espere un momento...", true);

        if (pdfFile.exists()){

            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try{
//                dialog.dismiss();
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
                activity.startActivity(Intent.createChooser(intent, "Abriendo PDF..."));
            }catch(Exception e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity, "No cuentas con una aplicacion para visualizar PDF", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(activity, "Archivo no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

}

