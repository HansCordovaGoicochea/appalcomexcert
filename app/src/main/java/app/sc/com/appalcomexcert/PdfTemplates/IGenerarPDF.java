package app.sc.com.appalcomexcert.PdfTemplates;

import android.content.Context;

public interface IGenerarPDF {
    void finishGeneratedPdf(boolean z, TemplatePDF.Action action);
    Context getContext();
}
