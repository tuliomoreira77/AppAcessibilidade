package com.example.a06.trabalhoandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ColorScaner extends Fragment {


    public ColorScaner() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fragment scanerView = new ScanerView();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.color_lay, scanerView).commit();
        return inflater.inflate(R.layout.fragment_color_scaner, container, false);

    }

}

