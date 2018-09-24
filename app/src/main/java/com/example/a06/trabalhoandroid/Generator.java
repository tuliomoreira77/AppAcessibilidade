package com.example.a06.trabalhoandroid;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v4.content.ContextCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


/**
 * A simple {@link Fragment} subclass.
 */
public class Generator extends Fragment {

    public final static int QRcodeWidth = 500;
    EditText editText;
    String EditTextValue;
    Button button;
    Bitmap bitmap;
    ImageView imageView;

    public Generator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_generator, container, false);
        editText = (EditText) v.findViewById(R.id.editText);
        imageView = (ImageView) v.findViewById(R.id.imageView2);
        button = (Button) v.findViewById(R.id.button);
        final Bitmap img = BitmapFactory.decodeResource(v.getResources(),
                R.mipmap.gerando_codigo);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                EditTextValue = editText.getText().toString();
                try {
                    //travar
                    imageView.setImageBitmap(img);
                    bitmap = generateQRcode(EditTextValue);
                    imageView.setImageBitmap(bitmap);
                    //desravar
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    public void generatorClick(View v)
    {

    }

    Bitmap generateQRcode(String text) throws WriterException
    {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    text,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRBlack):getResources().getColor(R.color.QRWrite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);

        return bitmap;
    }
}
