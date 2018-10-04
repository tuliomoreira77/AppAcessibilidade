package com.example.a06.trabalhoandroid.fragmentos;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.a06.trabalhoandroid.R;
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
    ProgressBar imgLoad;
    FloatingActionButton shareButton;

    public Generator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_generator, container, false);
        editText = (EditText) v.findViewById(R.id.editText);

        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
            }
        });

        imageView = (ImageView) v.findViewById(R.id.imageView2);
        button = (Button) v.findViewById(R.id.button);
        imgLoad = (ProgressBar) v.findViewById(R.id.carregamento_imagem);
        imgLoad.setVisibility(View.INVISIBLE);
        shareButton = (FloatingActionButton) v.findViewById(R.id.share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(bitmap == null)
                    return;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                String bitmapPath = MediaStore.Images.Media.insertImage(
                        getActivity().getContentResolver(), bitmap,"title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,bitmapUri);
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //EditTextValue = editText.getText().toString();
                /*try {
                    //imgLoad.setVisibility(View.VISIBLE);
                    //bitmap = generateQRcode(EditTextValue);
                    //imageView.setImageBitmap(bitmap);
                    //imgLoad.setVisibility(View.GONE);
                    //desravar
                } catch (WriterException e) {
                    e.printStackTrace();
                }*/
                hideSoftKeyboard(getActivity());
                new imgTask().execute();
            }
        });
        return v;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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

    private class imgTask extends AsyncTask<Void, Integer, Integer> {

        protected void onPreExecute() {
            button.setClickable(false);
            imgLoad.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            Snackbar.make(getView(), "Gerando QRCode", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            EditTextValue = editText.getText().toString();
            try {
                bitmap = generateQRcode(EditTextValue);
                return 0;
            } catch (WriterException e) {
                e.printStackTrace();
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer i) {
            if(i == 0) {
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
            imgLoad.setVisibility(View.GONE);
            button.setClickable(true);
        }
    }
}
