package com.pdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import static com.pdfviewer.PDFEventReceiver.FLUSH_AWAY;


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
        final File file = new File(Uri.parse(url).getPath());
        PDFView.Configurator configurator = pdfView.fromFile(file)
                .enableAnnotationRendering(false)
                .defaultPage(0)
                .enableAntialiasing(true);
        if(scrollDir.equals("horizontal")) {
            configurator.swipeHorizontal(true);
        }
        configurator.load();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Intent intent = new Intent();
        intent.setAction(FLUSH_AWAY);
        sendBroadcast(intent);
    }
}