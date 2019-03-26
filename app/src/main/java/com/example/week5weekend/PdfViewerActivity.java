package com.example.week5weekend;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.IOException;

public class PdfViewerActivity extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);
        try {
            pdfView.fromStream(getAssets().open("raw/aiw.pdf")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
