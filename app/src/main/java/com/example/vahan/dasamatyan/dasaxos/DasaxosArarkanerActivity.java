package com.example.vahan.dasamatyan.dasaxos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vahan.dasamatyan.DBHelper;
import com.example.vahan.dasamatyan.Dasaran;
import com.example.vahan.dasamatyan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DasaxosArarkanerActivity extends AppCompatActivity {

    private ListView ararkanerList;

    private final Map<Integer, Integer> dasaranArarkaner = new HashMap<>();

    private final Map<String, Integer> ararkanerMap = new HashMap<>();

    private Integer dasaxosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dasaxos_ararkaner);
        ararkanerList = findViewById(R.id.ararkanerListView);
        final Intent intent = getIntent();
        final int id = intent.getIntExtra("id_Poxancvox", 0);
        dasaxosId = id;
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        //region Vercnum enq tvyal dasatui ararkaneri idner@
        Cursor cursor = db.query("dasaran_ararka", null, "dasaxos_id=?", new String[]{String.valueOf(id)}, null, null, null);
        final Set<String> ararkaIds = new HashSet<>();
        //Qani vor me hatm chen whileov kfrranq vor saaaaaax idner@ vercnenq
        while (cursor.moveToNext()) {
            int id_col_index = cursor.getColumnIndex("id");
            int ararka_id_col_index = cursor.getColumnIndex("ararka_id");
            ararkaIds.add(cursor.getString(ararka_id_col_index));
            dasaranArarkaner.put(cursor.getInt(ararka_id_col_index), cursor.getInt(id_col_index));
        }
        //endregion
        //region Vercnum enq tvyal ararkaneri idenrov iranc anunner@(ararkai anunner@)
        String[] ararkaIdsPrimitivMasiv = new String[ararkaIds.size()];
        //Stex ed vercrar ararkaneri idner@ verev@ listi mej e. Sax hmi klcenq sovorakan primitiv Stringi masivi mej
        int i = 0;
        for (String ararkaId : ararkaIds) {
            ararkaIdsPrimitivMasiv[i] = ararkaId;
            i++;
        }
        //Stex el arden en verevi pes ararkaneri anunner@ kvercnenq ararkaneri idnerov(verevi pes@ aysinqn me hatm chen u while-ov cikl kfrranq)
        final String query = "SELECT * FROM ararka"
                + " WHERE id IN (" + DBHelper.makePlaceholders(ararkaIdsPrimitivMasiv.length) + ")";
        cursor = db.rawQuery(query, ararkaIdsPrimitivMasiv);
        final List<String> ararkaner = new ArrayList<>();
        while (cursor.moveToNext()) {
            int ararka_id_col_index = cursor.getColumnIndex("id");
            int ararka_name_col_index = cursor.getColumnIndex("anun");
            ararkaner.add(cursor.getString(ararka_name_col_index));
            this.ararkanerMap.put(cursor.getString(ararka_name_col_index), cursor.getInt(ararka_id_col_index));
        }
        //endregion
        //Stex el amen inch kqcenq ekrani vrai listi mej
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ararkaner);
        ararkanerList.setAdapter(adapter);

        ararkanerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selectedItem = (String) adapterView.getItemAtPosition(i);
                final Integer ararkaId = ararkanerMap.get(selectedItem);
                goToClasses(ararkaId);
            }
        });

    }

    private void goToClasses(final Integer ararkaId) {
        Intent intent = new Intent(this, Dasaran.class);
        intent.putExtra("ararka_id_poxancvox", ararkaId);
        intent.putExtra("dasaxos_id_poxancvox", dasaxosId);
        startActivity(intent);
    }


}
