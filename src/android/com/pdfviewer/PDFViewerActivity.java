package com.pdfviewer;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class PDFViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        final Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("fileUrl");
        final String scrollDir = intent.getStringExtra("scrollDir");

        this.setTitle(title);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        getPdf(pdfView, url);
    }


    private void getPdf(PDFView pdfView, String url) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                final ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
            }
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InputStream input = new URL(url).openStream();
                    pdfView.fromStream(input)
                            .enableAnnotationRendering(false)
                            .defaultPage(0)
                            .enableAntialiasing(true).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                final ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar1);
                spinner.setVisibility(View.GONE);
            }
        }.execute();
    }
}