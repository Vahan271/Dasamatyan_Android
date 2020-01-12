package com.example.vahan.dasamatyan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AshakertActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView ashakertnerList;

    private Integer dasaranId;

    private Integer ararkaId;

    private Integer dasaxosId;

    private Map<String, Integer> ashakertnerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashakert);
        ashakertnerList = findViewById(R.id.ashakertnerListView);
        final Intent intent = getIntent();
        final int dasaran_id = intent.getIntExtra("dasaran_id_poxancvox", 0);
        final int dasaxos_id = intent.getIntExtra("dasaxos_id_poxancvox", 0);
        final int ararka_id = intent.getIntExtra("ararka_id_poxancvox", 0);
        this.dasaranId = dasaran_id;
        this.dasaxosId = dasaxos_id;
        this.ararkaId = ararka_id;
        loadAshakertner(dasaran_id);

        ashakertnerList.setOnItemClickListener(this);
    }

    private void loadAshakertner(int id) {
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[1];
        args[0] = String.valueOf(id);
        Cursor cursor = db.query("usanox", null, "class_id=?", args, null, null, null);
        final List<String> ashakert = new ArrayList<>();
        while (cursor.moveToNext()) {
            int usanox_id_col_index = cursor.getColumnIndex("id");
            int usanox_name_col_index = cursor.getColumnIndex("name");
            int usanox_sname_col_index = cursor.getColumnIndex("sname");
            ashakert.add(cursor.getString(usanox_name_col_index) + " " + cursor.getString(usanox_sname_col_index));
            ashakertnerMap.put(cursor.getString(usanox_name_col_index) + " " + cursor.getString(usanox_sname_col_index), cursor.getInt(usanox_id_col_index));
        }
        showInfo(ashakert);
    }

    private void showInfo(List<String> ashakert) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ashakert);
        ashakertnerList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String selectedItem = (String) adapterView.getItemAtPosition(i);
        final Integer ashakertId = ashakertnerMap.get(selectedItem);
        goToGnahatakan(ashakertId);
    }

    private void goToGnahatakan(Integer ashakertId) {
        Intent intent = new Intent(this, GnahatelActivity.class);
        intent.putExtra("ararka_id_poxancvox", ararkaId);
        intent.putExtra("dasaxos_id_poxancvox", dasaxosId);
        intent.putExtra("ashakert_id_poxancvox", ashakertId);
        intent.putExtra("dasaran_id_poxancvox", dasaranId);
        startActivity(intent);
    }
}
