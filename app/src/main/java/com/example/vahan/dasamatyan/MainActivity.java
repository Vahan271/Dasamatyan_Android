package com.example.vahan.dasamatyan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vahan.dasamatyan.dasaxos.Dasaxos_gaxtnabar;
import com.example.vahan.dasamatyan.usanox.Usanox_gaxtnabar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button dasaxos, usanox, grancvel;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dasaxos = findViewById(R.id.dasaxos);
        dasaxos.setOnClickListener(this);
        usanox = findViewById(R.id.usanox);
        usanox.setOnClickListener(this);
        grancvel = findViewById(R.id.grancvel);
        grancvel.setOnClickListener(this);
        admin = findViewById(R.id.admin);
        admin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dasaxos:
                Intent usucich = new Intent(this, Dasaxos_gaxtnabar.class);
                startActivity(usucich);
                break;
            case R.id.usanox:
                Intent ashakert = new Intent(this, Usanox_gaxtnabar.class);
                startActivity(ashakert);
                break;
            case R.id.grancvel:
                Intent grancvel = new Intent(this, Grancvel.class);
                startActivity(grancvel);
                break;
            case R.id.admin:
                Intent admin = new Intent(this,Admin_gaxtnabar.class);
                startActivity(admin);
                break;
        }
    }

}
