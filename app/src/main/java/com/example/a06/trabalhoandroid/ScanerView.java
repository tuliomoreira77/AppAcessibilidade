package com.example.a06.trabalhoandroid;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.xml.transform.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanerView extends Fragment implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    TtsData ttsdata;

    public ScanerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ttsdata = ((MainActivity)getParentFragment().getActivity()).getTtsData();
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public void handleResult(com.google.zxing.Result rawResult) {
        String s = rawResult.getText();
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        ttsdata.setData(s);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanerView.this);
            }
        }, 2000);
    }

}
