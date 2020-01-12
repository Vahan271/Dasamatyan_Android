package com.example.vahan.dasamatyan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Grancvel extends AppCompatActivity implements View.OnClickListener {

    EditText anun, azganun, hayranun, login, gaxtnabar, krknel_gaxtnabary, grancvel_pass;
    CheckBox checkBox_dasaxos, checkBox_usanox;
    Button grancvel;
    DBHelper dbHelper;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grancvel);

        grancvel = findViewById(R.id.grancvel);
        grancvel.setOnClickListener(this);

        grancvel_pass = findViewById(R.id.grancvel_pass);
        grancvel_pass.setOnClickListener(this);

        checkBox_dasaxos = findViewById(R.id.checkBox_dasaxos);

        checkBox_usanox = findViewById(R.id.checkBox_usanox);

        anun = findViewById(R.id.anun);

        azganun = findViewById(R.id.azganun);

        hayranun = findViewById(R.id.hayranun);

        login = findViewById(R.id.login);

        gaxtnabar = findViewById(R.id.gaxtnabar);

        krknel_gaxtnabary = findViewById(R.id.krknel_gaxtnabar);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        checkBox_dasaxos.setOnClickListener(this);
        checkBox_usanox.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String tvac_pass = "dproc";

//        Axyusaki sunyak@ ev dasht@ nshelu hamar...
        ContentValues cv = new ContentValues();

//        Toxeric stanum enq text@...
        String etAnun = anun.getText().toString();
        String etAzganun = azganun.getText().toString();
        String etHayranun = hayranun.getText().toString();
        String etLogin = login.getText().toString();
        String etGaxtnabar = gaxtnabar.getText().toString();
        String etKrknel_gaxtnabary = krknel_gaxtnabary.getText().toString();
        String etGrancvel_pass = grancvel_pass.getText().toString();


//       Mianum enq bazain...
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.grancvel:
                if (checkBox_usanox.isChecked()) {
                    if (etGrancvel_pass.equals(tvac_pass)) {
                        cv.put("name", etAnun);
                        cv.put("sname", etAzganun);
                        cv.put("hayranun", etHayranun);
                        cv.put("login", etLogin);
                        if (etGaxtnabar.equals(etKrknel_gaxtnabary)) {
                            cv.put("pass", etGaxtnabar);
                        } else {
                            Toast toast = Toast.makeText(this, "Գաղտնաբառը չի համընկնում", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        long rowID = db.insert("usanox", null, cv);
                        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                        Log.d(LOG_TAG, "--- Insert in usanox: ---");
                    }else {
                        Toast toast = Toast.makeText(this, "Հաստատության գաղտնաբառը սխալ է", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    finish();
                } else if (checkBox_dasaxos.isChecked()) {
                    if (etGrancvel_pass.equals(tvac_pass)) {
                        cv.put("name", etAnun);
                        cv.put("sname", etAzganun);
                        cv.put("hayranun", etHayranun);
                        cv.put("login", etLogin);
                        if (etGaxtnabar.equals(etKrknel_gaxtnabary)) {
                            cv.put("pass", etGaxtnabar);
                        }

                        long rowID = db.insert("dasaxos", null, cv);
                        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                        Log.d(LOG_TAG, "--- Insert in dasaxos: ---");
                    }
                    else {
                        Toast toast = Toast.makeText(this, "Հաստատության գաղտնաբառը սխալ է", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    finish();
                } else {
                    Toast toast = Toast.makeText(this, "Նշեք գրանցվողը ուսուցիչ է թե աշակերտ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                dbHelper.close();
                break;
            case R.id.checkBox_dasaxos:
                if (checkBox_usanox.isChecked()) {
                    checkBox_usanox.setChecked(false);
                }
                break;
            case R.id.checkBox_usanox:
                if (checkBox_dasaxos.isChecked()) {
                    checkBox_dasaxos.setChecked(false);
                }
                break;

        }


    }
}
