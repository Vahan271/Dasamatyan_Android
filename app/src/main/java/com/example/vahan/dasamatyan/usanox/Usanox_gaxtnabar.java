package com.example.vahan.dasamatyan.usanox;

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

public class Usanox_gaxtnabar extends AppCompatActivity implements View.OnClickListener {
    EditText login, password;
    Button enter;
    boolean ok = false;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usanox_gaxtnabar);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        enter = findViewById(R.id.enter);
        enter.setOnClickListener(this);
        //Haytararum enq bazai obyekt
        dbHelper = new DBHelper(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter:

                //Vercnum enq login@ u parol@ vor heto hamematenq
                String username = login.getText().toString();
                String parol = password.getText().toString();

//                Bazan sarqum enq hasaneli
                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                Bazaic stanum enq USANOX cucakic login@
                Cursor cursor = db.query("usanox", null, "login=?", new String[]{username}, null, null, null);

                int id = 0;
                if (cursor.moveToFirst()) {
//                 Stanum enq bzaic id u parol@
                    int idFromDb = cursor.getColumnIndex("id");
                    int pass = cursor.getColumnIndex("pass");

//                   Bazaic stacac parol@ hamematum enq Useri nermucac paroli het

                    if (cursor.getString(pass).equals(parol)) {
                        ok = true;

//                        Stanum enq toxi id-n vor heto poxancenq myus ejin

                        id = cursor.getInt(idFromDb);
                    } else {
                        Toast toast = Toast.makeText(this, "Սխալ մուտքանուն կամ գաղտնաբառ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(this, "Սխաալ ուտքանուն կամ գաղտնաբառ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                cursor.close();

                if (ok == true) {
                    Intent intent = new Intent(this, Usanox_anhatakan.class);
                    intent.putExtra("id_Poxancvox", id);
                    startActivity(intent);
                 } else {
                    Toast.makeText(getApplicationContext(), "Սխալ է", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
