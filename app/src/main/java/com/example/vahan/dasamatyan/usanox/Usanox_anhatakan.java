package com.example.vahan.dasamatyan.usanox;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.vahan.dasamatyan.DBHelper;
import com.example.vahan.dasamatyan.Gnahatakanner;
import com.example.vahan.dasamatyan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Usanox_anhatakan extends AppCompatActivity implements View.OnClickListener {
    EditText anun, azganun;
    private ListView usanoxArarkanerListView;
    DBHelper dbHelper;
    private Integer ashakertId;
    private final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usanox_anhatakan);
        usanoxArarkanerListView = findViewById(R.id.usanox_ararkaner_list);
        anun = findViewById(R.id.anun_usanox);
        azganun = findViewById(R.id.azganun_usanox);
        Intent intent = getIntent();
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        48rd toxi id-n poxancvi hajord activity
        final int id = intent.getIntExtra("id_Poxancvox", 0);
        ashakertId = id;
        final String idTemp = String.valueOf(id);

        Cursor cursor = db.query("usanox", null, "id=?", new String[]{idTemp}, null, null, null);
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex("name");
            int snameColIndex = cursor.getColumnIndex("sname");

            anun.setText(cursor.getString(nameColIndex));
            azganun.setText(cursor.getString(snameColIndex));
        }
//       Stexic
        cursor = db.rawQuery("SELECT a.id as 'id', a.anun AS 'anun' from ararka a INNER JOIN dasaran_ararka da ON a.id = da.ararka_id INNER JOIN usanox u on da.dasaran_id = u.class_id WHERE u.id = ?", new String[]{idTemp});
        final Map<String, Integer> ararkaner = new HashMap<>();
        while (cursor.moveToNext()) {
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("anun");
            ararkaner.put(cursor.getString(nameColIndex), cursor.getInt(idColIndex));
            Log.d(LOG_TAG, "ararka id. ID = " + cursor.getString(idColIndex));
            Log.d(LOG_TAG, "ararka name. name = " + cursor.getString(nameColIndex));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>(ararkaner.keySet()));
        usanoxArarkanerListView.setAdapter(adapter);


        usanoxArarkanerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selectedItem = (String) adapterView.getItemAtPosition(i);
                final Integer ararkaId = ararkaner.get(selectedItem);
                goToGnahatakanner(ararkaId);
            }
        });
// Minjev stex
        dbHelper.close();

    }

    private void goToGnahatakanner(Integer ararkaId) {
        final Intent intent = new Intent(this, Gnahatakanner.class);
        intent.putExtra("ashakert_id_poxancvox", ashakertId);
        intent.putExtra("ararka_id_poxancvox", ararkaId);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(this, Gnahatakanner.class);
        intent.putExtra("ashakert_id_poxancvox", ashakertId);
        startActivity(intent);
    }
}
