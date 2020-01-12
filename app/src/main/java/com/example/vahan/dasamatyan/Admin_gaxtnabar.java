package com.example.vahan.dasamatyan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_gaxtnabar extends AppCompatActivity implements View.OnClickListener {

    EditText mutqanun, gaxtnabar;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gaxtnabar);
        mutqanun = findViewById(R.id.mutqanun);
        gaxtnabar = findViewById(R.id.gaxtnabar);
        enter = findViewById(R.id.mutqGorcel);
        enter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String login = "Admin";
        String password = "Admin";

        if (mutqanun.getText().toString().equals(login) && gaxtnabar.getText().toString().equals(password)){
            Intent intent= new Intent(this, Admin.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"Սխալ մուտքանուն կամ գաղտնաբառ",Toast.LENGTH_SHORT).show();
        }

    }
}
