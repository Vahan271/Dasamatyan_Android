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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dasaran extends AppCompatActivity {

    private ListView dasarannerList;

    private Integer dasatu_id;

    private Integer ararka_id;

    private Map<String, Integer> dasaranner = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasaran);
        dasarannerList = findViewById(R.id.dasarannerListView);
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("ararka_id_poxancvox") && extras.containsKey("dasaxos_id_poxancvox")) {
            final int dasaxos_id = intent.getIntExtra("dasaxos_id_poxancvox", 0);
            final int ararka_id = intent.getIntExtra("ararka_id_poxancvox", 0);
            this.dasatu_id = dasaxos_id;
            this.ararka_id = ararka_id;
            loadDasaranner(dasaxos_id, ararka_id);
        } else {
            loadAllDasaranner();
        }

        dasarannerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selectedItem = (String) adapterView.getItemAtPosition(i);
                final Integer dasaranId = dasaranner.get(selectedItem);
                goToAshakert(dasaranId);
            }
        });
    }

    private void goToAshakert(Integer dasaranId) {
        Intent intent = new Intent(this, AshakertActivity.class);
        intent.putExtra("dasaran_id_poxancvox", dasaranId);
        intent.putExtra("dasaxos_id_poxancvox", dasatu_id);
        intent.putExtra("ararka_id_poxancvox", ararka_id);
        startActivity(intent);
    }

    private void loadAllDasaranner() {
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("dasaran", null, null, null, null, null, null);
        final List<String> dasarans = new ArrayList<>();
        while (cursor.moveToNext()) {
            int dasaran_col_index = cursor.getColumnIndex("dasaran");
            dasarans.add(cursor.getString(dasaran_col_index));
        }
        showInfo(dasarans);
    }

    private void loadDasaranner(int dasatu_id, int ararka_id) {
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[2];
        args[0] = String.valueOf(ararka_id);
        args[1] = String.valueOf(dasatu_id);
        Cursor cursor = db.query("dasaran_ararka", null, "ararka_id=? AND dasaxos_id=?", args, null, null, null);
        final Set<String> dasaran_ids = new HashSet<>();
        while (cursor.moveToNext()) {
            int dasaran_id_col_index = cursor.getColumnIndex("dasaran_id");
            dasaran_ids.add(cursor.getString(dasaran_id_col_index));
        }
        final String[] dasaran_ids_primitiv = new String[dasaran_ids.size()];
        int i = 0;
        for (String dasaran_id : dasaran_ids) {
            dasaran_ids_primitiv[i++] = dasaran_id;
        }
        final String query = "SELECT * FROM dasaran"
                + " WHERE id IN (" + DBHelper.makePlaceholders(dasaran_ids_primitiv.length) + ")";
        cursor = db.rawQuery(query, dasaran_ids_primitiv);
        final List<String> dasarans = new ArrayList<>();
        while (cursor.moveToNext()) {
            int dasaran_id_col_index = cursor.getColumnIndex("id");
            int dasaran_name_col_index = cursor.getColumnIndex("dasaran");
            dasarans.add(cursor.getString(dasaran_name_col_index));
            this.dasaranner.put(cursor.getString(dasaran_name_col_index), cursor.getInt(dasaran_id_col_index));
        }
        showInfo(dasarans);
    }

    private void showInfo(final List<String> dasaranner) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dasaranner);
        dasarannerList.setAdapter(adapter);
    }

}
