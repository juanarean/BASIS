package com.basis.basis.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.basis.basis.BAS;
import com.basis.basis.R;

public class Main2Activity extends AppCompatActivity implements BASFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void onListFragmentInteraction(BAS item) {

    }
}
