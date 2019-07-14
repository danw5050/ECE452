package com.goose.tapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerationActivity extends AppCompatActivity {

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup Activity Views
        setContentView(R.layout.activity_qr_code_generator);
        ImageView imageView = findViewById(R.id.qrCode);

        // Get the string from activity launching intent
        String qrString = getIntent().getStringExtra("EXTRA_QR_STRING");
        if (qrString == null || qrString.isEmpty() ) {
            Log.e("QRCodeGeneration", "Failed to create QR Generation - EXTRA_QR_STRING is empty");
            return;
        }

        // Generate the QR code image
        try {
            Bitmap bitmap = generateQR(qrString);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap generateQR(String qrString) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(qrString, BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }

        // Generate the QR code image from QR data
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        // Create the bitmap to be used to the QR Code
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }
}
