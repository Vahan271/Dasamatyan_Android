package com.example.vahan.dasamatyan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GnahatelActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText date;

    private EditText gnahatakan;

    private Button nshanakel;

    private ListView gnahatakannerList;

    private DBHelper dbHelper;

    private Integer dasaran_ararka_id;

    private Integer ashakertId;

    private final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnahatel);
        dbHelper = new DBHelper(this);
        date = findViewById(R.id.date);
        gnahatakan = findViewById(R.id.gnahatakan);
        nshanakel = findViewById(R.id.nshanakelBtn);
        gnahatakannerList = findViewById(R.id.tvyalArarkaiGnahatakanner);
        final Intent intent = getIntent();
        final int ararka_id = intent.getIntExtra("ararka_id_poxancvox", 0);
        final int dasaxos_id = intent.getIntExtra("dasaxos_id_poxancvox", 0);
        final int ashakert_id = intent.getIntExtra("ashakert_id_poxancvox", 0);
        final int dasaran_id = intent.getIntExtra("dasaran_id_poxancvox", 0);
        this.ashakertId = ashakert_id;
        System.out.println("xXx");
        loadGnahatakanner(ararka_id, dasaxos_id, dasaran_id, ashakert_id);
        nshanakel.setOnClickListener(this);
    }

    private void loadGnahatakanner(int ararka_id, int dasaxos_id, int dasaran_id, int ashakert_id) {
        loadDasaranArarkaId(ararka_id, dasaxos_id, dasaran_id);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String[] args = new String[2];
        args[0] = String.valueOf(dasaran_ararka_id);
        args[1] = String.valueOf(ashakert_id);
        final Cursor cursor = db.query("gnahatakan", null, "dasaran_ararka_id=? AND ashakert_id=?", args, null, null, null);
        final Map<String, String> gnahatakanner = new HashMap<>();
        while (cursor.moveToNext()) {
            final int gnahatakan_gnah_col_index = cursor.getColumnIndex("gnah");
            final int gnahatakan_date_col_index = cursor.getColumnIndex("date");
            gnahatakanner.put(cursor.getString(gnahatakan_date_col_index), cursor.getString(gnahatakan_gnah_col_index));
        }
        showInfo(gnahatakanner);
    }

    private void showInfo(Map<String, String> gnahatakanner) {
        final List<String> gnahatakanList = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : gnahatakanner.entrySet()) {
            gnahatakanList.add(stringStringEntry.getKey() + " " + stringStringEntry.getValue());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, gnahatakanList);
        gnahatakannerList.setAdapter(adapter);
    }

    private void loadDasaranArarkaId(int ararka_id, int dasaxos_id, int dasaran_id) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String[] args = new String[3];
        args[0] = String.valueOf(dasaran_id);
        args[1] = String.valueOf(ararka_id);
        args[2] = String.valueOf(dasaxos_id);
        final Cursor cursor = db.query("dasaran_ararka", null, "dasaran_id=? AND ararka_id=? AND dasaxos_id=?", args, null, null, null);
        while (cursor.moveToNext()) {
            final int dasaran_ararka_id_col_index = cursor.getColumnIndex("id");
            this.dasaran_ararka_id = cursor.getInt(dasaran_ararka_id_col_index);
        }
    }

    @Override
    public void onClick(View view) {
        final String date = this.date.getText().toString();
        final String gnahatakan = this.gnahatakan.getText().toString();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();
        cv.put("ashakert_id", ashakertId);
        cv.put("dasaran_ararka_id", dasaran_ararka_id);
        cv.put("gnah", gnahatakan);
        cv.put("date", date);
        long rowID = db.insert("gnahatakan", null, cv);
        Log.d(LOG_TAG, "row inserted, in gnahatakan. ID = " + rowID);
    }
}
