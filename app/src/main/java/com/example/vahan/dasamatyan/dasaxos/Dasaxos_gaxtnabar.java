package com.example.vahan.dasamatyan.dasaxos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vahan.dasamatyan.DBHelper;
import com.example.vahan.dasamatyan.R;
import com.example.vahan.dasamatyan.usanox.Usanox_anhatakan;

public class Dasaxos_gaxtnabar extends AppCompatActivity implements View.OnClickListener {

    EditText login, password;
    Button enter;
    DBHelper dbHelper;
    boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasaxos_gaxtnabar);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        enter = findViewById(R.id.enter);
        enter.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter:
                String userName = login.getText().toString();
                String pass = password.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("dasaxos", null, "login=?", new String[]{userName}, null, null, null);


                int id = 0;
                if (cursor.moveToFirst()) {

                    int idFromDb = cursor.getColumnIndex("id");
                    int passFromDb = cursor.getColumnIndex("pass");


                    if (cursor.getString(passFromDb).equals(pass)) {
                        ok = true;
////                        Stanum enq toxi id-n vor heto poxancenq myus ejin
                        id = cursor.getInt(idFromDb);
                    } else {
                        Toast toast = Toast.makeText(this, "Սխալ մուտքանուն կամ գաղտնաբառ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(this, "Սխալ մուտքանուն կամ գաղտնաբառ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                cursor.close();
                if (ok == true) {
                    Intent intent = new Intent(this, Dasaxos_anhatakan.class);
                    intent.putExtra("id_Poxancvox", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Սխալ է", Toast.LENGTH_SHORT).show();
                }
                break;
        }

}


}
