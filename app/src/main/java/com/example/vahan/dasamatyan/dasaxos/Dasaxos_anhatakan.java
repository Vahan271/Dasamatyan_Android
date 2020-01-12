package com.example.vahan.dasamatyan.dasaxos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vahan.dasamatyan.DBHelper;
import com.example.vahan.dasamatyan.R;

import java.util.ArrayList;
import java.util.List;

public class Dasaxos_anhatakan extends AppCompatActivity implements View.OnClickListener {
    boolean ok = true;
    DBHelper dbHelper;
    final String LOG_TAG = "myLogs";
    EditText anun_dasaxos, azganun_dasaxos;
    Button ararkaner;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasaxos_anhatakan);
        anun_dasaxos = findViewById(R.id.anun_dasaxos);
        azganun_dasaxos = findViewById(R.id.azganun_dasaxos);
        ararkaner = findViewById(R.id.ararkaner);
        ararkaner.setOnClickListener(this);

        Intent intent = getIntent();
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        id = intent.getIntExtra("id_Poxancvox", 0);

        String idTemp = String.valueOf(id);

        Cursor cursor = db.query("dasaxos", null, "id=?", new String[]{idTemp}, null, null, null);
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex("name");
            int snameColIndex = cursor.getColumnIndex("sname");

            anun_dasaxos.setText(cursor.getString(nameColIndex));
            azganun_dasaxos.setText(cursor.getString(snameColIndex));
        }


//        Bazaic stanum enq USANOX cucakic id-ner@.

        Cursor cursor1 = db.query("usanox", null, null, null, null, null, null);

        //         Stacac id-ner@ lcnum enq List-i mej.

        List<Integer> id_usanox = new ArrayList<Integer>();
        int id = cursor1.getColumnIndex("id");
        for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {
            int rowId = cursor1.getInt(id);
            id_usanox.add(rowId);
        }



    }




    @Override
    public void onBackPressed() {

//            TODO dzel het gnalu proces@...!
        ok = false;
        Intent intent = new Intent(this, Dasaxos_gaxtnabar.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dasaxos_anhatakan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ararkaner:
                Intent intent = new Intent(this, DasaxosArarkanerActivity.class);
                intent.putExtra("id_Poxancvox", id);
                startActivity(intent);
                break;
        }
    }
}
