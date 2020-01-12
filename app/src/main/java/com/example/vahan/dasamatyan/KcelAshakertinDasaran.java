package com.example.vahan.dasamatyan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KcelAshakertinDasaran extends AppCompatActivity implements View.OnClickListener {


    private DBHelper dbHelper;

    private Spinner ashakertner;

    private Spinner dasaranner;

    private Button submit;

    private Map<String, Integer> ashakertnerMap = new HashMap<>();

    private Map<String, Integer> dasarannerMap = new HashMap<>();

    private final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcel_ashakertin_dasaran);
        dbHelper = new DBHelper(this);
        ashakertner = findViewById(R.id.ashakert_ashakert);
        dasaranner = findViewById(R.id.ashakert_dasaran);
        loadDasaranner();
        loadAshakertner();
        submit = findViewById(R.id.kcelAshakertin);
        submit.setOnClickListener(this);
    }


    private void loadAshakertner() {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("usanox", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int dasaxos_id_col_index = cursor.getColumnIndex("id");
            int dasaxos_name_col_index = cursor.getColumnIndex("name");
            int dasaxos_sname_col_index = cursor.getColumnIndex("sname");
            ashakertnerMap.put(cursor.getString(dasaxos_name_col_index) + " " + cursor.getString(dasaxos_sname_col_index), cursor.getInt(dasaxos_id_col_index));
        }
        final ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(ashakertnerMap.keySet()));
        this.ashakertner.setAdapter(adapter);
    }

    private void loadDasaranner() {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("dasaran", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int dasaran_id_col_index = cursor.getColumnIndex("id");
            int dasaran_hamar_col_index = cursor.getColumnIndex("dasaran");
            dasarannerMap.put(cursor.getString(dasaran_hamar_col_index), cursor.getInt(dasaran_id_col_index));
        }
        final ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(dasarannerMap.keySet()));
        this.dasaranner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        final String dasaran = (String) dasaranner.getSelectedItem();
        final String ashakert = (String) ashakertner.getSelectedItem();

        final Integer dasaranId = dasarannerMap.get(dasaran);
        final Integer ashakertId = ashakertnerMap.get(ashakert);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String[] args = new String[1];
        args[0] = String.valueOf(ashakertId);
        final ContentValues cv = new ContentValues();
        cv.put("class_id", dasaranId);
        long rowID = db.update("usanox", cv, "id=?", args);
        Log.d(LOG_TAG, "row inserted, in dasaran_ararka. ID = " + rowID);
        finish();
    }

}
