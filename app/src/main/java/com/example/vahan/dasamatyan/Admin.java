package com.example.vahan.dasamatyan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    Button avelacnelDasaran, avelacnelArarka, kcelDasaxosin, kcelAshakertin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        avelacnelDasaran = findViewById(R.id.avelacnelDasaranBtn);
        avelacnelDasaran.setOnClickListener(this);
        avelacnelArarka = findViewById(R.id.avelacnelArarkaBtn);
        avelacnelArarka.setOnClickListener(this);
        kcelDasaxosin = findViewById(R.id.kcelDasaxosinBtn);
        kcelDasaxosin.setOnClickListener(this);
        kcelAshakertin = findViewById(R.id.kcelAshakertinBtn);
        kcelAshakertin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avelacnelDasaranBtn:
                Intent avelacnelDasaran = new Intent(this, DasaranAvelacnelActivity.class);
                startActivity(avelacnelDasaran);
                break;
            case R.id.avelacnelArarkaBtn:
                Intent avelacnelArarka = new Intent(this, ArarkaAvelacnelActivity.class);
                startActivity(avelacnelArarka);
                break;
            case R.id.kcelDasaxosinBtn:
                Intent kcelDasaxosin = new Intent(this, KcelDasaxosinActivity.class);
                startActivity(kcelDasaxosin);
                break;
            case R.id.kcelAshakertinBtn:
                Intent kcelAshakertin = new Intent(this, KcelAshakertinDasaran.class);
                startActivity(kcelAshakertin);
                break;


        }
    }
}
