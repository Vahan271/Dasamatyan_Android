package com.example.vahan.dasamatyan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gnahatakanner extends AppCompatActivity {

    private DBHelper dbHelper;

    private ListView gnahatakannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnahatakanner);
        dbHelper = new DBHelper(this);
        gnahatakannerList = findViewById(R.id.gnahatakannerListView);
        final Intent intent = getIntent();
        final int ashakert_id = intent.getIntExtra("ashakert_id_poxancvox", 0);
        final int ararka_id = intent.getIntExtra("ararka_id_poxancvox", 0);
        loadGnahatakanner(ashakert_id, ararka_id);
    }

    private void loadGnahatakanner(int ashakert_id, int ararka_id) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String[] args = new String[2];
        args[0] = String.valueOf(ashakert_id);
        args[1] = String.valueOf(ararka_id);
        final Cursor cursor = db.rawQuery("SELECT g.gnah as 'gnah', g.date as 'date' FROM gnahatakan g INNER JOIN dasaran_ararka da ON g.dasaran_ararka_id = da.id WHERE g.ashakert_id = ? AND da.ararka_id = ?", args);
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
}
