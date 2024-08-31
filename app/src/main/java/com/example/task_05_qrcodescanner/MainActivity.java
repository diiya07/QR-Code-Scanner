package com.example.task_05_qrcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView qrResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrResult = findViewById(R.id.qr_result);
        Button scanButton = findViewById(R.id.scan_button);

        scanButton.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan a QR Code");
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                qrResult.setText("Cancelled");
            } else {
                String scannedData = result.getContents();
                qrResult.setText(scannedData);
                handleScannedData(scannedData);
            }
        }
    }

    private void handleScannedData(String data) {
        // Handle the scanned data
        // For example, if the data is a URL, open it in a browser
        if (data.startsWith("http://") || data.startsWith("https://")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(data));
            startActivity(browserIntent);
        } else {
            // Handle other types of data
            // For instance, show a toast or dialog
        }
    }
}