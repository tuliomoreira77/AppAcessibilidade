package com.example.a06.trabalhoandroid;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;


public class ColorScaner extends Fragment {

    TextureView textureView;
    MostraCamera camera;
    FloatingActionButton tirarFoto;
    TtsData ttsdata;

    public ColorScaner() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_color_scaner, container, false);
        ttsdata = ((MainActivity)getActivity()).getTtsData();
        textureView = v.findViewById(R.id.textureView);
        camera = new MostraCamera(getContext(),getActivity(),textureView);
        tirarFoto = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        tirarFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tiraFoto();
            }
        });
        return v;
    }

    private void tiraFoto()
    {
        Bitmap image = textureView.getBitmap();
        int width= image.getWidth();
        int height= image.getHeight();
        int centerX=width/2;
        int centerY=height/2;

        int pixel = image.getPixel(centerX,centerY);
        String cor = DescobreCor.getNome(pixel);
        Toast.makeText(getContext(), cor,Toast.LENGTH_LONG).show();
        ttsdata.setData(cor);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.closeSafe();
    }
}

