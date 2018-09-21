package com.example.a06.trabalhoandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Scanner extends Fragment {


    public Scanner() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fragment scanerView = new ScanerView();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, scanerView).commit();
        return inflater.inflate(R.layout.fragment_scanner, container, false);

    }

}
