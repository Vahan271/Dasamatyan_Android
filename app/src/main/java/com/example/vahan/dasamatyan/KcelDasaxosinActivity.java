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

public class KcelDasaxosinActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner dasaranner;

    private Spinner ararkaner;

    private Spinner dasaxosner;

    private Button pahpanel;

    private DBHelper dbHelper;

    private final String LOG_TAG = "myLogs";

    private final Map<String, Integer> ararkanerMap = new HashMap<>();

    private final Map<String, Integer> dasaxosnerMap = new HashMap<>();

    private final Map<String, Integer> dasarannerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcel_dasaxosin);
        dasaranner = findViewById(R.id.dasaranner);
        ararkaner = findViewById(R.id.ararka);
        dasaxosner = findViewById(R.id.dasaxosner);
        pahpanel = findViewById(R.id.pahpanel);
        pahpanel.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        loadDasaranner();
        loadDasaxosner();
        loadArarkaner();
    }

    private void loadArarkaner() {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("ararka", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int ararka_id_col_index = cursor.getColumnIndex("id");
            int ararka_name_col_index = cursor.getColumnIndex("anun");
            ararkanerMap.put(cursor.getString(ararka_name_col_index), cursor.getInt(ararka_id_col_index));
        }
        final ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(ararkanerMap.keySet()));
        this.ararkaner.setAdapter(adapter);
    }

    private void loadDasaxosner() {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("dasaxos", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int dasaxos_id_col_index = cursor.getColumnIndex("id");
            int dasaxos_name_col_index = cursor.getColumnIndex("name");
            int dasaxos_sname_col_index = cursor.getColumnIndex("sname");
            dasaxosnerMap.put(cursor.getString(dasaxos_name_col_index) + " " + cursor.getString(dasaxos_sname_col_index), cursor.getInt(dasaxos_id_col_index));
        }
        final ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(dasaxosnerMap.keySet()));
        this.dasaxosner.setAdapter(adapter);
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
        final String ararka = (String) ararkaner.getSelectedItem();
        final String dasaran = (String) dasaranner.getSelectedItem();
        final String dasaxos = (String) dasaxosner.getSelectedItem();

        final Integer ararkaId = ararkanerMap.get(ararka);
        final Integer dasaranId = dasarannerMap.get(dasaran);
        final Integer dasaxosId = dasaxosnerMap.get(dasaxos);

        if (!stugelKaTeVoch(ararkaId, dasaranId, dasaxosId)) {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            final ContentValues cv = new ContentValues();
            cv.put("ararka_id", ararkaId);
            cv.put("dasaran_id", dasaranId);
            cv.put("dasaxos_id", dasaxosId);
            long rowID = db.insert("dasaran_ararka", null, cv);
            Log.d(LOG_TAG, "row inserted, in dasaran_ararka. ID = " + rowID);
        }
        finish();
    }

    private boolean stugelKaTeVoch(final Integer ararkaId, final Integer dasaranId, final Integer dasaxosId) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String[] args = new String[3];
        args[0] = String.valueOf(ararkaId);
        args[1] = String.valueOf(dasaranId);
        args[2] = String.valueOf(dasaxosId);
        final Cursor cursor = db.query("dasaran_ararka", null, "ararka_id=? AND dasaran_id=? AND dasaxos_id=?", args, null, null, null);
        return cursor.moveToNext();
    }
}
